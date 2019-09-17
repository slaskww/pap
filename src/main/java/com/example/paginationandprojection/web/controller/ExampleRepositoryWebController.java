package com.example.paginationandprojection.web.controller;

import com.example.paginationandprojection.model.entity.UserEntity;
import com.example.paginationandprojection.model.entity.projections.Username;
import com.example.paginationandprojection.model.entity.projections.UsernameWithEmail;
import com.example.paginationandprojection.model.entity.projections.UsernameWithEmailWithFirstNameWithLastName;
import com.example.paginationandprojection.model.entity.projections.UzytkownikZNazwaRoli;
import com.example.paginationandprojection.model.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//Kontroler do testów repozytorium UserRepository
@Controller
@Slf4j
@RequestMapping("/examples/repositories")
public class ExampleRepositoryWebController {

    private final UserRepository userRepository;


    public ExampleRepositoryWebController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping("/simple")
    @ResponseBody
    public String testSimpleQueries() {
        log.info("--- Wszyscy użytkownicy ---");
        userRepository.findAll().forEach(System.out::println);
        log.info("--- Aktywni użytkownicy ---");
        userRepository.findAllByEnabledIsTrue().forEach(System.out::println);
        log.info("--- Wszyscy nieaktywni użytkownicy ---");
        userRepository.findAllByEnabledIsFalse().forEach(System.out::println);
        log.info("--- Wszyscy użytkownicy w roli USER ---");
        userRepository.findDistinctAllByRoles_RoleNameIn(
                new HashSet<>(
                        Arrays.asList("ROLE_USER")))
                .forEach(System.out::println);
        log.info("--- Wszyscy użytkownicy w roli MANAGER lub ADMIN ---");
        userRepository.findDistinctAllByRoles_RoleNameIn(
                new HashSet<>(
                        Arrays.asList("ROLE_MANAGER","ROLE_ADMIN")))
                .forEach(System.out::println);

        return "Zakończone";
    }

    @GetMapping("/advanced")
    @ResponseBody
    public String testAdvancedQueries() {
        log.info("--- Lista najnowszych 100 użytkowników ---");
        userRepository.findFirst100ByOrderByCreateOnDesc().forEach(System.out::println);

        log.info("--- Lista najnowszych 100 użytkowników (zapytanie natywne) ---");
        userRepository.findLast100Users().forEach(System.out::println);

        return "Zakończone";
    }

    @GetMapping("/built-in-pagination")
    @ResponseBody
    public String testBuildInPagination() {
        long usersCount = userRepository.count();
        System.out.println("--- Wszystkich użytkowników: " + usersCount);

        int pageSize = 20;
        int pageCount = (int) (usersCount / 20);

        IntStream.rangeClosed(0, pageCount)
                .mapToObj(page -> PageRequest.of(page, pageSize))
                .map(pageable -> userRepository.findAll(pageable))
                .map(userEntities -> userEntities.getContent())
                .forEach(o -> System.out.println(o));

        return "Zakończone";
    }

    @GetMapping("/custom-pagination")
    @ResponseBody
    public String testCustomPagination() {
        long usersCount = userRepository.count();
        System.out.println("--- Wszystkich użytkowników: " + usersCount);

        int pageSize = 20;
        int pageCount = (int) (usersCount / 20);

        Set<String> roles = Set.of("ROLE_USER");

        IntStream.rangeClosed(0, pageCount)
                .mapToObj(pageNumber -> PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "username")))
                .map(pageRequest -> userRepository.findAllByRoles_RoleNameIn(roles, pageRequest))
                .forEach(o1 -> System.out.println(o1));

        IntStream.rangeClosed(0, pageCount)
                .mapToObj(pageNumber -> PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "enabled", "email")))
                .map(pageRequest -> userRepository.findAllWithDetailsByRoles_RoleNameIn(roles, pageRequest))
                .forEach(o -> System.out.println(o));


        return "Zakończone";
    }


    @GetMapping("/users")
    public String displayListOfUsersAsPages(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size){

        int currentPage = page.orElse(0);
        int currentSize = size.orElse(5);

        Set<String> roles = Set.of("ROLE_USER");
        Page<UserEntity> users = userRepository.findAllUsersWithDetailsByRoles_RoleNameIn(roles, PageRequest.of(currentPage, currentSize));
        model.addAttribute("users", users);

        int totalPages = users.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.range(0, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "pagination";
    }

    @GetMapping("/projections")
    @ResponseBody
    public String testProjections() {
        System.out.println("--- Nazwy użytkowników ---");
        userRepository.findAllBy(Username.class)
                .stream()
                .map(username -> username.getUsername())
                .forEach(s1 -> System.out.println(s1));

        System.out.println("--- Nazwy użytkowników z emailami ---");
        userRepository.findAllBy(UsernameWithEmail.class)
                .stream()
                .map(proj -> String.format("Usernanem: %s, email: %s",
                        proj.getUsername(), proj.getEmail()))
                .forEach(s1 -> System.out.println(s1));

        System.out.println("--- Nazwy użytkowników z danymi szczegółówymi ---");
        userRepository.findAllBy(UsernameWithEmailWithFirstNameWithLastName.class)
                .stream()
                .map(proj -> {
                    if (proj.getDetails() != null) {
                        return String.format("Username: %s, email: %s, firstName: %s, lastName: %s",
                                proj.getUsername(), proj.getEmail(),
                                proj.getDetails().getFirstName(),
                                proj.getDetails().getLastName());
                    }
                    else {
                        return String.format("Username: %s, email: %s",
                                proj.getUsername(), proj.getEmail());
                    }
                })
                .forEach(System.out::println);

        System.out.println("--- Nazwy użytkowników z rolami ---");
        userRepository.findAllBy(UzytkownikZNazwaRoli.class)
                .stream()
                .map(proj -> {
                    return String.format("Username: %s, roles: %s",
                            proj.getUsername(),
                            proj.getRoles()
                                    .stream()
                                    .map(roleName -> roleName.getRoleName())
                                    .collect(Collectors.toSet()));
                })
                .forEach(s -> System.out.println(s));

        return "Zakończono";
    }
}
