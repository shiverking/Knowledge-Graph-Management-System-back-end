package com.group.KGMS.controller;

import com.group.KGMS.entity.T_artillery;
import com.group.KGMS.repository.ArtilleryRepository;
import com.group.KGMS.repository.BombRepository;
import com.group.KGMS.repository.AircraftRepository;
import com.group.KGMS.repository.VesselRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/artillery")
public class ArtilleryHandler {
    @Autowired
    private ArtilleryRepository artilleryRepository;
    @Autowired
    private AircraftRepository aircraftRepository;
    @Autowired
    private BombRepository bombRepository;
    @Autowired
    private VesselRepository vesselRepository;
    @GetMapping("/findAll/{page}/{size}")
    public Page<T_artillery> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        PageRequest request = PageRequest.of(page,size);
        return artilleryRepository.findAll(request);
    }

    @GetMapping("/count")
    public List<Long> count(){
        List<Long> values = new ArrayList<>();
        Long aircraft = aircraftRepository.count();
        Long artillery = artilleryRepository.count();
        Long bomb = bombRepository.count();
        Long vessel = vesselRepository.count();
        values.add(aircraft);
        values.add(artillery);
        values.add(bomb);
        values.add(vessel);
        return values;
    }
}

