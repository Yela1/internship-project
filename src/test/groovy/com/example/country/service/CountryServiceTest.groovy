package com.example.country.service

import com.example.country.models.Country
import com.example.country.repository.country.CountryRepository
import spock.lang.Specification

class CountryServiceTest extends Specification{

    CountryRepository countryRepository = Mock()

    CountryService countryService = new CountryService(countryRepository)

    def "getAll should return list of countries"(){
        given:
            Country country1 = new Country(name: "KAZAKHSTAN", code: "KZ")
            Country country2 = new Country(name: "RUSSIA", code: "RU")
            ArrayList countries = [country1, country2]

        when:
            def result = countryService.getAll()

        then:
            1 * countryRepository.findAll() >> countries

        and:
            countries[0] == result[0]
            countries[1] == result[1]

    }
}



