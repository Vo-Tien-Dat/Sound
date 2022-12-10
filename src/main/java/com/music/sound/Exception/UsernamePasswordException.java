package com.music.sound.Exception;

import java.lang.Exception;

public class UsernamePasswordException extends Exception {
    public UsernamePasswordException() {
        super();
    }

    public UsernamePasswordException(String message) {
        super(message);
    }
}
