package com.example.demo.service;

import com.example.demo.transfer.CatalogItemSummary;
import com.example.demo.transfer.CreateCatalogItem;
import com.example.demo.transfer.update.UpdateCatalogItem;

import java.util.List;
import java.util.UUID;

public interface CatalogService {

    CatalogItemSummary createCatalogItem(CreateCatalogItem catalogItem);

    List<CatalogItemSummary> getAllCatalogItems(UUID orgID);

    CatalogItemSummary getCatalogItem(UUID catalogItemID);

    CatalogItemSummary updateCatalogItem(UUID catalogItemID, UpdateCatalogItem input);

    long countCatalogItems(UUID orgID);
}
