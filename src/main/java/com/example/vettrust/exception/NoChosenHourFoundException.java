package com.example.vettrust.exception;

public class NoChosenHourFoundException extends RuntimeException{
    public NoChosenHourFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
