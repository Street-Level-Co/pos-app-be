package com.example.demo.service;

import com.example.demo.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

public interface ItemService {

    Optional<Item> findByName(String name);

    Optional<Item> findByID(UUID id);

    Item createItem(Item item);

    Page<Item> findAll(String keyword, PageRequest of);
}
