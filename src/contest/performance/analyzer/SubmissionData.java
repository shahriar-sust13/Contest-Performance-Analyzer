/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * This Class Process all submissions in all Contest
 * 
 * @author Shahriar
 * @version 1.0
 */
public class SubmissionData {
    List<Contestant> rankList;
    
    /**
     * receive the Contestant Data to Process
     * 
     * @param list a List of Contestant
     * @see Contestant
     */
    SubmissionData( List<Contestant> list ){
        rankList = list;
    }
    
    /**
     * Process all submissions.
     * Then make a Summary of all Submissions and display it.
     * 
     * @return a VBox which contains the Summary of all submissions
     */
    VBox getSubmissionContainer(){
        VBox submissionContainer = new VBox();
        submissionContainer.setAlignment(Pos.CENTER);
        submissionContainer.setMinHeight(250);
        submissionContainer.setMinWidth(870);
        
        int totalSub = 0, totalAcSub = 0;
        for( int i = 0; i<rankList.size(); i++ ){
            totalSub += rankList.get(i).totalSubmission;
            totalAcSub += rankList.get(i).totalSolved;
        }
        
        Label totalSubmission = new Label("Total Submissions: " + totalSub);
        totalSubmission.setFont(Font.font("Times New Roman", 20));
        totalSubmission.setPadding(new Insets(0, 0, 10, 0));
        Label totalAcSubmission = new Label("Total Accepted Submissions: " + totalAcSub);
        totalAcSubmission.setFont(Font.font("Times New Roman", 20));
        
        submissionContainer.getChildren().addAll(totalSubmission, totalAcSubmission);
        
        return submissionContainer;
    }
}
