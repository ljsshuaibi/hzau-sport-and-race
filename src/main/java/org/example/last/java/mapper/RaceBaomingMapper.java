package org.example.last.java.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.example.last.java.entity.RacerBaoMing;

@Mapper
public interface RaceBaomingMapper {
    @Insert("INSERT INTO racermessage (USerId,UserName,UserMessage,RaceName,RaceTime)"+
            "values"+
            "(#{UserId},#{UserName},#{UserMessage},#{RaceName},#{RaceTime})")
    void InsertRacer(RacerBaoMing racerBaoMing);
    @Update("update race set Total=Total+1 where Id=#{id}")
    void InsertPeople(int id);
}
