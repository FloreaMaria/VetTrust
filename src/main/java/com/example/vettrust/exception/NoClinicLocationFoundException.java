package com.example.vettrust.exception;

public class NoClinicLocationFoundException extends RuntimeException{
    public NoClinicLocationFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
