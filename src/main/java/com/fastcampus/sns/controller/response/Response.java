package com.fastcampus.sns.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

//성공여부+결과를 대답하도록 만들기
@AllArgsConstructor
@Getter
public class Response<T> {
    private String resultCode;
    private T result;

    //실패의 경우
    public static Response<Void> error(String resultCode) {
        return new Response<Void>(resultCode, null);
    }
    //성공의 경우 return 형식이 제각각임
    public static Response<Void> success() {
        return new Response<Void>("SUCCESS", null);
    }

    public static <T> Response<T> success(T result) {
        return new Response<T>("SUCCESS", result);
    }


    public String toStream() {
        if (result == null) {
            return "{" +
                    "\"resultCode\":" + "\"" + resultCode + "\"," +
                    "\"result\":" + null +
                    "}";
        }
        return "{" +
                "\"resultCode\":" + "\"" + resultCode + "\"," +
                "\"result\":" + "\"" + result + "\"," +
                "}";
    }
}
