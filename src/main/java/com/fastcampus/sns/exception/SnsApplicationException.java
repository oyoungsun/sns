package com.fastcampus.sns.exception;

import com.fastcampus.sns.SnsApplication;
import lombok.AllArgsConstructor;
import lombok.Getter;

//TODO : implements
@AllArgsConstructor
@Getter
public class SnsApplicationException extends RuntimeException {
    //제각기 다른 에러에 대한 이유를 넣어주자

    private ErrorCode errorCode;
    private String message;

    public SnsApplicationException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.message = null;
    }

    @Override
    public String getMessage(){
        if(message==null) return errorCode.getMessage();
        return String.format("%s. %s", errorCode.getMessage(), message);
    }

}
