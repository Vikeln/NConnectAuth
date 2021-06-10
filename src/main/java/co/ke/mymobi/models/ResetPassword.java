package co.ke.mymobi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class            ResetPassword {
    private String userID;
    private String password;
    private Boolean verifyEmail = false;
}
