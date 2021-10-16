package com.pinhobrunodev.brunolanches.services.validation.user;

import com.pinhobrunodev.brunolanches.dto.user.UserInsertDTO;
import com.pinhobrunodev.brunolanches.entities.User;
import com.pinhobrunodev.brunolanches.repositories.UserRepository;
import com.pinhobrunodev.brunolanches.resources.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(UserInsertValid ann) {
    }


    @Override
    public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        User user = repository.findByEmail(dto.getEmail());
        User user1 = repository.findByCpf(dto.getCpf());
        User user2 = repository.findByPhone(dto.getPhone());

        if (user != null) {
            list.add(new FieldMessage("email","Email already exists"));
        }
        if (user1 != null){
            list.add(new FieldMessage("cpf","CPF already exists"));
        }
        if (user2 != null){
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