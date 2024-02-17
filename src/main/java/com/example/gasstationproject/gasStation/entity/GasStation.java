package com.example.gasstationproject.gasStation.entity;

import com.example.gasstationproject.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name="gas_station")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GasStation extends BaseTimeEntity {

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
