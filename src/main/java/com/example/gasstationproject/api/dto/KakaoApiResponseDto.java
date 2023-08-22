package com.example.gasstationproject.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class KakaoApiResponseDto {

    private MetaDto metaDto;

    private List<DocumentDto> documentDtoList  ;




}
