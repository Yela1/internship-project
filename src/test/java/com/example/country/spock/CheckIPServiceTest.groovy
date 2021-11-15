package com.example.country.spock

import com.example.country.models.Room
import com.example.country.service.CheckIPService
import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Specification

import com.maxmind.geoip2.exception.GeoIp2Exception

class CheckIPServiceTest extends Specification {


    CheckIPService checkIPService = new CheckIPService()

    def setup(){
        ReflectionTestUtils.setField(checkIPService, "key", "MxP8cy6HjNKNHiHU")
        ReflectionTestUtils.setField(checkIPService, "clientId", 565526)
        ReflectionTestUtils.setField(checkIPService, "host", "geolite.info")

    }
    def "get false when country is different"(){
        given:
            def room = new Room(1L, "RUSSIA", true, "RU")
            def ip = InetAddress.getByName("2.132.176.34")
        when:
            def result = checkIPService.checkIp(room, ip)

        then:
            !result
    }

    def "get exception if client id wrong"(){
        given:
            ReflectionTestUtils.setField(checkIPService, "clientId", 1)
            def room = new Room(1L, "RUSSIA", true, "RU")
            def ip = InetAddress.getByName("2.132.176.34")

        when:
            def result = checkIPService.checkIp(room,ip)

        then:

            !result

    }

    def "checkIp"(){
        given:
            def room = new Room(1L, "KAZAKHSTAN", true, "KZ")
            def ip = InetAddress.getByName("2.132.176.34")

        when:
            def result = checkIPService.checkIp(room,ip)

        then:
            result
    }

}
