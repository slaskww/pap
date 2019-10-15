package com.example.paginationandprojection.web.controller;

import com.example.paginationandprojection.dto.FileEntityDto;
import com.example.paginationandprojection.dto.UserEntityDto;
import com.example.paginationandprojection.service.UserService;
import com.example.paginationandprojection.utils.Pages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
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
    public String prepareUserAccountPage(Model model, Principal principal){
        log.info("Zalogowany użytkownik: {}", principal.getName());
        UserEntityDto user = userService.getUserWithDetails(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("hasProfileFile", hasProfileFile(user));
        return Pages.User.ACCOUNT;
    }

    @PostMapping(params = {"edit"})
    public String editUserData(Model model, Principal principal){

        UserEntityDto user = userService.getUserWithDetails(principal.getName());
        log.info("Edycja danych uzytkownika: {}", user.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        model.addAttribute("hasProfileFile", hasProfileFile(user));
        return Pages.User.ACCOUNT;
    }

    @PostMapping(params = {"save"})
    public String saveUserData(@Valid @ModelAttribute("user") UserEntityDto user, BindingResult bindingResult, Model model){
        log.info("Zmiana danych uzytkownika: {}", user.getUsername());

        if (bindingResult.hasErrors()){
            model.addAttribute("edit", true);
            model.addAttribute("hasProfileFile", hasProfileFile(user));
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


    @PostMapping(params = {"upload"})
    public String uploadFile(@RequestParam MultipartFile file, Principal principal) throws IOException {

    UserEntityDto userDto = userService.getUserWithDetails(principal.getName());
    log.info("Dodanie pliku do konta użytkownika {}", principal.getName());

        FileEntityDto fileDto = new FileEntityDto();
        fileDto.setFileName(file.getName());
        fileDto.setContentType(file.getContentType());
        fileDto.setContent(file.getBytes());

    if (isFileValid(fileDto)){
        userDto.setFileEntityDto(fileDto);
        userService.updateUserData(userDto);
        System.out.println("zapisano");
    }
    return "redirect:/account";
    }


    private Boolean hasProfileFile(UserEntityDto userDto){

        if (userDto.getProfileFileId() == null){
            return false;
        }
        return true;
    }

    private Boolean isFileValid(FileEntityDto fileDto){

        if (fileDto.getFileName() == null || fileDto.getFileName().equals("")){ return false;}
        if (fileDto.getContentType() == null) {return false;}
        if (fileDto.getContent() == null){return false;}
        return true;
    }
}
