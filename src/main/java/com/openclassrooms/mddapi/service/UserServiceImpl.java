package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.UserLoginDto;
import com.openclassrooms.mddapi.dto.UserUpdateDto;
import com.openclassrooms.mddapi.token.JwtTokenProvider;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.dto.UserRegisterDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
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
        User user = User.builder().username(userRegisterDto.getUsername()).email(userRegisterDto.getEmail()).password(hashedPassword).role("ADMIN").build();

        // Sauvegarder
        User savedUser = userRepository.save(user);

        // Retourner un UserDto
        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        userDto.setUsername(savedUser.getUsername());
        userDto.setEmail(savedUser.getEmail());

        return userDto;
    }

    //------------methode login------
    @Override
    public String login(UserLoginDto userLoginDto) {
        // Trouver l'utilisateur par email ou username
        Optional<User> userOpt = userRepository.findByEmail(userLoginDto.getEmail()).or(() -> userRepository.findByUsername(userLoginDto.getEmail()));

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


    //Récupère l'utilisateur connecté à partir du token dans la requête
    public User getCurrentUser(HttpServletRequest request) {
        String token = getJwtFromRequest(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Long userId = jwtTokenProvider.getUserIdFromToken(token);
            return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        }
        return null;
    }

    //Extrait le token JWT du header Authorization
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    //--- Met à jour les informations de l'utilisateur connecté
    @Override
    public UserDto updateCurrentUser(HttpServletRequest request, UserUpdateDto userUpdateDto) {

        if (userUpdateDto.getUsername() == null || userUpdateDto.getUsername().trim().isEmpty()) {
            throw new RuntimeException("Le nom d'utilisateur ne peut pas être vide");
        }
        if (userUpdateDto.getEmail() == null || userUpdateDto.getEmail().trim().isEmpty()) {
            throw new RuntimeException("L'email ne peut pas être vide");
        }

        // Récupère l'utilisateur connecté via le token JWT
        User user = getAuthenticatedUser(request);
        boolean hasChanges = false;


        if (!userUpdateDto.getUsername().equals(user.getUsername())) {
            if (userRepository.findByUsername(userUpdateDto.getUsername()).isPresent()) {
                throw new RuntimeException("Nom d'utilisateur déjà utilisé");
            }
            user.setUsername(userUpdateDto.getUsername());
            hasChanges = true;
        }


        if (!userUpdateDto.getEmail().equals(user.getEmail())) {
            if (userRepository.findByEmail(userUpdateDto.getEmail()).isPresent()) {
                throw new RuntimeException("Email déjà utilisé");
            }
            user.setEmail(userUpdateDto.getEmail());
            hasChanges = true;
        }

        if (userUpdateDto.getPassword() != null && !userUpdateDto.getPassword().isBlank()) {
            if (!passwordEncoder.matches(userUpdateDto.getPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
                hasChanges = true;
            }
        }
        if (hasChanges) {
            user = userRepository.save(user);
        }

        // Conversion en DTO avant retour
        return UserDto.builder().id(user.getId()).username(user.getUsername()).email(user.getEmail()).build();
    }

    //Méthode interne identique à getCurrentUser — peut être fusionnée
    private User getAuthenticatedUser(HttpServletRequest request) {
        String token = getJwtFromRequest(request);
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            throw new RuntimeException("Utilisateur non authentifié");
        }

        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    //Convertit une entité User en UserDto
    private UserDto convertToDto(User user) {
        return UserDto.builder().id(user.getId()).username(user.getUsername()).email(user.getEmail()).build();
    }

}