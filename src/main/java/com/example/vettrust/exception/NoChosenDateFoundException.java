package com.example.vettrust.exception;

public class NoChosenDateFoundException extends RuntimeException{
    public NoChosenDateFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
