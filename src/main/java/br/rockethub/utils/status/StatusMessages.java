package br.rockethub.utils.status;

public enum StatusMessages {
    USER_NOT_FOUND("User not found", 1),
    USER_NOT_ACTIVATED("User not activated", 2),

    USER_FOUND("User found", 3),
    SUCCESS("SUCCESS", 4),
    ERROR("ERROR", 5);


    public final String message;

    public final int code;

    StatusMessages(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
