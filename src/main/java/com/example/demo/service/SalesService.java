package com.example.demo.service;

import com.example.demo.transfer.SaleSummary;
import com.example.demo.transfer.create.CreateSale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SalesService {

    SaleSummary registerSale(CreateSale input);

    Page<SaleSummary> getAllSales(UUID orgId, Pageable pageable);

    long countSales(UUID orgId);
}
