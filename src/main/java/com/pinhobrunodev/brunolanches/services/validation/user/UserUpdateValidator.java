package com.pinhobrunodev.brunolanches.services.validation.user;

import com.pinhobrunodev.brunolanches.dto.user.UserUpdateDTO;
import com.pinhobrunodev.brunolanches.entities.User;
import com.pinhobrunodev.brunolanches.repositories.UserRepository;
import com.pinhobrunodev.brunolanches.resources.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(UserUpdateValid ann) {
    }


    @Override
    public boolean isValid(UserUpdateDTO dto, ConstraintValidatorContext context) {

        var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long userId = Long.parseLong(uriVars.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        User user = repository.findByEmail(dto.getEmail());
        User user1 = repository.findByCpf(dto.getCpf());
        User user2 = repository.findByPhone(dto.getPhone());

        if (user != null && userId != user.getId()) {
            list.add(new FieldMessage("email", "Email already exists"));
        }
        if (user1 != null && userId != user.getId()) {
            list.add(new FieldMessage("cpf", "CPF already exists"));
        }
        if (user2 != null && userId != user.getId()) {
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