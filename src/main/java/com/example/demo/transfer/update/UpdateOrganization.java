package com.example.demo.transfer.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateOrganization {
    private String name;
    private String address;
    private Long contact;
    private String brNumber;
    private UUID country;
    private Map<String, Object> additionalDeclaration;
}
