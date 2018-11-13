/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cowrycode.entities;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

/**
 *
 * @author JavaEE
 */
@Entity
@NamedQuery(name = ApplicationUser.FIND_USER_BY_CREDENTIALS, query = "select u from ApplicationUser u where u.email =:email")
public class ApplicationUser extends AbstractEntity{
    public static final String FIND_USER_BY_CREDENTIALS = "User.findUserByCredentials";
    //    @SequenceGenerator(name = "User_seq", sequenceName = "User_sequence")
//    @GeneratedValue(generator = "User_seq")
//    @Id
//    private Long id;

//    CREATE SEQUENCE Emp_Seq
//    MINVALUE 1
//    START WITH 1
//    INCREMENT BY 50
    @NotEmpty(message = "email must not be empty")
    @Email(message = "email should be in the form :youremail@domain.com")
    @FormParam("email")
    private String email;
    
    @NotEmpty(message = "Password must be set")
    @Size(min = 8)
    @FormParam("password")
    private String password;
    
    private String salt;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    
        
}
