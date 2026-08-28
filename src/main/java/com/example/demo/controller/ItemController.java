package com.example.demo.controller;

import com.example.demo.service.ItemService;
import com.example.demo.util.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/item")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("all")
    public ResponseEntity<StandardResponse> getClientOrganizations(@RequestParam(required = false) String keyword,
                                                                   @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                   @RequestParam(required = false, defaultValue = "10") Integer size) {
        return new ResponseEntity<>(
                new StandardResponse("Success !", itemService.findAll(keyword, PageRequest.of(page, size))),
                HttpStatus.OK
        );
    }
}
