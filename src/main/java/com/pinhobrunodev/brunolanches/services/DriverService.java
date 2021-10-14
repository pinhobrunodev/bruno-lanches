package com.pinhobrunodev.brunolanches.services;

import com.pinhobrunodev.brunolanches.dto.driver.DriverPagedSearchDTO;
import com.pinhobrunodev.brunolanches.dto.driver.RegisterDriverDTO;
import com.pinhobrunodev.brunolanches.dto.driver.ShowDriverInfoDTO;
import com.pinhobrunodev.brunolanches.dto.driver.UpdateDriverDTO;
import com.pinhobrunodev.brunolanches.dto.order.ShowOrderInfoDTO;
import com.pinhobrunodev.brunolanches.dto.role.RoleDTO;
import com.pinhobrunodev.brunolanches.entities.Driver;
import com.pinhobrunodev.brunolanches.entities.Order;
import com.pinhobrunodev.brunolanches.entities.Role;
import com.pinhobrunodev.brunolanches.entities.enums.OrderStatus;
import com.pinhobrunodev.brunolanches.repositories.DriverRepository;
import com.pinhobrunodev.brunolanches.repositories.OrderRepository;
import com.pinhobrunodev.brunolanches.repositories.RoleRepository;
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
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DriverService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private DriverRepository repository;
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private RoleRepository roleRepository;

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
		return repository.findById(id).map(ShowDriverInfoDTO::new)
				.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
	}

	@Transactional(readOnly = true)
	public ShowDriverInfoDTO findByName(String name) {
		Driver aux = repository.findByName(name.substring(0, 1).toUpperCase().concat(name.substring(1)));
		if (aux == null) {
			throw new ResourceNotFoundException("Name not found :" + name);
		}
		return new ShowDriverInfoDTO(aux);
	}

	// Can be Admin "All Drivers of the app" screen.
	@Transactional(readOnly = true)
	public List<ShowDriverInfoDTO> findAll() {
		return repository.findAll().stream().map(ShowDriverInfoDTO::new).collect(Collectors.toList());
	}

	// Can be Admin "Paged All Drivers of the app" screen.
	@Transactional(readOnly = true)
	public Page<DriverPagedSearchDTO> pagedSearch(Pageable pageable) {
		Page<Driver> result = repository.findAll(pageable);
		return result.map(DriverPagedSearchDTO::new);
	}

	// Can be used on "Paged Pending Order" on Driver screen.
	@Transactional(readOnly = true)
	public Page<ShowOrderInfoDTO> showPendingOrders(Pageable pageable) {
		Page<Order> page = orderRepository.PageshowAllPendingOrders(pageable);
		return page.map(ShowOrderInfoDTO::new);

	}

	@Transactional
	public void takePendingOrder(Long id, Long driver_id) {

		try {
			Order aux = orderRepository.getById(id);
			Driver driverAux = repository.getById(driver_id);
			if (aux.getDriver() != null) {
				throw new UnprocessableActionException(aux.getDriver().getName() + " already took that Order.");
			}
			if (aux.getDriver() == null) {
				aux.setDriver(driverAux);
				// if the driver took the order but is in a already order , we remove that order that was taken by him
				if (aux.getDriver().getInCurrentOrder() == Boolean.TRUE) {
					aux.getDriver().getOrders().remove(driverAux);
					//aux.getDriver().getOrders().removeIf(x -> x.getDriver().getInCurrentOrder() == Boolean.TRUE);
					throw new UnprocessableActionException(
							aux.getDriver().getName() + " is already making a delivery.");
				}
			}

			aux.setDriver(driverAux);
			aux.getDriver().setInCurrentOrder(true);
			aux.setInProgress(Boolean.TRUE);
			aux.setStatus(OrderStatus.IN_PROGRESS);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Entity not found ");
		}
	}

	@Transactional
	public void setDelivered(Long id) {
		try {
			Order aux = orderRepository.getById(id);
			Driver driverAux = aux.getDriver();
			if (driverAux == null) {
				throw new ResourceNotFoundException("Entity not found");
			}
			if (null != driverAux.getOrders().stream().filter(x -> Objects.equals(x.getId(), aux.getId())).findFirst()
					.orElse(null)) {
				aux.setStatus(OrderStatus.DELIVERED);
				aux.getDriver().setInCurrentOrder(Boolean.FALSE);
				aux.setInProgress(Boolean.FALSE);
			}

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Entity not found");
		}

	}

	// Can be used on "My Finished Orders" Driver screen
	@Transactional(readOnly = true)
	public List<ShowOrderInfoDTO> showAllDeliveredOrdersByDriverId(Long id) {
		return orderRepository.showAllDeliveredOrdersByDriverId(id).stream().map(ShowOrderInfoDTO::new)
				.collect(Collectors.toList());
	}

	// *
	// Can be used on "My Current Orders (IN_PROGRESS) " Driver screen
	@Transactional(readOnly = true)
	public List<ShowOrderInfoDTO> showAllInProgressOrdersByDriverId(Long id) {
		return orderRepository.showAllInProgressOrdersByDriverId(id).stream().map(ShowOrderInfoDTO::new)
				.collect(Collectors.toList());
	}
	// Auxiliary methods

	public Driver copyDtoToEntity(Driver entity, RegisterDriverDTO dto) {
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		entity.setPhone(dto.getPhone());
		entity.setEmail(dto.getEmail());
		entity.setDate(dto.getDate());
		for (RoleDTO roleDTO : dto.getRoles()) {
			Role aux = roleRepository.getById(roleDTO.getId());
			if (aux.getId() == 3) {
				entity.getRoles().add(aux);
			} else {
				throw new UnprocessableActionException("Error");
			}
		}
		return entity;
	}

	public Driver toUpdate(Long id, UpdateDriverDTO dto) {
		Driver aux = repository.getById(id);
		aux.setName(dto.getName());
		aux.setCpf(dto.getCpf());
		aux.setPassword(passwordEncoder.encode(dto.getPassword()));
		aux.setPhone(dto.getPhone());
		aux.setEmail(dto.getEmail());
		aux.setDate(dto.getDate());
		return aux;
	}

}
