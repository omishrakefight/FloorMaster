/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormaster.Dto;

import java.math.BigDecimal;

/**
 *
 * @author omish
 */
public class Tax {
    String name;
    BigDecimal salesTax;

    public String getName() {
        return name;
    }

    public BigDecimal getSalesTax() {
        return salesTax;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalesTax(String salesTax) {
        //BigDecimal tax = new BigDecimal(salesTax);
        this.salesTax = new BigDecimal(salesTax);
    }
    
    
    
}
