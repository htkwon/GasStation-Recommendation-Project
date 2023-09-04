package com.example.gasstationproject.direction.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OutputDto {

    private String gasStationName;
    private String gasStationAddress;
    private String directionUrl;
    private String roadViewUrl;
    private String distance;


}
