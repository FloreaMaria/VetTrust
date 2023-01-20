package com.example.vettrust.exception;

public class NoScheduleForVetIdAndLocationIdFoundException extends RuntimeException{
    public NoScheduleForVetIdAndLocationIdFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
