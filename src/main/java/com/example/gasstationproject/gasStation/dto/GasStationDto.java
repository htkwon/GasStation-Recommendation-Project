package com.example.gasstationproject.gasStation.dto;

import lombok.*;

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
