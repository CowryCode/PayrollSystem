/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cowrycode.entities;

import com.cowrycode.config.AbstractEntityListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.MapKeyJoinColumns;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 *
 * @author JavaEE
 */
@Entity
@NamedQuery(name = Department.GET_DEPARTMENT_LIST, query = "select d Department d")
@NamedQuery(name = Department.GET_DEPARTMENT_NAMES, query = "select d.departmentName from Department d")
@NamedQuery(name = Department.FIND_BY_ID, query = "select d from Department d where d.id = :id and d.userEmail = :email")
@NamedQuery(name = Department.FIND_BY_NAME, query = "select d from Department d where d.departmentName = :name and d.userEmail = :email")
@NamedQuery(name = Department.LIST_DEPARTMENTS, query = "select d from Department d where  d.userEmail = :email")
@Access(AccessType.FIELD)
@EntityListeners(AbstractEntityListener.class)
public class Department extends AbstractEntity{
    public static final String FIND_BY_ID = "Department.findById";
    public static final String FIND_BY_NAME = "Department.findByName";
    public static final String LIST_DEPARTMENTS = "Department.listDepartments";
    public static final String GET_DEPARTMENT_LIST = "Department.getAllDepartments";
    public static final String GET_DEPARTMENT_NAMES = "Department.getDeptNames";
    
    @NotEmpty(message = "Department name must be set")
    @Pattern(regexp = "", message = "Department name must be of form E.g FIN0011MAIN")
    private String departmentName;
    
    @OneToMany(mappedBy = "department")
    @OrderBy("fullName ASC, dateofDate desc")
    @OrderColumn(name = "EMPLOYEE_POSITION")
    private List<Employee> employees = new ArrayList<>();
        //    @OneToMany
        //    @MapKey(name = "id")
        //    @JoinTable(name = "DEPT_EMPLOYEES")
        //    private Map<Long, Employee> employees = new HashMap<>();
    
    @ElementCollection
    @CollectionTable(name = "EMPLOYEE_RANKS")
    @MapKeyJoinColumn(name = "EMP_ID")
    @Column(name ="RANK")
    private Map<Employee, Integer> employeeRanks = new HashMap<>();   
    
    @Transient
    private String departmentCode;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Map<Employee, Integer> getEmployeeRanks() {
        return employeeRanks;
    }

    public void setEmployeeRanks(Map<Employee, Integer> employeeRanks) {
        this.employeeRanks = employeeRanks;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    @Override
    public int hashCode() {
        
        return Objects.hash(getDepartmentName().toUpperCase());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
         Department other = (Department) obj;
         return Objects.equals(getDepartmentName().toUpperCase(), other.getDepartmentName().toUpperCase());
    }
    
    
    
    
}
