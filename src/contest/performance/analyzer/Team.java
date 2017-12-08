/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains a Team Info,
 * Its all member detailed data
 * 
 * @author Shahriar
 * @version 1.0
 */
public class Team {
    
    List<Student> teamMember;
    
    /**
     * Initialize Team Member List, which is actually a Student list
     * 
     * @see Student
     */
    Team(){
        teamMember = new ArrayList<Student>();
    }
    
    /**
     * Add a new student to The Team
     * 
     * @param student Student object which will be added to the team
     * @see Student
     */
    void addTeamMember(Student student){
        teamMember.add(student);
    }
    
}
