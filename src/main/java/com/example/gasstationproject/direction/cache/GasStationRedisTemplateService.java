package com.example.gasstationproject.direction.cache;

import com.example.gasstationproject.gasStation.dto.GasStationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class GasStationRedisTemplateService {

    private static final String CACHE_KEY = "GASSTATION";

    private final RedisTemplate<String,Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private HashOperations<String,String,String> hashOperations;

    @PostConstruct
    public void init(){
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void save(GasStationDto gasStationDto){
        if(Objects.isNull(gasStationDto) || Objects.isNull(gasStationDto.getId())){
            log.error("GasStationDto is null");
            return;
        }
        try {
            hashOperations.put(CACHE_KEY,
                    gasStationDto.getId().toString(),
                    serializeGasStationDto(gasStationDto));
            log.info("GasStationRedisTemplateService save id : {}", gasStationDto.getId());
        } catch (Exception e) {
            log.error("GasStationRedisTemplateService error : {}",e.getMessage());
        }
    }
    public List<GasStationDto> findAll(){
        try {
            List<GasStationDto> list = new ArrayList<>();
            for(String value : hashOperations.entries(CACHE_KEY).values()){
                GasStationDto gasStationDto = deserializeGasStationDto(value);
                list.add(gasStationDto);
            }
            return list;
        }catch (Exception e){
            log.error("GasStationRedisTemplateService findAll error : {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public void delete(Long id){
        hashOperations.delete(CACHE_KEY, String.valueOf(id));
        log.info("GasStationRedisTemplateService delete id : {}", id);
    }

    private String serializeGasStationDto(GasStationDto gasStationDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(gasStationDto);
    }
    private GasStationDto deserializeGasStationDto(String value) throws JsonProcessingException {
        return objectMapper.readValue(value, GasStationDto.class);
    }
}
