package com.openclassrooms.mddapi.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented // Indique que cette annotation sera documentée dans la JavaDoc
@Constraint(validatedBy = PasswordConstraintValidator.class) // Précise la classe responsable de la logique de validation
@Target({ElementType.FIELD}) // Définit que cette annotation peut être appliquée uniquement aux champs
@Retention(RetentionPolicy.RUNTIME) // L'annotation sera disponible pendant l'exécution du programme
public @interface ValidPassword {
    String message() default "Le mot de passe doit contenir au moins 8 caractères, dont une majuscule, une minuscule, un chiffre et un caractère spécial.";

    // Permet de spécifier des groupes de validation
    Class<?>[] groups() default {};

    // Permet d'associer des métadonnées supplémentaires à la validation
    Class<? extends Payload>[] payload() default {};
}
