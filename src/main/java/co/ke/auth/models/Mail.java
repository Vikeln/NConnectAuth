package co.ke.auth.models;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mail {

    private String templateName;
    private String portalName;

    private String from;
    private String to;
    private String subject;

    private Map<String, Object> model;

    private List<ItemCode> attachments;
}
