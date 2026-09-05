package com.example.demo.repository;

import com.example.demo.model.CatalogItem;
import com.example.demo.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CatalogRepository extends JpaRepository<CatalogItem, UUID> {
    boolean existsByItem(Item item);

    List<CatalogItem> findAllByOrg_Id(UUID orgID);

    long countByOrg_Id(UUID orgID);
}
