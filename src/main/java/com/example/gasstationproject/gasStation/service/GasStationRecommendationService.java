package com.example.gasstationproject.gasStation.service;

import com.example.gasstationproject.api.dto.DocumentDto;
import com.example.gasstationproject.api.dto.KakaoApiResponseDto;
import com.example.gasstationproject.api.service.KakaoAddressSearchService;
import com.example.gasstationproject.direction.dto.OutputDto;
import com.example.gasstationproject.direction.entity.Direction;
import com.example.gasstationproject.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.result.Output;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GasStationRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;

    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";
    private static final String DIRECTION_BASE_URL = "https://map.kakao.com/link/map/";


    public List<OutputDto> recommendGasStationList(String address){
        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

        if(Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentDtoList())){
            log.error("[GasStationRecommendationService recommendGasStationList fail] Input address : {}",address);
            return Collections.emptyList();
        }

        DocumentDto documentDto = kakaoApiResponseDto.getDocumentDtoList().get(0);

//        List<Direction> directionList = directionService.buildDirectionList(documentDto);
        List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto);

        return directionService.saveAll(directionList)
                .stream().map(this::convertToOutputDto)
                .collect(Collectors.toList());

    }

    private OutputDto convertToOutputDto(Direction direction){

        String params = String.join(",",direction.getTargetGasStationName(),
                String.valueOf(direction.getTargetLatitude()),String.valueOf(direction.getTargetLongitude()));
        String resultUri = UriComponentsBuilder.fromHttpUrl(DIRECTION_BASE_URL+params).toUriString();

        log.info("direction params : {}, url : {} ", params,resultUri);

        return OutputDto.builder()
                .gasStationAddress(direction.getTargetAddress())
                .gasStationName(direction.getTargetGasStationName())
                .directionUrl(resultUri)
                .roadViewUrl(ROAD_VIEW_BASE_URL+direction.getTargetLatitude()+","+direction.getTargetLongitude())
                .distance(String.format("%.2f km",direction.getDistance()))
                .build();
    }





}
