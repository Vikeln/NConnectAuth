package co.ke.mymobi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tbl_tenants")
public class Tenant extends Auditable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "active_status", columnDefinition = "BOOLEAN DEFAULT true")
    private boolean status;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "client_base_url", nullable = true)
    private String clientBaseUrl;

    @Column(name = "app_key", nullable = false)
    private String appKey;
}
