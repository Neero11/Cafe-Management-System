package com.inn.cafe.utils;


public record CustomErrorResponse(String message,String error,Integer code) {

    public CustomErrorResponse(String message, String error, Integer code) {
        this.message = message;
        this.error = error;
        this.code = code;
    }
}
