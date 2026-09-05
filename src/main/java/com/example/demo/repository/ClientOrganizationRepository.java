package com.example.demo.repository;

import com.example.demo.model.ClientOrganization;
import com.example.demo.model.ClientOrganizationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClientOrganizationRepository extends JpaRepository<ClientOrganization, ClientOrganizationId> {
    List<ClientOrganization> findAllByClient_Id(UUID clientId);
}
