package com.example.vettrust.exception;

public class NoPetOwnerFoundException extends RuntimeException{
    public NoPetOwnerFoundException(String exceptionMessage){
        super(exceptionMessage);
    }
}
