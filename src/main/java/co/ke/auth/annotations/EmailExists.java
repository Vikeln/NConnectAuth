package co.ke.auth.annotations;

import co.ke.auth.validations.EmailExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = EmailExistsValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface EmailExists {

    String message() default "User with Similar Email already Exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}