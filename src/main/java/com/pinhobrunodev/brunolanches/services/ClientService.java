package com.pinhobrunodev.brunolanches.services;

import com.pinhobrunodev.brunolanches.dto.order.ShowOrderInfoDTO;
import com.pinhobrunodev.brunolanches.dto.role.RoleDTO;
import com.pinhobrunodev.brunolanches.dto.client.ShowClientInfoDTO;
import com.pinhobrunodev.brunolanches.dto.client.ClientInsertDTO;
import com.pinhobrunodev.brunolanches.dto.client.ClientPagedSearchDTO;
import com.pinhobrunodev.brunolanches.dto.client.ClientUpdateDTO;
import com.pinhobrunodev.brunolanches.entities.Client;
import com.pinhobrunodev.brunolanches.entities.Role;
import com.pinhobrunodev.brunolanches.repositories.OrderRepository;
import com.pinhobrunodev.brunolanches.repositories.RoleRepository;
import com.pinhobrunodev.brunolanches.repositories.ClientRepository;
import com.pinhobrunodev.brunolanches.services.exceptions.DatabaseException;
import com.pinhobrunodev.brunolanches.services.exceptions.ResourceNotFoundException;
import com.pinhobrunodev.brunolanches.services.exceptions.UnprocessableActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ClientRepository repository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private OrderRepository orderRepository;


    @Transactional
    public void save(ClientInsertDTO dto) {
        Client entity = new Client();
        repository.save(copyDtoToEntity(entity, dto));
    }

    @Transactional
    public ShowClientInfoDTO update(ClientUpdateDTO dto, Long id) {
        try {
            Client aux = toUpdate(id, dto);
            repository.save(aux);
            return new ShowClientInfoDTO(aux);
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
    public ShowClientInfoDTO findById(Long id) {
        return repository.findById(id).map(ShowClientInfoDTO::new).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    }

    @Transactional(readOnly = true)
    public ShowClientInfoDTO findByName(String name) {
        Client aux = repository.findByName(name.substring(0, 1).toUpperCase().concat(name.substring(1)));
        if (aux == null) {
            throw new ResourceNotFoundException("Name not found :" + name);
        }
        return new ShowClientInfoDTO(aux);
    }


    // Can be Admin "All Users of the app" screen.
    @Transactional(readOnly = true)
    public List<ShowClientInfoDTO> findAll() {
        return repository.findAll().stream().map(ShowClientInfoDTO::new).collect(Collectors.toList());
    }

    // Can be Admin "Paged All Users of the app" screen.
    @Transactional(readOnly = true)
    public Page<ClientPagedSearchDTO> pagedSearch(Pageable pageable) {
        Page<Client> result = repository.findAll(pageable);
        return result.map(ClientPagedSearchDTO::new);
    }

    // Can be "User PENDING Orders - PENDING " screen.
    @Transactional(readOnly = true)
    public List<ShowOrderInfoDTO> showAllPendingOrdersByUserId(Long id) {
        return orderRepository.showAllPendingOrdersByUserId(id).stream().map(ShowOrderInfoDTO::new).collect(Collectors.toList());
    }

    // Can be "User FINISHED Orders" screen.
    @Transactional(readOnly = true)
    public List<ShowOrderInfoDTO> showAllDeliveredOrdersByUserId(Long id) {
        return orderRepository.showAllDeliveredOrdersByUserId(id).stream().map(ShowOrderInfoDTO::new).collect(Collectors.toList());
    }

    // Can be "User IN PROGRESS Orders" screen.
    @Transactional(readOnly = true)
    public List<ShowOrderInfoDTO> showAllInProgressOrdersByUserId(Long id) {
        return orderRepository.showAllInProgressOrdersByUserId(id).stream().map(ShowOrderInfoDTO::new).collect(Collectors.toList());
    }


    // Auxiliary methods

    public Client copyDtoToEntity(Client entity, ClientInsertDTO dto) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setPhone(dto.getPhone());
        entity.setAddress(dto.getAddress());
        entity.setEmail(dto.getEmail());
        entity.setDate(dto.getDate());
        for (RoleDTO roleDTO : dto.getRoles()) {
            Role aux = roleRepository.getOne(roleDTO.getId());
            if (aux.getId() == 1) {
                entity.getRoles().add(aux);
            } else {
                throw new UnprocessableActionException("Client can't be role DRIVER");
            }
        }
        return entity;
    }

    public Client toUpdate(Long id, ClientUpdateDTO dto) {
        Client aux = repository.getOne(id);
        aux.setName(dto.getName());
        aux.setCpf(dto.getCpf());
        aux.setPhone(dto.getPhone());
        aux.setAddress(dto.getAddress());
        aux.setEmail(dto.getEmail());
        aux.setDate(dto.getDate());
        return aux;
    }

}
