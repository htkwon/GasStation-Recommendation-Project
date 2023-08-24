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


}
