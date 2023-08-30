package com.example.gasstationproject.direction.service

import com.example.gasstationproject.api.dto.DocumentDto
import com.example.gasstationproject.gasStation.dto.GasStationDto
import com.example.gasstationproject.gasStation.service.GasStationSearchService
import spock.lang.Specification

class DirectionServiceTest extends Specification {

    private GasStationSearchService gasStationSearchService = Mock()

    private DirectionService directionService = new DirectionService(gasStationSearchService)

    private List<GasStationDto> gasStationDtoList

    def setup(){
        gasStationDtoList = new ArrayList<>()
        gasStationDtoList.addAll(
                GasStationDto.builder()
                .id(1L)
                .gasStationName("행복주유소")
                .gasStationAddress("주소1")
                .latitude(37.61040424)
                .longitude(127.0569046)
                .build(),

                GasStationDto.builder()
                .id(2L)
                .gasStationName("성북주유소")
                .gasStationAddress("주소2")
                .latitude(37.60894036)
                .longitude(127.029052)
                .build()
        )
    }


    def "buildDirectionList - 결과 값이 거리 순으로 정렬이 되는지 확인" (){
        given:
        def addressName = "서울 성북구 종암로10길"
        double inputLatitude = 37.5960650456809
        double inputLongitude = 127.037033003036

        def documentDto = DocumentDto.builder()
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build()

        when:
        gasStationSearchService.searchGasStationDtoList() >> gasStationDtoList

        def results = directionService.buildDirectionList(documentDto)

        then:
        results.size() == 2
        results.get(0).targetGasStationName == "성북주유소"
        results.get(1).targetGasStationName == "행복주유소"

    }

    def "buildDirectionList - 정해진 반경 10km 내에 검색이 되는지 확인" (){
        given:
        gasStationDtoList.add(
                GasStationDto.builder()
                        .id(3L)
                        .gasStationName("경기주유소")
                        .gasStationAddress("주소3")
                        .latitude(37.3825107393401)
                        .longitude(127.236707811313)
                        .build())
        def addressName = "서울 성북구 종암로10길"
        double inputLatitude = 37.5960650456809
        double inputLongitude = 127.037033003036

        def documentDto = DocumentDto.builder()
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build()
        when:
        gasStationSearchService.searchGasStationDtoList() >> gasStationDtoList

        def res = directionService.buildDirectionList(documentDto)

        then:
        res.size() == 2
        res.get(0).targetGasStationName == "성북주유소"
        res.get(1).targetGasStationName == "행복주유소"

    }



}
