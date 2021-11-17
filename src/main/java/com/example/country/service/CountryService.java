package com.example.country.service;


import com.example.country.models.Country;
import com.example.country.repository.country.CountryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;


    public List<Country> getAll(){
        return countryRepository.findAll();
    }
}
