package org.example.last.java.service.impl;

import org.example.last.java.mapper.RaceMapper;
import org.example.last.java.entity.RaceLjs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class RaceService extends RaceLjs {
    @Autowired
    RaceMapper raceMapper;
    @Override
    public List<RaceLjs> SelectRace() {
        List<RaceLjs> ljs =new ArrayList<>();
        ljs=raceMapper.SelectRace();
        return ljs;
    }
}
