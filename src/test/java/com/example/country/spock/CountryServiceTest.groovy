package com.example.country.spock

import com.example.country.models.Country
import com.example.country.repository.CountryRepository
import com.example.country.service.CountryService
import spock.lang.Specification

class CountryServiceTest extends Specification{
    CountryRepository countryRepository = Mock()

    CountryService countryService = new CountryService(countryRepository)

    def "getAll"(){
        given:
            Country country1 = new Country(name: "KAZAKHSTAN", code: "KZ")
            Country country2 = new Country(name: "RUSSIA", code: "RU")
            ArrayList countries = [country1, country2]

        when:
            def result = countryService.getAll()

        then:
            1 * countryRepository.findAll() >> countries

        and:
            for(int i = 0; i < countries.size(); i++) {
                countries[i] == result[i]
            }

    }
}



