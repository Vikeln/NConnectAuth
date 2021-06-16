package co.ke.auth.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_users", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueUserNameConstraint", columnNames = "user_name"),
        @UniqueConstraint(name = "UniqueEmailConstraint", columnNames = "email")})
public class User extends Auditable implements Serializable {
    private static final long serialVersionUID = 8819401178958465483L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name", unique = true)
    private String userName;

    @Length(max = 50, min = 3, message = "First Name should have min of 3 and max  of  50 Characters")
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "other_names")
    private String otherName;

    @Email
    @Column(name = "email", unique = true)
    private String email;

    private boolean enabled = false;

    @Column(name = "is_super_user")
    private boolean superUser = false;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tenant", referencedColumnName = "id")
    private Tenant tenant;

    @ManyToOne(optional = false)
    private Role role;

    @JoinColumn(name = "userType", referencedColumnName = "id")
    @ManyToOne(optional = false, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private UserType userType;

    private String correlator;

}
