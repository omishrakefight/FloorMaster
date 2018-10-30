/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormaster.Dao;

import com.sg.floormaster.Dto.Order;
import com.sg.floormaster.Exceptions.DaoFilePersistenceException;
import com.sg.floormaster.Exceptions.ServiceInvalidEntryException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author omish
 */
public class OrderImpl implements Orders {

    TaxImpl taxes;
    ProductImpl products;
    int nextOrderNumber = 0;
    Map<String, Order> orders = new HashMap();
    Map<String, Map<String, Order>> ordersByDates = new HashMap();
       
    int fileNameLength = 20;
    int fileNameDateStart = 6;
    int fileNameDateEnd = 16;

    private static final String DELIMITER = ",";
    private static final String ORDER_NUMBER_FILE = "OrderNumber.txt";
    private File fileName;

    @Override
    public void loadWorkOrders() throws DaoFilePersistenceException {
        Scanner scanner;
        final File file = new File("./Orders/");

        File[] files = file.listFiles();

        for (File f : files) {
            if (f.getName().length() == fileNameLength){
                Map<String, Order> currentMap = new HashMap();
                String date = "";
                fileName = f;
                try {
                    scanner = new Scanner(
                            new BufferedReader(
                                    new FileReader(fileName)));
                } catch (FileNotFoundException e) {
                    throw new DaoFilePersistenceException("Could not find the taxes file...", e);
                }
                // set up strings
                String currentLine;
                String[] currentTokens;

                while (scanner.hasNextLine()) {
                    // gets and splits the line into an object
                    currentLine = scanner.nextLine();
                    if (currentLine.contains(DELIMITER)) {

                        currentTokens = currentLine.split(DELIMITER);
                        //if it has delimiter yay its worth reading, THEN if orderNumber (written from number) does NOT equal ORderNumber then its more worth using.
                            if(!currentTokens[11].trim().equalsIgnoreCase("OrderNumber")) {
                                // making the order
                                Order order = new Order();

                                order.setName(currentTokens[0]);
                                order.setState(currentTokens[1]);
                                order.setMaterial(currentTokens[2]);

                                // get tax info on pull, works if changed while logged out.
                                order.setTaxRate(new BigDecimal(currentTokens[3]));
                                order.setArea(new BigDecimal(currentTokens[4]));
                                // not updated at time so grandfathered pricing can happen.
                                order.setCostPerSqFt(new BigDecimal(currentTokens[5]));
                                order.setLaborCostPerSqFt(new BigDecimal(currentTokens[6]));
                                order.setMaterialCost(new BigDecimal(currentTokens[7]));
                                order.setLaborCost(new BigDecimal(currentTokens[8]));
                                order.setPaidTaxes(new BigDecimal(currentTokens[9]));
                                order.setTotalCost(new BigDecimal(currentTokens[10]));
                                order.setOrderNumber(currentTokens[11]);


                                order.setOrderDate(f.getName().substring(fileNameDateStart, fileNameDateEnd));

                                date = order.getOrderDate();
                                // add it to inner map
                                currentMap.put(order.getOrderNumber(), order);
                            }
                    }
                }
                ordersByDates.put(date, currentMap);
            }
        }
    }

    public void writeWorkOrders() throws DaoFilePersistenceException {
        PrintWriter out;

        // this will be the file names - the keys which are the dates
        Set<String> fileNames = ordersByDates.keySet();
        for (String files : fileNames) {
            try {
                //makes the date into a .txt file
                out = new PrintWriter(new FileWriter("./Orders/" + "Order_" + files + ".txt"));
            } catch (IOException e) {
                throw new DaoFilePersistenceException("Could not write to the file.");
            }
            // Write in the information on the rop of files
            out.println("Name, state, material, taxRate, area, costMaterialPerSquareFoot, costLaborPerSquareFoot,"
                    + " materialCost, laborCost, totalTaxes, totalCost, OrderNumber.");
            //double iterates - each 'file by date' and each order in said date.
            orders = ordersByDates.get(files);
            Set<String> orderKeys = orders.keySet();
            for (String currentOrderKey : orderKeys) {
                Order currentOrder = orders.get(currentOrderKey);

                out.println(currentOrder.getName() + DELIMITER
                        + currentOrder.getState() + DELIMITER
                        + currentOrder.getMaterial() + DELIMITER
                        + currentOrder.getTaxRate() + DELIMITER
                        + currentOrder.getArea() + DELIMITER
                        + currentOrder.getCostPerSqFt() + DELIMITER
                        + currentOrder.getLaborCostPerSqFt() + DELIMITER
                        + currentOrder.getMaterialCost() + DELIMITER
                        + currentOrder.getLaborCost() + DELIMITER
                        + currentOrder.getPaidTaxes() + DELIMITER
                        + currentOrder.getTotalCost() + DELIMITER
                        + currentOrder.getOrderNumber());
                out.flush();
            }

        }
        // WAITING UNTIL I GET MY ADD AND MAPS SET UP BE4 WRITING.
        //Set<String> keys =
    }

