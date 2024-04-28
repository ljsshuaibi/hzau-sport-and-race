package org.example.last.java.entity;

import lombok.Data;

@Data
public class RacerBaoMing {
    public String UserId;
    public String UserName;
    public String UserMessage;
    public String RaceTime;
    public String RaceName;
    public  RacerBaoMing(EmployeeDto employeeDto){
        this.UserId=employeeDto.username;
        this.UserName=employeeDto.USerId;
        this.UserMessage=employeeDto.UserMessage;
    }
}
