package com.buyukkaya.blogappapi.blogpost.service.imp;

import com.buyukkaya.blogappapi.blogpost.exception.BlogDoesNotFoundException;
import com.buyukkaya.blogappapi.blogpost.exception.BlogExistsException;
import com.buyukkaya.blogappapi.blogpost.model.entity.Blog;
import com.buyukkaya.blogappapi.blogpost.model.request.CreateBlogRequest;
import com.buyukkaya.blogappapi.blogpost.repository.BlogRepository;
import com.buyukkaya.blogappapi.blogpost.service.BlogService;
import com.buyukkaya.blogappapi.common.model.response.ApiResponse;
import com.buyukkaya.blogappapi.security.service.AuthenticationService;
import com.buyukkaya.blogappapi.user.model.entity.UserEntity;
import com.buyukkaya.blogappapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogServiceImp implements BlogService {

    private final BlogRepository blogRepository;
    private final UserService userService;
    private final AuthenticationService authenticationService;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");


    @Override
    public ApiResponse createBlog(CreateBlogRequest request) {

        try {

            authenticationService.checkUserAuthentication(request.getUsername());

            if (blogRepository.existsByTitle(request.getTitle())) {

                throw new BlogExistsException("Blog with title " + request.getTitle() + " already exist!");

            }

            Blog blog = new Blog(request.getTitle(), request.getBody(), dateFormat.format(new Date()));
            blog.setUserEntity((UserEntity) userService.findUserByUsername(request.getUsername()).getResponse());

            blogRepository.save(blog);
            userService.addBlogToUser(request.getUsername(), blog);

            log.info("Successfully added blogpost to user {} with title {}.", request.getUsername(), request.getTitle());

            return ApiResponse.builder()
                    .message("Blog with title " + request.getTitle() + " created successfully.")
                    .status(HttpStatus.OK)
                    .build();

        } catch (Exception e) {

            log.error("Error while creating a blogpost for user {}. Error message = {}", request.getUsername(), e.getMessage());
            return ApiResponse.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }

    }


    @Override
    public ApiResponse getBlogs(int page, int size, String sortDirection, String sortTo) {


        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortTo));

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .response(blogRepository.findAll(pageRequest))
                .build();
    }

    @Override
    public ApiResponse getBlogById(Long id) {

        try {

            return ApiResponse.builder()
                    .response(blogRepository.findById(id).orElseThrow(() ->
                            new BlogDoesNotFoundException("Blog with given id " + id + " is not found")))
                    .status(HttpStatus.OK)
                    .build();

        } catch (Exception e) {

            return ApiResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(e.getMessage())
                    .build();

        }

    }

    @Override
    public ApiResponse getUserBlogs(int page, int size, String sortDirection, String sortTo, String username) {

        UserEntity userEntity = (UserEntity) userService.findUserByUsername(username).getResponse();

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortTo));

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .response(blogRepository.findAllByUserEntity(userEntity, pageRequest))
                .build();

    }


}

