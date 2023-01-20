package com.example.vettrust.exception;

public class NoDiagnosisConclusionFoundException extends RuntimeException{
    public NoDiagnosisConclusionFoundException(String exceptionMessage){
        super(exceptionMessage);
    }
}
