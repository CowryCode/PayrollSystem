/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cowrycode.service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.util.ByteSource;

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
    
    public Date toDate(LocalDateTime localDateTime){
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
    
    public boolean passwordsmatch(String dbStoredHashpassword, String saltText, String clearTextpassword){
        ByteSource salt = ByteSource.Util.bytes(Hex.decode(saltText));
        String hashedpassword = hashandSaltpassword(clearTextpassword, salt);
        return hashedpassword.equals(dbStoredHashpassword);
    }
    
    public Map<String,String> hashPassword(String clearTextpassword){
        ByteSource salt = getSalt();
        
        Map<String, String> credMap = new HashMap<>();
        credMap.put("hashedpassword", hashandSaltpassword(clearTextpassword, salt));
        credMap.put("Salt", salt.toHex());
        return credMap;
    }
    
    private String hashandSaltpassword(String clearTextpassword, ByteSource salt) {
        return  new Sha512Hash(clearTextpassword, salt, 2000000).toHex();
    }

    private ByteSource getSalt() {
     return new SecureRandomNumberGenerator().nextBytes();
    }
}