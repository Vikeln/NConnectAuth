package co.ke.auth.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_type")
public class UserType {
    @Id
    private Integer id;
    private String name;

    public UserType(int id) {
        this.id = id;
    }
}
