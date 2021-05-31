package co.ke.mymobi.controllers;

import co.ke.mymobi.models.*;
import co.ke.mymobi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("accounts")
public class AuthController {

    @Autowired
    private UserService userService;


    @PostMapping(path = "/resetpassword")
    public ResponseEntity<AppResponseModel> resetPassword(@RequestBody ResetPassword resetPasswordModel) {
        return userService.verifyEmailResetPassword(resetPasswordModel);
    }

    @PostMapping(path = "/forgotpassword")
    public ResponseEntity<AppResponseModel> resetPassword(@RequestBody(required = true) ForgotPasswordModel forgotPasswordModel) {
        return userService.forgotPassword(forgotPasswordModel.getEmail());
    }

    @GetMapping(path = "/unlockUserAccount/{userId}")
    public ResponseEntity<AppResponseModel> unlockUserAccount(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.unlockUserAccount(userId));
    }
}
