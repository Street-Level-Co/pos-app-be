package com.example.demo.repository;

import com.example.demo.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {
    Optional<Item> findByItemName(String trim);

    Page<Item> findAllByItemNameContainsIgnoreCase(String itemName, Pageable of);
}
