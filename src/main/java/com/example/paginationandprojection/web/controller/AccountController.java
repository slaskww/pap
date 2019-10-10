package com.example.paginationandprojection.web.controller;

import com.example.paginationandprojection.dto.UserEntityDto;
import com.example.paginationandprojection.service.UserService;
import com.example.paginationandprojection.utils.Pages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@Slf4j
@RequestMapping("/account")
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    String prepareUserAccountPage(Model model, Principal principal){

        log.info("Zalogowany użytkownik: {}", principal.getName());
        UserEntityDto user = userService.getUserWithDetails(principal.getName());
        model.addAttribute("user", user);
        return Pages.User.ACCOUNT;
    }

    @PostMapping(params = {"edit"})
    public String editUserData(Model model, Principal principal){

        UserEntityDto user = userService.getUserWithDetails(principal.getName());
        log.info("Edycja danych uzytkownika: {}", user.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        return Pages.User.ACCOUNT;
    }

    @PostMapping(params = {"save"})
    public String saveUserData(@Valid @ModelAttribute("user") UserEntityDto user, BindingResult bindingResult, Model model){
        log.info("Zmiana danych uzytkownika: {}", user.getUsername());

        if (bindingResult.hasErrors()){
            model.addAttribute("edit", true);
            return Pages.User.ACCOUNT;
        }
        log.info("id = {}, username = {}, email = {}, firstName = {}, lastName = {}, PESEL = {}, DateOfBirth = {}",
                user.getId(), user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getPesel(), user.getDateOfBirth());
        userService.updateUserData(user);
        return "redirect:/account";
    }

    @PostMapping(params = {"cancel"})
    public String cancelEditUserData(){
        return "redirect:/account";
    }
}
