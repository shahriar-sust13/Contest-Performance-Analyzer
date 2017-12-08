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
 * This Class contains Participant Tab of Contest Page
 * 
 * @author Shahriar
 * @version 1.0
 */
public class ContestParticipant {
    
    List<Contestant> rankList;
    
    /**
     * receive the list of Contestant Participate in a Contest
     * 
     * @param list list of Contestant
     */
    ContestParticipant( List<Contestant> list ){
        rankList = list;
    }
    
    /**
     * display the Participant Tab
     * 
     * @return VBox contains the Participant Data
     */
    VBox getContestParticipantContainer(){
        VBox ret = new VBox();
        ret.setAlignment(Pos.CENTER);
        ret.setMinHeight(250);
        ret.setMinWidth(870);
        
        int i;
        for( i = 0; i<rankList.size(); i++ ){
            if( rankList.get(i).totalSolved<=0 )
                break;    
        }
        
        Label totalParticipant = new Label("Total Participant: " + rankList.size());
        totalParticipant.setPadding(new Insets(0, 0, 10, 0));
        totalParticipant.setFont(Font.font("Times New Roman", 20));
        Label totalParticipantSolved1 = new Label("Total Participant Solved At Least One Problem: " + i);
        totalParticipantSolved1.setFont(Font.font("Times New Roman", 20));
        
        ret.getChildren().addAll( totalParticipant, totalParticipantSolved1 );
        
        return ret;
    }
    
}
