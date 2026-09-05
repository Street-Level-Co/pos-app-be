package com.example.demo.transfer.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @NotEmpty(message = "At least one item is required")
    @Valid
    private List<CreateSaleItem> items;
}
