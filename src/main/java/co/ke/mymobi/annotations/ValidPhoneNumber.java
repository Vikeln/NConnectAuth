package co.ke.mymobi.annotations;

import co.ke.mymobi.validations.PhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author TMwaura on 22/10/2019
 * @Project mobiloan-customer-management
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface ValidPhoneNumber {

    String message() default "Not Valid PhoneNumber";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
