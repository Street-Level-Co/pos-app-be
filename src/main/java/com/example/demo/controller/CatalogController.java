package com.example.demo.controller;

import com.example.demo.service.CatalogService;
import com.example.demo.transfer.CreateCatalogItem;
import com.example.demo.transfer.update.UpdateCatalogItem;
import com.example.demo.util.StandardResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("api/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @PostMapping("register")
    public ResponseEntity<StandardResponse> registerCatalog(@RequestBody @Valid CreateCatalogItem input) {
        return new ResponseEntity<>(
                new StandardResponse("Success", catalogService.createCatalogItem(input)),
                HttpStatus.CREATED
        );
    }

    @GetMapping("all/{orgID}")
    public ResponseEntity<StandardResponse> getAllCatalogItems(@PathVariable UUID orgID) {
        return new ResponseEntity<>(
                new StandardResponse("Success", catalogService.getAllCatalogItems(orgID)),
                HttpStatus.OK
        );    }

    @GetMapping("count/{orgID}")
    public ResponseEntity<StandardResponse> countCatalogItems(@PathVariable UUID orgID) {
        return new ResponseEntity<>(
                new StandardResponse("Success", catalogService.countCatalogItems(orgID)),
                HttpStatus.OK
        );
    }

    @GetMapping("{catalogItemID}")
    public ResponseEntity<StandardResponse> getCatalogItem(@PathVariable UUID catalogItemID) {
        return new ResponseEntity<>(
                new StandardResponse("Success", catalogService.getCatalogItem(catalogItemID)),
                HttpStatus.OK
        );    }

    @PutMapping("{catalogItemID}")
    public ResponseEntity<StandardResponse> updateCatalogItem(@PathVariable UUID catalogItemID, @RequestBody @Valid UpdateCatalogItem input) {
        return new ResponseEntity<>(
                new StandardResponse("Success", catalogService.updateCatalogItem(catalogItemID, input)),
                HttpStatus.OK
        );
    }
}
