package co.ke.mymobi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tbl_permissions", uniqueConstraints = @UniqueConstraint(name = "UniquePermissionName", columnNames = "name"))
public class Permission extends Auditable implements Serializable {

    private static final long serialVersionUID = -5432523136901198391L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

    @JsonBackReference
    @ManyToMany(mappedBy = "permissions")
    private Collection<Role> roles;

    public Permission(String name) {
        this.name = name;
    }
}