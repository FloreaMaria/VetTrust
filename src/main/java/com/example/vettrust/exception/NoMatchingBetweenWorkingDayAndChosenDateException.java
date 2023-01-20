package com.example.vettrust.exception;

public class NoMatchingBetweenWorkingDayAndChosenDateException extends RuntimeException{
    public NoMatchingBetweenWorkingDayAndChosenDateException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
