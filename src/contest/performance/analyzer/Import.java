/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import static contest.performance.analyzer.ContestPerformanceAnalyzer.active;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This Class import Data from "BackupDataBase" to "ContestDataBase",
 * Data currently kept in "ContestDataBase" will be Lost.
 * 
 * @author Shahriar
 * @version 1.0
 */
public class Import {
    
    int activeTab;
    Stage importWindow;
    Stage loadingWindow;
    HBox menuContainer;
    
    /**
     * create two Stage for Dialog Box and Loading Window
     * 
     * @param tab current tab no. which is opened
     * @param menu it will be disabled during import
     */
    Import(int tab, HBox menu){
        activeTab = tab;
        menuContainer = menu;
        importWindow = new Stage();
        loadingWindow = new Stage();
    }
    
    /**
     * When this method is called, it starts Importing.
     */
    public void start(){
        
        int width = 350;
        int height = 200;
        
        Label warning = new Label("Warning: ");
        warning.setFont(Font.font("Arial", FontWeight.BOLD, 17));
        
        Label msg = new Label("Your current Data may Lost");
        msg.setFont(Font.font("Georgia", 17));
        
        HBox warningContainer = new HBox();
        warningContainer.setAlignment(Pos.TOP_CENTER);
        warningContainer.setMinWidth(width);
        warningContainer.setMinHeight(40);
        warningContainer.setMaxHeight(40);
        warningContainer.getChildren().addAll(warning, msg);
        
        VBox.setMargin(warningContainer, new Insets(30,0,0,0));
        
        Label command = new Label("Click OK to continue or Cancel to Abort");
        command.setFont(Font.font("Times New Roman", 18));
        
        HBox commandContainer = new HBox();
        commandContainer.setAlignment(Pos.CENTER);
        commandContainer.setMaxWidth(width);
        commandContainer.getChildren().addAll(command);
        
        Button okBtn = new Button("OK");
        okBtn.setMinWidth(70);
        
        okBtn.setOnAction((ActionEvent e)->{
            importWindow.close();
            showLoadingWindow();
        });
        
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setMinWidth(70);
        
        cancelBtn.setOnAction((ActionEvent e)->{
            importWindow.close();
        });
        
        HBox btnContainer = new HBox(10);
        btnContainer.setAlignment(Pos.CENTER_RIGHT);
        btnContainer.setMinHeight(90);
        btnContainer.setMinWidth(width);
        btnContainer.setStyle("-fx-background-color: #D8D8D8;");
        btnContainer.setPadding(new Insets(0,40,0,0));
        btnContainer.getChildren().addAll(okBtn, cancelBtn);
        
        VBox.setMargin(btnContainer, new Insets(20,0,0,0));
        
        VBox dataContainer = new VBox();
        dataContainer.getChildren().addAll(warningContainer, commandContainer, btnContainer);
        
        Scene scene = new Scene(dataContainer, width, height, Color.WHITE);
        
        importWindow.setResizable(false);
        importWindow.initStyle(StageStyle.UTILITY);
        importWindow.setScene(scene);
        importWindow.setTitle("Warning!");
        importWindow.show();
        
    }
    
    /**
     * Display the Loading Window
     */
    void showLoadingWindow(){
        
        menuContainer.setDisable(true);
        
        Image loadingIcon = new Image("/contest/performance/analyzer/resources/v2/loading.gif", 250, 100, true, true);
        ImageView loadingView = new ImageView(loadingIcon);
        
        VBox container = new VBox();
        container.getChildren().addAll(loadingView);
        
        Scene scene = new Scene(container, 250, 100);

        loadingWindow.setResizable(false);
        loadingWindow.initStyle(StageStyle.UTILITY);
        loadingWindow.setScene(scene);
        loadingWindow.show();
        
        Task importing = new Task(){
            @Override protected Integer call() throws Exception {
                SqlConnection.importBackUp();
                return 1;
            }
        };
        Thread importThread = new Thread(importing);
//        importThread.setDaemon(true);
        importThread.start();
        
        importing.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                menuContainer.setDisable(false);
                loadingWindow.close();
                if( active == 3 )
                    ContestPerformanceAnalyzer.updateTab(active);
                else
                    ContestPerformanceAnalyzer.updateTab(active, false);
                }
        });
        
    }
    
}
