/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import java.util.Calendar;

/**
 *  This Class store the data of a Contest
 * 
 * @author Shahriar
 * @version 1.0
 */
public class Contest {
    
    String contestName;
    int contestId = 1;
    ContestDate date;
    int totalContestant, totalSubmission, totalAc, added;
    Student[] participant;
    Performance[] performance;
    
    /**
     * store the name and total contestant of a Contest
     * 
     * @param name name of the Contest
     * @param p total Contestant in this Contest
     */
    Contest( String name, int p ){
        contestName = name;
        totalContestant = p;
        totalSubmission = totalAc = added = 0;
        participant = new Student[p+1];
        performance = new Performance[p+1];
    }
    
    /**
     * store id and visibility of a Contest
     * 
     * @param id id of the Contest
     * @param visible visibility of this Contest
     */
    Contest( int id, boolean visible ){
        contestId = id;
    }
    
    /**
     * store contest id, contest name and contest date of a contest
     * 
     * @param id id of the contest
     * @param name name of the contest
     * @param date date of the contest
     */
    Contest( int id, String name, ContestDate date ){
        contestId = id;
        contestName = name;
        this.date = date;
    }
    
    /**
     * add a contestant in the rank list of this Contest
     * 
     * @param name name of the Contestant
     * @param regNo registration no. of the Contestant
     * @param solved total solved problem by Contestant
     * @param tried total tried problems by Contestant
     * @param submission total submissions by Contestant
     * @param timePenalty total Time Penalty by Contestant
     */
    void addData( String name, String regNo, int solved, int tried, int submission, int timePenalty ){
        participant[added] = new Student( name, regNo );
        performance[added] = new Performance( contestId, solved, tried, submission, timePenalty );
        totalSubmission += submission;
        totalAc += solved;
        added++;
    }
    
    /**
     * add contest date to the Contest object 
     * 
     * @param dateString contest date as a string
     * @throws Exception throw InvalidDateException if the date isn't in right format
     */
    void addDate(String dateString) throws Exception{
        int day, month, year, i;
        day = month = year = 0;
        for( i = 0; i<dateString.length(); i++ ){
            if( dateString.charAt(i) == '.' )
                break;
            if( dateString.charAt(i) < '0' || dateString.charAt(i) > '9' )
                throw new InvalidDateException();
            day = day*10 + ( dateString.charAt(i)-'0' );
        }
        for( i++ ; i<dateString.length(); i++ ){
            if( dateString.charAt(i) == '.' )   break;
            if( dateString.charAt(i) < '0' || dateString.charAt(i) > '9' )
                throw new InvalidDateException();
            month = month*10 + ( dateString.charAt(i)-'0' );
        }
        for( i++ ; i<dateString.length(); i++ ){
            if( dateString.charAt(i) < '0' || dateString.charAt(i) > '9' )
                throw new InvalidDateException();
            year = year*10 + ( dateString.charAt(i)-'0' );
        }
        if( day<1 || day>31 || month<1 || month>12 || year<1 || year>Calendar.getInstance().get(Calendar.YEAR) ){
            throw new InvalidDateException();
        }
        date = new ContestDate(day, month, year);
    }
    
    /**
     * Give the name of the Contest having Contest id = "contestId"
     * 
     * @param contestId id of the Contest
     * @return name of the Contest having id = "contestId"
     */
    public static String getContestName(int contestId){
        return SqlConnection.getContestName(contestId);
    }
    
}
