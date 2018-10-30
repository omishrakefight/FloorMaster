/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormaster.Dao;

import com.sg.floormaster.Service.ServiceImpl;
import com.sg.floormaster.Exceptions.DaoFilePersistenceException;
import com.sg.floormaster.Exceptions.ServiceInvalidEntryException;
import com.sg.floormaster.Exceptions.ServiceNoOrderNumberException;
import com.sg.floormaster.Exceptions.ServiceNotAValidNumberException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author omish
 */
public class ServiceImplTest {
    
    TaxImpl tax;
    ProductImpl prod;
    OrderImpl od;
    ServiceImpl serv;
    public ServiceImplTest() throws DaoFilePersistenceException{

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        serv = ctx.getBean("Service", ServiceImpl.class);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws DaoFilePersistenceException {
        serv.getOrderMap().clear();
        
        //AHA! this one is not static.  This helps.
        String state = "OH", material = "Wood", area = "10", name = "Testies", date = "0001-01-01";
         serv.addOrder(state, material, area, name, date);
    }
    
    @After
    public void tearDown() {
    }

    @Test (expected  = ServiceInvalidEntryException.class)
    public void nameWithDelimiter() throws ServiceInvalidEntryException{
        serv.checkName("::fail");
    }
    
    @Test
    public void normalName()throws ServiceInvalidEntryException{
        assertFalse(serv.checkName("Jack-the! dude, lots accepted."));
    }
    
    @Test
    public void blankStateAllowed() throws ServiceInvalidEntryException{
        String blank = "";
        serv.checkStateWithSpace(blank, serv.getTaxKeyList());
        assertFalse(serv.checkStateWithSpace(blank, serv.getTaxKeyList()));
    }
    @Test (expected = ServiceInvalidEntryException.class)
    public void blankStateUnallowed() throws ServiceInvalidEntryException {
        String blank = "";
        serv.checkState(blank, serv.getTaxKeyList());
        
    }
    @Test
    public void acceptedState() throws ServiceInvalidEntryException{
        String OH = "OH";
        assertFalse(serv.checkState(OH, serv.getTaxKeyList()));
    }
    
    @Test
    public void checkDateWithSpaceAllowed() throws ServiceInvalidEntryException{
        String blank = "";
        assertFalse(serv.checkDateWithSpace(blank));
    }
    @Test (expected = ServiceInvalidEntryException.class)
    public void checkDateWithSpaceUnallowed() throws ServiceInvalidEntryException{
        String blank = "";
        serv.checkDate(blank);
    }
    @Test (expected = ServiceInvalidEntryException.class)
    public void checkBadDate() throws ServiceInvalidEntryException{
        String bad = "100-100-100";
        serv.checkDate(bad);
    }
    @Test
    public void acceptableDate() throws ServiceInvalidEntryException{
        String goodDate = "2020-05-22";
        assertFalse(serv.checkDate(goodDate));
    }
    
    @Test
    public void areaWithSpaceAllowed() throws ServiceNotAValidNumberException{
        String blank = "";
        assertFalse(serv.checkAreaWithSpace(blank));
    }
    @Test (expected = ServiceNotAValidNumberException.class)
    public void areaWithSpaceUnallowed() throws ServiceNotAValidNumberException{
        String blank = "";
        serv.checkArea(blank);
    }
    @Test (expected = ServiceNotAValidNumberException.class)
    public void areaWithbadInput() throws ServiceNotAValidNumberException{
        String negative = "-20";
        serv.checkArea(negative);
    }
    @Test
    public void areaWithGoodInput() throws ServiceNotAValidNumberException{
        String good = "256";
        assertFalse(serv.checkArea(good));
    }
    
    @Test
    public void materialWithSpaceAllowed() throws ServiceInvalidEntryException{
        String blank = "";
        assertFalse(serv.checkProductWithSpace(blank, serv.getProductKeySet()));
    }
    @Test (expected = ServiceInvalidEntryException.class)
    public void materialWithSpaceUnallowed() throws ServiceInvalidEntryException{
        String blank = "";
        serv.checkProduct(blank, serv.getProductKeySet());
    }
    @Test (expected = ServiceInvalidEntryException.class)
    public void materialWithBadInput() throws ServiceInvalidEntryException{
        String blank = "Potatoe";
        serv.checkProduct(blank, serv.getProductKeySet());
    }
    @Test
    public void materialWithGoodInput() throws ServiceInvalidEntryException{
        String blank = "Carpet";
        assertFalse(serv.checkProduct(blank, serv.getProductKeySet()));
    }
    
    @Test (expected = ServiceNoOrderNumberException.class)
    public void checkHasNull() throws ServiceNoOrderNumberException{
        String date = "1002-03-05";
        serv.orderCheckForNull(date);
    }
    @Test (expected = ServiceNoOrderNumberException.class)
    public void checkForNullBadInput() throws ServiceNoOrderNumberException{
        String date = "potatoes";
        serv.orderCheckForNull(date);
    }
    @Test
    public void checkForNullFindsOne() throws ServiceNoOrderNumberException{
        String date = "0001-01-01";
        serv.orderCheckForNull(date);
        //This one does not have an assert because it throws an exception but returns void.
        //So if it fails I get an exception (I don't it passes).
    }
}
