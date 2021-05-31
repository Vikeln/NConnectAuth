package co.ke.mymobi.security;

import co.ke.mymobi.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonModel {
    private Integer id;
    private String firstName;
    private String lastName;
    private String emailAddress;

    public static CommonModel transform(User user) {
        CommonModel model = doTransform(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName());
        return model;
    }

    public static CommonModel doTransform(Integer id, String emailAddress, String firstName, String lastName) {
        CommonModel model = new CommonModel();
        model.setId(id);
        model.setEmailAddress(emailAddress);
        model.setFirstName(firstName);
        model.setLastName(lastName);
        return model;
    }
}