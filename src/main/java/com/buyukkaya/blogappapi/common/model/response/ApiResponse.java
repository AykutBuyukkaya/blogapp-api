package com.buyukkaya.blogappapi.common.model.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ApiResponse {

    private Object response;

    private String message;

    private HttpStatus status;


}
