package com.example.paginationandprojection.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "password")
public class UserEntityDto {

    private Long id;
  //  @NotBlank(message = "Pole username nie może być puste")
    @Size(min = 8, max = 12, message = "pole username powinno zawierać od 8 do 12 znaków")
    private String username;
  //  @NotBlank(message = "Pole password nie może być puste")
    @Size(min = 8, message = "pole password powinno zawierać minimum 8 znaków")
    private String password;
 //   @NotBlank(message = "Pole re-password nie może być puste")
    @Size(min = 8, message = "pole re-password powinno zawierać minimum 8 znaków")
    private String rePassword;
    @NotBlank(message = "Pole email nie może być puste")
    @Email
    private String email;
    private Boolean enabled;

    private String firstName;
    private String lastName;
    private String pesel;
    private LocalDate dateOfBirth;
}
