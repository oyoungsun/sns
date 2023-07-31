package com.fastcampus.sns.fixture;

import com.fastcampus.sns.model.entity.PostEntity;
import com.fastcampus.sns.model.entity.UserEntity;

public class PostEntityFixture {
    //비밀번호 일치 검사하는 테스트용 user
    public static PostEntity get(String username, Integer postId){
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setUserName(username);
        PostEntity result = new PostEntity();
        result.setUser(user);
        result.setId(postId);
        return result;
    }
}
