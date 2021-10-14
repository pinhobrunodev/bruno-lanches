package com.pinhobrunodev.brunolanches.services.validation.driver;

import com.pinhobrunodev.brunolanches.dto.driver.InsertDriverDTO;
import com.pinhobrunodev.brunolanches.dto.driver.UpdateDriverDTO;
import com.pinhobrunodev.brunolanches.entities.Driver;
import com.pinhobrunodev.brunolanches.repositories.DriverRepository;
import com.pinhobrunodev.brunolanches.resources.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DriverUpdateValidator implements ConstraintValidator<DriverUpdateValid, UpdateDriverDTO> {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private DriverRepository repository;

    @Override
    public void initialize(DriverUpdateValid ann) {
    }


    @Override
    public boolean isValid(UpdateDriverDTO dto, ConstraintValidatorContext context) {

        var Urivars = (Map<String,String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long driverId = Long.parseLong(Urivars.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        Driver driver = repository.findByEmail(dto.getEmail());
        Driver driver1 = repository.findByCpf(dto.getCpf());
        Driver driver2 = repository.findByPhone(dto.getPhone());

        if (driver != null && driverId != driver.getId()) {
            list.add(new FieldMessage("email","Email already exists"));
        }
        if (driver1 != null && driverId != driver1.getId() ){
            list.add(new FieldMessage("cpf","CPF already exists"));
        }
        if (driver2 != null  && driverId != driver2.getId() ){
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
