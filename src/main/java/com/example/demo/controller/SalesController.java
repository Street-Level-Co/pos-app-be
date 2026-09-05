package com.example.demo.controller;

import com.example.demo.service.SalesService;
import com.example.demo.transfer.create.CreateSale;
import com.example.demo.util.StandardResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
