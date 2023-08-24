package com.example.gasstationproject.gasStation.service

import com.example.gasstationproject.AbstractIntegrationContainerBaseTest
import com.example.gasstationproject.gasStation.entity.GasStation
import com.example.gasstationproject.gasStation.repository.GasStationRepository
import org.springframework.beans.factory.annotation.Autowired


class GasStationServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private GasStationService gasStationService

    @Autowired
    private GasStationRepository gasStationRepository

    def setup(){
        gasStationRepository.deleteAll()
    }


    def "GasStationRepository update - dirty checking success" (){

        given:
        String inputAddress = "서울 특별시 성북구 종암동"
        String modifiedAddress = "서울 광진구 군자동"
        String name = "sk 주유소"

        def gasStation = GasStation.builder()
        .gasStationName(name)
        .gasStationAddress(inputAddress)
        .build()

        when:
        def entity = gasStationRepository.save(gasStation)

        gasStationService.updateAddress(entity.getId(),modifiedAddress)

        def res = gasStationRepository.findAll()

        then:
        res.get(0).getGasStationAddress() == modifiedAddress

    }

    def "GasStationRepository update - dirty checking fail" (){

        given:
        String inputAddress = "서울 특별시 성북구 종암동"
        String modifiedAddress = "서울 광진구 군자동"
        String name = "sk 주유소"

        def gasStation = GasStation.builder()
                .gasStationName(name)
                .gasStationAddress(inputAddress)
                .build()

        when:
        def entity = gasStationRepository.save(gasStation)

        gasStationService.updateAddressNotTransaction(entity.getId(),modifiedAddress)

        def res = gasStationRepository.findAll()

        then:
        res.get(0).getGasStationAddress() == inputAddress

    }


}
