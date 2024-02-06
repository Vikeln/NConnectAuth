package co.ke.auth.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkSMSDataModel {
    private String createdBy;
    private Integer client;
    private List<MessageModel> messages;
}
