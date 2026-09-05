package com.example.demo.transfer.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateOrganizationForUser {

    @NotBlank(message = "Name is required")
    private String name;
    private String address;
    private Long contact;
    private String brNumber;
    @NotNull(message = "Country is required")
    private UUID country;
    private Map<String, Object> additionalDeclaration;
}