package com.pinhobrunodev.brunolanches.services.exceptions;

public class UnprocessableActionException extends  RuntimeException{
    public  UnprocessableActionException(String msg){
        super(msg);
    }
}
