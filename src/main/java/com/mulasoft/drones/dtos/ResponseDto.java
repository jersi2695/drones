package com.mulasoft.drones.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto<T> {

    public T data;
    public String message;
}