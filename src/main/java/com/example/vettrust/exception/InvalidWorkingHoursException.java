package com.example.vettrust.exception;

public class InvalidWorkingHoursException extends RuntimeException{
    public InvalidWorkingHoursException(String exceptionMessage){
        super(exceptionMessage);
    }
}
