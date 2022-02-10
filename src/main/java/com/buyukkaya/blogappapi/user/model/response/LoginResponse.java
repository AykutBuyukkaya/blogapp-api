package com.buyukkaya.blogappapi.user.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@RequiredArgsConstructor
@Builder
public class LoginResponse {

    @Nullable
    private String token;

    @NonNull
    private String message;


}
