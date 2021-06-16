package co.ke.auth.services;

import co.ke.auth.entities.User;
import co.ke.auth.models.AppResponseModel;
import co.ke.auth.models.ResetPassword;
import co.ke.auth.models.UserDto;
import co.ke.auth.utils.Status;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Status updateAttempts(String username, int attempts);

    public AppResponseModel findAll(String tenant);

    public AppResponseModel viewOne(String tenant, Integer userId);

    public AppResponseModel findOne(String tenant, Integer userId);

    public String createUpdateUsers();

    AppResponseModel createUpdateUsers(String key, UserDto userDto, Integer userType);

    public AppResponseModel unlockUserAccount(Integer userId);

    public AppResponseModel changeUserAccountStatus(Integer userId);

    public Optional<User> findUserByEmail(String email);

    Boolean enabledUser(String email);

    AppResponseModel enableUser(String username);

    Optional<User> findUserByPhoneNumber(String phoneNumber);

    ResponseEntity<AppResponseModel> forgotPassword(String email);

    ResponseEntity<AppResponseModel> verifyEmailResetPassword(ResetPassword resetPasswordModel);

    void createUserAccount(User u, List<String> permissions, String userName);
}
