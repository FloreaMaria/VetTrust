package com.example.vettrust.exception;

public class NoVetScheduleFoundException extends RuntimeException{
    public NoVetScheduleFoundException(String exceptionMessage){
        super(exceptionMessage);
    }
}
