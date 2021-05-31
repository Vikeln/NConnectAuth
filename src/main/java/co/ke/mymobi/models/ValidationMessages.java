package co.ke.mymobi.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data

public class ValidationMessages {
    List<ValidationMessage> messages;
}
