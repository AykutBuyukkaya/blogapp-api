package com.buyukkaya.blogappapi.user.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class RegisterResponse {

    @NonNull
    private String message;

}
