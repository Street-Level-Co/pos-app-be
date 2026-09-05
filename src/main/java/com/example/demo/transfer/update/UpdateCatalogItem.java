package com.example.demo.transfer.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCatalogItem {
    private BigDecimal price;
    private BigDecimal disPrice;
    private String imgUrl;
    private String description;
}