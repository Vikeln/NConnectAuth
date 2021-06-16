package co.ke.auth.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class ValidationMessage {
    private String field;
    private String message;
}
