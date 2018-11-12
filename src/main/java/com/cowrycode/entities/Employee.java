/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cowrycode.entities;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

/**
 *
 * @author JavaEE
 */
@Entity

@NamedNativeQuery(name = "Employee.findAllNativeNamed", query = "select * from Employee", resultClass = Employee.class)

@NamedQuery(name = Employee.GET_EMPLOYEE_ALLOWANCES, query = "select al from Employee  e join e.employeeAllowances al where al.allowanceAmount > :greaterThanValue")
@NamedQuery(name = Employee.EMPLOYEE_SALARY_BOUND, query = "select e from Employee e where e.basicSalary between :lowerBound and :upperBound")
@NamedQuery(name = Employee.GET_EMPLOYEE_PHONE_NUMBERS, query = "select e.fullName, KEY(p), VALUE(p) from Employee e join e.employeePhoneNumbers p")
@NamedQuery(name = Employee.GET_EMPLOYEE_ALLOWANCES_JOIN_FETCH, query = "select e from Employee e join fetch e.employeeAllowances")
@NamedQuery(name = Employee.GET_ALL_PARKING_SPACES, query = "select e.parkingSpace from Employee e")
@NamedQuery(name = Employee.EMPLOYEE_PROJECTION, query = "select e.fullName, e.basicSalary from Employee e")
@NamedQuery(name = Employee.EMPLOYEE_CONSTRUCTOR_PROJ, query = "select new academy.learnprogramming.entities.EmployeeDetails(e.fullName, e.basicSalary, e.department.departmentName) from Employee  e")
@NamedQuery(name = Employee.FIND_BY_ID, query = "select e from Employee e where e.id = :id and e.userEmail = :email")
@NamedQuery(name = Employee.FIND_BY_NAME, query = "select e from Employee e where e.fullName = :name and e.userEmail = :email")
@NamedQuery(name = Employee.LIST_EMPLOYEES, query = "select  e from Employee e  order by e.fullName")
@NamedQuery(name = Employee.FIND_PAST_PAYSLIP_BY_ID,
        query = "select p from Employee e join e.pastPayslips p where e.id = :employeeId and e.userEmail =:email and p.id =:payslipId and p.userEmail = :email")
@NamedQuery(name = Employee.GET_PAST_PAYSLIPS, query = "select p from Employee e inner join e.pastPayslips p where e.id = :employeeId and e.userEmail=:email")
//@Table(name = "Employee", schema = "HR")
public class Employee extends AbstractEntity{
    
    
//    @TableGenerator(name = "Emp_Gen", table = "ID_GEN",
//            pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE")
//    @GeneratedValue(generator = "Emp_Gen")
//    @Id
//    private Long id;

    public static final String EMPLOYEE_SALARY_BOUND = "EmployeeSalaryBound";
    public static final String EMPLOYEE_PROJECTION = "Employee.nameAndSalaryProjection";
    public static final String EMPLOYEE_CONSTRUCTOR_PROJ = "Employee.projection";
    public static final String GET_EMPLOYEE_ALLOWANCES = "Employee.getAllowances";
    public static final String GET_EMPLOYEE_ALLOWANCES_JOIN_FETCH = "Employee.getAllowancesJoinFetch";
    public static final String FIND_BY_ID = "Employee.findById";
    public static final String FIND_BY_NAME = "Employee.findByName";
    public static final String LIST_EMPLOYEES = "Employee.listEmployees";
    public static final String FIND_PAST_PAYSLIP_BY_ID = "Employee.findPastPayslipById";
    public static final String GET_PAST_PAYSLIPS = "Employee.getPastPayslips";
    public static final String GET_ALL_PARKING_SPACES = "Employee.getAllParkingSpaces";
    public static final String GET_EMPLOYEE_PHONE_NUMBERS = "Employee.getPhoneNumbers";
    
    @NotNull(message = "Name cannot be empty")
    @Size(max = 40, message = "Full name must be less than 40 characters")
    @Basic
    private String fullName;
    
    @NotEmpty(message = "SSN must be set")
    private String socialSecurityNumber;
    
    @NotNull(message = "Date of birth must be set")
    @Past(message = "Date must be in the Past ")
    @JsonbDateFormat(value = "yyyy-MM-dd")
    private LocalDate dateofDate;
    
    @NotNull(message = "Basic Salry must be set")
    @DecimalMin(value = "500", message = "Basic salary must be equal to or exceed 500")
    private BigDecimal basicSalary;
    
    @NotNull(message = "Hire date must be set")
    @JsonbDateFormat(value = "yyyy-MM-dd")
    @PastOrPresent(message = "Hires date must be past of present")
    private LocalDate hireDate;
    
    @ManyToMany
    private Employee reportsTo;
    
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;
    
    @Embedded
    private Address address;
    
    @ElementCollection
    @CollectionTable(name = "Qualifications", joinColumns = @JoinColumn (name = "EMP_ID"))
    private Collection<Qualifications> qualificationses = new ArrayList<>();
    
    @ElementCollection
    @Column(name = "NICKY")
    private Collection<String> nickNames = new ArrayList<>();
    
    @DecimalMax(value = "60", message = "Age can not exceed 60 years")
    private int age;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Allowance> employeeAllowances = new HashSet<>();
    
    @OneToOne
    @JoinColumn(name = "CURRENT_PAYSLIP_ID")
    private Payslip cuurentPayslip;
    
    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private ParkingSpace parkingSpace;
    
    @OneToMany
    private Collection<Payslip> pastPayslips = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "EMP_PHONE_NUMBERS")
    @MapKeyColumn(name = "PHONE_TYPE")
    @Column(name = "PHONE_NUMBER")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<PhoneType,String> employeePhoneNumbers =  new HashMap<>();
    
    @OneToMany
    private Set<Employee> subordinates = new HashSet<>();
    
    public LocalDate getDateofDate() {
        return dateofDate;
    }

    public void setDateofDate(LocalDate dateofDate) {
        this.dateofDate = dateofDate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public Employee getReportsTo() {
        return reportsTo;
    }

    public void setReportsTo(Employee reportsTo) {
        this.reportsTo = reportsTo;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Collection<Qualifications> getQualificationses() {
        return qualificationses;
    }

    public void setQualificationses(Collection<Qualifications> qualificationses) {
        this.qualificationses = qualificationses;
    }

    public Collection<String> getNickNames() {
        return nickNames;
    }

    public void setNickNames(Collection<String> nickNames) {
        this.nickNames = nickNames;
    }

    public Set<Allowance> getEmployeeAllowances() {
        return employeeAllowances;
    }

    public void setEmployeeAllowances(Set<Allowance> employeeAllowances) {
        this.employeeAllowances = employeeAllowances;
    }

    public Payslip getCuurentPayslip() {
        return cuurentPayslip;
    }

    public void setCuurentPayslip(Payslip cuurentPayslip) {
        this.cuurentPayslip = cuurentPayslip;
    }

    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    public Collection<Payslip> getPastPayslips() {
        return pastPayslips;
    }

    public void setPastPayslips(Collection<Payslip> pastPayslips) {
        this.pastPayslips = pastPayslips;
    }

    public Map<PhoneType, String> getEmployeePhoneNumbers() {
        return employeePhoneNumbers;
    }

    public void setEmployeePhoneNumbers(Map<PhoneType, String> employeePhoneNumbers) {
        this.employeePhoneNumbers = employeePhoneNumbers;
    }

    public Set<Employee> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(Set<Employee> subordinates) {
        this.subordinates = subordinates;
    }
    
    
}
