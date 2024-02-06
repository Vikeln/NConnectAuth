package co.ke.auth.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageModel {

    private String contact;
    private Integer entity;
    private String senderId;
    private String entityType;

    private String text;

    private String templateName;

    private Map<String,Object> model;
}
