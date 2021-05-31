package co.ke.mymobi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegAuthModel {
    private String realm;
    private String password;
    private String correlator;
    private Integer userRole;

    public static UserRegAuthModel transform(String realm, String password, String correlator, Integer userRole) {
        UserRegAuthModel model = new UserRegAuthModel();
        model.setCorrelator(correlator);
        model.setPassword(password);
        model.setRealm(realm);
        if (userRole != null)
            model.setUserRole(userRole);
        return model;
    }
}
