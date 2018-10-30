/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormaster.Dao;

import com.sg.floormaster.Dto.Order;
import com.sg.floormaster.Exceptions.DaoFilePersistenceException;
import com.sg.floormaster.Exceptions.ServiceInvalidEntryException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author omish
 */
public interface Orders {

    int orderNumber = -1;

    Map orders = new HashMap<String, HashMap<String, Order>>();

    public Order addOrder(String state, String material, String area, String name, String date) throws DaoFilePersistenceException;

    public void editOrder(Order newOrder);

    public void removeOrder(String orderNumber, String date) throws ServiceInvalidEntryException;
    
    public Map<String, Order> getOrdersByDate(String str)throws ServiceInvalidEntryException;
    
    public Set<String> getOrderKeysByDate(String date)throws ServiceInvalidEntryException;

    public void writeCurrentWork() throws DaoFilePersistenceException;
    
    public void loadWorkOrders() throws DaoFilePersistenceException;
    
    public Order getASpecificOrder(String date, String orderNumber) throws ServiceInvalidEntryException;
    
    public Map getOrderMap();
    
    public Set getOrderKeySet();
    
}
