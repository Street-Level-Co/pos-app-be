package com.example.demo.repository;

import com.example.demo.model.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SaleRepository extends JpaRepository<Sale, UUID> {
    Page<Sale> findAllByOrg_Id(UUID orgId, Pageable pageable);

    long countByOrg_Id(UUID orgId);
}
