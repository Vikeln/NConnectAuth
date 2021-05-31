package co.ke.mymobi.entities;


import co.ke.mymobi.utils.IpAddressGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

    @CreatedBy
    protected String createdBy;

    @CreatedDate
    @Temporal(TIMESTAMP)
    @Column(name ="date_time_created", updatable = false)
    private Date dateTimeCreated;

    @LastModifiedBy
    @Column(name ="last_modified_by")
    private String lastModifiedBy;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    @Column(name ="last_modified_date")
    private Date lastModifiedDate;

    @GeneratorType(type = IpAddressGenerator.class, when = GenerationTime.ALWAYS)
    @Column(name ="ip_address")
    private String ipAddress;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    @JsonIgnore
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @JsonProperty
    public Date getDateTimeCreated() {
        return dateTimeCreated;
    }

    @JsonIgnore
    public void setDateTimeCreated(Date dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    @JsonIgnore
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }


    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    @JsonIgnore
    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
