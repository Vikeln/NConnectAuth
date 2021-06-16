package co.ke.auth.validations;

import co.ke.auth.services.UserService;
import co.ke.auth.annotations.ValidPhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author TMwaura on 22/10/2019
 * @Project mobiloan-customer-management
 */
public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {
    private final String pattern = "(?:254|\\+254|0)?([27]{1}(?:(?:[0-9][0-9])|(?:0[0-8])|(4[0-1]))[0-9]{6})$";

    @Autowired
    UserService usersService;

    public boolean isValid(String obj, ConstraintValidatorContext context) {
        if (obj != null) {
            return obj.matches(pattern) ;
        } else {
            return true;
        }
    }
}
