package com.example.gasstationproject.gasStation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GasStationDto {

    private Long id;
    private String gasStationName;
    private String gasStationAddress;
    private double latitude;
    private double longitude;





}
