package com.example.vettrust.exception;

public class NoAvailableWorkingHoursFoundException extends RuntimeException{
    public NoAvailableWorkingHoursFoundException(String exceptionMessage){
        super(exceptionMessage);
    }
}
