package org.example.last.java.service;

import org.example.last.java.entity.RaceLjs;
import org.example.last.java.entity.RacerBaoMing;
import org.example.last.java.entity.RacerMessage;
import org.example.last.java.entity.ResultRace;

import java.util.List;

public interface RaceLjs1 {
    List<RaceLjs> SelectRace();
    List<RacerBaoMing> ShowRacerMessage(String UserId);
    public void QuxiaoBaoMing(RacerMessage racerMessage);
    public void deleteRace(RaceLjs raceLjs);
    public void updateRace(RaceLjs raceLjs);
    public void UpdateRaceResult(ResultRace resultRace);
    public ResultRace SelectRaceResult(String name);
}
