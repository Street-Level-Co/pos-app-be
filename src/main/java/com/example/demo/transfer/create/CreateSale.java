package com.example.demo.transfer.create;

import com.example.demo.model.DiscountType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateSale {
    @NotNull(message = "Organization required")
    private UUID orgID;

    /** Optional customer mobile number captured at checkout. */
    private Long customerMobile;

    /** Optional whole-sale discount; provide both fields together, or neither. */
    private DiscountType discountType;
    private BigDecimal discountValue;

    @NotEmpty(message = "At least one item is required")
    @Valid
    private List<CreateSaleItem> items;
}
