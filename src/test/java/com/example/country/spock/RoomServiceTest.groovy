package com.example.country.spock

import com.example.country.exception.RoomNotFoundException
import com.example.country.models.Room
import com.example.country.repository.RoomRepository
import com.example.country.service.RoomService
import spock.lang.Specification


class RoomServiceTest extends Specification {

    RoomRepository roomRepository = Mock()

    RoomService roomService = new RoomService(roomRepository)

    def "get"() {

        given:
            Room room = new Room(id: 1L, name: "Sdsd", status: true, countryCode: "sdsds")

        and:
            roomRepository.findById(1L) >> Optional.of(room)

        when:
            def result = roomService.get(1L)

        then:
            0 * roomRepository.findById(1L)

        and:
            result.getId() == 1L

    }

    def "getRoomNotFoundException"() {

        given:
            roomRepository.findById(1L) >> Optional.empty()

        when:
            roomService.get(1L)

        then:
            thrown(RoomNotFoundException)

    }

    def "getAllRooms"() {

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

    def "create"() {

        given:
            Room room = new Room(id: 1L, name: "TEST1", status: true, countryCode: "TEST1")

        when:
            def result = roomService.create(room)

        then:
            1 * roomRepository.save(room) >> room

        and:
            result.getName() == "TEST1"

    }

    def "deleteThrownExceptionWhenRoomDoesntExist"() {

        given:
            roomRepository.findById(1L) >> Optional.empty()

        when:
            roomService.delete(1L)

        then:
            thrown(RoomNotFoundException)

    }

    def "delete"() {

        given:
            Room room = new Room(id: 1L, name: "TEST1", status: true, countryCode: "TEST1")

        when:
            roomService.delete(1L)

        then:
            roomRepository.findById(1L) >> Optional.of(room)
            roomRepository.delete(room) >> Optional.empty()

    }

    def "updateThrownExceptionIfRoomDoesn'tExist"(){

        given:
            roomRepository.findById(1L) >> Optional.empty();

        when:
            roomService.update(1L)

        then:
            thrown(RoomNotFoundException)

    }

    def "update"(){
        given:
            Room room = new Room(id: 1L, name: "TEST1", status: true, countryCode: "TEST1")

        when:
            roomService.update(1L)

        then:
            1 * roomRepository.findById(1L) >> Optional.of(room)
            1 * roomRepository.save(room) >> room

        and:
            println(room.isStatus())
            !room.isStatus()
    }

    def "createAll"(){
        given:
            Room room1 = new Room(id: 1L, name: "TEST1", status: true, countryCode: "TEST1")
            Room room2 = new Room(id: 2L, name: "TEST2", status: true, countryCode: "TEST2")
            ArrayList rooms = [room1, room2]

        when:
            def result = roomService.createAll(rooms)

        then:
            1 * roomRepository.saveAll(rooms) >> rooms

        and:
            for (int i = 0; i < rooms.size(); i++) {
                rooms[i] == result[i]
            }
    }




}