package org.example.last.java.mapper;

import org.apache.ibatis.annotations.*;
import org.example.last.java.entity.RacerBaoMing;
import org.example.last.java.entity.RacerMessage;

import java.util.List;

@Mapper
public interface RaceBaomingMapper {
    @Insert("INSERT INTO racermessage (USerId,UserName,UserMessage,RaceName,RaceTime)"+
            "values"+
            "(#{UserId},#{UserName},#{UserMessage},#{RaceName},#{RaceTime})")
    void InsertRacer(RacerBaoMing racerBaoMing);
    @Update("update race set Total=Total+1 where Id=#{id}")
    void InsertPeople(int id);
    @Select("select * from racermessage where UserId=#{UserId}")
    List<RacerBaoMing> SelectRaceMessage(String UserId);
    @Delete("delete from racermessage where UserId=#{userId} and RaceName=#{RaceName}")
    void QuxaioBaoming(RacerMessage racerMessage);
}
