package com.fastcampus.sns.fixture;

import com.fastcampus.sns.entity.UserEntity;
import com.fastcampus.sns.model.User;

public class UserEntityFixture {
    //비밀번호 일치 검사하는 테스트용 user
    public static UserEntity get(String username, String password){
        UserEntity result = new UserEntity();
        result.setId(1);
        result.setUserName(username);
        result.setPassword(password);

        return result;
    }
}
