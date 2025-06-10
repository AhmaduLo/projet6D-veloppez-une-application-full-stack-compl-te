package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.UserLoginDto;
import com.openclassrooms.mddapi.token.JwtTokenProvider;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.dto.UserRegisterDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public UserDto register(UserRegisterDto userRegisterDto) {

        // Vérifier si l'email ou le username existent déjà
        if (userRepository.findByEmail(userRegisterDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé.");
        }

        if (userRepository.findByUsername(userRegisterDto.getUsername()).isPresent()) {
            throw new RuntimeException("Nom d'utilisateur déjà utilisé.");
        }

        // Encoder le mot de passe
        String hashedPassword = passwordEncoder.encode(userRegisterDto.getPassword());

        // Créer un utilisateur
        User user = User.builder().username(userRegisterDto.getUsername()).email(userRegisterDto.getEmail()).password(hashedPassword).build();

        // Sauvegarder
        User savedUser = userRepository.save(user);

        // Retourner un UserDto
        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        userDto.setUsername(savedUser.getUsername());
        userDto.setEmail(savedUser.getEmail());

        return userDto;
    }

    public String login(UserLoginDto userLoginDto) {
        // Trouver l'utilisateur par email ou username
        Optional<User> userOpt = userRepository.findByEmail(userLoginDto.getEmail())
                .or(() -> userRepository.findByUsername(userLoginDto.getEmail()));

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Email/Username ou mot de passe incorrect");
        }

        User user = userOpt.get();

        // Vérifier le mot de passe
        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Email/Username ou mot de passe incorrect");
        }

        // Générer et retourner le token JWT
        return jwtTokenProvider.generateToken(user);

    }


}