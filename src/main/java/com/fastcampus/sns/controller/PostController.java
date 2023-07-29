package com.fastcampus.sns.controller;

import com.fastcampus.sns.controller.request.PostCreateRequest;
import com.fastcampus.sns.controller.response.Response;
import com.fastcampus.sns.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor //final, notnull 필드 생성자주입
public class PostController {
    private final PostService postService;

    @PostMapping
    public Response<Void> create(@RequestBody PostCreateRequest request, Authentication authentication) {
        postService.create(authentication.getName(), request.getTitle(), request.getBody());
        return Response.success(null);
    }

}

