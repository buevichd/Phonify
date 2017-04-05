package com.expertsoft.web.cart;

public class ErrorMessage {

    private String message;
    private Long key;

    public ErrorMessage() {
    }

    public ErrorMessage(String message, Long key) {
        this.message = message;
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }
}
