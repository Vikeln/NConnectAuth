package co.ke.mymobi.annotations;

import co.ke.mymobi.validations.PhoneNumberExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PhoneNumberExistsValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface PhoneNumberExists {

    String message() default "User with Similar PhoneNumber already Exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}