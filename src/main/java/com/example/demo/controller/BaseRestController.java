package com.example.demo.controller;

import com.example.demo.model.response.ResponseType;
import com.example.demo.model.response.Response;

public class BaseRestController {

    public <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setResponseType(ResponseType.SUCCESS);
        response.setData(data);

        return response;
    }

    public <T> Response<T> success(T data, String message) {
        ResponseType success = ResponseType.SUCCESS.clone();
        success.setMessage(message);

        Response<T> response = new Response<>();
        response.setResponseType(success);
        response.setData(data);

        return response;
    }

}
