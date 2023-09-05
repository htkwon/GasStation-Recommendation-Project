package com.example.gasstationproject.direction.controller;

import com.example.gasstationproject.direction.dto.OutputDto;
import com.example.gasstationproject.direction.entity.Direction;
import com.example.gasstationproject.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@Slf4j
@RequiredArgsConstructor
public class DirectionContoller {

    public final DirectionService directionService;
    private static final String DIRECTION_BASE_URL = "https://map.kakao.com/link/map/";

    @GetMapping("/dir/{encodedId}")
    public String searchDirection(@PathVariable("encodedId") String encodedId){
        String resultDirectionUri = directionService.findDirectionUrlById(encodedId);

        return "redirect:"+resultDirectionUri;
    }






}
