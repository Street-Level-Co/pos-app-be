package com.example.demo.service.impl;

import com.example.demo.exception.EntryNotFoundException;
import com.example.demo.model.Client;
import com.example.demo.model.ClientOrganization;
import com.example.demo.model.Organization;
import com.example.demo.model.UserOrganization;
import com.example.demo.repository.ClientOrganizationRepository;
import com.example.demo.repository.OrganizationRepository;
import com.example.demo.repository.UserOrganizationRepository;
import com.example.demo.service.ClientService;
import com.example.demo.service.CountryService;
import com.example.demo.service.OrganizationService;
import com.example.demo.transfer.create.CreateOrganization;
import com.example.demo.transfer.update.AddUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    @Override
    public Organization createOrganization(CreateOrganization input) {

        UUID orgID = UUID.randomUUID();
        var client = clientService.getClient(input.getClientID());
        List<ClientOrganization> clientOrganizationsExist = clientOrganizationRepository.findAllByClient(client.getId());
        clientOrganizationsExist.add(new ClientOrganization(
                UUID.randomUUID(),
                client.getId(),
                orgID
        ));

        return organizationRepository.save(new Organization(
                orgID,
                input.getName(),
                input.getAddress(),
                input.getContact(),
                input.getBrNumber(),
                countryService.findCountry(input.getCountry()),
                null,
                clientOrganizationsExist
        ));
    }

    @Override
    public Organization getOrganization(UUID organizationID) {
        return organizationRepository.findById(organizationID)
                .orElseThrow(() -> new EntryNotFoundException("Organization not found"));
    }

    @Override
    public ClientOrganization addUser(AddUser input) {
        Organization organization = organizationRepository.findById(input.getOrgID())
                .orElseThrow(() -> new EntryNotFoundException("Organization not found"));
        Client client = clientService.getClient(input.getClientID());
        return clientOrganizationRepository.save(new ClientOrganization(
                UUID.randomUUID(),
                client.getId(),
                organization.getId()
        ));
    }

    @Override
    public List<UserOrganization> getOrganizationsByUser(UUID userId) {
        return userOrganizationRepository.findAllByUser_Id(userId);
    }

    @Override
    public List<UserOrganization> getUsersByOrganization(UUID organizationId) {
        return userOrganizationRepository.findAllByOrg_Id(organizationId);
    }
}
