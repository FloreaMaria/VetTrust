package com.example.vettrust.exception;

public class NoPetFoundException extends RuntimeException{
    public NoPetFoundException(String exceptionMessage){
        super(exceptionMessage);
    }
}
