package com.example.demo.service.impl;

import com.example.demo.exception.EntryNotFoundException;
import com.example.demo.model.Client;
import com.example.demo.model.ClientOrganization;
import com.example.demo.model.Organization;
import com.example.demo.model.User;
import com.example.demo.model.UserOrganization;
import com.example.demo.repository.ClientOrganizationRepository;
import com.example.demo.repository.OrganizationRepository;
import com.example.demo.repository.UserOrganizationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ClientService;
import com.example.demo.service.CountryService;
import com.example.demo.service.OrganizationService;
import com.example.demo.transfer.create.CreateOrganization;
import com.example.demo.transfer.create.CreateOrganizationForUser;
import com.example.demo.transfer.update.AddUser;
import com.example.demo.transfer.update.UpdateOrganization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    private final ClientOrganizationRepository  clientOrganizationRepository;

    private final CountryService countryService;

    private final ClientService clientService;

    private final UserOrganizationRepository userOrganizationRepository;

    private final UserRepository userRepository;

    @Override
    public Organization createOrganizationForUser(UUID userId, CreateOrganizationForUser input) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntryNotFoundException("User not found"));

        Organization organization = organizationRepository.save(new Organization(
                UUID.randomUUID(),
                input.getName(),
                input.getAddress(),
                input.getContact(),
                input.getBrNumber(),
                countryService.findCountry(input.getCountry()),
                input.getAdditionalDeclaration(),
                new ArrayList<>()
        ));

        userOrganizationRepository.save(new UserOrganization(user, organization));

        return organization;
    }

    @Override
    public Organization createOrganization(CreateOrganization input) {

        UUID orgID = UUID.randomUUID();
        var client = clientService.getClient(input.getClientID());

        Organization organization = new Organization(
                orgID,
                input.getName(),
                input.getAddress(),
                input.getContact(),
                input.getBrNumber(),
                countryService.findCountry(input.getCountry()),
                null,
                new ArrayList<>()
        );

        List<ClientOrganization> clientOrganizationsExist = clientOrganizationRepository.findAllByClient_Id(client.getId());
        clientOrganizationsExist.add(new ClientOrganization(client, organization));
        organization.setClientOrganizations(clientOrganizationsExist);

        return organizationRepository.save(organization);
    }

    @Override
    public Organization getOrganization(UUID organizationID) {
        return organizationRepository.findById(organizationID)
                .orElseThrow(() -> new EntryNotFoundException("Organization not found"));
    }

    @Override
    public Organization updateOrganization(UUID organizationID, UpdateOrganization input) {
        Organization organization = organizationRepository.findById(organizationID)
                .orElseThrow(() -> new EntryNotFoundException("Organization not found"));

        if (input.getName() != null) organization.setOrgName(input.getName());
        if (input.getAddress() != null) organization.setOrgAddress(input.getAddress());
        if (input.getContact() != null) organization.setOrgContact(input.getContact());
        if (input.getBrNumber() != null) organization.setBrNumber(input.getBrNumber());
        if (input.getCountry() != null) organization.setCountry(countryService.findCountry(input.getCountry()));
        if (input.getAdditionalDeclaration() != null) organization.setAdditionalDeclaration(input.getAdditionalDeclaration());

        return organizationRepository.save(organization);
    }

    @Override
    public ClientOrganization addUser(AddUser input) {
        Organization organization = organizationRepository.findById(input.getOrgID())
                .orElseThrow(() -> new EntryNotFoundException("Organization not found"));
        Client client = clientService.getClient(input.getClientID());
        return clientOrganizationRepository.save(new ClientOrganization(client, organization));
    }

    @Override
    public List<Organization> getOrganizationsByUser(UUID userId) {
        return userOrganizationRepository.findOrganizationsByUserId(userId);
    }

    @Override
    public List<User> getUsersByOrganization(UUID organizationId) {
        return userOrganizationRepository.findUsersByOrgId(organizationId);
    }
}
