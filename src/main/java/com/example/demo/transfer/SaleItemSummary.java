package com.example.demo.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaleItemSummary {
    private UUID catalogItemID;
    private String itemName;
    private Integer qty;
    private BigDecimal price;
    private BigDecimal discountPrice;
}
