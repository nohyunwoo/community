package com.example.community.exception;


public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String message){
        super(message);
    }
}
