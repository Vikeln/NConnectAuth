package co.ke.auth.exceptions;

public class DataUnMarshalingException extends Exception {

    public DataUnMarshalingException(String s) {
        super(s);
    }

    public DataUnMarshalingException(String s, Exception ex) {
        super(s, ex);
    }
}
