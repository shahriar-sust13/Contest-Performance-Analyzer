/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

/**
 * It process performance of a Contestant in a single Contest
 * 
 * @author Shahriar
 * @version 1.0
 */
public class Performance {
    
    int contestId, rank, solved, tried, submission, timePenalty, rating;
    
    /**
     * collect the data 
     * @param contestId id of the contest
     * @param rank position in the Standings of the Contest
     * @param slv total solved problems in that contest
     * @param tr total tried problems in that contest
     * @param sbm total submissions in that contest
     * @param penalty total time penalty in that contest
     */
    Performance( int contestId, int rank, int slv, int tr, int sbm, int penalty ){
        this.contestId = contestId;
        this.rank = rank;
        solved = slv;
        tried = tr;
        submission = sbm;
        timePenalty = penalty;
        rating = getRating();
    }
    
    /**
     * collect the data 
     * @param contestId id of the contest
     * @param slv total solved problems in that contest
     * @param tr total tried problems in that contest
     * @param sbm total submissions in that contest
     * @param penalty total time penalty in that contest
     */
    Performance( int contestId, int slv, int tr, int sbm, int penalty ){
        solved = slv;
        tried = tr;
        submission = sbm;
        timePenalty = penalty;
        rating = getRating();
    }
    
    /**
     * Processing the data it calculates the Performance rating of a Contestant in that Contest
     * @return an Integer number indicating the Rating
     */
    int getRating(){
//        return 100;
        int beat = (SqlConnection.getTotalContestant(contestId) - rank + 1)*8;
//        int beat = 40; // debugging
        return solved*beat;
    }
    
    /**
     * Processing the data it calculates the Accuracy of a Contestant in that Contest
     * @return an floating point number indicating the Accuracy
     */
    double getAccuracy(){
        if( submission == 0 )   return 0;
        double ret = (double)solved/submission;
        ret *= 100;
        return ret;
    }
    
}
