package com.luis.proyectoaplicacionmovilv3.models;

public class HttpErrorResponseCustom {
    private int statusCode;
    private String message;
    private String error;
    private String timestamp;
    private String path;

    public HttpErrorResponseCustom(int statusCode, String message, String error, String timestamp, String path) {
        this.statusCode = statusCode;
        this.message = message;
        this.error = error;
        this.timestamp = timestamp;
        this.path = path;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
