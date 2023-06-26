package com.fastcampus.sns.controller;

import com.fastcampus.sns.controller.request.UserJoinRequest;
import com.fastcampus.sns.controller.request.UserLoginRequest;
import com.fastcampus.sns.exception.SnsApplicationException;
import com.fastcampus.sns.model.User;
import com.fastcampus.sns.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void 회원가입() throws Exception {
        String username = "userName";
        String password = "password";
        //TODO : mocking
        when(userService.join(username, password)).thenReturn(mock(User.class));


        mockMvc.perform(post("api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                //TODO : add request body
                .content(objectMapper.writeValueAsBytes(new UserJoinRequest(username,password)))
        ).andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void 회원가입시_이미_username이_존재_에러반환() throws Exception{
        String username = "userName";
        String password = "password";

        when(userService.join(username, password)).thenThrow(new SnsApplicationException());

        mockMvc.perform(post("api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        //TODO : add request body
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(username,password)))
                ).andDo(print())
                .andExpect(status().isConflict());
    }
    @Test
    public void 로그인() throws Exception {
        String username = "userName";
        String password = "password";
        //TODO : mocking
        when(userService.login(username, password)).thenReturn("test_token");


        mockMvc.perform(post("api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        //TODO : add request body
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(username,password)))
                ).andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void 없는_username으로_로그인() throws Exception {
        String username = "userName";
        String password = "password";
        //TODO : mocking
        when(userService.login(username, password)).thenThrow(new SnsApplicationException());


        mockMvc.perform(post("api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        //TODO : add request body
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(username,password)))
                ).andDo(print())
                .andExpect(status().isNotFound()); //유저를 찾을수없음-> not found
    }
    @Test
    public void 잘못된_pasword로_로그인() throws Exception {
        String username = "userName";
        String password = "password";
        //TODO : mocking
        when(userService.login(username, password)).thenThrow(new SnsApplicationException());


        mockMvc.perform(post("api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        //TODO : add request body
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(username,password)))
                ).andDo(print())
                .andExpect(status().isUnauthorized()); //인증안됨->권한 부여불가
    }

}
