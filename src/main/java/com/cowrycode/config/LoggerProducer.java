/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cowrycode.config;

import java.util.logging.Logger;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.ws.rs.Produces;

/**
 *
 * @author JavaEE
 */
public class LoggerProducer {
    
    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint){
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
    
}
