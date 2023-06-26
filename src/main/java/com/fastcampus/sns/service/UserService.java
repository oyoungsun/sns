package com.fastcampus.sns.service;

import com.fastcampus.sns.entity.UserEntity;
import com.fastcampus.sns.exception.SnsApplicationException;
import com.fastcampus.sns.model.User;
import com.fastcampus.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
    //TODO : implements
    public User join(String username, String password){
        //username 중복검사
        Optional<UserEntity> userEntity = userEntityRepository.findByUserName(username);
        //회원가입 진행= user등록
        userEntityRepository.save(new UserEntity());
        return new User();
    }
    //TODO : implements
    public String login(String username, String password){
        //회원가입 여부 체크
        UserEntity userEntity = userEntityRepository.findByUserName(username).orElseThrow(()-> new SnsApplicationException());

        //비밀번호 체크
        if(!userEntity.getPassword().equals(password)){
            throw new SnsApplicationException();
        }

        //토큰 생성
        //토큰 반환
        return "";
    }
}
