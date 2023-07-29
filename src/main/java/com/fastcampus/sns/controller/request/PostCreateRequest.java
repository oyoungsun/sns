package com.fastcampus.sns.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

//controller에서 사용할 리퀘스트
@Getter
@AllArgsConstructor
public class PostCreateRequest {
    private String title;
    private String body;
}
