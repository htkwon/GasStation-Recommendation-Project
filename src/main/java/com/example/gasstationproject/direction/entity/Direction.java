package com.example.gasstationproject.direction.entity;

import com.example.gasstationproject.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="direction")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Direction extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String inputAddress;
    private double inputLatitude;
    private double inputLongitude;

    private String targetGasStationName;
    private String targetAddress;
    private double targetLatitude;
    private double targetLongitude;

    private double distance;


}
