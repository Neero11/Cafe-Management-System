package com.inn.cafe.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CafeUtils {
    private CafeUtils(){

    }
    public static ResponseEntity<String > getResponseEntity(String responseMessage, HttpStatus httpStatus){
//        return new ResponseEntity<String>("{\"message\":\"Something went Wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<String>("{\"message\":\""+responseMessage+"\"}", httpStatus);
    }

}
