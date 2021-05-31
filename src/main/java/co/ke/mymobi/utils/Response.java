package co.ke.mymobi.utils;


public enum Response {

    SUCCESS(0, "Success"),
    USER_NOT_FOUND(1, "User not found"),
    USER_NAME_TAKEN(2, "Username has been taken"),
    ACCOUNT_BLOCKED(3, "Account is blocked"),
    ACCOUNT_NOT_ENABLED(3, "Account not enabled"),
    INVALID_PHONE_NUMBER(4, "INVALID PHONE NUMBER"),
    OPERATION_NOT_SUCCESSFUL(5, "Operation Not SUCCESSFUL ON DUMP"),
    NOT_FOUND(6, "{1} not found by the supplied id/code");

    private Status status;
    Response(final int code, final String message) {
        status = new Status(code,message);
    }

    public Status status(){
        return status;
    }
}
