package com.example.demo.controller;

import com.example.demo.service.SalesService;
import com.example.demo.transfer.create.CreateSale;
import com.example.demo.util.StandardResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/sales")
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;

    @PostMapping("register")
    public ResponseEntity<StandardResponse> registerSale(@RequestBody @Valid CreateSale input) {
        return new ResponseEntity<>(
                new StandardResponse("Success", salesService.registerSale(input)),
                HttpStatus.CREATED
        );
    }

    @GetMapping("all/{orgID}")
    public ResponseEntity<StandardResponse> getAllSales(
            @PathVariable UUID orgID,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        return new ResponseEntity<>(
                new StandardResponse(
                        "Success",
                        salesService.getAllSales(orgID, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")))
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("count/{orgID}")
    public ResponseEntity<StandardResponse> countSales(@PathVariable UUID orgID) {
        return new ResponseEntity<>(
                new StandardResponse("Success", salesService.countSales(orgID)),
                HttpStatus.OK
        );
    }
}
