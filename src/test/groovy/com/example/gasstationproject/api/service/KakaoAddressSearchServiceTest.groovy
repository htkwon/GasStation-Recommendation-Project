package com.example.gasstationproject.api.service

import com.example.gasstationproject.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService

    def "address 파라미터 값이 null일 때, reqeustAddressSearch 메소드는 null 리턴" (){
        given:
        def address = null

        when:
        def res = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        res == null
    }

    def "주소값이 적절하다면, requestAddressSearch 메소드는 정상적으로 document 반환"(){
        given:
        def address = "서울 성북구 삼선교로 10길"

        when:
        def res = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        res.documentDtoList.size() > 0
        res.metaDto.totalCount > 0
        res.documentDtoList.get(0).addressName != null
    }

    def "정상적인 주소를 입력했을 경우, 정상적으로 위도 경도로 변환" (){
        given:
        boolean actualResult = false

        when:
        def searchResult = kakaoAddressSearchService.requestAddressSearch(inputAddress)

        then:
        if(searchResult == null) actualResult = false
        else actualResult = searchResult.getDocumentDtoList().size() > 0

        where:
        inputAddress               |     expectedResult
        "서울 특별시 성북구 종암동"     |     true
        "서울 성북구 종암동 91"        |     true
        "서울 대학로"                |      true
        "서울 잘못된 주소"            |     false
        ""                         |     false

    }


}
