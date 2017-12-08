/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

/**
 *
 * @author USER
 */
public class Person {
    int rank, solved, tried, submission, timePenalty;
    String name, regNo;
    
    Person( int rank, String name, String regNo, int slv, int tr, int sbm, int penalty ){
        this.rank = rank;
        this.name = name;
        this.regNo = regNo;
        solved = slv;
        tried = tr;
        submission = sbm;
        timePenalty = penalty;
    }
    
    public void setRank( int rnk ){
        rank = rnk;
    }
    
    public int getRank(){
        return rank;
    }
    
    public void setName( String nm ){
        name = nm;
    }
    
    public String getName(){
        return name;
    }
    
    public void setRegNo( String reg ){
        regNo = reg;
    }
    
    public String getRegNo(){
        return regNo;
    }
    
    public void setSolved( int slv ){
        solved = slv;
    }
    
    public int getSolved(){
        return solved;
    }
    
    public void setTried( int tr ){
        tried = tr;
    }
    
    public int getTried(){
        return tried;
    }
    
    public void setSubmission( int sbm ){
        submission = sbm;
    }
    
    public int getSubmission(){
        return submission;
    }
    
    public void setTimePenalty( int penalty ){
        timePenalty = penalty;
    }
    
    public int getTimePenalty(){
        return timePenalty;
    }
    
}
