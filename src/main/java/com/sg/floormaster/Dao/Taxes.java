/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormaster.Dao;

import com.sg.floormaster.Dto.Tax;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author omish
 */
public interface Taxes {
    
    Map taxes = new HashMap<String, Taxes>();
    
    public Set getTaxKeyList();
    
    public Map<String, Tax> getTaxMap();
    
    public Tax getTax(String str);
}
