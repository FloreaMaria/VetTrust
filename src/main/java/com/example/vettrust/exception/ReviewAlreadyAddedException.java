package com.example.vettrust.exception;

public class ReviewAlreadyAddedException extends RuntimeException{
    public ReviewAlreadyAddedException(String exceptionMessage){
        super(exceptionMessage);
    }
}
