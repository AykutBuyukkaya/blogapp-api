package com.buyukkaya.blogappapi.user.model.request;

import com.buyukkaya.blogappapi.security.util.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;

    @ValidPassword
    private String password;

    private String email;

    private String role;

}
