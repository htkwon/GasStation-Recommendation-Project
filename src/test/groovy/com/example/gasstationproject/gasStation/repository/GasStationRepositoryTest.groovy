package com.example.gasstationproject.gasStation.repository

import com.example.gasstationproject.AbstractIntegrationContainerBaseTest
import com.example.gasstationproject.gasStation.entity.GasStation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cglib.core.Local
import spock.lang.Specification

import java.time.LocalDateTime

@SpringBootTest
class GasStationRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private GasStationRepository gasStationRepository;


    //각 메서드 시작 전, 정리
    def setup(){
        gasStationRepository.deleteAll()
    }

    def "GasStation save" (){
        given:
        String address = "서울 특별시 성북구 종암동"
        String name = "SK 주유소"
        double latitude = 36.11
        double longitude = 128.11

        def gasStation = GasStation.builder()
        .gasStationAddress(address)
        .gasStationName(name)
        .longitude(longitude)
        .latitude(latitude)
        .build()

        when:
        def res = gasStationRepository.save(gasStation)

        then:
        res.getGasStationAddress() == address
        res.getGasStationName() == name
        res.getLatitude() == latitude
        res.getLongitude() == longitude

    }

    def "GasStationRepository saveAll" (){
        given:
        String address = "서울 특별시 성북구 종암동"
        String name = "SK 주유소"
        double latitude = 36.11
        double longitude = 128.11

        def gasStation = GasStation.builder()
                .gasStationAddress(address)
                .gasStationName(name)
                .longitude(longitude)
                .latitude(latitude)
                .build()

        when:
        gasStationRepository.saveAll(Arrays.asList(gasStation))
        def res = gasStationRepository.findAll()

        then:
        res.size()

    }

    def "BaseTimeEntity 등록"() {
        given:
        LocalDateTime now = LocalDateTime.now()
        String address = "서울 특별시 성북구 종암동"
        String name = "sk 주유소"

        def entity = GasStation.builder()
            .gasStationAddress(address)
            .gasStationName(name)
            .build()
        when:
        gasStationRepository.save(entity)
        def res = gasStationRepository.findAll()

        then:
        res.get(0).getCreatedDate().isAfter(now)
        res.get(0).getModifiedDate().isAfter(now)

    }



}
