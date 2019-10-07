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
        } else{

            try{
              //  userService.registerUser(userDto);
                model.addAttribute("registerMsg", "Rejestracja przebiegła pomyślnie!");
            } catch(Exception ex){
                model.addAttribute("registerMsg", "Coś poszło nie tak!");

            }
        }
        return "register-result";
    }

    private BindingResult validateRegistrationData(BindingResult bindingResult, UserEntityDto userDto){

        try{
            if (userService.countByUsername(userDto.getUsername()) > 0){
                bindingResult.rejectValue("username", null, "Podana nazwa użytkownika istniej już bazie");
            }
        } catch(Exception ex){
            bindingResult.rejectValue("username", null, "Nie udało się sprawdzić nazwy użytkownika");
        }

        try {
            if (userService.countByEmail(userDto.getEmail()) > 0){
                bindingResult.rejectValue("email", null, "Podany adres email istnieje już w bazie");
            }
        } catch(Exception ex){
            bindingResult.rejectValue("email", null, "Nie udało się sprawdzić adresu email");

        }

        if (!userDto.getPassword().equals(userDto.getRePassword())){
            bindingResult.rejectValue("password", null, "Pola 'hasło' i 'powtórz hasło' różnią się");
        }

        return bindingResult;
    }
}
