package com.example.vettrust.exception;

public class InvalidWorkingDayException extends RuntimeException{
    public InvalidWorkingDayException(String exceptionMessage){
        super(exceptionMessage);
    }
}
