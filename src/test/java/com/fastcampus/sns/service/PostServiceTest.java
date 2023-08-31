package com.fastcampus.sns.service;

import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.exception.SnsApplicationException;
import com.fastcampus.sns.fixture.PostEntityFixture;
import com.fastcampus.sns.fixture.UserEntityFixture;
import com.fastcampus.sns.model.entity.PostEntity;
import com.fastcampus.sns.model.entity.UserEntity;
import com.fastcampus.sns.repository.PostEntityRepository;
import com.fastcampus.sns.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import javax.swing.plaf.OptionPaneUI;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostEntityRepository postEntityRepository;
    @MockBean
    private UserEntityRepository userEntityRepository;

    @Test
    void 포스트작성이_성공한경우(){
        //유저가 존재
        String title = "title";
        String body = "body";
        String username = "userName";
        //mocking
        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));

        Assertions.assertDoesNotThrow(()-> postService.create(title, body, username));
    }

    @Test
    void 포스트작성시_요청유저가_존재하지않는경우(){
        String title = "title";
        String body = "body";
        String username = "userName";
        //mocking

        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));

        SnsApplicationException e = Assertions.assertThrows( SnsApplicationException.class, ()-> postService.create(title, body, username));
        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, e.getErrorCode());

    }
    @Test
    void 포스트수정이_성공한경우(){
        //유저가 존재
        String title = "title";
        String body = "body";
        String username = "userName";
        Integer postId = 1;
        PostEntity postEntity = PostEntityFixture.get(username, postId, 1);
        UserEntity userEntity = postEntity.getUser();
        //mocking
        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));
        when(postEntityRepository.saveAndFlush(any())).thenReturn(postEntity);

        //permission check
        Assertions.assertDoesNotThrow(()-> postService.modify(title, body, username, postId));
    }

    @Test
    void 포스트수정시_포스트가_존재하지않는경우(){
        String title = "title";
        String body = "body";
        String username = "userName";
        Integer postId = 1;
        PostEntity postEntity = PostEntityFixture.get(username, postId, 1);
        UserEntity userEntity = postEntity.getUser();
        //mocking
        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.empty());
        when(postEntityRepository.saveAndFlush(any())).thenReturn(postEntity);

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, ()-> postService.modify(title, body, username, postId));
        Assertions.assertEquals(ErrorCode.POST_NOT_FOUND, e.getErrorCode());

    }
    @Test
    void 포스트수정시_권한이_없는경우(){
        String title = "title";
        String body = "body";
        String username = "userName";
        Integer postId = 1;
        PostEntity postEntity = PostEntityFixture.get(username, postId, 1);
        UserEntity userEntity = postEntity.getUser();
        UserEntity writer = UserEntityFixture.get("usernameOther", "password");
        //mocking
        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(writer));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));
        when(postEntityRepository.saveAndFlush(any())).thenReturn(postEntity);

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, ()-> postService.modify(title, body, username, postId));
        Assertions.assertEquals(ErrorCode.INVALID_PERMISSION, e.getErrorCode());

    }

}
