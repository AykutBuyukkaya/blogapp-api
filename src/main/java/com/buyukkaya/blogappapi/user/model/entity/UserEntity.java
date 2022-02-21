package com.buyukkaya.blogappapi.user.model.entity;

import com.buyukkaya.blogappapi.blogpost.model.entity.Blog;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity")
    @JsonIgnore
    List<Blog> posts;

    @Email
    @NonNull
    @Column(name = "email", unique = true)
    private String email;
    @NonNull
    @Column(name = "username", unique = true)
    @Size(min = 3, max = 16)
    private String username;

    @NonNull
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> role = new ArrayList<>();
    @NonNull
    @Column(name = "password")
    @JsonIgnore
    @Size(min = 8, max = 64)
    private String password;

}
