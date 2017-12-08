/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * This Class contains the Contestant tab of the Application
 * 
 * @author Shahriar
 * @version 1.0
 */
public class ContestantView {
    
    List<Contestant> contestantList;
    List<Group> profileList;
    Group profileContainer;
    int active;
    
    /**
     * Collect the Contestant Detailed list from the Data Base
     */
    ContestantView(){
        contestantList = SqlConnection.getContestantList();
    }
    
    /**
     * make a list of Profile Page View of all Contestant 
     * 
     * @return the list of Group which contains the Profile of all Contestant
     * @see Profile
     */
    List getProfileList(){
        List<Group> list = new ArrayList<Group>();
        for( int i = 0; i<contestantList.size(); i++ ){
            Profile profile = new Profile(contestantList.get(i));
            profileContainer = profile.getProfileContainer();
            profileContainer.setLayoutX(200);
            profileContainer.setLayoutY(0);
            list.add(profileContainer);
        }
        return list;
    }
    
    /**
     * Generate a View for Contestant Tab
     * 
     * @param withDelete defines the Contestant List contains Delete Button
     * @return a Group which contains Contestant Tab View
     */
    Group getContestantView(boolean withDelete){
        
        active = 0;
        Group group = new Group();

        /*
        *   This will show No Data Page, If no data available in the Database
        */
        if( contestantList.size() == 0 ){
            NotFoundView noData = new NotFoundView("No Contestant Found");
            HBox msgContainer = noData.getNotFoundView();
            group.getChildren().add(msgContainer);
            return group;
        }
        
        profileList = getProfileList();
        
        HBox contestantNameContainer = new HBox();
        contestantNameContainer.getStyleClass().add("contestant-name-container");
        contestantNameContainer.setAlignment(Pos.CENTER);
        contestantNameContainer.setLayoutX(130);
        contestantNameContainer.setLayoutY(135);
        contestantNameContainer.setMinWidth(950);
        contestantNameContainer.setMinHeight(60);
        Label contestantName = new Label(contestantList.get(0).name + " (" + contestantList.get(0).regNo + ")");
        contestantName.getStyleClass().add("contestant-name");
        contestantNameContainer.getChildren().add(contestantName);
        
        /*
        *   Contestant List
        */
        VBox contestantListView = new VBox();
        contestantListView.getStyleClass().add("contestant-list");
        contestantListView.setLayoutX(0);
        contestantListView.setLayoutY(200);
        contestantListView.setAlignment(Pos.TOP_CENTER);
        contestantListView.setPadding(new Insets(0,0,0,0));
        
        Label listTitle = new Label("Contestant");
        listTitle.getStyleClass().add("list-title");
        
        contestantListView.getChildren().add(listTitle);
        
        for( int i = 0; i<contestantList.size(); i++ ){
            Button contestantBtn = new Button(contestantList.get(i).name);
            
            if( i<contestantList.size()-1 )
                contestantBtn.getStyleClass().add("contestant-list-btn");
            else
                contestantBtn.getStyleClass().add("contestant-list-last-btn");
            
            final int id = i;
            
            contestantBtn.setOnAction((ActionEvent e)->{
                contestantName.setText(contestantList.get(id).name + " (" + contestantList.get(id).regNo + ")");
                profileList.get(active).setVisible(false);
                profileList.get(id).setVisible(true);
                active = id;
            });
            
            contestantListView.getChildren().add( contestantBtn );
        }
        
        Button deleteBtn = new Button("Delete");
        deleteBtn.getStyleClass().add("contest-delete-btn");
        deleteBtn.setLayoutX(45);
        deleteBtn.setLayoutY(142);
        deleteBtn.setVisible(withDelete);
        
        List<CheckBox> checkBoxList = new ArrayList<CheckBox>();
        
        VBox checkBoxListView = new VBox(43);
        checkBoxListView.setLayoutX(25);
        checkBoxListView.setLayoutY(282);
        checkBoxListView.setVisible(withDelete);
        
        for( int i = 1; i<=contestantList.size(); i++ ){
            CheckBox check = new CheckBox();
            checkBoxList.add(check);
            checkBoxListView.getChildren().add(check);
        }
        
        deleteBtn.setOnAction((ActionEvent e) -> {
            for( int i = 0; i<checkBoxList.size(); i++ ){
                if( checkBoxList.get(i).isSelected() == true ){
                    SqlConnection.delete(contestantList.get(i));
                }
            }
            ContestPerformanceAnalyzer.updateTab(1, false);
        });
        
        group.getChildren().addAll(contestantNameContainer, contestantListView, profileList.get(active), deleteBtn, checkBoxListView);
        
        for( int i = 1; i<contestantList.size(); i++ ){
            profileList.get(i).setVisible(false);
            group.getChildren().add(profileList.get(i));
        }
        
        return group;
    }
    
}
