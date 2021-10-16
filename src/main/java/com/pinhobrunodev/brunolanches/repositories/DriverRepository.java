package com.pinhobrunodev.brunolanches.repositories;

import com.pinhobrunodev.brunolanches.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver,Long> {
    Driver findByName(String concat);


    Driver findByEmail(String email);

    Driver findByCpf(String cpf);

    Driver findByPhone(String phone);
}
