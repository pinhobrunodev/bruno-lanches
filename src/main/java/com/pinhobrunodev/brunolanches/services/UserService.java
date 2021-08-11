package com.pinhobrunodev.brunolanches.services;

import com.pinhobrunodev.brunolanches.dto.user.ShowUserInfoDTO;
import com.pinhobrunodev.brunolanches.dto.user.UserRegisterDTO;
import com.pinhobrunodev.brunolanches.dto.user.UserUpdateDTO;
import com.pinhobrunodev.brunolanches.entities.User;
import com.pinhobrunodev.brunolanches.repositories.UserRepository;
import com.pinhobrunodev.brunolanches.services.exceptions.DatabaseException;
import com.pinhobrunodev.brunolanches.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.xml.crypto.Data;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;


    @Transactional
    public void save(UserRegisterDTO dto) {
        User entity = new User();
        repository.save(copyDtoToEntity(entity, dto));
    }

    @Transactional
    public ShowUserInfoDTO update(UserUpdateDTO dto, Long id) {
        try {
            User aux = toUpdate(id, dto);
            repository.save(aux);
            return new ShowUserInfoDTO(aux);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found :" + id);
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found : " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Database violation");
        }
    }

    @Transactional(readOnly = true)
    public ShowUserInfoDTO findById(Long id) {
        return repository.findById(id).map(ShowUserInfoDTO::new).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    }

    @Transactional(readOnly = true)
    public ShowUserInfoDTO findByName(String name) {
        User aux = repository.findByName(name.substring(0, 1).toUpperCase().concat(name.substring(1)));
        if (aux == null) {
            throw new ResourceNotFoundException("Name not found :" + name);
        }
        return new ShowUserInfoDTO(aux);
    }


    @Transactional(readOnly = true)
    public List<ShowUserInfoDTO> findAll() {
        return repository.findAll().stream().map(ShowUserInfoDTO::new).collect(Collectors.toList());
    }

    // Auxiliary methods

    public User copyDtoToEntity(User entity, UserRegisterDTO dto) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setPassword(dto.getPassword());
        entity.setPhone(dto.getPhone());
        entity.setAddress(dto.getAddress());
        entity.setEmail(dto.getEmail());
        entity.setDate(dto.getDate());
        return entity;
    }

    public User toUpdate(Long id, UserUpdateDTO dto) {
        User aux = repository.getById(id);
        aux.setName(dto.getName());
        aux.setCpf(dto.getCpf());
        aux.setPassword(dto.getPassword());
        aux.setPhone(dto.getPhone());
        aux.setAddress(dto.getAddress());
        aux.setEmail(dto.getEmail());
        aux.setDate(dto.getDate());
        return aux;
    }

}
