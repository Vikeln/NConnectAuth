package co.ke.mymobi.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class ValidationMessage {
    private String field;
    private String message;
}
