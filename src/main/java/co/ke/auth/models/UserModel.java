package co.ke.auth.models;

import co.ke.auth.entities.Role;
import co.ke.auth.entities.Tenant;
import co.ke.auth.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private Integer id;
    private String firstName;
    private String lastName;
    private String otherName;
    private String email;
    private boolean enabled;
    private boolean superUser;
    private String phoneNumber;
    private Tenant tenant;
    private Role role;
    private String correlator;
    private List<String> userPermissions;


    public static UserModel transform(User user, List<String> userPermissions){
        UserModel model = new UserModel();
        model.setCorrelator(user.getCorrelator());
        model.setEmail(user.getEmail());
        model.setEnabled(user.isEnabled());
        model.setFirstName(user.getFirstName());
        model.setLastName(user.getLastName());
        model.setId(user.getId());
        model.setOtherName(user.getOtherName());
        model.setPhoneNumber(user.getPhoneNumber());
        model.setRole(user.getRole());
        model.setSuperUser(user.isSuperUser());
        model.setTenant(user.getTenant());

        model.setUserPermissions(userPermissions);

        return model;
    }
}
