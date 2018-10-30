/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormaster.Service;

import com.sg.floormaster.Dao.Orders;
import com.sg.floormaster.Dao.Products;
import com.sg.floormaster.Dao.Taxes;
import com.sg.floormaster.Dto.Order;
import com.sg.floormaster.Exceptions.ServiceInvalidEntryException;
import com.sg.floormaster.Exceptions.ServiceNoOrderNumberException;
import com.sg.floormaster.Exceptions.ServiceNotAValidNumberException;
import java.util.Set;

/**
 *
 * @author omish
 */
public interface Service extends Taxes, Products, Orders{
    
    public void orderCheckForNull(String date) throws ServiceNoOrderNumberException;
    
    public boolean checkName(String str) throws ServiceInvalidEntryException;
    public boolean checkDate(String str) throws ServiceInvalidEntryException;
    public boolean checkArea(String str) throws ServiceNotAValidNumberException, ServiceNotAValidNumberException;
    public boolean checkProduct(String str, Set<String> products) throws ServiceInvalidEntryException;
    public boolean checkState(String str, Set<String> products) throws ServiceInvalidEntryException;
    
    // these add on and then call the base functions - D.R.Y for added effect.
    public boolean checkDateWithSpace(String str) throws ServiceInvalidEntryException;
    public boolean checkAreaWithSpace(String str) throws ServiceNotAValidNumberException, ServiceNotAValidNumberException;
    public boolean checkProductWithSpace(String str, Set<String> products) throws ServiceInvalidEntryException;
    public boolean checkStateWithSpace(String str, Set<String> products) throws ServiceInvalidEntryException;
    
    public boolean productionCheck();
    
    public Order createAndCompare(String state, String material, String area, String name, String date, Order oldOrder);
}
