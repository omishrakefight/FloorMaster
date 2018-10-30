/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormaster.Service;

import com.sg.floormaster.Dao.OrderImpl;
import com.sg.floormaster.Dao.ProductImpl;
import com.sg.floormaster.Dao.TaxImpl;
import com.sg.floormaster.Dto.Order;
import com.sg.floormaster.Dto.Tax;
import com.sg.floormaster.Exceptions.DaoFilePersistenceException;
import com.sg.floormaster.Exceptions.ServiceInvalidEntryException;
import com.sg.floormaster.Exceptions.ServiceNoOrderNumberException;
import com.sg.floormaster.Exceptions.ServiceNotAValidNumberException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author omish
 */
public class ServiceImpl implements Service {

    TaxImpl tax;
    OrderImpl order;
    ProductImpl product;

    boolean canSave;

    public ServiceImpl(TaxImpl tax, OrderImpl order, ProductImpl product) throws DaoFilePersistenceException {
        this.tax = tax;
        this.product = product;
        this.order = order;

        loadProductionCheck();
    }

    @Override
    public boolean checkName(String str) throws ServiceInvalidEntryException {
        if (str.contains("::")) {
            throw new ServiceInvalidEntryException("Don't be like that, no name contains ::");
        }
        return false;
    }

    @Override
    public boolean checkStateWithSpace(String str, Set<String> states) throws ServiceInvalidEntryException {
        Set<String> addSpace = new HashSet<>();
        for (String s : states) {
            addSpace.add(s);
        }
        addSpace.add("");
        
        return checkState(str, addSpace);
    }

    @Override
    public boolean checkState(String str, Set<String> states) throws ServiceInvalidEntryException {
        boolean invalidSelection = true;
        for (String state : states) {
            if (str.equals(state)) {
                invalidSelection = false;
            }
        }
        if (invalidSelection) {
            throw new ServiceInvalidEntryException("not a valid state, try again.");
        }
        return invalidSelection;
    }

    @Override
    public boolean checkDateWithSpace(String str) throws ServiceInvalidEntryException {
        if(str.equalsIgnoreCase("")){
            return false;
        }
        return checkDate(str);
    }

