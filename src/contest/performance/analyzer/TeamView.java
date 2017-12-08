/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Display Team info
 * 
 * @author Shahriar
 * @version 1.0
 */
public class TeamView {
    
    Team team;
    String name;
    
    /**
     * receive the Team and team name which will be displayed
     * 
     * @param team Team to display
     * @param name a String contains the name of the Team
     */
    TeamView( Team team, String name ){
        this.team = team;
        this.name = name;
    }
    
    /**
     * Display team info
     * 
     * @return a VBox which contains the Team info
     */
    VBox getTeamView(){
        VBox teamView = new VBox();
        Label teamName = new Label(name);
        teamName.setFont(Font.font("Arial", 22));
        teamName.setPadding(new Insets(0, 0, 0, 350));
        teamView.getChildren().add(teamName);
        for( int i = 0; i<team.teamMember.size(); i++ ){
            Student student = team.teamMember.get(i);
            int tmp = i+1;
            Label memberName = new Label("Team Member " + tmp + ": " + student.name + "(" + student.regNo + ")");
            memberName.setFont(Font.font("Times New Roman", 18));
            memberName.setPadding(new Insets(10, 0, 0, 400));
            teamView.getChildren().add(memberName);
        }
        return teamView;
    }
}
