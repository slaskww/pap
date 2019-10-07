package com.example.paginationandprojection.web.controller;

import com.example.paginationandprojection.dto.UserEntityDto;
import com.example.paginationandprojection.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {

private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String prepareRegistrationPage(Model model){

        model.addAttribute("user", new UserEntityDto());
        return "register-form";
    }

    @PostMapping
    public String processRegistrationPage(@Valid @ModelAttribute("user") UserEntityDto userDto, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            return "register-form";
        }

        bindingResult = validateRegistrationData(bindingResult, userDto);
        if (bindingResult.hasErrors()){
            return "register-form";
        }

        userService.registerUser(userDto);
        model.addAttribute("successMsg", "Rejestracja przebiegła pomyślnie!");
        return "register-result";
    }

    private BindingResult validateRegistrationData(BindingResult bindingResult, UserEntityDto userDto){

        //walidacja nazwy użytkownika
        //walidacja adresu email
        //walidacja hasła i re-hasłą
        return bindingResult;
    }
}
