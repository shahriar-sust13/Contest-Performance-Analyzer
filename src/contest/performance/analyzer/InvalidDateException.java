/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

/**
 * It is Exception Class which response in Invalid Date Format
 * 
 * @author Shahriar
 * @version 1.0
 */
public class InvalidDateException extends Exception{
    
    InvalidDateException(){}
    
    @Override
    /**
     * Contains the Exception Message 
     * @return a String contains the Exception message
     */
    public String toString(){
        return "Invalid Date Format!";
    }
    
}
