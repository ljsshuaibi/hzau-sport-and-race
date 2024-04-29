package org.example.last.java.service.impl;

import org.example.last.java.entity.EmployeeDto;
import org.example.last.java.entity.RacerBaoMing;
import org.example.last.java.entity.RacerMessage;
import org.example.last.java.mapper.EmployeeMapper;
import org.example.last.java.mapper.RaceBaomingMapper;
import org.example.last.java.mapper.RaceMapper;
import org.example.last.java.service.RaceLjs1;
import org.example.last.java.service.RaceLjs2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.last.java.entity.RaceLjs;

import java.util.ArrayList;
import java.util.List;
@Service
public class RaceService implements RaceLjs1, RaceLjs2 {
    @Autowired
    RaceMapper raceMapper;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    RaceBaomingMapper raceBaomingMapper;
    @Override
    public List<RaceLjs> SelectRace() {
        List<RaceLjs> ljs =new ArrayList<>();
        ljs=raceMapper.SelectRace();
        return ljs;
    }

    @Override
    public void BaoMing(String userid,int raceid) {
        EmployeeDto employeeDto=employeeMapper.selectRacer(userid);
        RaceLjs raceLjs=raceMapper.Select(raceid);
        RacerBaoMing racerBaoMing=new RacerBaoMing();
        racerBaoMing.UserId=employeeDto.username;
        racerBaoMing.UserName=employeeDto.USerId;
        racerBaoMing.UserMessage=employeeDto.UserMessage;
        racerBaoMing.RaceTime=raceLjs.Time;
        racerBaoMing.RaceName=raceLjs.RaceName;
        raceBaomingMapper.InsertRacer(racerBaoMing);
        raceBaomingMapper.InsertPeople(raceid);
    }
    @Override
    public List<RacerBaoMing> ShowRacerMessage(String UserId){
        System.out.println(UserId);
        List<RacerBaoMing> racerMessages=new ArrayList<>();
        racerMessages=raceBaomingMapper.SelectRaceMessage(UserId);
        return racerMessages;
    }
    @Override
    public void QuxiaoBaoMing(RacerMessage racerMessage){
        raceBaomingMapper.QuxaioBaoming(racerMessage);
    }
}