    @Override
    public boolean checkDate(String str) throws ServiceInvalidEntryException {
        try {
            LocalDate ld = LocalDate.parse(str, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw new ServiceInvalidEntryException("That is not a valid ISO format, try again.");
        }
        return false;
    }

    @Override
    public boolean checkAreaWithSpace(String str) throws ServiceNotAValidNumberException, ServiceNotAValidNumberException {
        if(str.equalsIgnoreCase("")){
            return false;
        }
        return checkArea(str);
    }

    @Override
    public boolean checkArea(String str) throws ServiceNotAValidNumberException, ServiceNotAValidNumberException {
        try {
            if (Integer.parseInt(str) <= 0) {
                throw new ServiceNotAValidNumberException("Don't be absurd.  Merely removing carpet is not profitable.");
            }
        } catch (java.lang.NumberFormatException e) {
            throw new ServiceNotAValidNumberException("Not a valid number -.-");
        }
        return false;
    }

    @Override
    public boolean checkProductWithSpace(String str, Set<String> products) throws ServiceInvalidEntryException {
    Set<String> addSpace = new HashSet<>();
    // LAMBDA HERE YESSSSSS!!!!!!
    products.forEach((s) -> {
        addSpace.add(s);
        });
        addSpace.add("");
        
        return checkProduct(str, addSpace);
    }

    @Override
    public boolean checkProduct(String str, Set<String> products) throws ServiceInvalidEntryException {
        boolean invalidSelection = true;
        // this cycles all current states to find a match
        for (String product : products) {
            if (str.equals(product)) {
                invalidSelection = false;
            }
        }
        if (invalidSelection == true) {
            throw new ServiceInvalidEntryException("Not a valid product, please try again.");
        }
        return invalidSelection;
    }

    @Override
    public boolean productionCheck() {
        return canSave;
    }

    public void loadProductionCheck() throws DaoFilePersistenceException {
        Scanner scanner;

        final String PRODUCTION_CHECK = "Production.txt";
        final String DELIMITER = "::";
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(PRODUCTION_CHECK)));
        } catch (FileNotFoundException e) {
            throw new DaoFilePersistenceException("Could not find the products.", e);
        }

        //Set up the string reader
        String currentLine;
        String[] currentTokens;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            // get current line and test it has Delimiter - that means it is my data.
            if (currentLine.contains(DELIMITER)) {
                if (currentLine.equalsIgnoreCase("::true")) {
                    canSave = true;
                } else {
                    canSave = false;
                }
            }
        }
        scanner.close();
    }

    @Override
    public Order createAndCompare(String state, String material, String area, String name, String date, Order oldOrder) {
        
        
        //if the value was skipped, import old value.
        if (state.equalsIgnoreCase("")) {
            state = oldOrder.getState();
        }
        if (material.equalsIgnoreCase("")) {
            material = oldOrder.getMaterial();
        }
        if (area.equalsIgnoreCase("")) {
            area = oldOrder.getArea().toString();
        }
        if (name.equalsIgnoreCase("")) {
            name = oldOrder.getName();
        }
        if (date.equalsIgnoreCase("")) {
            date = oldOrder.getOrderDate();
        }
        Order order = new Order(state, material, area, name);

        //import order number regardless, this doesn't change.
        order.setOrderNumber(oldOrder.getOrderNumber());

        order.setTaxRate(tax.getTax(state).getSalesTax());
        order.setCostPerSqFt(product.getCostPerSqFt(material));
        order.setLaborCostPerSqFt(product.getLaborCostPerSqFt(material));
        order.setOrderDate(date);

        order.setMaterialCost(order.getCostPerSqFt().multiply(order.getArea()));
        order.setLaborCost(order.getArea().multiply(order.getLaborCostPerSqFt()));

        BigDecimal preTotal = (order.getMaterialCost().add(order.getLaborCost()).setScale(2, RoundingMode.HALF_UP));
        order.setPaidTaxes(preTotal.multiply(order.getTaxRate()).setScale(2, RoundingMode.HALF_UP));
        order.setTotalCost(preTotal.add(order.getPaidTaxes()));

        return order;
    }

    @Override
    public void loadWorkOrders() throws DaoFilePersistenceException {
        order.loadWorkOrders();
    }

    @Override
    public Map<String, Order> getOrdersByDate(String str) throws ServiceInvalidEntryException {
        return order.getOrdersByDate(str);
    }

    @Override
    public Set<String> getOrderKeysByDate(String date) throws ServiceInvalidEntryException {
        return order.getOrderKeysByDate(date);
    }

    @Override
    public Order getASpecificOrder(String date, String orderNumber) throws ServiceInvalidEntryException {
        return order.getASpecificOrder(date, orderNumber);
    }

    @Override
    public Tax getTax(String str) {
        return tax.getTax(str);
    }

    @Override
    public Map getProductMap() {
        return product.getProductMap();
    }

    @Override
    public Set getProductKeySet() {
        return product.getProductKeySet();
    }

    @Override
    public void orderCheckForNull(String date) throws ServiceNoOrderNumberException {
        Map<String, Order> currentMap = new HashMap<>();
        Map<String, Map<String, Order>> ordersByDates = new HashMap();
        ordersByDates = order.getOrderMap();
        currentMap = ordersByDates.get(date);
        if (currentMap == null) {
            throw new ServiceNoOrderNumberException("No orders exist for that date.");
        }
    }

    @Override
    public Map<String, Tax> getTaxMap() {
        return tax.getTaxMap();
    }

    @Override
    public Map getOrderMap() {
        return order.getOrderMap();
    }

    @Override
    public Set getOrderKeySet() {
        return order.getOrderKeySet();
    }

    @Override
    public Set getTaxKeyList() {
        return tax.getTaxKeyList();
    }

    @Override
    public BigDecimal getCostPerSqFt(String material) {
        return product.getCostPerSqFt(material);
    }

    @Override
    public BigDecimal getLaborCostPerSqFt(String material) {
        return product.getLaborCostPerSqFt(material);
    }

    @Override
    public Order addOrder(String state, String material, String area, String name, String date) throws DaoFilePersistenceException {
        return order.addOrder(state, material, area, name, date);
    }

    @Override
    public void editOrder(Order newOrder) {
        order.editOrder(newOrder);
    }

    @Override
    public void removeOrder(String orderNumber, String date) throws ServiceInvalidEntryException {
        order.removeOrder(orderNumber, date);
    }

    @Override
    public void writeCurrentWork() throws DaoFilePersistenceException {
        order.writeWorkOrders();
    }

}
