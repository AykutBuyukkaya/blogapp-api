package com.buyukkaya.blogappapi.blogpost.model.entity;

import com.buyukkaya.blogappapi.user.model.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "title", unique = true)
    @Size(min = 5, max = 16)
    private String title;

    @NonNull
    @Column(name = "body")
    private String body;

    @NonNull
    @Column(name = "created_at")
    private String createdAt;

    @ManyToOne
    private UserEntity userEntity;

}
