/*
package com.example.springboottaskmanager.customValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import java.lang.annotation.RetentionPolicy;
import static java.lang.annotation.ElementType.FIELD;

@Target({ FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StatusValidator.class)
@Documented
public @interface StatusValid {
    String message() default "{StatusValid.invalid}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
*/