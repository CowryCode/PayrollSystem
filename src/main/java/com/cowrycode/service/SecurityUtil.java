/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cowrycode.service;

import javax.enterprise.context.RequestScoped;

/**
 *
 * @author JavaEE
 */
@RequestScoped
public class SecurityUtil {
    private final PasswordService passwordService = new DefaulPasswordService();
}
