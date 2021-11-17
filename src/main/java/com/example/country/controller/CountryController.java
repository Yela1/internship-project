package com.example.country.controller;


import com.example.country.models.Country;
import com.example.country.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;


    @GetMapping()
    public ResponseEntity<List<Country>> getAllCountry() {
        return ResponseEntity.status(HttpStatus.OK).body(countryService.getAll());
    }




}
