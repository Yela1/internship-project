package com.example.country.service;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.stream.Collectors;

//@Component
public class GetIPService {

    public InetAddress getIp(String uri) throws IOException {
        URL url = new URL(uri);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        return InetAddress.getByName(br.lines().collect(Collectors.joining()));
    }
}
