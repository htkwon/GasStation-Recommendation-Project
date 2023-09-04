package com.example.gasstationproject.direction.controller

import com.example.gasstationproject.direction.dto.OutputDto
import com.example.gasstationproject.gasStation.service.GasStationRecommendationService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*



class FormControllerTest extends Specification {

    private MockMvc mockMvc
    private GasStationRecommendationService gasStationRecommendationService = Mock()
    private List<OutputDto> outputDtoList

    def setup(){
        //FormController MockMvc 객체 생성
        mockMvc = MockMvcBuilders.standaloneSetup(new FormController(gasStationRecommendationService))
        .build()

        outputDtoList = new ArrayList<>()
        outputDtoList.addAll(
                OutputDto.builder()
                .gasStationName("gas1")
                .build(),
                OutputDto.builder()
                .gasStationName("gas2")
                .build()
        )
    }

    def "Get /"(){
        expect:
        mockMvc.perform(get("/"))
        .andExpect(handler().handlerType(FormController.class))
        .andExpect(handler().methodName("main"))
        .andExpect(status().isOk())
        .andExpect(view().name("main"))
        .andDo(log())
    }

    def "POST /search"(){

        given:
        String inputAddress ="서울 성북구 삼선동"

        when:
        def resultActions = mockMvc.perform(post("/search")
        .param("address",inputAddress))

        then:
        1 * gasStationRecommendationService.recommendGasStationList(argument ->{
            assert argument == inputAddress //mock 객체 argument 검증
        }) >> outputDtoList

        resultActions
        .andExpect(status().isOk())
        .andExpect(view().name("output"))
        .andExpect(model().attributeExists("outputFormList"))
        .andExpect(model().attribute("outputFormList",outputDtoList))
        .andDo(print())

    }







}