    public OrderImpl(TaxImpl taxes, ProductImpl products) throws DaoFilePersistenceException {
        // this is to set up preloading for possible future changes (and for getting taxes now)
        this.taxes = taxes;
        this.products = products;
        loadWorkOrders();
    }

    @Override
    public Map getOrderMap() {
        return ordersByDates;
    }

    @Override
    public Map<String, Order> getOrdersByDate(String date) throws ServiceInvalidEntryException {
        Map<String, Order> currentMap = new HashMap();
        currentMap = ordersByDates.get(date);
        if (currentMap == null) {
            throw new ServiceInvalidEntryException("No orders exist for that date.");
        }
        return currentMap;
    }

    // new function
    public Order getASpecificOrder(String date, String orderNumber) throws ServiceInvalidEntryException {
        Map<String, Order> currentMap = new HashMap();
        currentMap = getOrdersByDate(date);
        Order currentOrder = currentMap.get(orderNumber);
        return currentOrder;
    }
    //its cool

    @Override
    public Set<String> getOrderKeysByDate(String date) throws ServiceInvalidEntryException {
        Map<String, Order> currentMap = new HashMap<>();
        currentMap = ordersByDates.get(date);
        return currentMap.keySet();
    }

    @Override
    public Set getOrderKeySet() {
        return ordersByDates.keySet();
    }

    @Override
    public Order addOrder(String state, String material, String area, String name, String date) throws DaoFilePersistenceException {
        String orderNumber = loadOrderNumber();
        saveOrderNumber();

        Order order = new Order(state, material, area, name);
        order.setOrderNumber(orderNumber);

        order.setTaxRate(taxes.getTax(state).getSalesTax());
        order.setCostPerSqFt(products.getCostPerSqFt(material));
        order.setLaborCostPerSqFt(products.getLaborCostPerSqFt(material));
        order.setOrderDate(date);

        order.setMaterialCost(order.getCostPerSqFt().multiply(order.getArea()));
        order.setLaborCost(order.getArea().multiply(order.getLaborCostPerSqFt()));

        BigDecimal preTotal = (order.getMaterialCost().add(order.getLaborCost()).setScale(2, RoundingMode.HALF_UP));
        order.setPaidTaxes(preTotal.multiply(order.getTaxRate()).setScale(2, RoundingMode.HALF_UP));
        order.setTotalCost(preTotal.add(order.getPaidTaxes()));

        orders = ordersByDates.get(date);
        //if the date exists
        if (orders != null) {
            orders.put(orderNumber, order);
        } else {
            //else make a new date hasmap and populate it.
            Map<String, Order> newOrder = new HashMap();
            newOrder.put(orderNumber, order);
            ordersByDates.put(date, newOrder);
        }
        return order;
    }

    @Override  // HERE I WILL READ IN NEW ORDER AND SAVE IT IN MAP OVER OLD.  
    public void editOrder(Order newOrder) {
        String date = newOrder.getOrderDate();
        String orderNumber = newOrder.getOrderNumber();

        orders = ordersByDates.get(date);
        //if the date exists, put it in.
        if (orders != null) {
            orders.put(orderNumber, newOrder);
        } else {
            //else make a new date hashmap and populate it.
            Map<String, Order> newMap = new HashMap();
            newMap.put(orderNumber, newOrder);
            ordersByDates.put(date, newMap);
        }
    }

    @Override
    public void removeOrder(String orderNumber, String date) throws ServiceInvalidEntryException {
        //double If's because you mentioned nullPointers shouldn't be in catches, and the second requires the first to not be null.
        if (ordersByDates.get(date) == null) {
            throw new ServiceInvalidEntryException("No order under that date and number exist.");
        }
        orders = ordersByDates.get(date);
        if (orders.get(orderNumber) == null) {
            throw new ServiceInvalidEntryException("No order under that date and number exist.");
        }
        orders.remove(orderNumber);

    }

    @Override
    public void writeCurrentWork() {

    }

    public String loadOrderNumber() throws DaoFilePersistenceException {
        Scanner scanner;
        String sNextOrderNumber = "";
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(ORDER_NUMBER_FILE)));
        } catch (FileNotFoundException e) {
            throw new DaoFilePersistenceException("could not find order number file.");
        }

        if (scanner.hasNextLine()) {
            sNextOrderNumber = scanner.nextLine();
            nextOrderNumber = Integer.parseInt(sNextOrderNumber);
            nextOrderNumber++;
            sNextOrderNumber = Integer.toString(nextOrderNumber);
        }

        return sNextOrderNumber;
    }

    public void saveOrderNumber() throws DaoFilePersistenceException {

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(ORDER_NUMBER_FILE));
        } catch (IOException e) {
            throw new DaoFilePersistenceException("Could not save the new stock.");
        }
        out.println(nextOrderNumber);
        out.flush();
        out.close();
    }
}
