package co.ke.mymobi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_roles", uniqueConstraints = @UniqueConstraint(name = "UniqueRoleNameConstraint", columnNames = "role_name"))
public class Role extends Auditable implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "role_name", unique = true)
    private String name;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "tenant", referencedColumnName = "id")
    private Tenant tenant;

    @ManyToMany
    @JsonBackReference
    @JoinTable(name = "tbl_roles_permissions", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    private Collection<Permission> permissions;
}
