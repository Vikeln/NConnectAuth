package co.ke.auth.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkMail {
    private Mail mail;
    private String contact="";
    private Integer entity;
    private String entityType;
}
