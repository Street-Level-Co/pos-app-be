package com.example.demo.service;

import com.example.demo.model.ClientOrganization;
import com.example.demo.model.Organization;
import com.example.demo.model.UserOrganization;
import com.example.demo.transfer.create.CreateOrganization;
import com.example.demo.transfer.update.AddUser;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface OrganizationService {
    Organization createOrganization(@Valid CreateOrganization input);

    Organization getOrganization(UUID organizationID);

    ClientOrganization addUser(@Valid AddUser input);

    List<UserOrganization> getOrganizationsByUser(UUID userId);

    List<UserOrganization> getUsersByOrganization(UUID organizationId);
}
