package com.pinhobrunodev.brunolanches.services.exceptions;

public class ResourceNotFoundException extends  RuntimeException{

    public ResourceNotFoundException(String msg){
        super(msg);
    }
}
