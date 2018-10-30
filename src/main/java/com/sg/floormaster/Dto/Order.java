/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormaster.Dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author omish
 */
public class Order {

    String name;
    String state;
    String material;
    String orderNumber;
    String orderDate;
    BigDecimal taxRate;
    BigDecimal area;
    BigDecimal costPerSqFt;
    BigDecimal laborCostPerSqFt;
    BigDecimal materialCost;
    BigDecimal laborCost;
    BigDecimal paidTaxes;
    BigDecimal totalCost;

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Order(String state, String material, String area, String name) {
        this.area = new BigDecimal(area);
        this.state = state;
        this.material = material;
        this.name = name;
    }

    public Order() {

    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String getMaterial() {
        return material;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public BigDecimal getArea() {
        return area;
    }

    public BigDecimal getCostPerSqFt() {
        return costPerSqFt;
    }

    public BigDecimal getLaborCostPerSqFt() {
        return laborCostPerSqFt;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public BigDecimal getPaidTaxes() {
        return paidTaxes;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    // SETTERES AFTER HERE ------------------------------------------
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setTaxRate(BigDecimal taxRate) {
        BigDecimal convertToDecimal = new BigDecimal("100");
        this.taxRate = taxRate.setScale(2, RoundingMode.HALF_UP).divide(convertToDecimal);
    }

    public void setArea(BigDecimal area) {
        this.area = area.setScale(2, RoundingMode.HALF_UP);
    }

    public void setCostPerSqFt(BigDecimal costPerSqFt) {
        this.costPerSqFt = costPerSqFt.setScale(2, RoundingMode.HALF_UP);
    }

    public void setLaborCostPerSqFt(BigDecimal laborCostPerSqFt) {
        this.laborCostPerSqFt = laborCostPerSqFt.setScale(2, RoundingMode.HALF_UP);
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost.setScale(2, RoundingMode.HALF_UP);
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost.setScale(2, RoundingMode.HALF_UP);
    }

    public void setPaidTaxes(BigDecimal paidTaxes) {
        this.paidTaxes = (paidTaxes).setScale(2, RoundingMode.HALF_UP);
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = (totalCost).setScale(2, RoundingMode.HALF_UP);
    }

}
