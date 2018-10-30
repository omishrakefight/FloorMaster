/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormaster;

import com.sg.floormaster.Controller.Controller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author omish
 */
public class App {
    public static void main(String[] args) {
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        Controller cont = ctx.getBean("Controller", Controller.class);
            
            cont.run();
    }
}
