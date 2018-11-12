/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cowrycode.entities;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 *
 * @author JavaEE
 */
@Entity
public class Allowance extends AbstractEntity{
    
    @NotNull(message = "Allowance must be set")
    private BigDecimal allowanceAmount;
    
    @NotNull(message = "Allowance Name must be set")
    private String allowanceName;

    public BigDecimal getAllowanceAmount() {
        return allowanceAmount;
    }

    public void setAllowanceAmount(BigDecimal allowanceAmount) {
        this.allowanceAmount = allowanceAmount;
    }

    public String getAllowanceName() {
        return allowanceName;
    }

    public void setAllowanceName(String allowanceName) {
        this.allowanceName = allowanceName;
    }
    
        
    
}
