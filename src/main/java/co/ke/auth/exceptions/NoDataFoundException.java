package co.ke.auth.exceptions;

public class NoDataFoundException  extends Exception {

    public NoDataFoundException(String s) { super(s);
    }
    public NoDataFoundException(String s, Exception ex) { super(s, ex);
    }
}
