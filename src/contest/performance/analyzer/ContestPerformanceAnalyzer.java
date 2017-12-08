/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import java.io.File;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;




/**
 *
 * @author USER
 */
public class ContestPerformanceAnalyzer extends Application {
    
    static ScrollPane scrollPane = new ScrollPane();
    Group group;
    static Group currentTab;
    static int active = 2;
    static Label statusBar = new Label();
    
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setResizable(false);
        primaryStage.setTitle("Programming Contest Performance Analyzer");
        
//        System.out.println("javafx.runtime.version: " + System.getProperties().get("javafx.runtime.version"));
        
        group = new Group();

        currentTab = new Group();
        currentTab.setLayoutX(0);
        currentTab.setLayoutY(150);
        
        if( active != 3 )
            updateTab(active, false);
        else
            updateTab(active);
        
        scrollPane.setContent(currentTab);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setLayoutX(0);
        scrollPane.setLayoutY(140);
        scrollPane.setMinHeight(530);
        scrollPane.setMinWidth(1090);
        scrollPane.setMaxHeight(530);
        scrollPane.setMaxWidth(1090);
        
        statusBar.setMinWidth(1080);
        statusBar.setAlignment(Pos.CENTER);
        statusBar.setFont(Font.font("Lucida Console", 18));
        statusBar.setLayoutY(115);
        
        HBox menuContainer = new HBox();
        menuContainer.setMinHeight(100);
        menuContainer.setMinWidth(1090);
        menuContainer.setStyle("-fx-background-color: #232323");
        menuContainer.setAlignment(Pos.TOP_CENTER);
        menuContainer.setPadding(new Insets(0,0,0,0));
        
        Image contestantIcon = new Image("/contest/performance/analyzer/resources/v2/contestant.jpg", 143, 100, true, true);
        Button contestantBtn = new Button( "", new ImageView(contestantIcon) );
        contestantBtn.getStyleClass().add("contestant-btn");
        contestantBtn.setPadding(Insets.EMPTY);
        
        contestantBtn.setOnAction((ActionEvent e)->{
            updateTab(1, false);
        });
        
        Image rankListIcon = new Image("/contest/performance/analyzer/resources/v2/rank-list-icon.jpg", 143, 100, true, true);
        Button rankListBtn = new Button( "", new ImageView(rankListIcon) );
        rankListBtn.getStyleClass().add("rank-list-btn");
        rankListBtn.setPadding(Insets.EMPTY);
        
        rankListBtn.setOnAction((ActionEvent e)->{
            updateTab(2, false);
        });
        
        Image teamIcon = new Image("/contest/performance/analyzer/resources/v2/team-icon.jpg", 143, 100, true, true);
        Button teamBtn = new Button( "", new ImageView(teamIcon) );
        teamBtn.getStyleClass().add("team-btn");
        teamBtn.setPadding(Insets.EMPTY);
        
        teamBtn.setOnAction((ActionEvent e) ->{
            updateTab(3);
        });
        
        Image addContestIcon = new Image("/contest/performance/analyzer/resources/v2/add-icon.jpg", 143, 100, true, true);
        Button addContestBtn = new Button( "", new ImageView(addContestIcon) );
        addContestBtn.getStyleClass().add("add-contest-btn");
        addContestBtn.setPadding(Insets.EMPTY);
        
        // This code will response to the addContestBtn Button click
        addContestBtn.setOnAction( (ActionEvent e) ->{
            statusBar.setText("");
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(new Stage());
            if( file != null ){
                if( getFileExtension(file).matches("xlsx") == false ){
                    updateStatus("Invalid File Format! [File should be .xlsx]", false);
                }
                else if( file.getName().length()>45 ){
                    updateStatus("File Name Too Long! [File Name can be at most 40 Characters long]", false);
                }
                else{
                    if( OverView.getTotalContest()<=OverView.contestLimit ){
                        UploadContest uploadContest = new UploadContest(file);
                        uploadContest.start(menuContainer);
                    }
                    else{
                    }
                }
            }
        });
        
