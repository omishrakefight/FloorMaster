/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormaster.UI;

import com.sg.floormaster.Dto.Order;
import com.sg.floormaster.Exceptions.ServiceNotAValidNumberException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author omish
 */
public class ViewImpl implements View {

    Ui ui;

    public ViewImpl(Ui ui) {
        this.ui = ui;
    }

    @Override
    public void displayTestModeSaveBanner() {
        ui.print("=== Test mode save completed ===");
    }

    @Override
    public void displayOrderDatesBanner() {
        ui.print("=== Display orders by Date ===");
    }

    @Override
    public void displayNewOrderBanner() {
        ui.print("=== Creating a new work order ===");
    }

    @Override
    public void displayEditOrderBanner() {
        ui.print("=== Editing a work Order ===");
    }

    @Override
    public void displaySpecificOrderBanner() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void displaySaveDataBanner() {
        ui.print("=== Attempting to save... ===");
    }

    @Override
    public void displayByeBanner() {
        ui.print("=== Have a nice day, GoodBye ===");
    }

    @Override
    public void displaySuccessBanner() {
        ui.print("=== Success! current task completed ===");
    }

    @Override
    public void displayAnOrder(Map<String, Order> orders, Set<String> keys) {
        for (String key : keys) {
            ui.print("\n");
            Order currentOrder = orders.get(key);
            ui.print("Order number : " + currentOrder.getOrderNumber());
            ui.print("Order name : " + currentOrder.getName());
            ui.print("Order state : " + currentOrder.getState());
            ui.print("Order material : " + currentOrder.getMaterial());
            ui.print("Order date : " + currentOrder.getOrderDate());
            ui.print("Order tax rate : " + currentOrder.getTaxRate());
            ui.print("Order area for work : " + currentOrder.getArea());
            ui.print("Order material cost per square ft : " + currentOrder.getCostPerSqFt());
            ui.print("Order labor cost per sqaure ft : " + currentOrder.getLaborCostPerSqFt());
            ui.print("Order total material cost : " + currentOrder.getMaterialCost());
            ui.print("Order total labor cost : " + currentOrder.getLaborCost());
            ui.print("Order total taxes paid : " + currentOrder.getPaidTaxes());
            ui.print("Order total cost paid : " + currentOrder.getTotalCost());
        }
    }

    @Override
    public void displayASingleOrder(Order currentOrder) {
        ui.print("\n");

        ui.print("Order number : " + currentOrder.getOrderNumber());
        ui.print("Order name : " + currentOrder.getName());
        ui.print("Order state : " + currentOrder.getState());
        ui.print("Order material : " + currentOrder.getMaterial());
        ui.print("Order date : " + currentOrder.getOrderDate());
        ui.print("Order tax decimal : " + currentOrder.getTaxRate());
        ui.print("Order area for work : " + currentOrder.getArea());
        ui.print("Order material cost per square ft : " + currentOrder.getCostPerSqFt());
        ui.print("Order labor cost per sqaure ft : " + currentOrder.getLaborCostPerSqFt());
        ui.print("Order total material cost : " + currentOrder.getMaterialCost());
        ui.print("Order total labor cost : " + currentOrder.getLaborCost());
        ui.print("Order total taxes paid : " + currentOrder.getPaidTaxes());
        ui.print("Order total cost paid : " + currentOrder.getTotalCost());
    }

    @Override
    public int displayMainMenu() {
        ui.print("* * * * * * * * * * * * * * * * ");
        ui.print("===  Main menu ===");
        ui.print("1) Display orders.");
        ui.print("2) Add a work order.");
        ui.print("3) Edit a work order.");
        ui.print("4) Remove a work order.");
        ui.print("5) Save current work.");
        ui.print("6) Save and quit");
        ui.print("* * * * * * * * * * * * * * * * ");
        return ui.readInt("Choose an option, please", 1, 6);
    }

    @Override
    public String getName() {
        return ui.readString("What is the name for the order?");
    }

    @Override
    public String getStateAllowEmpty(Set<String> states) {
        Set<String> statesWithSpaces = new HashSet<>();
        // Sets instantiated by .KeySet are linked to the map and do not support .add -.- was fun.
        for (String state : states) {
            statesWithSpaces.add(state);
        }
        statesWithSpaces.add("");
        return getState(statesWithSpaces);
    }

    @Override
    public String getState(Set<String> states) {
        boolean invalidSelection = true;
        String stateChoice = "";
            ui.print("We service theses areas...");
            for (String state : states) {
                if (state.equals("")) {
                    //do nothing, I dont want the blank space
                } else {
                    ui.print(state);
                }
            }
            stateChoice = ui.readString("What is the state of purchase?");
        return stateChoice;
    }

    @Override
    public String getProductionTypeAllowEmpty(Set<String> products) {
        // for edit, I want the empty string allowed.
        Set<String> productsWithSpaces = new HashSet<>();
        for (String prod : products) {
            productsWithSpaces.add(prod);
        }
        productsWithSpaces.add("");
        return getProductionType(productsWithSpaces);
    }

    @Override
    public String getProductionType(Set<String> products) {
        boolean invalidSelection = true;
        String prod = "";
            ui.print("These are our available products.");
            for (String product : products) {
                if (product.equals("")) {
                    //do nothing
                } else {
                    ui.print(product);
                }
            }

            prod = ui.readString("Which one would you like?");
        return prod;
    }

    @Override
    public String getArea() throws ServiceNotAValidNumberException {
        String area = ui.readString("What is the area in inches squared");
        return area;
    }

    @Override
    public String getDateToLookup() {
        String date = ui.readString("Please enter the date to lookup work orders.  Use a yyyy-MM-dd format.");
        return date;
    }

    @Override
    public void displayEditRules() {
        ui.print("=== This is the current information for the selected order. ===");
        ui.print("Leave the field blank to keep the old value, or type a new value to overwrite it.");
    }

    @Override
    public String getCommitChange() {
        return ui.readString("Would you like to commit this update? If no, the changes will be lost.   Yes / No");
    }

    @Override
    public String getOrderNumber() {
        return ui.readString("What is the order number?");
    }

    @Override
    public String getDateForSpecific() {
        return ui.readString("What is the date for the order?  Use a yyyy-MM-dd format.");
    }

    @Override
    public String getDate(boolean allowEmpty) {
        String date = "";
        boolean invalidSelection = true;
            try {
                date = ui.readString("What is the date you would like to set for this work order? Please enter yyyy-MM-dd");
                if (date.equalsIgnoreCase("") && allowEmpty) {
                    invalidSelection = false;
                    return date;
                }
                invalidSelection = false;
            } catch (java.lang.NumberFormatException e) {
                ui.print("Not a valid date for this format, please try again.");
            }
        return date;
    }

    @Override
    public void displayException(String str) {
        ui.print(str);
        ui.print("=== did not finish task ===");
    }

}
