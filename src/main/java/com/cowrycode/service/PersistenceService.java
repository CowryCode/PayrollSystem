/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cowrycode.service;

import com.cowrycode.entities.ApplicationUser;
import com.cowrycode.entities.Department;
import com.cowrycode.entities.Employee;
import com.cowrycode.entities.ParkingSpace;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author JavaEE
 */
@Stateless
public class PersistenceService {
    @Inject
    EntityManager entityManager;
    
    @Inject
    QueryService queryService;
    
    @Inject
    SecurityUtil securityUtil;
    
    public void saveDepartent(Department department){
        entityManager.persist(department);
    }
    
    public void removeParkingSpace(Long employeeID){
        Employee employee = queryService.findEmployeeById(employeeID);  
        ParkingSpace parkingSpace = employee.getParkingSpace();
        employee.setParkingSpace(null);
        entityManager.remove(parkingSpace);
    }
    
    public void saveEmployee(Employee employee, ParkingSpace parkingSpace){
        employee.setParkingSpace(parkingSpace);
        entityManager.persist(employee);
    }
    
    public void saveEmployee(Employee employee){
        if(employee.getId() == null){
            entityManager.persist(employee);
        }else{
            entityManager.merge(employee);
        }
    }
    
    public void saveUser(ApplicationUser applicationUser){
        
    }
    
    
}
