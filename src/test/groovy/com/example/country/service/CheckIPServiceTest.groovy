package com.example.country.service

import com.example.country.models.Room
import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Specification

class CheckIPServiceTest extends Specification {


    CheckIPService checkIPService = new CheckIPService()

    def setup(){
        ReflectionTestUtils.setField(checkIPService, "key", "MxP8cy6HjNKNHiHU")
        ReflectionTestUtils.setField(checkIPService, "clientId", 565526)
        ReflectionTestUtils.setField(checkIPService, "host", "geolite.info")

    }
    def "checkIp should return false when country is different"(){
        given:
            def room = new Room(1L, "RUSSIA", true, "RU")
            "ip for Kazakhstan"
            def ip = InetAddress.getByName("2.132.176.34")
        when:
            def result = checkIPService.checkIp(room, ip)

        then:
            !result
    }

    def "checkIp should throw exceptions if credentials is wrong"(){
        given:
            ReflectionTestUtils.setField(checkIPService, "clientId", 1)
            def room = new Room(1L, "RUSSIA", true, "RU")
            def ip = InetAddress.getByName("2.132.176.34")

        when:
            def result = checkIPService.checkIp(room,ip)

        then:
            !result

    }

    def "checkIp should return true if everything is ok"(){
        given:
            def room = new Room(1L, "KAZAKHSTAN", true, "KZ")
            def ip = InetAddress.getByName("2.132.176.34")

        when:
            def result = checkIPService.checkIp(room,ip)

        then:
            result
    }

}
