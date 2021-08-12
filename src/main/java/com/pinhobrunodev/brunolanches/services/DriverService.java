package com.pinhobrunodev.brunolanches.services;

import com.pinhobrunodev.brunolanches.dto.driver.RegisterDriverDTO;
import com.pinhobrunodev.brunolanches.dto.driver.ShowDriverInfoDTO;
import com.pinhobrunodev.brunolanches.dto.driver.UpdateDriverDTO;
import com.pinhobrunodev.brunolanches.dto.order.ShowOrderInfoDTO;
import com.pinhobrunodev.brunolanches.entities.Driver;
import com.pinhobrunodev.brunolanches.entities.Order;
import com.pinhobrunodev.brunolanches.repositories.DriverRepository;
import com.pinhobrunodev.brunolanches.repositories.OrderRepository;
import com.pinhobrunodev.brunolanches.services.exceptions.DatabaseException;
import com.pinhobrunodev.brunolanches.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverService {

    @Autowired
    private DriverRepository repository;
    @Autowired
    private OrderRepository orderRepository;


    @Transactional
    public void save(RegisterDriverDTO dto) {
        Driver entity = new Driver();
        repository.save(copyDtoToEntity(entity, dto));
    }

    @Transactional
    public ShowDriverInfoDTO update(UpdateDriverDTO dto, Long id) {
        try {
            Driver aux = toUpdate(id, dto);
            repository.save(aux);
            return new ShowDriverInfoDTO(aux);
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
    public ShowDriverInfoDTO findById(Long id) {
        return repository.findById(id).map(ShowDriverInfoDTO::new).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    }

    @Transactional(readOnly = true)
    public ShowDriverInfoDTO findByName(String name) {
        Driver aux = repository.findByName(name.substring(0, 1).toUpperCase().concat(name.substring(1)));
        if (aux == null) {
            throw new ResourceNotFoundException("Name not found :" + name);
        }
        return new ShowDriverInfoDTO(aux);
    }


    @Transactional(readOnly = true)
    public List<ShowDriverInfoDTO> findAll() {
        return repository.findAll().stream().map(ShowDriverInfoDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ShowOrderInfoDTO> showPendingOrders() {
        return orderRepository.ShowAllPendingOrders().stream().map(ShowOrderInfoDTO::new).collect(Collectors.toList());
    }

    // Auxiliary methods

    public Driver copyDtoToEntity(Driver entity, RegisterDriverDTO dto) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setPassword(dto.getPassword());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setDate(dto.getDate());
        return entity;
    }

    public Driver toUpdate(Long id, UpdateDriverDTO dto) {
        Driver aux = repository.getById(id);
        aux.setName(dto.getName());
        aux.setCpf(dto.getCpf());
        aux.setPassword(dto.getPassword());
        aux.setPhone(dto.getPhone());
        aux.setEmail(dto.getEmail());
        aux.setDate(dto.getDate());
        return aux;
    }


}
