package com.example.gasstationproject.gasStation.service;

import com.example.gasstationproject.api.dto.DocumentDto;
import com.example.gasstationproject.api.dto.KakaoApiResponseDto;
import com.example.gasstationproject.api.service.KakaoAddressSearchService;
import com.example.gasstationproject.direction.dto.OutputDto;
import com.example.gasstationproject.direction.entity.Direction;
import com.example.gasstationproject.direction.service.Base62Service;
import com.example.gasstationproject.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    private final Base62Service base62Service;

    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";


    @Value("${gasStation.recommendation.base.url}")
    private String baseUrl;

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
        return OutputDto.builder()
                .gasStationAddress(direction.getTargetAddress())
                .gasStationName(direction.getTargetGasStationName())
                .directionUrl(baseUrl + base62Service.encodeDirectionId(direction.getId()))
                .roadViewUrl(ROAD_VIEW_BASE_URL+direction.getTargetLatitude()+","+direction.getTargetLongitude())
                .distance(String.format("%.2f km",direction.getDistance()))
                .build();
    }





}
