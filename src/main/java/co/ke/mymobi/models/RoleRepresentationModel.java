package co.ke.mymobi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRepresentationModel {
    private String name;

    public static RoleRepresentationModel transform(String name){
        RoleRepresentationModel model = new RoleRepresentationModel();
        model.setName(name);
        return model;
    }
}
