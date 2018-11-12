package com.cowrycode.entities;


import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JavaEE
 */


//    Primitive Java types: byte, int, short, long, and char
//    Wrapper classes of primitive Java types: Byte, Integer, Short, Long,
//    and Character
//    String: java.lang.String
//    Large numeric type: java.math.BigInteger
//    Temporal types: java.util.Date and java.sql.Date
@MappedSuperclass
public abstract class AbstractEntity implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    
    protected String userEmail;
    
    @Version
    protected Long version;
    
    protected LocalDateTime createdon;
    protected LocalDateTime updatedon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public LocalDateTime getCreatedon() {
        return createdon;
    }

    public void setCreatedon(LocalDateTime createdon) {
        this.createdon = createdon;
    }

    public LocalDateTime getUpdatedon() {
        return updatedon;
    }

    public void setUpdatedon(LocalDateTime updatedon) {
        this.updatedon = updatedon;
    }
    
    
    
    
    
}
