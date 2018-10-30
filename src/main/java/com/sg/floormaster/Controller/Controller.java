/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormaster.Controller;

import com.sg.floormaster.Service.Service;
import com.sg.floormaster.Service.ServiceImpl;
import com.sg.floormaster.Dto.Order;
import com.sg.floormaster.Exceptions.DaoFilePersistenceException;
import com.sg.floormaster.Exceptions.ServiceInvalidEntryException;
import com.sg.floormaster.Exceptions.ServiceNoOrderNumberException;
import com.sg.floormaster.Exceptions.ServiceNotAValidNumberException;
import com.sg.floormaster.UI.ViewImpl;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author omish
 */
public class Controller {
    
    ViewImpl view;
    Service service;
    
    public void run() {
        int choice = 0;
        try {
            service.loadWorkOrders();
            
            service.getOrderKeySet();
            System.out.println(service.getOrderKeySet());
            do {
                choice = displayMenu();
                switch (choice) {
                    case 1:
                        lookupWorkOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        saveWork();
                        break;
                    case 6:
                        saveWork();
                        break;
                }
                
            } while (choice != 6);
        } catch (DaoFilePersistenceException e) {
            view.displayException(e.getMessage());
        }
    }
    
    public Controller(ServiceImpl service, ViewImpl view) {
        this.service = service;
        this.view = view;
    }
    
    private int displayMenu() {
        return view.displayMainMenu();
    }
    
    private void editOrder() {
        try {
            view.displayEditOrderBanner();
            String date = view.getDateForSpecific();
            String orderNumber = view.getOrderNumber();
            Order currentOrder = service.getASpecificOrder(date, orderNumber);
            view.displayASingleOrder(currentOrder);
            view.displayEditRules();
            // copy from addOrder to get a new one made, pass in the old one for value copying. Dont give new order number.
            String newName = view.getName();
            service.checkName(newName);
            String newState = view.getStateAllowEmpty(service.getTaxKeyList());
            service.checkStateWithSpace(newState, service.getTaxKeyList());
            String newMaterial = view.getProductionTypeAllowEmpty(service.getProductKeySet());
            service.checkProductWithSpace(newMaterial, service.getProductKeySet());
            String newArea = view.getArea();
            service.checkAreaWithSpace(newArea);
            String newDate = view.getDate(true);
            service.checkDateWithSpace(newDate);

            //Compare the new one and ask if they want the changes.
            Order newOrder = service.createAndCompare(newState, newMaterial, newArea, newName, newDate, currentOrder);
            view.displayASingleOrder(newOrder);
            String save = view.getCommitChange();
            // Erase old one and put in the new one, since dates can change Removal is necessary.
            if (save.equalsIgnoreCase("yes") && service.productionCheck()) {
                // this checks you selected yes and production mode is enabled
                service.removeOrder(orderNumber, date);
                service.editOrder(newOrder);
            }
            //if not do nothing, changed will be lost.
            
            view.displaySuccessBanner();
        } catch (ServiceInvalidEntryException | ServiceNotAValidNumberException e) {
            view.displayException(e.getMessage());
        }
    }
    
    private void removeOrder() {
        try {
            String orderNum = view.getOrderNumber();
            String date = view.getDateForSpecific();
            service.orderCheckForNull(date);
            service.removeOrder(orderNum, date);
            view.displaySuccessBanner();
        } catch (ServiceInvalidEntryException | ServiceNoOrderNumberException e) {
            view.displayException(e.getMessage());
        }
    }
    
    private void showTaxes() {
        
    }
    
    private void lookupWorkOrders() {
        try {
            String date = view.getDateToLookup();
            service.orderCheckForNull(date);
            Map<String, Order> orders = service.getOrdersByDate(date);
            Set<String> keys = service.getOrderKeysByDate(date);
            view.displayAnOrder(orders, keys);
        } catch (ServiceInvalidEntryException | ServiceNoOrderNumberException e) {
            view.displayException(e.getMessage());
        }
    }
    
    private void addOrder() {
        view.displayNewOrderBanner();
        boolean checkName = true, checkState = true, checkMaterial = true, checkArea = true, checkDate = true;
        String name = "", state = "", material = "", area = "", date = "";
        // This mess is the price i pay from moving this all from the view. It is hideous. : /
        //check and validate name
        do {
            try {
                name = view.getName();
                checkName = service.checkName(name);
            } catch (ServiceInvalidEntryException e) {
                view.displayException(e.getMessage());
            }
        } while (checkName);
        //check and validate state
        do {
            try {
                state = view.getState(service.getTaxKeyList());
                checkState = service.checkState(state, service.getTaxKeyList());
            } catch (ServiceInvalidEntryException e) {
                view.displayException(e.getMessage());
            }
        } while (checkState);
        //check and validate material
        do {
            try {
                material = view.getProductionType(service.getProductKeySet());
                checkMaterial = service.checkProduct(material, service.getProductKeySet());
            } catch (ServiceInvalidEntryException e) {
                view.displayException(e.getMessage());
            }
        } while (checkMaterial);
        //check and validate area
        do {
            try {
                area = view.getArea();
                checkArea = service.checkArea(area);
            } catch (ServiceNotAValidNumberException e) {
                view.displayException(e.getMessage());
            }
        } while (checkArea);
        //check and validate date.
        do {
            try {
                date = view.getDate(false);
                checkDate = service.checkDate(date);
            } catch (ServiceInvalidEntryException e) {
                view.displayException(e.getMessage());
            }
        } while (checkDate);
//        date = view.getDate(false);
        try {            
            service.addOrder(state, material, area, name, date);
            view.displaySuccessBanner();
        } catch (DaoFilePersistenceException  e) {
            view.displayException(e.getMessage());
        }
    }
    
    private void saveWork() {
        if (service.productionCheck()) {
            try {
                service.writeCurrentWork();
            } catch (DaoFilePersistenceException e) {
                view.displayException(e.getMessage());
            }
        } else {
            view.displayTestModeSaveBanner();
        }
    }
}
