package com.buyukkaya.blogappapi.blogpost.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CreateBlogRequest {

    @NonNull
    private String title;

    @NonNull
    private String body;

    @NonNull
    private String username;

}
