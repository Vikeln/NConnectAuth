package co.ke.mymobi.validations;

import co.ke.mymobi.annotations.PhoneNumberExists;
import co.ke.mymobi.entities.User;
import co.ke.mymobi.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

/**
 * @author TMwaura on 22/10/2019
 * @Project mobiloan-customer-management
 */

public class PhoneNumberExistsValidator implements ConstraintValidator<PhoneNumberExists, String> {

    private final Logger logger= LoggerFactory.getLogger(PhoneNumberExistsValidator.class);

    @Autowired
    UserService usersService;


    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        LoggerFactory.getLogger("PhoneNumberExistsValidator").info("Validating  PhoneNumberExistsValidator");
        try{
            Optional<User> userOptional = usersService.findUserByPhoneNumber(phoneNumber);
            if(userOptional.isPresent()){
                return false;
            }
        }catch (Exception e){
            logger.warn("error={}", e.getMessage());
        }
        return true;
    }
}
