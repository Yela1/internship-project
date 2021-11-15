package com.example.country.spock

import com.example.country.service.GetIPService
import spock.lang.Specification

class GetIPServiceTest extends Specification {
    GetIPService getIPService = new GetIPService()

    def "get ip"(){
        given:
            def uri = "https://checkip.amazonaws.com/"
            def expectedIp = InetAddress.getByName("2.132.176.34")

        expect:
            getIPService.getIp(uri) == expectedIp

    }
}
