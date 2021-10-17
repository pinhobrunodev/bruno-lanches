package com.pinhobrunodev.brunolanches.services.validation.client;

import com.pinhobrunodev.brunolanches.dto.client.ClientUpdateDTO;
import com.pinhobrunodev.brunolanches.entities.Client;
import com.pinhobrunodev.brunolanches.repositories.ClientRepository;
import com.pinhobrunodev.brunolanches.resources.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientUpdateValidator implements ConstraintValidator<ClientUpdateValid, ClientUpdateDTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClientRepository repository;

    @Override
    public void initialize(ClientUpdateValid ann) {
    }


    @Override
    public boolean isValid(ClientUpdateDTO dto, ConstraintValidatorContext context) {

        var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long userId = Long.parseLong(uriVars.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        Client client = repository.findByEmail(dto.getEmail());
        Client client1 = repository.findByCpf(dto.getCpf());
        Client client2 = repository.findByPhone(dto.getPhone());

        if (client != null && userId != client.getId()) {
            list.add(new FieldMessage("email", "Email already exists"));
        }
        if (client1 != null && userId != client1.getId()) {
            list.add(new FieldMessage("cpf", "CPF already exists"));
        }
        if (client2 != null && userId != client2.getId()) {
            list.add(new FieldMessage("phone", "Phone already exists"));
        }


        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}