package com.pinhobrunodev.brunolanches.repositories;

import com.pinhobrunodev.brunolanches.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByPhone(String phone);
    User findByCpf(String cpf);
    User findByEmail(String email);
    User findByName(String name);



}
