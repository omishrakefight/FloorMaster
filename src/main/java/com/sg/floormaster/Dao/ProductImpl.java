/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormaster.Dao;

import com.sg.floormaster.Dto.Product;
import com.sg.floormaster.Exceptions.DaoFilePersistenceException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author omish
 */
public class ProductImpl implements Products{

    public static final String PRODUCT_FILE = "Products.txt";
    public static final String DELIMITER = ",";
    
    Map<String, Product> products = new HashMap<>();
    
    public void loadProduct() throws DaoFilePersistenceException{
        Scanner scanner;
        
        try{
            scanner = new Scanner (
                new BufferedReader(
                    new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e){
            throw new DaoFilePersistenceException("Could not find the products.", e);
        }
        
        //Set up the string reader
        String currentLine;
        String[] currentTokens;
        
        while(scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            // get current line and test it has Delimiter - that means it is my data.
            if(currentLine.contains(DELIMITER)){
                
                currentTokens = currentLine.split(DELIMITER);
                //since I cannot change my delimiter and same delimiter is used for header / actual data : /
                if(!currentTokens[0].trim().equalsIgnoreCase("ProductType")) {
                    Product product = new Product();
                    //assign parts
                    product.setName(currentTokens[0].trim());
                    product.setCostPerSqFt(currentTokens[1].trim());
                    product.setLaborCostPerSqFt(currentTokens[2].trim());

                    //puts in map
                    products.put(currentTokens[0], product);
                }
            }
        }
        scanner.close();
    }
    
    public ProductImpl() throws DaoFilePersistenceException{
        loadProduct();
    }
    
    @Override
    public BigDecimal getCostPerSqFt(String product) {
        Product prod = products.get(product);
        return prod.getCostPerSqFt();
    }

    @Override
    public BigDecimal getLaborCostPerSqFt(String product) {
        Product prod = products.get(product);
        return prod.getLaborCostPerSqFt();
    }

    @Override
    public Map getProductMap() {
        return  products;
    }

    @Override
    public Set getProductKeySet() {
        return products.keySet();
    }
    
}
