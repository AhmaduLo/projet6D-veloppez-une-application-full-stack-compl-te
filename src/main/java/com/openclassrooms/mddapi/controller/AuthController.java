package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.dto.UserLoginDto;
import com.openclassrooms.mddapi.dto.UserRegisterDto;
import com.openclassrooms.mddapi.dto.UserUpdateDto;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.service.UserService;
import com.openclassrooms.mddapi.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public AuthController(UserService userService, UserServiceImpl userServiceImpl) {
        this.userService = userService;
        this.userServiceImpl = userServiceImpl;
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

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(HttpServletRequest request) {
        User user = userServiceImpl.getCurrentUser(request);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Convertit l'entité User en DTO
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());

        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/update/me")
    public ResponseEntity<?> updateCurrentUser(
            HttpServletRequest request,
            @Valid @RequestBody UserUpdateDto userUpdateDto,
            BindingResult bindingResult) {

        // Gestion des erreurs de validation
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        // Tente la mise à jour via le service
        try {
            UserDto updatedUser = userService.updateCurrentUser(request, userUpdateDto);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Mise à jour réussie");
           // response.put("user", updatedUser);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
