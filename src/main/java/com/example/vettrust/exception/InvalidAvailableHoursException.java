package com.example.vettrust.exception;

public class InvalidAvailableHoursException extends RuntimeException{
    public InvalidAvailableHoursException(String exceptionMessage){
        super(exceptionMessage);
    }
}
