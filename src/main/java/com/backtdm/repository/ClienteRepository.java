package com.backtdm.repository;

import com.backtdm.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByUsername(String name);
}
