package co.ke.mymobi.validations;

import co.ke.mymobi.annotations.EmailExists;
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

public class EmailExistsValidator implements ConstraintValidator<EmailExists, String> {

    private final Logger logger= LoggerFactory.getLogger(EmailExistsValidator.class);

    @Autowired
    private UserService usersService;


    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        LoggerFactory.getLogger("EmailExistsValidator").info("Validating  EmailExistsValidator");
        try{
            Optional<User> userOptional = usersService.findUserByEmail(email);
            if(userOptional.isPresent()){
                return false;
            }
        }catch (Exception e){
            logger.warn("error={}", e.getMessage());
        }
        return true;
    }
}
