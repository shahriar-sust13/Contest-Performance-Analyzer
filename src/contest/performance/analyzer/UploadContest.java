/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import java.io.File;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Upload a Contest Data to the "ContestDataBase".
 * 
 * @author Shahriar
 * @version 1.0
 */
public class UploadContest {
    
    File file;
    Stage loadingWindow;
    boolean uploadError;
    Exception error;
    
    /**
     * Receive a .xlsx file which will be processed
     * 
     * @param file .xlsx file which will be processed
     */
    UploadContest(File file){
        this.file = file;
        loadingWindow = new Stage();
        uploadError = false;
    }
    
    /**
     * When this method is called Upload starts
     */
    void start(HBox menuContainer){
        
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
        
        Task uploading = new Task(){
            @Override protected Integer call() throws Exception {
                try{
//                    System.out.println("Uploading...");
                    ReadExcelFile excelFile = new ReadExcelFile(file);
                    Contest contest = excelFile.getContest();
                    SqlConnection.uploadContest(contest);
//                    System.out.println("Upload End");
                }catch(Exception e){
//                    System.out.println(e.toString());
                    uploadError = true;
                    error = e;
                }
                return 1;
            }
        };
        Thread uploadThread = new Thread(uploading);
//        importThread.setDaemon(true);
        uploadThread.start();
        
        uploading.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                loadingWindow.close();
                menuContainer.setDisable(false);
                
                if( uploadError == true ){
                    if( error.toString() == "Invalid Date Format!")
                        ContestPerformanceAnalyzer.updateStatus(error.toString(), false);
                    else
                        ContestPerformanceAnalyzer.updateStatus("Something Went Wrong :( Try Again!", false);
                    return;
                }
                
                if( ContestPerformanceAnalyzer.active != 3 )
                    ContestPerformanceAnalyzer.updateTab(ContestPerformanceAnalyzer.active, false);
                else
                    ContestPerformanceAnalyzer.updateTab(ContestPerformanceAnalyzer.active, false);
                
                ContestPerformanceAnalyzer.updateStatus("New Contest File Uploaded!", true);
            }
        });
        
    }
    
}
