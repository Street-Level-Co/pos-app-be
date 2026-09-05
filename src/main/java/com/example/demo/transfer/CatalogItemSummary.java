package com.example.demo.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CatalogItemSummary {
    private UUID id;
    private UUID itemID;
    private String itemName;
    private UUID orgID;
    private BigDecimal price;
    private BigDecimal discountedPrice;
    private String imgUrl;
    private String description;
}
