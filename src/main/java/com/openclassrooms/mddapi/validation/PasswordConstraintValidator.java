package com.openclassrooms.mddapi.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

// Classe implémentant la logique de validation pour l'annotation @ValidPassword
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }

        // Regex pour valider le mot de passe
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$");
    }
}