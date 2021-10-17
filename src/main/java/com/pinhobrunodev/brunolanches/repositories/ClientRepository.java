package com.pinhobrunodev.brunolanches.repositories;

import com.pinhobrunodev.brunolanches.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByPhone(String phone);
    Client findByCpf(String cpf);
    Client findByEmail(String email);
    Client findByName(String name);



}