        Image clearIcon = new Image("/contest/performance/analyzer/resources/v2/clear-icon.jpg", 143, 100, true, true);
        Button clearBtn = new Button( "", new ImageView(clearIcon) );
        clearBtn.getStyleClass().add("clear-btn");
        clearBtn.setPadding(Insets.EMPTY);
        
        clearBtn.setOnAction((ActionEvent e)->{
            if( active != 3 ){
                updateTab(active, true);
            }
        });
        
        Image backupIcon = new Image("/contest/performance/analyzer/resources/v2/backup-icon.jpg", 143, 100, true, true);
        Button backupBtn = new Button( "", new ImageView(backupIcon) );
        backupBtn.getStyleClass().add("backup-btn");
        backupBtn.setPadding(Insets.EMPTY);
        
        Image importIcon = new Image("/contest/performance/analyzer/resources/v2/import-icon.jpg", 143, 100, true, true);
        Button importBtn = new Button( "", new ImageView(importIcon) );
        importBtn.getStyleClass().add("import-btn");
        importBtn.setPadding(Insets.EMPTY);
        
        backupBtn.setOnAction((ActionEvent e)->{
            Export export = new Export();
            export.start(addContestBtn, menuContainer);
        });
        
        importBtn.setOnAction((ActionEvent e)->{
            Import importWindow = new Import(active, menuContainer);
            importWindow.start();
        });
        
        menuContainer.getChildren().addAll(contestantBtn, rankListBtn, teamBtn, addContestBtn, clearBtn, backupBtn, importBtn);
        
        group.getChildren().addAll( menuContainer, statusBar, scrollPane);
        
        Scene scene = new Scene( group, 1080, 660, Color.web("#F1F1EF") );
        scene.getStylesheets().add("/contest/performance/analyzer/resources/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
    /**
     * Change the Tab of App Window based on the value of "tab"
     * tab[ 1==contestant tab, 2==contest tab ]
     * 
     * @param tab new tab number
     * @param withDelete define whether the page show delete option
     */
    public static void updateTab(int tab, boolean withDelete){
        /*
        *   This will show the Contestant Tab
        */
        if( tab == 1 ){
            ContestantView contestantView = new ContestantView();
            currentTab = contestantView.getContestantView(withDelete);
            scrollPane.setContent(currentTab);  
        }
        else{ // This will show the Contest Tab
            ContestPage contestPage = new ContestPage();
            currentTab = contestPage.getContestPage(withDelete);
            scrollPane.setContent(currentTab);
        }
        active = tab;
    }
    
    /**
     * Change the Tab of App Window based on the value of "tab"
     * tab[ 1==team tab ]
     * 
     * @param tab new tab number
     */
    public static void updateTab(int tab){
        List<Contestant> contestantList = SqlConnection.getContestantList();
        if( contestantList.size()>0 ){
            TeamList teamList = new TeamList(contestantList);
            List<Team> teams = teamList.getTeamList();
            TeamListView teamListView = new TeamListView(teams);
            currentTab = teamListView.getTeamListView();
        }
        else{
            NotFoundView noData = new NotFoundView("No Team Available");
            HBox msgContainer = noData.getNotFoundView();
            currentTab = new Group();
            currentTab.getChildren().add(msgContainer);
        }
        scrollPane.setContent(currentTab);
        active = tab;
    }
    
    /**
     * It will show different message to user.
     * "green" defines the message text color
     * green[ 1==green, 0==red ]
     * 
     * @param status message to display
     * @param green defines whether the message to display should be Green or Red
     */
    public static void updateStatus(String status, boolean green){
        statusBar.setText(status);
        if( green ){
            statusBar.setTextFill(Color.web("#178923"));
        }
        else{
            statusBar.setTextFill(Color.web("#DC0000"));
        }
        PauseTransition errorDisplayTime = new PauseTransition(Duration.seconds(4));
        errorDisplayTime.setOnFinished( event -> statusBar.setText("") );
        errorDisplayTime.play();
    }
    
    /**
     * It will return the extension of "file"
     * 
     * @param file which extension you want
     * @return extension of "file" 
     */
    public static String getFileExtension(File file) {
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }
    
    /**
     * Disable a tab in App Window
     * 
     * @param tab Tab no. which will be disabled
     */
    public static void disableTab(int tab){
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
