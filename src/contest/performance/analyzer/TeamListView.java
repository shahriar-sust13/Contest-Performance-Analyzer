/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import java.io.File;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * make a View of all Teams
 * 
 * @author Shahriar
 * @version 1.0
 */
public class TeamListView {
    
    List<Team> teamList;
    
    /**
     * receive the list of Teams which will be displayed 
     * 
     * @param list a list of Teams
     * @see Team
     */
    TeamListView(List<Team> list){
        teamList = list;
    }
    
    /**
     * Display the Team info in a Group
     * 
     * @return a Group object which display the Teams
     * @see Team
     */
    Group getTeamListView(){
        
        Group group = new Group();
        
        VBox teamListContainer = new VBox(30);
        teamListContainer.setAlignment(Pos.TOP_CENTER);
        teamListContainer.setLayoutX(0);
        teamListContainer.setLayoutY(0);
        teamListContainer.setMinWidth(1080);
        
        Label teamListTitle = new Label("Team List");
        teamListTitle.setFont(Font.font("Courier", 25));
        teamListTitle.setPadding( new Insets( 10, 0, 0, 0 ) );
        
        PdfButton pdfBtn = new PdfButton();
        Button pdfBtnView = pdfBtn.getPdfButton();
        pdfBtnView.setLayoutX(700);
        pdfBtnView.setLayoutY(0);
        
        pdfBtnView.setOnAction((ActionEvent e)->{
            TeamPublisher teamPublisher = new TeamPublisher(teamList);
            teamPublisher.start();
        });
        
        teamListContainer.getChildren().add(teamListTitle);
        
        for( int i = 0; i<teamList.size(); i++ ){
            Team team = teamList.get(i);
            int tmp = i+1;
            TeamView teamView = new TeamView(team, "Team " + tmp);
            VBox teamContainer = teamView.getTeamView();
            teamContainer.setMinWidth(1080);
            teamListContainer.getChildren().add(teamContainer);
        }
        VBox bottomSpace = new VBox();
        bottomSpace.setMinHeight(10);
        
        teamListContainer.getChildren().add(bottomSpace);
        
        group.getChildren().addAll( teamListContainer, pdfBtnView );
        
        return group; 
        
    }
    
}
