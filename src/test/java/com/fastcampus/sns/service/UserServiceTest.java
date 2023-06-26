package com.fastcampus.sns.service;

import com.fastcampus.sns.entity.UserEntity;
import com.fastcampus.sns.exception.SnsApplicationException;
import com.fastcampus.sns.fixture.UserEntityFixture;
import com.fastcampus.sns.model.User;
import com.fastcampus.sns.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private UserEntityRepository userEntityRepository;

    @Test
    void 회원가입_정상동작(){
        String username = "userName";
        String password = "password";
        //중복되지 않음으로, 비었다고 출력
        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.empty());
        when(userEntityRepository.save(any())).thenReturn(Optional.of( UserEntityFixture.get(username, password)));

        Assertions.assertDoesNotThrow(()->userService.join(username, password));
    }

    @Test
    void 회원가입시_username_중복(){
        String username = "userName";
        String password = "password";
        UserEntity fixture = UserEntityFixture.get(username, password);


        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(fixture));
        when(userEntityRepository.save(any())).thenReturn(Optional.of(fixture));
        //에러반환
        Assertions.assertThrows(SnsApplicationException.class, ()->userService.join(username, password));
    }

    @Test
    void 로그인_정상동작(){
        String username = "userName";
        String password = "password";
        UserEntity fixture = UserEntityFixture.get(username, password);

        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(fixture));
        when(userEntityRepository.save(any())).thenReturn(Optional.of(mock(UserEntity.class)));

        Assertions.assertDoesNotThrow(()->userService.login(username, password));
    }

    @Test
    void 로그인시_username_중복(){
        String username = "userName";
        String password = "password";
        UserEntity fixture = UserEntityFixture.get(username, password);
        //중복되지 않음으로, 비었다고 출력
        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(fixture));
        when(userEntityRepository.save(any())).thenReturn(Optional.of(mock(UserEntity.class)));
        //에러반환
        Assertions.assertThrows(SnsApplicationException.class, ()->userService.login(username, password));
    }
    @Test
    void 로그인시_username_없음(){
        String username = "userName";
        String password = "password";
        //중복되지 않음으로, 비었다고 출력
        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.empty());
        //에러반환
        Assertions.assertThrows(SnsApplicationException.class, ()->userService.login(username, password));
    }
    @Test
    void 로그인시_password_틀림(){
        String username = "userName";
        String password = "password";
        String wrongPassword = "wrong";
        UserEntity fixture = UserEntityFixture.get(username, password);
        //중복되지 않음으로, 비었다고 출력
        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(fixture));

        //에러반환
        Assertions.assertThrows(SnsApplicationException.class, ()->userService.login(username, wrongPassword);
    }
}