package com.example.gasstationproject.direction.service;

import com.example.gasstationproject.api.dto.DocumentDto;
import com.example.gasstationproject.api.service.KakaoCategorySearchService;
import com.example.gasstationproject.direction.entity.Direction;
import com.example.gasstationproject.direction.repository.DirectionRepository;
import com.example.gasstationproject.gasStation.dto.GasStationDto;
import com.example.gasstationproject.gasStation.service.GasStationSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DirectionService {

    private static final int MAX_SEARCH_COUNT = 3;
    private static final double RADIUS_KM = 10.0;
    private static final String DIRECTION_BASE_URL = "https://map.kakao.com/link/map/";

    private final GasStationSearchService gasStationSearchService;
    private final DirectionRepository directionRepository;

    private final KakaoCategorySearchService kakaoCategorySearchService;

    private final Base62Service base62Service;


    @Transactional
    public List<Direction> saveAll(List<Direction> directionList){
        if(CollectionUtils.isEmpty(directionList)){
            return Collections.emptyList();
        }

        return directionRepository.saveAll(directionList);

    }

    public String findDirectionUrlById(String encodeId){
        Long decodedId = base62Service.decodeDirectionId(encodeId);
        Direction direction = directionRepository.findById(decodedId)
                .orElse(null);

        String params = String.join(",",direction.getTargetGasStationName(),
                String.valueOf(direction.getTargetLatitude()),String.valueOf(direction.getTargetLongitude()));
        String resultUri = UriComponentsBuilder.fromHttpUrl(DIRECTION_BASE_URL+params).toUriString();

        log.info("direction params : {}, url : {} ", params,resultUri);

        return resultUri;


    }



    public List<Direction> buildDirectionList(DocumentDto documentDto){

        if(Objects.isNull(documentDto)) return Collections.emptyList();

        return gasStationSearchService.searchGasStationDtoList()
                .stream().map(dto ->
                        Direction.builder()
                                .inputAddress(documentDto.getAddressName())
                                .inputLatitude(documentDto.getLatitude())
                                .inputLongitude(documentDto.getLongitude())
                                .targetGasStationName(dto.getGasStationName())
                                .targetAddress(dto.getGasStationAddress())
                                .targetLatitude(dto.getLatitude())
                                .targetLongitude(dto.getLongitude())
                                .distance(
                                        calculateDistance(documentDto.getLatitude(), documentDto.getLongitude(),
                                                dto.getLatitude(), dto.getLongitude()))
                                .build())
                .filter(direction -> direction.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparing(Direction::getDistance))
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }


    public List<Direction> buildDirectionListByCategoryApi(DocumentDto inputDocumentDto){
        if(Objects.isNull(inputDocumentDto)) return Collections.emptyList();

        return kakaoCategorySearchService
                .requestGasStationCategorySearch(inputDocumentDto.getLatitude(),inputDocumentDto.getLongitude(),RADIUS_KM)
                .getDocumentDtoList()
                .stream().map(resultDocumentDto ->
                        Direction.builder()
                                .inputAddress(inputDocumentDto.getAddressName())
                                .inputLongitude(inputDocumentDto.getLongitude())
                                .inputLatitude(inputDocumentDto.getLatitude())
                                .targetGasStationName(resultDocumentDto.getPlaceName())
                                .targetAddress(resultDocumentDto.getAddressName())
                                .targetLongitude(resultDocumentDto.getLongitude())
                                .targetLatitude(resultDocumentDto.getLatitude())
                                .distance(resultDocumentDto.getDistance() * 0.001)
                                .build())
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }





    //Haversine fomula
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2){
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371;
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

    }








}
