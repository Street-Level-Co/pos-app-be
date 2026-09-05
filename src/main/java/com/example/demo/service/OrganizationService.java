package com.example.demo.service;

import com.example.demo.model.ClientOrganization;
import com.example.demo.model.Organization;
import com.example.demo.model.User;
import com.example.demo.transfer.create.CreateOrganization;
import com.example.demo.transfer.create.CreateOrganizationForUser;
import com.example.demo.transfer.update.AddUser;
import com.example.demo.transfer.update.UpdateOrganization;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface OrganizationService {
    Organization createOrganization(@Valid CreateOrganization input);

    Organization createOrganizationForUser(UUID userId, @Valid CreateOrganizationForUser input);

    Organization getOrganization(UUID organizationID);

    Organization updateOrganization(UUID organizationID, @Valid UpdateOrganization input);

    ClientOrganization addUser(@Valid AddUser input);

    List<Organization> getOrganizationsByUser(UUID userId);

    List<User> getUsersByOrganization(UUID organizationId);
}
