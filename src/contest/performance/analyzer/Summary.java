/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Process all types of Data of a Contestant,
 * Then make summary of his Performance
 * 
 * @author Shahriar
 * @version 1.0
 */
public class Summary {
    
    Contestant contestant;
    
    /**
     * receive a Contestant object which data will be processed and summarized
     * 
     * @param contestant Contestant object
     */
    Summary(Contestant contestant){
        this.contestant = contestant;
    }
    
    /**
     * Processed the Contestant Data,
     * And make a Summary of Data
     * 
     * @return a VBox which contains the Summary of all Data
     */
    VBox getSummary(){
        VBox summary = new VBox(10);
        summary.setAlignment(Pos.TOP_CENTER);
        
        Label solved = new Label( "Total Solved " + contestant.totalSolved );
        solved.setFont(Font.font("Times New Roman", 22));
        
        Label tried = new Label( "Total Tried " + contestant.totalTried );
        tried.setFont(Font.font("Times New Roman", 22));
        
        Label submission = new Label( "Total Submissions " + contestant.totalSubmission );
        submission.setFont(Font.font("Times New Roman", 22));
        
        Label penalty = new Label( "Time Penalty " + contestant.totalTimePenalty );
        penalty.setFont(Font.font("Times New Roman", 22));
        
        Label avgSolved = new Label("Average Solved " + contestant.getAverageSolved());
        avgSolved.setFont(Font.font("Times New Roman", 22));
        
        Label totalContest = new Label("Total Contest Attended " + contestant.numberOfContestAttended);
        totalContest.setFont(Font.font("Times New Roman", 22));
        
        summary.getChildren().addAll( solved, tried, submission, penalty, avgSolved, totalContest );
        
        return summary;
    }
    
}
