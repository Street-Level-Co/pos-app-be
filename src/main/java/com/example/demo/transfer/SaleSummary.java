package com.example.demo.transfer;

import com.example.demo.model.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaleSummary {
    private UUID id;
    private UUID orgID;
    private UUID clientID;
    private Long customerMobile;
    private BigDecimal subtotal;
    private DiscountType discountType;
    private BigDecimal discountValue;
    private BigDecimal totalAmount;
    private Instant createdAt;
    private List<SaleItemSummary> items;
}
