/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormaster.Dao;

import com.sg.floormaster.Dto.Tax;
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
public class TaxImpl implements Taxes{

    private Map<String, Tax> taxes = new HashMap();
    
    private static final String TAXES_FILE = "Taxes.txt";
    private static final String DELIMITER = ",";
    
    public void loadTaxes() throws DaoFilePersistenceException{
        Scanner scanner;
        
        try {
            scanner = new Scanner(
                new BufferedReader (
                    new FileReader(TAXES_FILE)));
        } catch (FileNotFoundException e) {
             throw new DaoFilePersistenceException("Could not find the taxes file...", e);
        }
        
        //Set up to read the state and tax associated
        String currentLine;
        String[] currentTokens;
        
        while(scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            // get current line and test it has Delimiter - that means it is my data.
            if(currentLine.contains(DELIMITER)){
                currentTokens = currentLine.split(DELIMITER);
                
                if(!currentTokens[0].trim().equalsIgnoreCase("State")) {
                    Tax tax = new Tax();
                    tax.setName(currentTokens[0]);
                    tax.setSalesTax(currentTokens[1]);
                    taxes.put(currentTokens[0], tax);
                }
            }
        }
        scanner.close();
    }
    
    public TaxImpl() throws DaoFilePersistenceException{
            loadTaxes();
    }
    
    @Override
    public Set getTaxKeyList() {
        Set<String> keyList = taxes.keySet();
        return keyList;
    }

    @Override
    public Map<String, Tax> getTaxMap() {
        return taxes;
    }
    
    @Override
    public Tax getTax(String str){
        return taxes.get(str);
    }
    


    
}
