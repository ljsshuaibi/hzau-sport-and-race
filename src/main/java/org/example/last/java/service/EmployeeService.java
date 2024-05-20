package org.example.last.java.service;


import org.example.last.java.entity.Employee;
import org.example.last.java.entity.SportDto;

public interface EmployeeService {


    Employee login(Employee employeeDTO);


    void Punish(Employee employee);
    void enable(Employee employee);
}
