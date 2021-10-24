package com.example.todayflowers.Exception;

import lombok.Data;

@Data
public class Message {
    private StatusEnum status;
    private boolean success;
    private Object user;

    public Message() {
        this.status = StatusEnum.BAD_REQUEST;
        this.user = null;
        this.success = false;
    }

    public enum StatusEnum {
        OK(200, "OK"),
        NO_CONTENT(204, "NO_CONTENT"),
        BAD_REQUEST(400, "BAD_REQUEST"),
        NOT_FOUND(404, "NOT_FOUND"),
        INTERNAL_SERER_ERROR(500, "INTERNAL_SERVER_ERROR");

        int statusCode;
        String code;

        StatusEnum(int statusCode, String code) {
            this.statusCode = statusCode;
            this.code = code;
        }
    }

}
