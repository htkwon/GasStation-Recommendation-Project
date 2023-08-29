package com.example.gasstationproject.gasStation.service;

import com.example.gasstationproject.gasStation.entity.GasStation;
import com.example.gasstationproject.gasStation.repository.GasStationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class GasStationService {

    private final GasStationRepository gasStationRepository;

    @Transactional
    public void updateAddress(Long id, String address){
        GasStation entity = gasStationRepository.findById(id)
                .orElse(null);

        if(Objects.isNull(entity)){
            log.error("[GasStationService updateAddress] not found id : {}",id);
            return;
        }

        entity.changeGasStationAddress(address);

    }

    //test
    public void updateAddressNotTransaction(Long id, String address){
        GasStation entity = gasStationRepository.findById(id)
                .orElse(null);

        if(Objects.isNull(entity)){
            log.error("[GasStationService updateAddress] not found id : {}",id);
            return;
        }

        entity.changeGasStationAddress(address);

    }

    @Transactional(readOnly = true)
    public List<GasStation> findAll(){
        return gasStationRepository.findAll();
    }

}
