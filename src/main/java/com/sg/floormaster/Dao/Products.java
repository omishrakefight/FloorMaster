/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormaster.Dao;

import com.sg.floormaster.Dto.Product;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author omish
 */
public interface Products {
    
    Map products = new HashMap<String, Product>();
    
    
    public BigDecimal getCostPerSqFt(String product);
    public BigDecimal getLaborCostPerSqFt(String product);
    public Map getProductMap();
    public Set getProductKeySet();
}
