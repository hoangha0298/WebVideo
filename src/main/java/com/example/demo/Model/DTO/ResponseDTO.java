package com.example.demo.Model.DTO;

import lombok.Getter;

@Getter
public class ResponseDTO<T> {

    private Integer code;
    private String message;
    private T data;

    public ResponseDTO setCode(Integer code) {
        this.code = code;
        return this;
    }

    public ResponseDTO setMessage(String message) {
        this.message = message;
        return this;
    }

    public ResponseDTO setData(T data) {
        this.data = data;
        return this;
    }
}
