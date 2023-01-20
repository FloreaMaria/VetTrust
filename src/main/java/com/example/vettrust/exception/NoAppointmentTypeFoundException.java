package com.example.vettrust.exception;

public class NoAppointmentTypeFoundException extends RuntimeException{
    public NoAppointmentTypeFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
