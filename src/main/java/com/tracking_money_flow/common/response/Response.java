package com.tracking_money_flow.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) //exclude null fields from JSON response
public class Response<T> {
    private int status;
    private String message;
    private T data;
    private Map<String, Serializable> metadata;
}
