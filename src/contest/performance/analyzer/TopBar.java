/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Display the Top Bar of the App
 * 
 * @author Shahriar
 * @version 1.0
 */
public class TopBar {
    
    String headLineText;
    
    /**
     * receive the headline
     * 
     * @param text a String which will be displayed in Top Bar
     */
    TopBar(String text){
        headLineText = text;
    }
    
    /**
     * Display the Top Bar
     * 
     * @return a HBox which contains the Top Bar
     */
    public HBox getTopBarNode(){
        HBox topBar = new HBox();
        topBar.getStyleClass().add("top-bar");
        topBar.setMinHeight(100);
        topBar.setMinWidth(1080);
        topBar.setAlignment(Pos.CENTER);
        Label headLine = new Label(headLineText);
        headLine.getStyleClass().add("head-line");
        headLine.setTextFill(Color.web("#E4E4DE"));
        headLine.setFont(Font.font("Arial", 22));
        
        topBar.getChildren().add(headLine);
        
        return topBar;
    }
    
}
