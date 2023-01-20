package com.example.vettrust.exception;

public class NoWorkingHoursFoundException extends RuntimeException{
    public NoWorkingHoursFoundException(String exceptionMessage){
        super(exceptionMessage);
    }
}
