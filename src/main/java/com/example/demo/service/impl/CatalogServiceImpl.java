package com.example.demo.service.impl;

import com.example.demo.exception.EntryAlreadyExistsException;
import com.example.demo.exception.EntryNotFoundException;
import com.example.demo.model.CatalogItem;
import com.example.demo.model.Item;
import com.example.demo.model.Organization;
import com.example.demo.repository.CatalogRepository;
import com.example.demo.service.CatalogService;
import com.example.demo.service.ItemService;
import com.example.demo.service.OrganizationService;
import com.example.demo.transfer.CatalogItemSummary;
import com.example.demo.transfer.CreateCatalogItem;
import com.example.demo.transfer.update.UpdateCatalogItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository catalogRepository;

    private final ItemService itemService;

    private final OrganizationService organizationService;

    @Override
    public CatalogItemSummary createCatalogItem(CreateCatalogItem catalogItem) {
        Item item;
        if (catalogItem.getItemID() == null) {
            item = itemService.findByName(catalogItem.getItemName().trim())
                    .orElse(itemService.createItem(new Item(
                            catalogItem.getItemName()
                    )));
        } else {
            item = itemService.findByID(catalogItem.getItemID())
                    .orElse(itemService.createItem(new Item(
                            catalogItem.getItemName()
                    )));
        }
        if (catalogRepository.existsByItem(item)){
            throw new EntryAlreadyExistsException("Item already exists");
        }
        Organization org = organizationService.getOrganization(catalogItem.getOrgID());
        CatalogItem saved = catalogRepository.save(new CatalogItem(
                item,
                org,
                catalogItem.getPrice(),
                catalogItem.getDisPrice(),
                catalogItem.getImgUrl(),
                catalogItem.getDescription()
        ));
        return toSummary(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CatalogItemSummary> getAllCatalogItems(UUID orgID) {
        log.info("getting organization for : {} ", orgID);
        return catalogRepository.findAllByOrg_Id(orgID).stream()
                .map(this::toSummary)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CatalogItemSummary getCatalogItem(UUID catalogItemID) {
        return catalogRepository.findById(catalogItemID)
                .map(this::toSummary)
                .orElseThrow(() -> new EntryNotFoundException("Catalog Item Not Found"));
    }

    @Override
    public CatalogItemSummary updateCatalogItem(UUID catalogItemID, UpdateCatalogItem input) {
        CatalogItem catalogItem = catalogRepository.findById(catalogItemID)
                .orElseThrow(() -> new EntryNotFoundException("Catalog Item Not Found"));

        if (input.getPrice() != null) catalogItem.setPrice(input.getPrice());
        if (input.getDisPrice() != null) catalogItem.setDiscountedPrice(input.getDisPrice());
        if (input.getImgUrl() != null) catalogItem.setImgUrl(input.getImgUrl());
        if (input.getDescription() != null) catalogItem.setDescription(input.getDescription());

        return toSummary(catalogRepository.save(catalogItem));
    }

    @Override
    @Transactional(readOnly = true)
    public long countCatalogItems(UUID orgID) {
        return catalogRepository.countByOrg_Id(orgID);
    }

    private CatalogItemSummary toSummary(CatalogItem catalogItem) {
        return new CatalogItemSummary(
                catalogItem.getId(),
                catalogItem.getItem().getId(),
                catalogItem.getItem().getItemName(),
                catalogItem.getOrg().getId(),
                catalogItem.getPrice(),
                catalogItem.getDiscountedPrice(),
                catalogItem.getImgUrl(),
                catalogItem.getDescription()
        );
    }
}
