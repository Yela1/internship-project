package com.example.country.service

import com.example.country.exception.RoomNotFoundException
import com.example.country.models.Room
import com.example.country.repository.room.RoomRepository
import spock.lang.Specification


class RoomServiceTest extends Specification {

    RoomRepository roomRepository = Mock()

    RoomService roomService = new RoomService(roomRepository)

    def "get should return room IF found"() {

        given:
            Room room = new Room(id: 1L, name: "Kazakhstan", status: true, countryCode: "KZ")

        when:
            def result = roomService.get(1L)

        then:
            1 * roomRepository.findById(1L) >> Optional.of(room)

        and:
            result.getId() == 1L

    }

    def "get should throw exception IF room doesnt exist"() {

        given:
            roomRepository.findById(1L) >> Optional.empty()

        when:
            roomService.get(1L)

        then:
            thrown(RoomNotFoundException)

    }

    def "getAll should return all rooms"() {

        given:
            def room1 = new Room(id: 1L, name: "TEST1", status: true, countryCode: "TEST1")
            def room2 = new Room(id: 2L, name: "TEST2", status: true, countryCode: "TEST2")

        when:
            def result = roomService.getAll()

        then:
            1 * roomRepository.findAll() >> [room1, room2]

        and:
            result[0] == room1

    }

    def "create method should create room"() {

        given:
            Room room = new Room(id: 1L, name: "TEST1", status: true, countryCode: "TEST1")

        when:
            def result = roomService.create(room)

        then:
            1 * roomRepository.save(room) >> room

        and:
            result.getName() == "TEST1"

    }

    def "delete should throw exception if room doesnt exist"() {

        given:
            roomRepository.findById(1L) >> Optional.empty()

        when:
            roomService.delete(1L)

        then:
            thrown(RoomNotFoundException)

    }

    def "delete should remove room if exist"() {

        given:
            Room room = new Room(id: 1L, name: "TEST1", status: true, countryCode: "TEST1")

        when:
            roomService.delete(1L)

        then:
            roomRepository.findById(1L) >> Optional.of(room)
            roomRepository.delete(room) >> Optional.empty()

    }

    def "update should throw exception if room doesnt exist"(){

        given:
            roomRepository.findById(1L) >> Optional.empty()

        when:
            roomService.updateStatus(1L)

        then:
            thrown(RoomNotFoundException)

    }

    def "update"(){
        given:
            Room room = new Room(id: 1L, name: "TEST1", status: true, countryCode: "TEST1")

        when:
            roomService.updateStatus(1L)

        then: "We find the room we need, and change the status of the room to another"
            1 * roomRepository.findById(1L) >> Optional.of(room)
            1 * roomRepository.save(room) >> room

        and:
            !room.isStatus()
    }

    def "createAll should create all rooms "(){
        given:
            Room room1 = new Room(id: 1L, name: "TEST1", status: true, countryCode: "TEST1")
            Room room2 = new Room(id: 2L, name: "TEST2", status: true, countryCode: "TEST2")
            ArrayList rooms = [room1, room2]

        when:
            def result = roomService.createAll(rooms)

        then:
            1 * roomRepository.saveAll(rooms) >> rooms

        and:
            rooms[0] == result[0]
            rooms[1] == result[1]
    }




}