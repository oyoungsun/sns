package com.fastcampus.sns.fixture;

import com.fastcampus.sns.model.entity.UserEntity;

public class UserEntityFixture {
    //비밀번호 일치 검사하는 테스트용 user
    public static UserEntity get(String username, String password, Integer id){
        UserEntity result = new UserEntity();
        result.setId(id);
        result.setUserName(username);
        result.setPassword(password);

        return result;
    }
}
