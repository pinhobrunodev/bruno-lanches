package com.pinhobrunodev.brunolanches.services.validation.driver;

import com.pinhobrunodev.brunolanches.dto.driver.InsertDriverDTO;
import com.pinhobrunodev.brunolanches.entities.Driver;
import com.pinhobrunodev.brunolanches.repositories.DriverRepository;
import com.pinhobrunodev.brunolanches.resources.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class DriverInsertValidator implements ConstraintValidator<DriverInsertValid, InsertDriverDTO> {

    @Autowired
    private DriverRepository repository;

    @Override
    public void initialize(DriverInsertValid ann) {
    }


    @Override
    public boolean isValid(InsertDriverDTO dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        Driver driver = repository.findByEmail(dto.getEmail());
        Driver driver1 = repository.findByCpf(dto.getCpf());
        Driver driver2 = repository.findByPhone(dto.getPhone());

        if (driver != null) {
            list.add(new FieldMessage("email","Email already exists"));
        }
        if (driver1 != null){
            list.add(new FieldMessage("cpf","CPF already exists"));
        }
        if (driver2 != null){
            list.add(new FieldMessage("phone","Phone already exists"));
        }


        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}