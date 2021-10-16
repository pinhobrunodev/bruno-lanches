package com.pinhobrunodev.brunolanches.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

    private final List<FieldMessage> errors = new ArrayList<>();

    public List<FieldMessage> getErrors() {
        return errors;
    }

    void add(String fieldMessage,String msg){
        errors.add(new FieldMessage(fieldMessage,msg));
    }
}
