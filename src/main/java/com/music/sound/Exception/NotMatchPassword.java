package com.music.sound.Exception;

import org.springframework.security.core.AuthenticationException;

public class NotMatchPassword extends AuthenticationException{
    public NotMatchPassword(String msg){
        super(msg);
    }
}
