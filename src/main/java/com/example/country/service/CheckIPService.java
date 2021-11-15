package com.example.country.service;

import com.example.country.models.Room;
import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;

//@Component
public class CheckIPService {

    @Value("${web.service.client.key}")
    private String key;

    @Value("${web.service.client.id}")
    private Integer clientId;

    @Value("${web.service.client.host}")
    private String host;



    public Boolean checkIp(Room room, InetAddress ipAddress) throws IOException {
        try (WebServiceClient client = new WebServiceClient.Builder(clientId, key).host(host).build()) {
            String IsoCode = client.country(ipAddress).getCountry().getIsoCode();
            if (!IsoCode.equals(room.getCountryCode())) {
                return false;
            }

        } catch (GeoIp2Exception e) {
            return false;
        }
        return true;
    }


}
