package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.validation.ValidPassword;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserUpdateDto {

    @Size(min = 3, max = 20)
    @NotBlank(message = "Le nom d'utilisateur ne peut pas être vide")
    private String username;

    @NotBlank(message = "L'email ne peut pas être vide")
    @Email(message = "L'email doit être valide")
    private String email;

    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    @ValidPassword
    private String password;
}
