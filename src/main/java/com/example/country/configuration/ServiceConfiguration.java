package com.example.country.configuration;


import com.example.country.repository.country.CountryRepository;
import com.example.country.repository.room.RoomRepository;
import com.example.country.service.CheckIPService;
import com.example.country.service.CountryService;
import com.example.country.service.GetIPService;
import com.example.country.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ServiceConfiguration {

    private final RoomRepository roomRepository;
    private final CountryRepository countryRepository;


    @Bean
    public RoomService roomServiceBean(){
        return new RoomService(roomRepository);
    }

    @Bean
    public CountryService countryRepositoryBean(){
        return new CountryService(countryRepository);
    }

    @Bean
    public GetIPService getIPServiceBean(){
        return new GetIPService();
    }

    @Bean
    public CheckIPService checkIPService(){
        return new CheckIPService();
    }
}
