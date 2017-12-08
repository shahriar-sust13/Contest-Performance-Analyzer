/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author USER
 */
public class Champion {
    
    Contestant contestant;
    
    Champion( Contestant contestant ){
        this.contestant = contestant;
    }
    
    VBox getChampionContainer(){
        VBox championContainer = new VBox();
        championContainer.getStyleClass().add("champion-container");
        championContainer.setAlignment(Pos.CENTER);
        championContainer.setMinWidth(870);
        championContainer.setMinHeight(250);
        Label championTitle = new Label("Champion");
        championTitle.setFont(Font.font("Arial", 25));
        Label name = new Label(contestant.name);
        name.setFont(Font.font("Times New Roman", 20));
        name.setPadding(new Insets(20, 0, 10, 0));
        Label regNo = new Label(contestant.regNo);
        regNo.setFont(Font.font("Times New Roman", 20));
        
        championContainer.getChildren().addAll(championTitle, name, regNo);
        
        return championContainer;
    }
}
