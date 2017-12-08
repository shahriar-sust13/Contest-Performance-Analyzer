/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import java.util.List;

/**
 * an Overview of all Contest Data
 * 
 * @author Shahriar
 * @version 1.0
 */
public class OverView {
    
    public static int totalContestant = 0;
    public static int contestLimit = 20;
    public static List<Contestant> contestantList;
    
    /**
    * Calculate total Contest has been arranged
    * 
    * @return an integer indicating total Contest has been arranged
    */
    public static int getTotalContest(){
        return SqlConnection.getTotalContest();
    }
    
    /**
     * Calculate total Contestant in all Contest
     * 
     * @return an integer which indicates total Contestant
     */
    public static int getTotalContestant(){
        List<Contestant> list = SqlConnection.getContestantList();
        return list.size();
    }
    
    /**
     * Find the largest contest id
     * 
     * @return an integer which is the largest contest id
     */
    public static int getMaxContestId(){
        return SqlConnection.getMaxContestId();
    }
    
}
