package com.example.demo.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response<T> {

    @JsonProperty("response_type")
    private ResponseType responseType;

    private T data;

}
