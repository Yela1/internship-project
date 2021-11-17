package com.example.country.controller

import com.example.country.models.Country
import com.example.country.service.CountryService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import spock.lang.Specification
import org.springframework.test.web.servlet.MockMvc

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class CountryControllerTest extends Specification{

    CountryService countryService = Mock()

    CountryController restApi = new CountryController(countryService)

    MockMvc mockMvc = standaloneSetup(restApi).build()

    def "getAll should return all country"(){
        given:
        def country1 = new Country(name: "Kazakhstan", code: "KZ")
        def country2 = new Country(name:"Russia", code: "RU")
        def countries = [country1,country2]
        def json = new ObjectMapper().writeValueAsString(countries)

        when:
            mockMvc.perform(get("/country"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json))

        then:
            1 * countryService.getAll() >> countries

    }

}
