/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This Class make a Back Up of current Data kept in "ContestDataBase" to a new Data Base named "BackupDataBase".
 * 
 * @author Shahriar
 * @version 1.0
 */
public class Export {
    
    Stage loadingWindow;
    
    /**
     * Create a Progress Bar Stage
     */
    Export(){
        loadingWindow = new Stage();
    }
    
    /**
     * When this method called it starts Exporting and Blocked the restricted Buttons
     * 
     * @param menuContainer it will be Blocked during Exporting
     */
    void start(Button addContestBtn, HBox menuContainer){
        
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
                SqlConnection.backUp();
                return 1;
            }
        };
        Thread importThread = new Thread(importing);
//        importThread.setDaemon(false);
        importThread.start();
        
        importing.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                menuContainer.setDisable(false);
                loadingWindow.close();
            }
        });
        
    }
    
}
