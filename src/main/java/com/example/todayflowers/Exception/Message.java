package com.example.todayflowers.Exception;

import lombok.Data;

@Data
public class Message {
    private StatusEnum status;
    private Object  success;
    private Object user;

    public Message() {
        this.status = StatusEnum.BAD_REQUEST;
        this.user = null;
        this.success = null;
    }

    public enum StatusEnum {
        OK(200, "OK"),
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
