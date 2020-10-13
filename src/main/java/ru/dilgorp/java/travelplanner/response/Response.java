package ru.dilgorp.java.travelplanner.response;

abstract public class Response {
    protected final ResponseType type;
    protected final String message;

    public Response(ResponseType type, String message) {
        this.type = type;
        this.message = message == null ? "" : message;
    }

    public ResponseType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
