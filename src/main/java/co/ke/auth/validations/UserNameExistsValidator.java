package co.ke.auth.validations;

import co.ke.auth.entities.User;
import co.ke.auth.repositories.UserDao;
import co.ke.auth.services.UserService;
import co.ke.auth.services.impl.UserServiceImpl;
import co.ke.auth.annotations.UserName;
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

public class UserNameExistsValidator implements ConstraintValidator<UserName, String> {

    private final Logger logger= LoggerFactory.getLogger(UserNameExistsValidator.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    UserService usersService;


    @Override
    public boolean isValid(String userName, ConstraintValidatorContext constraintValidatorContext) {
        LoggerFactory.getLogger("UserNameExistsValidator").info("Validating  Username");
        try{
            Optional<User> userOptional = UserServiceImpl.findUserByUserName(userDao, userName);
            if(userOptional.isPresent()){
                return false;
            }
        }catch (Exception e){
            logger.warn("error={}", e.getMessage());
        }
        return true;
    }
}
