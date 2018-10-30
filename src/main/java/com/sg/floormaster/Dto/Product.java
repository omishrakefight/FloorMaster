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
public class Product {

    String name;

    BigDecimal costPerSqFt;
    BigDecimal laborCostPerSqFt;

    public String getName() {
        return name;
    }

    public BigDecimal getCostPerSqFt() {
        return costPerSqFt;
    }

    public BigDecimal getLaborCostPerSqFt() {
        return laborCostPerSqFt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCostPerSqFt(String costPerSqFt) {
        // converting from String (file) to bigDecimal
        this.costPerSqFt = new BigDecimal(costPerSqFt);
    }

    public void setLaborCostPerSqFt(String laborCostPerSqFt) {
        // converting from String (file) to bigDecimal
        this.laborCostPerSqFt = new BigDecimal(laborCostPerSqFt);
    }

}
