package com.example.vettrust.exception;

public class NoVetFoundException extends RuntimeException{
    public NoVetFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
