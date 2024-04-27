package org.example.last.java.entity;

import lombok.Data;

import java.util.List;

@Data
public abstract class RaceLjs {
    public int id;
    public int status;
    public String RaceMessage;
    public String Time;
    public String Boss;

    public abstract List<RaceLjs> SelectRace();
}
