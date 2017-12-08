/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

/**
 *  This Class contains the date of a Contest
 * 
 * @author Shahriar
 * @version 1.0
 */
public class ContestDate {
    
    int day, month, year;
    
    /**
     * store the day, month and year of a Contest
     * 
     * @param day day of the Contest
     * @param month month of the Contest
     * @param year year of the Contest
     */
    ContestDate(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }
    
    /**
     * generate a string showing the date of a Contest
     * 
     * @return the date as a String
     */
    String getDateString(){
        String str = day + "/" + month + "/" + year;
        return str;
    }
    
}
