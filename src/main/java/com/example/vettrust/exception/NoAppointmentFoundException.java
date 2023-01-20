package com.example.vettrust.exception;

public class NoAppointmentFoundException extends RuntimeException{
    public NoAppointmentFoundException(String exceptionMessage){
        super(exceptionMessage);
    }
}
