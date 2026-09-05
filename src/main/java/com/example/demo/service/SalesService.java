package com.example.demo.service;

import com.example.demo.transfer.SaleSummary;
import com.example.demo.transfer.create.CreateSale;

public interface SalesService {

    SaleSummary registerSale(CreateSale input);
}
