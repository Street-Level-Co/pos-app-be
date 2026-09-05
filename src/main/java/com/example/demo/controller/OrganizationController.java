package com.example.demo.controller;

import com.example.demo.service.OrganizationService;
import com.example.demo.transfer.create.CreateOrganization;
import com.example.demo.transfer.create.CreateOrganizationForUser;
import com.example.demo.transfer.update.AddUser;
import com.example.demo.transfer.update.UpdateOrganization;
import com.example.demo.util.StandardResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping("{organizationID}")
    public ResponseEntity<StandardResponse> getOrganization(@PathVariable UUID organizationID) {
        return new ResponseEntity<>(
                new StandardResponse("Success", organizationService.getOrganization(organizationID)),
                HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<StandardResponse> registerClient(@RequestBody @Valid CreateOrganization input) {
        return new ResponseEntity<>(
                new StandardResponse("Success", organizationService.createOrganization(input)),
                HttpStatus.CREATED
        );
    }

    @PostMapping("register-for-user/{userId}")
    public ResponseEntity<StandardResponse> registerOrganizationForUser(
            @PathVariable UUID userId,
            @RequestBody @Valid CreateOrganizationForUser input) {
        return new ResponseEntity<>(
                new StandardResponse("Success", organizationService.createOrganizationForUser(userId, input)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{organizationID}")
    public ResponseEntity<StandardResponse> updateOrganization(@PathVariable UUID organizationID, @RequestBody @Valid UpdateOrganization input) {
        return new ResponseEntity<>(
                new StandardResponse("Success", organizationService.updateOrganization(organizationID, input)),
                HttpStatus.OK
        );
    }

    @PostMapping("add-user")
    public ResponseEntity<StandardResponse> addUser(@RequestBody @Valid AddUser input) {
        return new ResponseEntity<>(
                new StandardResponse("Success", organizationService.addUser(input)),
                HttpStatus.CREATED
        );
    }

    @GetMapping("organizations-by-user/{userId}")
    public ResponseEntity<StandardResponse> getOrganizationsByUser(@PathVariable UUID userId) {
        return new ResponseEntity<>(
                new StandardResponse("Success", organizationService.getOrganizationsByUser(userId)),
                HttpStatus.CREATED
        );
    }

    @GetMapping("users-by-organization/{organizationId}")
    public ResponseEntity<StandardResponse> getUsersByOrganization(@PathVariable UUID organizationId) {
        return new ResponseEntity<>(
                new StandardResponse("Success", organizationService.getUsersByOrganization(organizationId)),
                HttpStatus.CREATED
        );
    }
}
