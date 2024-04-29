package org.example.last.java.service;

import org.example.last.java.entity.RaceLjs;
import org.example.last.java.entity.RacerBaoMing;
import org.example.last.java.entity.RacerMessage;

import java.util.List;

public interface RaceLjs1 {
    List<RaceLjs> SelectRace();
    List<RacerBaoMing> ShowRacerMessage(String UserId);
    public void QuxiaoBaoMing(RacerMessage racerMessage);
}
