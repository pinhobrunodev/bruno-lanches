package com.pinhobrunodev.brunolanches.services;

import com.pinhobrunodev.brunolanches.dto.order.ShowOrderInfoDTO;
import com.pinhobrunodev.brunolanches.dto.user.*;
import com.pinhobrunodev.brunolanches.entities.User;
import com.pinhobrunodev.brunolanches.repositories.OrderRepository;
import com.pinhobrunodev.brunolanches.repositories.UserRepository;
import com.pinhobrunodev.brunolanches.services.exceptions.DatabaseException;
import com.pinhobrunodev.brunolanches.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    // Can be Admin "All Users of the app" screen.
    @Transactional(readOnly = true)
    public List<ShowUserInfoDTO> findAll() {
        return repository.findAll().stream().map(ShowUserInfoDTO::new).collect(Collectors.toList());
    }

    // Can be Admin "Paged All Users of the app" screen.
    @Transactional(readOnly = true)
    public Page<UserPagedSearchDTO> pagedSearch(Pageable pageable) {
        Page<User> result = repository.findAll(pageable);
        return result.map(UserPagedSearchDTO::new);
    }

    // Can be "User Current Orders - PENDING " screen.
    @Transactional(readOnly = true)
    public List<ShowUserOrderDTO> showAllPendingOrdersByUserId(Long id) {
        return repository.showAllPendingOrdersByUserId(id).stream().map(ShowUserOrderDTO::new).collect(Collectors.toList());
    }

    // Can be "User FINISHED Orders" screen.
    @Transactional(readOnly = true)
    public List<ShowUserOrderDTO> showAllDeliveredOrdersByUserId(Long id) {
        return repository.showAllDeliveredOrdersByUserId(id).stream().map(ShowUserOrderDTO::new).collect(Collectors.toList());
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
