/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormaster.Exceptions;

/**
 *
 * @author omish
 */
public class ServiceInvalidEntryException extends Exception{
    
    public ServiceInvalidEntryException(String msg){
        super(msg);
    }
    public ServiceInvalidEntryException(String msg, Throwable cause){
        super(msg, cause);
    }
}
