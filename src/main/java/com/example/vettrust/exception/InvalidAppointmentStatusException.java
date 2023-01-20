package com.example.vettrust.exception;

public class InvalidAppointmentStatusException extends RuntimeException{
    public InvalidAppointmentStatusException(String exceptionMessage){
        super(exceptionMessage);
    }
}
