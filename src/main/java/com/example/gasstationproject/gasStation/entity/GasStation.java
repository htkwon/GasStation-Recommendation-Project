package com.example.gasstationproject.gasStation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="gasstation")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GasStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gasStationName;
    private String gasStationAddress;
    private double latitude;
    private double longitude;



    public void changeGasStationAddress(String address){
        this.gasStationAddress = address;
    }



}
