package com.example.gasstationproject.gasStation.service;

import com.example.gasstationproject.api.dto.DocumentDto;
import com.example.gasstationproject.api.dto.KakaoApiResponseDto;
import com.example.gasstationproject.api.service.KakaoAddressSearchService;
import com.example.gasstationproject.direction.entity.Direction;
import com.example.gasstationproject.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class GasStationRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;

    public void recommendGasStationList(String address){
        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

        if(Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentDtoList())){
            log.error("[GasStationRecommendationService recommendGasStationList fail] Input address : {}",address);
            return;
        }

        DocumentDto documentDto = kakaoApiResponseDto.getDocumentDtoList().get(0);

//        List<Direction> directionList = directionService.buildDirectionList(documentDto);
        List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto);

        directionService.saveAll(directionList);

    }





}
