package com.example.gasstationproject.direction.controller;

import com.example.gasstationproject.direction.dto.InputDto;
import com.example.gasstationproject.gasStation.service.GasStationRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class FormController {

    private final GasStationRecommendationService gasStationRecommendationService;


    @GetMapping("/")
    public String main(){
        return "main";
    }

    @PostMapping("/search")
    public ModelAndView postDirection(@ModelAttribute InputDto inputDto){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("output");
        modelAndView.addObject("outputFormList",
                gasStationRecommendationService.recommendGasStationList(inputDto.getAddress()));

        return modelAndView;
    }



}
