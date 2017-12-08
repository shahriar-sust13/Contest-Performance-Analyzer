/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

/**
 * This Class contains Data of a Student
 * 
 * @author Shahriar
 * @version 1.0
 */
public class Student {
    
    String name, regNo;
    
    /**
     * Save name and registration number of a Student
     * 
     * @param name name of the Student
     * @param reg  registration no. of a Student
     */
    Student( String name, String reg ){
        this.name = name;
        regNo = reg;
    }
    
}
