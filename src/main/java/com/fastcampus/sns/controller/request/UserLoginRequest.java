package com.fastcampus.sns.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

//controller에서 사용할 리퀘스트
@Getter
@AllArgsConstructor
public class UserLoginRequest {
    private String username;
    private String password;
}
