package com.example.paginationandprojection.web.controller;

import com.example.paginationandprojection.model.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashSet;

//Kontroler do testów repozytorium UserRepository
@Controller
@RequestMapping("/examples/repositories")
public class ExampleRepositoryWebController {

    private final UserRepository userRepository;


    public ExampleRepositoryWebController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping("/simple")
    @ResponseBody
    public String testSimpleQueries() {
        System.out.println("--- Wszyscy użytkownicy ---");
        userRepository.findAll().forEach(System.out::println);
        System.out.println("--- Aktywni użytkownicy ---");
        userRepository.findAllByEnabledIsTrue().forEach(System.out::println);
        System.out.println("--- Wszyscy nieaktywni użytkownicy ---");
        userRepository.findAllByEnabledIsFalse().forEach(System.out::println);
        System.out.println("--- Wszyscy użytkownicy w roli USER ---");
        userRepository.findDistinctAllByRoles_RoleNameIn(
                new HashSet<>(
                        Arrays.asList("ROLE_USER")))
                .forEach(System.out::println);
        System.out.println("--- Wszyscy użytkownicy w roli MANAGER lub ADMIN ---");
        userRepository.findDistinctAllByRoles_RoleNameIn(
                new HashSet<>(
                        Arrays.asList("ROLE_MANAGER","ROLE_ADMIN")))
                .forEach(System.out::println);

        return "Zakończone";
    }

    @GetMapping("/advanced")
    @ResponseBody
    public String testAdvancedQueries() {
        System.out.println("--- Lista najnowszych 100 użytkowników ---");
        userRepository.findFirst100ByOrderByCreateOnDesc().forEach(System.out::println);

        System.out.println("--- Lista najnowszych 100 użytkowników (zapytanie natywne) ---");
        userRepository.findLast100Users().forEach(System.out::println);

        return "Zakończone";
    }
}
