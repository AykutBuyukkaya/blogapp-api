package com.buyukkaya.blogappapi.blogpost.repository;

import com.buyukkaya.blogappapi.blogpost.model.entity.Blog;
import com.buyukkaya.blogappapi.user.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    boolean existsByTitle(String title);

    Optional<Blog> findByTitle(String title);

    Optional<Blog> findById(Long id);

    Page<Blog> findAllByUserEntity(UserEntity userEntity, Pageable pageable);


}
