package com.example.country.controller


import com.example.country.models.Room
import com.example.country.service.CheckIPService
import com.example.country.service.GetIPService
import com.example.country.service.RoomService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class RoomControllerTest extends Specification{

    RoomService roomService = Mock()

    CheckIPService checkIPService = Mock()

    GetIPService getIPService = Mock()

    RoomController restApi = new RoomController(roomService, checkIPService, getIPService)

    MockMvc mockMvc = standaloneSetup(restApi).build()

    def setup(){
        ReflectionTestUtils.setField(restApi, "uri", "217.196.24.251")
        ReflectionTestUtils.setField(checkIPService, "key", "dssds")
        ReflectionTestUtils.setField(checkIPService, "clientId", 1)
        ReflectionTestUtils.setField(checkIPService, "host", "dssds")

    }


    def "getAllRoom should return list of room as json"(){
        given:
            def room = new Room(name: "Kazakhstan", countryCode: "KZ")
            def room1 = new Room(name:"Russia", countryCode: "RU")
            def list = [room,room1]
            def json = new ObjectMapper().writeValueAsString(list)

        when:
            mockMvc.perform(get("/rooms"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(json))

        then:
            1 * roomService.getAll() >> list

    }

    def "getRoom should return status 403"(){
        given:
            def room = new Room(id: 1L,name: "Kazakhstan", countryCode: "KZ")
            def stringIp = "217.196.24.251"
            def ip = InetAddress.getByName("217.196.24.251")

        when:
            mockMvc.perform(get("/rooms/{id}",1L)).andExpect(status().isForbidden())

        then:
            1 * roomService.get(1L) >> room
            1 * getIPService.getIp(stringIp) >> ip
            1 * checkIPService.checkIp( room, ip) >> false
    }

    def "getRoom should return status 200"(){
        given:
            def room = new Room(id: 1L,name: "Kazakhstan", countryCode: "KZ")
            def stringIp = "217.196.24.251"
            def ip = InetAddress.getByName("217.196.24.251")
            def json =  new ObjectMapper().writeValueAsString(room)

        when:
            mockMvc.perform(get("/rooms/{id}",1L))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().json(json))

        then:
            1 * roomService.get(1L) >> room
            1 * getIPService.getIp(stringIp) >> ip
            1 * checkIPService.checkIp( room, ip) >> true
    }

    def "createRoom should return status 201"() {
        given:
            def room = new Room(id:1L,name: "KAZAKHSTAN",status: true, countryCode: "KZ")
            def json = new ObjectMapper().writeValueAsString(room)

        when:
            mockMvc.perform(post("/rooms")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(json))

        then:
            1 * roomService.create(room) >> room
    }


    def "createAll should return status 201"(){
        given:
            def room1 = new Room(id:1L,name: "KAZAKHSTAN",status: true, countryCode: "KZ")
            def room2 = new Room(id:1L,name: "KAZAKHSTAN",status: true, countryCode: "KZ")
            def array = [room1, room2]
            def json = new ObjectMapper().writeValueAsString(array)

        when:
            mockMvc.perform(post("/rooms/all")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(json))

        then:
            1 * roomService.createAll(array) >> array
    }

    def "deleteRoom should delete and return status 204"(){
        when:
            mockMvc.perform(delete("/rooms/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Element with id - 1 was deleted"))

        then:
            1 * roomService.delete(1L)

    }

    def "updateRoom should update and return status 200"(){
        given:
            def room = new Room(id:1L,name: "KAZAKHSTAN",status: true, countryCode: "KZ")

        when:
            mockMvc.perform(put("/rooms/{id}", 1L))
                    .andExpect(status().isOk())

        then:
            1 * roomService.updateStatus(1L)
            1 * roomService.get(1L) >> room

    }
}
