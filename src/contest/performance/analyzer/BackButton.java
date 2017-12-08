/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author USER
 */
public class BackButton {
    
    Button getBackButton(){
        Image image = new Image("/contest/performance/analyzer/resources/back.png", 80, 35, false, true);
        Button backBtn = new Button( "", new ImageView(image) );
        backBtn.getStyleClass().add("back-btn");
        backBtn.setStyle("-fx-background-color: transparent;");
        return backBtn;
    }
    
}
