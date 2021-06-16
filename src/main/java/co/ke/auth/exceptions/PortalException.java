package co.ke.auth.exceptions;

/**
 * @author TMwaura on 28/05/2020
 * @Project admin-dashboard
 */
public class PortalException extends RuntimeException {

    public PortalException(String s) { super(s);
    }
    public PortalException(String s, Exception ex) { super(s, ex);
    }
}
