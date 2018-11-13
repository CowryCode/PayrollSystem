/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cowrycode.service;

import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;

/**
 *
 * @author JavaEE
 */
@RequestScoped
public class SecurityUtil {
    //We need Shiro Apache package for Password service
    private final PasswordService passwordService = new DefaultPasswordService();
    
    @Inject
    private  QueryService queryService;
    
    public String encryptText(String plaintext){
       return passwordService.encryptPassword(plaintext);
    }
    
    //    public boolean passwordsMatch(String plainTextPassword, String encryptedPassword) {
//        return passwordService.passwordsMatch(plainTextPassword, encryptedPassword);
//    }
    
    public Key generateKey(String KeyString){
        return  new SecretKeySpec(KeyString.getBytes(),0, KeyString.getBytes().length, "DES");
    }
    
    public boolean authenticateUser(String email, String password){
        return queryService.authenticateUser(email, password);
    }
}
