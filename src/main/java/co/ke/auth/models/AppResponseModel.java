package co.ke.auth.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"status", "data", "message", "response_code"})
public class AppResponseModel {
    @JsonProperty(value = "status", defaultValue = "200")
    private String statusMessage;

    private String message;

    private Object data;

    private Integer response_code;

    @JsonIgnore
    private boolean status;

    @JsonIgnore
    private HttpStatus httpStatus;

    public AppResponseModel(boolean status) {
        this.status = status;
    }

    public AppResponseModel(){}

    public String getStatusMessage() {
        statusMessage = status ? "success" : "error";
        return statusMessage;
    }

    public void setStatus(){
        this.status = this.statusMessage.equals("success");
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
        this.status= this.statusMessage.equals("success");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getResponse_code() {
        return response_code;
    }

    public void setResponse_code(Integer response_code) {
        this.response_code = response_code;
    }

    public boolean isStatus() {
        return status;
    }



    public void setStatus(boolean status) {
        this.status = status;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}
