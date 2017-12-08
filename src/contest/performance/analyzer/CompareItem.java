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
import javafx.scene.text.FontPosture;

/**
 * This Class will show the difference between two data item
 * 
 * @author Shahriar
 * @version 1.0
 */
public class CompareItem {
    
    int leftValue, rightValue;
    String item;
    
    /**
     * store different values of two data-item
     * 
     * @param left value of left side Contestant
     * @param right value of right side Contestant
     * @param name name of data-item
     */
    CompareItem(int left, int right, String name){
        leftValue = left;
        rightValue = right;
        item = name;
    }
    
    /**
     * shows the difference of value between two Contestant based on the corresponding data-item
     * 
     * @return HBox contains the difference of two values
     */
    HBox getItem(){
        
        Label leftSide = new Label(Integer.toString(leftValue));
        leftSide.getStyleClass().add("left-side");
        leftSide.setFont(Font.font("Lucida Console", 20));
        
        Label itemName = new Label(item);
        itemName.getStyleClass().add("compare-item-name");
        itemName.setAlignment(Pos.CENTER);
        itemName.setFont(Font.font("Times New Roman", 22));
        itemName.setMinHeight(50);
        itemName.setMinWidth(250);
        
        Label rightSide = new Label(Integer.toString(rightValue));
        rightSide.getStyleClass().add("right-side");
        rightSide.setFont(Font.font("Lucida Console", 20));
        
        HBox itemContainer = new HBox();
        itemContainer.getStyleClass().add("compare-item-container");
        itemContainer.setMinWidth(1080);
        itemContainer.setMinHeight(80);
        itemContainer.setAlignment(Pos.BOTTOM_CENTER);
        
        itemContainer.getChildren().addAll(leftSide, itemName, rightSide);
        
        return itemContainer;
    }
    
}
