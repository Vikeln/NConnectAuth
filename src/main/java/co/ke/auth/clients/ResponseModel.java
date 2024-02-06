package co.ke.auth.clients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel {
    private String message;
    private Integer response_code;
    private Object data;

    private boolean status;

    public ResponseModel(boolean status) {
        this.status = status;
    }

    private HttpStatus httpStatus;
}
