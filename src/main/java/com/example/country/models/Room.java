package com.example.country.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "room")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private boolean status=true;

    @Column(name = "countryCode")
    private String countryCode;
    public Room(String name, String countryCode){
        this.name = name;
        this.countryCode = countryCode;
    }



}
