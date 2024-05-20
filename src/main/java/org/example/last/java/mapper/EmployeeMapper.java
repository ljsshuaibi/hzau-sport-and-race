package org.example.last.java.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.last.java.entity.Employee;
import org.example.last.java.entity.EmployeeDto;
import org.example.last.java.entity.SportDto;
import org.example.last.java.entity.Test;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    @Select("select * from employee where username=#{id}")
    EmployeeDto selectRacer(String id);
    /*0不正常*/
    @Update("update employee set Status=0 where username=#{username}")
    void UpdateStatus(Employee employee);
    @Update("update employee set Status=1 where username=#{username}")
    void UpdateStatus1(Employee employee);
    @Select("select * from  employee where username=#{UserId}")
    EmployeeDto SearchStatus(Test test);
}
