package com.example.paginationandprojection.web.controller;

import com.example.paginationandprojection.dto.FileEntityDto;
import com.example.paginationandprojection.dto.UserEntityDto;
import com.example.paginationandprojection.service.UserService;
import com.example.paginationandprojection.utils.Pages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
        fileDto.setFileName(file.getOriginalFilename());
        fileDto.setContentType(file.getContentType());
        fileDto.setContent(file.getBytes());

    if (isFileValid(file)){
        userDto.setFileEntityDto(fileDto);
        userService.updateUserData(userDto);
        System.out.println("zapisano");
    }
    return "redirect:/account";
    }

    @GetMapping("/profile-file")
    public ResponseEntity<Resource> getUserFile(Principal principal){

        UserEntityDto userDto = userService.getUserWithDetails(principal.getName());
        log.info("Pobieranie zdjęcia profilowego dla użytkownika: {}", principal.getName());

        if (hasProfileFile(userDto)){
            log.info("Zwrócono zdjęcie profilowe użytkownika: {}", principal.getName());
            return buildFileResponse(userDto);
        } else{
            log.info("Nie zwrócono zdjęcia profilowego użytkownika: {}", principal.getName());
            return buildNoFileResponse(userDto);
        }
    }

    private ResponseEntity<Resource> buildNoFileResponse(UserEntityDto userDto){
        return ResponseEntity.noContent().build();
    }


    private ResponseEntity<Resource> buildFileResponse(UserEntityDto userDto){

        FileEntityDto fileDto = userDto.getFileEntityDto();
        ByteArrayResource content = new ByteArrayResource(fileDto.getContent());

       return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(fileDto.getContentType()))
                .header("Content-Disposition", String.format("filename=%s", fileDto.getFileName()))
                .body(content);
    }


    private Boolean hasProfileFile(UserEntityDto userDto){

        if (userDto.getProfileFileId() == null){
            return false;
        }
        return true;
    }

    private Boolean isFileValid(MultipartFile file) throws IOException {

        if (file.isEmpty()){return false;}
        if (file.getName() == null || file.getName().equals("")){ return false;}
        if (file.getContentType() == null) {return false;}
        if (file.getBytes()== null) {return false;}
        return true;
    }
}
