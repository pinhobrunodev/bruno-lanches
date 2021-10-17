package com.pinhobrunodev.brunolanches.services.validation.client;

import com.pinhobrunodev.brunolanches.dto.client.ClientInsertDTO;
import com.pinhobrunodev.brunolanches.entities.Client;
import com.pinhobrunodev.brunolanches.repositories.ClientRepository;
import com.pinhobrunodev.brunolanches.resources.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClientInsertValidator implements ConstraintValidator<ClientInsertValid, ClientInsertDTO> {

    @Autowired
    private ClientRepository repository;

    @Override
    public void initialize(ClientInsertValid ann) {
    }


    @Override
    public boolean isValid(ClientInsertDTO dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        Client client = repository.findByEmail(dto.getEmail());
        Client client1 = repository.findByCpf(dto.getCpf());
        Client client2 = repository.findByPhone(dto.getPhone());

        if (client != null) {
            list.add(new FieldMessage("email","Email already exists"));
        }
        if (client1 != null){
            list.add(new FieldMessage("cpf","CPF already exists"));
        }
        if (client2 != null){
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