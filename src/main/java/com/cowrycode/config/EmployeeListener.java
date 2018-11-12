/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cowrycode.config;

import com.cowrycode.entities.Employee;
import java.time.LocalDate;
import java.time.Period;
import javax.persistence.PrePersist;

/**
 *
 * @author JavaEE
 */
public class EmployeeListener {
    
    @PrePersist
    public void calculateEmployeeAge(Employee employee){
        employee.setAge(Period.between(employee.getDateofDate(), LocalDate.now()).getYears());
    }
}