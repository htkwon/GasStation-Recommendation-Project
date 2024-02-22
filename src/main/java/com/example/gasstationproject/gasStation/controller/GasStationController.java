package com.example.gasstationproject.gasStation.controller;

import com.example.gasstationproject.direction.cache.GasStationRedisTemplateService;
import com.example.gasstationproject.gasStation.dto.GasStationDto;
import com.example.gasstationproject.gasStation.service.GasStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GasStationController {

    private final GasStationService gasStationService;
    private final GasStationRedisTemplateService gasStationRedisTemplateService;


    @GetMapping("/csv/save")
    public void saveToRedis(){
        saveDataToRedis();
    }

    public void saveDataToRedis(){
        List<GasStationDto> gasStationDtoList = gasStationService.findAll()
                .stream()
                .map(gasStation -> GasStationDto.builder()
                        .id(gasStation.getId())
                        .gasStationName(gasStation.getGasStationName())
                        .gasStationAddress(gasStation.getGasStationAddress())
                        .latitude(gasStation.getLatitude())
                        .longitude(gasStation.getLongitude())
                        .build())
                .collect(Collectors.toList());
        gasStationDtoList.forEach(gasStationRedisTemplateService::save);
    }
}
