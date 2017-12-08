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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * This Class shows the Contest Page,
 * which contains list of all Contest,
 * By clicking any of the Contest we can get the detail info about it
 * 
 * @author Shahriar
 * @version 1.0
 */
public class ContestPage {
    
    private Group contestContainer = new Group();
    private Group group = new Group();
    List<Contest> contestDataList;
    VBox contestList;
    
    ContestPage(){
    }
    
    /**
     * shows the Contest page with list of all Contest
     * 
     * @param withDelete defines whether the Page should display Contest List with Delete Button
     * @return Group which contains the Contest Page
     * @see Contest
     */
    public Group getContestPage(boolean withDelete){
        
        group = new Group();
        
        contestDataList = SqlConnection.getContestList();
        
        if( contestDataList.size() == 0 ){
            NotFoundView noData = new NotFoundView("No Contest Data Available");
            HBox msgContainer = noData.getNotFoundView();
            group.getChildren().add(msgContainer);
            return group;
        }
        
        HBox contestNameContainer = new HBox();
        contestNameContainer.getStyleClass().add("contest-name-container");
        contestNameContainer.setAlignment(Pos.CENTER);
        contestNameContainer.setLayoutX(150);
        contestNameContainer.setLayoutY(135);
        contestNameContainer.setMinWidth(950);
        contestNameContainer.setMinHeight(60);
        Label contestName = new Label(contestDataList.get(0).contestName + " (" + contestDataList.get(0).date.getDateString() + ")");
        contestName.getStyleClass().add("contest-name");
        contestNameContainer.getChildren().add(contestName);
        
        /*
        *   When the page is loaded Contest - 1 Data will appear
        */
        ContestView defaultContestView = new ContestView(contestDataList.get(0).contestId);
        contestContainer = defaultContestView.getContestContainer();
        contestContainer.setLayoutX(200);
        
        /*
        *   Contest List
        */
        contestList = getContestList(contestName);
        
        Button deleteBtn = new Button("Delete");
        deleteBtn.getStyleClass().add("contest-delete-btn");
        deleteBtn.setLayoutX(45);
        deleteBtn.setLayoutY(142);
        deleteBtn.setVisible(withDelete);
        
        List<CheckBox> checkBoxList = new ArrayList<CheckBox>();
        
        VBox checkBoxListView = new VBox(43);
        checkBoxListView.setLayoutX(15);
        checkBoxListView.setLayoutY(282);
        checkBoxListView.setVisible(withDelete);
        
        for( int i = 1; i<=OverView.getTotalContest(); i++ ){
            CheckBox check = new CheckBox();
            checkBoxList.add(check);
            checkBoxListView.getChildren().add(check);
        }
        
        deleteBtn.setOnAction((ActionEvent e) -> {
            for( int i = 0; i<checkBoxList.size(); i++ ){
                if( checkBoxList.get(i).isSelected() == true ){
                    SqlConnection.delete(contestDataList.get(i).contestId);
                }
            }
            ContestPerformanceAnalyzer.updateTab(2, false);
        });
        
        VBox dateListView = new VBox(45);
        dateListView.setLayoutX(95);
        dateListView.setLayoutY(300);
        dateListView.setVisible(true);
        dateListView.setMaxWidth(80);
        
        for( int i = 0; i<contestDataList.size(); i++ ){
            Label dateStr = new Label("(" + contestDataList.get(i).date.getDateString() + ")");
            dateStr.setFont(Font.font("Times New Roman", 13));
            dateStr.setTextFill(Color.WHITE);
            dateListView.getChildren().add(dateStr); 
        }
        
        group.getChildren().addAll( contestNameContainer, contestList, contestContainer, deleteBtn, checkBoxListView, dateListView );
        
        return group;
    }
    
    /**
     * display all Contest list with its date
     * 
     * @param contestName contest name bar
     * @return a VBox which contains Contest List
     * @see Contest
     */
    private VBox getContestList(Label contestName){
        VBox list = new VBox();
        list.getStyleClass().add("contest-list");
        list.setLayoutX(0);
        list.setLayoutY(200);
        list.setAlignment(Pos.TOP_CENTER);
        list.setPadding(new Insets(0,0,0,0));
        
        Label listTitle = new Label("Contest");
        listTitle.getStyleClass().add("list-title");
        
        list.getChildren().add(listTitle);
        
        for( int i = 0; i<contestDataList.size(); i++ ){
            
            Button contestBtn = new Button(contestDataList.get(i).contestName);
            
//            contestBtn.set
            
            if( i<contestDataList.size()-1 )
                contestBtn.getStyleClass().add("contest-btn");
            else
                contestBtn.getStyleClass().add("last-contest-btn");
            
            list.getChildren().add( contestBtn );
            final int id = i;
            contestBtn.setOnAction((ActionEvent e)->{
                contestContainer.setVisible(false);
                contestName.setText(contestDataList.get(id).contestName + " (" + contestDataList.get(id).date.getDateString() + ")");
                ContestView contestView = new ContestView(contestDataList.get(id).contestId);
                contestContainer = contestView.getContestContainer();
                contestContainer.setLayoutX(200);
                contestContainer.setLayoutY(0);
                contestContainer.setVisible(true);
                group.getChildren().add(contestContainer);
            });   
        }
        return list;
    }
}  
