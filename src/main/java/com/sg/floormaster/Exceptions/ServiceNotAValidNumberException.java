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
public class ServiceNotAValidNumberException extends Exception {

    public ServiceNotAValidNumberException(String msg) {
        super(msg);
    }

    public ServiceNotAValidNumberException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
