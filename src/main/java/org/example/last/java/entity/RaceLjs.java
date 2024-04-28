package org.example.last.java.entity;

import lombok.Data;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public  class RaceLjs {
    public int Id;
    public int Status;
    public String RaceMessage;
    public String Time;
    public String Boss;
    public String RaceName;
    public int Number;
    public int Total;
}
