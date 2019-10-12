package com.example.paginationandprojection.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
  //  @Size(min = 8, max = 12, message = "pole username powinno zawierać od 8 do 12 znaków")
    private String username;
  //  @NotBlank(message = "Pole password nie może być puste")
    @Size(min = 8, message = "Pole password powinno zawierać minimum 8 znaków")
    private String password;
 //   @NotBlank(message = "Pole re-password nie może być puste")
    @Size(min = 8, message = "Pole re-password powinno zawierać minimum 8 znaków")
    private String rePassword;
    @NotBlank(message = "Pole email nie może być puste")
    @Email
    private String email;
    private Boolean enabled;

    private String firstName;
    private String lastName;
    @Size(min = 11, max = 11, message = "Pole PESEL powinno zawierać dokładnie 11 cyfr")
    private String pesel;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;

    private Long profileFileId;
}
