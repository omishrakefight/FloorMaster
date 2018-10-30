/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormaster.Dao;

import com.sg.floormaster.Dto.Order;
import com.sg.floormaster.Exceptions.DaoFilePersistenceException;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author omish
 */
public class OrderImplTest {

    TaxImpl taxes;
    ProductImpl products;
    OrderImpl orderImpl;

    Order order;

    public OrderImplTest() throws DaoFilePersistenceException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        taxes = ctx.getBean("taxImpl", TaxImpl.class);
        products = ctx.getBean("productImpl", ProductImpl.class);
        orderImpl = ctx.getBean("orderImpl", OrderImpl.class);
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        //cleats but it doesn't save the changes.
        orderImpl.getOrderMap().clear();

        //AHA! this one is not static.  This helps.
        String state = "OH", material = "Wood", area = "10", name = "Testies", date = "0001-01-01";
        order = orderImpl.addOrder(state, material, area, name, date);

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of loadWorkOrders method, of class OrderImpl.
     */
    @Test
    public void testLoadWorkOrders() throws Exception {
        //no try catch, so merely makes sure it finds the files.
        orderImpl.loadWorkOrders();
    }

    /**
     * Test of getASpecificOrder method, of class OrderImpl.
     */
    @Test
    public void testGetASpecificOrder() throws Exception {
        assertEquals(order, orderImpl.getASpecificOrder(order.getOrderDate(), order.getOrderNumber()));
    }

    /**
     * Test of getOrderKeysByDate method, of class OrderImpl.
     */
    @Test
    public void testGetOrderKeysByDate() throws Exception {
        assertEquals(1, orderImpl.getOrderKeysByDate(order.getOrderDate()).size());
    }

    /**
     * Test of getOrderKeySet method, of class OrderImpl.
     */
    @Test
    public void testGetOrderKeySet() throws Exception {
        orderImpl.getOrderKeySet();
    }

    /**
     * Test of createAndCompare method, of class OrderImpl.
     */
    @Test
    public void testCreateAndCompare() {
    }

    /**
     * Test of addOrder method, of class OrderImpl.
     */
    @Test
    public void testAddOrder() throws Exception {
        String state = "OH", material = "Wood", area = "10", name = "Testies", date = "0001-01-02";
        Order order = orderImpl.addOrder(state, material, area, name, date);

        assertEquals(order, orderImpl.getASpecificOrder(date, order.getOrderNumber()));

    }

    /**
     * Test of editOrder method, of class OrderImpl.
     */
    @Test
    public void testEditOrder() {
    }

    /**
     * Test of removeOrder method, of class OrderImpl.
     */
    @Test
    public void testRemoveOrder() throws Exception {
        orderImpl.removeOrder(order.getOrderNumber(), order.getOrderDate());

        assertEquals(null, orderImpl.getASpecificOrder(order.getOrderDate(), order.getOrderNumber()));
    }

}
