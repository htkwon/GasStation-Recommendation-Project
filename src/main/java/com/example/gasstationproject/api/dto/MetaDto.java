package com.example.gasstationproject.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MetaDto {

    @JsonProperty("total_count")
    private Integer totalCount;





}
