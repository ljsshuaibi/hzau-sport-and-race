package org.example.last.java.mapper;

import org.apache.ibatis.annotations.*;
import org.example.last.java.entity.RaceLjs;
import org.example.last.java.entity.ResultRace;

import java.util.List;
@Mapper
public interface RaceMapper {
    @Select("select * from race")
    List<RaceLjs> SelectRace();
    @Select("select * from race where Id=#{id}")
     RaceLjs Select(int id);
    @Insert("INSERT INTO race (Time,RaceMessage,Boss,Status,RaceName,Number)"+
            "values"+
            "(#{Time},#{RaceMessage},#{Boss},#{Status},#{RaceName},#{Number})")
    void InsertRace(RaceLjs raceLjs);
    @Delete("delete from race where RaceName=#{RaceName}")
    void deleteRace(RaceLjs raceLjs);
    @Update("update race set Time=#{Time},Boss=#{Boss},RaceMessage=#{RaceMessage},Number=#{Number},Status=#{Status} where RaceName=#{RaceName}")
    void updateRace(RaceLjs raceLjs);
    @Insert("INSERT INTO raceresult(RaceName,first,second,third)"+
    "values"+
    "(#{RaceName},#{first},#{second},#{third})")
    void InsertRaceResult(ResultRace resultRace);
    @Select("select * from raceresult where RaceName=#{RaceName}")
    ResultRace SelectRaceResult(String RaceName);
}
