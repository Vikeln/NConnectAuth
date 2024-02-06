package co.ke.auth.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewSmsModel {
    private String contact;
    private String entityType;
    private String createdBy;
    private String text;
    private String templateName;
    private String senderId;
    private Map<String,Object> model;
    private Integer entityId;

}
