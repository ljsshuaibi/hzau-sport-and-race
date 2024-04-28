package org.example.last.java.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.last.java.entity.RaceLjs;

import java.util.List;
@Mapper
public interface RaceMapper {
    @Select("select * from race")
    List<RaceLjs> SelectRace();
    @Select("select * from race where Id=#{id}")
     RaceLjs Select(int id);
}
