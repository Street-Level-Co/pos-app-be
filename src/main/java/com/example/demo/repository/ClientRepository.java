package com.example.demo.repository;

import com.example.demo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> , JpaSpecificationExecutor<Client> {
    boolean existsClientByUsername(String username);

    boolean existsClientByMobile(Long mobile);

    Optional<Client> findClientByUsername(String username);

    Optional<Client> findClientByMobile(Long mobile);
}
