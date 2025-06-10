package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.UserLoginDto;
import com.openclassrooms.mddapi.dto.UserRegisterDto;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(
            @Valid @RequestBody UserRegisterDto userRegisterDto,
            BindingResult bindingResult) {

        // Si des erreurs de validation sont détectées
        if (bindingResult.hasErrors()) {
            // Construction d'une map des erreurs par champ
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            // Tentative d'enregistrement de l'utilisateur via le service
            userService.register(userRegisterDto);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Inscription réussie");

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getMessage());

            return ResponseEntity.badRequest().body(error);
        } catch (Exception ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "User avec cette email existe déja");

            return ResponseEntity.status(500).body(error);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {
        try {
            String token = String.valueOf(userService.login(userLoginDto));
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
