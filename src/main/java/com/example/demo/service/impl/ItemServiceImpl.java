package com.example.demo.service.impl;

import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;
import com.example.demo.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public Optional<Item> findByName(String name) {
        return itemRepository.findByItemName(name);
    }

    @Override
    public Optional<Item> findByID(UUID id) {
        return itemRepository.findById(id);
    }

    @Override
    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Page<Item> findAll(String keyword, PageRequest of) {
        return itemRepository.findAllByItemNameContainsIgnoreCase(keyword, of);
    }
}
