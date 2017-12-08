/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 * Make the Not Found Page
 * 
 * @author Shahriar
 * @version 1.0
 */
public class NotFoundView {
    
    String error = "No Data Avaiable";
    
    NotFoundView(){}
    
    /**
     * receive the message which will be displayed in the Not Found Page
     * 
     * @param error a String which is the Error message
     */
    NotFoundView(String error){
        this.error = error;
    }
    
    /**
     * make a Not Found Page View
     * 
     * @return a HBox which contains the Not Found Page
     */
    HBox getNotFoundView(){
        HBox msgContainer = new HBox();
        msgContainer.setMinHeight(300);
        msgContainer.setMinWidth(1080);
        msgContainer.setAlignment(Pos.CENTER);
        Label noDataMsg = new Label(error);
        noDataMsg.setFont(Font.font("Times New Roman", 26));
        noDataMsg.setLayoutX(250);
        noDataMsg.setLayoutY(300);
        msgContainer.getChildren().add(noDataMsg);
        return msgContainer;
    }
    
}
