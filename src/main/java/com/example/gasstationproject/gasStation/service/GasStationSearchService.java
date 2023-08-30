package com.example.gasstationproject.gasStation.service;

import com.example.gasstationproject.gasStation.dto.GasStationDto;
import com.example.gasstationproject.gasStation.entity.GasStation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GasStationSearchService {

    private final GasStationService gasStationService;

    public List<GasStationDto> searchGasStationDtoList(){

        //redis


        //db
        return gasStationService.findAll()
                .stream()
                .map(this::convertToGasStationDto)
                .collect(Collectors.toList());


    }

    private GasStationDto convertToGasStationDto(GasStation gasStation){
        return GasStationDto.builder()
                .id(gasStation.getId())
                .gasStationAddress(gasStation.getGasStationAddress())
                .gasStationName(gasStation.getGasStationName())
                .longitude(gasStation.getLongitude())
                .latitude(gasStation.getLatitude())
                .build();
    }


}
