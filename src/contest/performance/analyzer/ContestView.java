/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Contains data of a Selected Contest
 * 
 * @author Shahriar
 * @version 1.0
 */
public class ContestView {
    
    int contestId;
    Stage contestWindow;
    List<Contestant> rankList;
    
    /**
     * receive id of the contest which data will be stored
     * 
     * @param id contest id
     */
    ContestView( int id ){
        contestId = id;
        contestWindow = new Stage();
        rankList = SqlConnection.getContestRankList(contestId);
    }
    
    /**
     * Display the Data of a Contest.
     * It contains several tabs, which contains detail information about it.
     * 
     * @return Group which display the whole Contest Data
     */
    Group getContestContainer(){
        
        Group group = new Group();
        
        HBox contestTab = new HBox();
        contestTab.getStyleClass().add("contest-tab");
        contestTab.setLayoutX(0);
        contestTab.setLayoutY(200);
        contestTab.setMinHeight(60);
        contestTab.setMinWidth(900);
        contestTab.setAlignment(Pos.CENTER);
        
        Button rankListTab = new Button("Rank List");
        rankListTab.getStyleClass().add("tab-item1");
        Button championTab = new Button("Champion");
        championTab.getStyleClass().add("tab-item2");
        Button submissionTab = new Button("Submissions");
        submissionTab.getStyleClass().add("tab-item3");
        Button participantTab = new Button("Participant");
        participantTab.getStyleClass().add("right-most-tab");
        
        contestTab.getChildren().addAll( rankListTab, championTab, submissionTab, participantTab );
        
        VBox rankListContainer = new VBox();
        rankListContainer.setMinWidth(870);
        rankListContainer.setAlignment(Pos.CENTER);
        rankListContainer.setLayoutX(10);
        rankListContainer.setLayoutY(280);
        
        Label contestTitle = new Label(Contest.getContestName(contestId) + " Rank List");
        contestTitle.getStyleClass().add("contest-title");
        contestTitle.setPadding( new Insets( 10, 10, 20, 10 ) );
        contestTitle.setFont(Font.font("Arial", 25));
        
        RankList rankListTable = new RankList(rankList);
        TableView table = rankListTable.getRankListTable();
        
        rankListContainer.getChildren().addAll(contestTitle, table);
        
        Champion champion = new Champion( rankList.get(0) );
        VBox championContainer = champion.getChampionContainer();
        championContainer.setVisible(false);
        championContainer.setLayoutX(0);
        championContainer.setLayoutY(250);
        
        SubmissionData submissionData = new SubmissionData( rankList );
        VBox submissionContainer = submissionData.getSubmissionContainer();
        submissionContainer.setVisible(false);
        submissionContainer.setLayoutX(0);
        submissionContainer.setLayoutY(280);
        
        ContestParticipant contestParticipant = new ContestParticipant(rankList);
        VBox participantContainer = contestParticipant.getContestParticipantContainer();
        participantContainer.setVisible(false);
        participantContainer.setLayoutX(0);
        participantContainer.setLayoutY(280);
        
        rankListTab.setOnAction((ActionEvent e)->{
            rankListContainer.setVisible(true);
            championContainer.setVisible(false);
            submissionContainer.setVisible(false);
            participantContainer.setVisible(false);
        });
        championTab.setOnAction((ActionEvent e)->{
            rankListContainer.setVisible(false);
            championContainer.setVisible(true);
            submissionContainer.setVisible(false);
            participantContainer.setVisible(false);
        });
        submissionTab.setOnAction((ActionEvent e)->{
            submissionContainer.setVisible(true);
            rankListContainer.setVisible(false);
            championContainer.setVisible(false);
            participantContainer.setVisible(false);
        });
        participantTab.setOnAction((ActionEvent e)->{
            participantContainer.setVisible(true);
            submissionContainer.setVisible(false);
            rankListContainer.setVisible(false);
            championContainer.setVisible(false);
        });
        
        group.getChildren().addAll( contestTab, rankListContainer, championContainer, submissionContainer, participantContainer );
        
        return group;
    }
    
}
