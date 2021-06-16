package co.ke.auth.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "accounts")
public class Account extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @JoinColumn(name = "userId", referencedColumnName = "id")
    @ManyToOne(optional = false, targetEntity = User.class)
    private User userId;

    @Column(name = "last_login")
    private Date lastLogin;

    @Column(name = "date_blocked")
    private Date dateBlocked;

    @Column(name = "failed_attempts")
    private Integer failedAttempts;

    @Column(name = "blocked")
    private boolean blocked;

    public Account(String username) {
        this.username = username;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinTable(name = "tbl_account_permissions", joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    private Collection<Permission> permissions;

}
