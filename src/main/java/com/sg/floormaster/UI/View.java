/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormaster.UI;

import com.sg.floormaster.Dto.Order;
import com.sg.floormaster.Exceptions.ServiceNotAValidNumberException;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author omish
 */
public interface View {
    
    
    public void displayOrderDatesBanner();
    public void displayNewOrderBanner();
    public void displayEditOrderBanner();
    public void displaySpecificOrderBanner();
    public void displaySaveDataBanner();
    public void displayByeBanner();
    public void displaySuccessBanner();
    public void displayAnOrder(Map<String, Order> orders, Set<String> keys);
    public void displayASingleOrder(Order currentOrder);
    public void displayEditRules();
    public void displayTestModeSaveBanner();
    
    public String getCommitChange();
    public String getDateToLookup();
    public String getName();
    
    public String getStateAllowEmpty(Set<String> states);
    public String getState(Set<String> states);
    
    public String getProductionTypeAllowEmpty(Set<String> products);
    public String getProductionType(Set<String> product);
    
    public String getArea() throws ServiceNotAValidNumberException;
    public String getDate(boolean allowEmpty);
    public String getDateForSpecific();
    public String getOrderNumber();
    
    public int displayMainMenu();
    public void displayException(String str);
    
}
