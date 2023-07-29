package com.fastcampus.sns.service;

import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.model.entity.UserEntity;
import com.fastcampus.sns.exception.SnsApplicationException;
import com.fastcampus.sns.fixture.UserEntityFixture;
import com.fastcampus.sns.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private UserEntityRepository userEntityRepository;

    @MockBean
    private BCryptPasswordEncoder encoder;

    @Test
    void 회원가입_정상동작(){
        String username = "userName";
        String password = "password";
        //중복되지 않음으로, 비었다고 출력
        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.empty());
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(userEntityRepository.save(any())).thenReturn(UserEntityFixture.get(username, password));

        Assertions.assertDoesNotThrow(()->userService.join(username, password));
    }

    @Test
    void 회원가입시_username_중복() {
        String username = "userName";
        String password = "password";
        UserEntity fixture = UserEntityFixture.get(username, password);


        when(userEntityRepository.findByUserName(fixture.getUserName()))
                .thenReturn(Optional.of(UserEntityFixture.get(fixture.getUserName(), fixture.getPassword())));

        SnsApplicationException exception = Assertions.assertThrows(SnsApplicationException.class,
                () -> userService.join(fixture.getUserName(), fixture.getPassword()));

        Assertions.assertEquals(ErrorCode.DUPLICATED_USER_NAME, exception.getErrorCode());
    }
    @Test
    void 로그인_정상동작(){
        String username = "userName";
        String password = "password";
        UserEntity fixture = UserEntityFixture.get(username, password);

        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(fixture));
        when(encoder.matches(password, fixture.getPassword())).thenReturn(true);

        Assertions.assertDoesNotThrow(()->userService.login(username, password));
    }

//    @Test
//    void 로그인시_username_중복(){
//        String username = "userName";
//        String password = "password";
//        UserEntity fixture = UserEntityFixture.get(username, password);
//        //중복되지 않음으로, 비었다고 출력
//        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(fixture));
//        //에러반환
//        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, ()->userService.login(username, password));
//    }
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
        SnsApplicationException exception = Assertions.assertThrows(SnsApplicationException.class, ()->userService.login(username, wrongPassword));
        Assertions.assertEquals(ErrorCode.INVALID_PASSWORD, exception.getErrorCode());

    }
}