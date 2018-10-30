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
public class DaoFilePersistenceException extends Exception{
    public DaoFilePersistenceException(String msg){
        super(msg);
    }
        public DaoFilePersistenceException(String msg, Throwable cause){
        super(msg, cause);
    }
}
