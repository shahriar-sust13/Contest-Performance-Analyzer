/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

/**
 * Shows the comparison tab in Contestant Profile.
 * This Page Contains a list of Contestant in Combo-Box,
 * after selecting one of the contestant a new Window will be opened,
 * Which shows the Comparison between corresponding profile and selected Contestant
 * 
 * @author USER
 * @version  1.0
 */
public class Comparison {
    
    Contestant contestant;
    List<Contestant> contestantList;
    Map<String, String> contestantId = new HashMap<String, String>();
    
    /**
     * Store profile Contestant and all Contestant List
     * 
     * @param contestant Contestant object from which this Tab is opened
     * @param list list of all Contestant
     */
    Comparison(Contestant contestant, List<Contestant> list){
        this.contestant = contestant;
        contestantList = list;
    }
    
    /**
     * Shows Contestant Combo-Box
     * 
     * @return HBox contains Combo-Box of all Contestant
     */
    HBox getComparisonContainer(){
        
        ComboBox contestantComboBox = new ComboBox();
        contestantComboBox.setVisibleRowCount(6);
        
        for( int i = 0; i<contestantList.size(); i++ ){
            if( contestantList.get(i).regNo.matches(contestant.regNo) == true )   continue;
            contestantId.put(contestantList.get(i).name + " (" + contestantList.get(i).regNo + ")", contestantList.get(i).regNo);
            contestantComboBox.getItems().add(contestantList.get(i).name + " (" + contestantList.get(i).regNo + ")");
        }
        
        Button compareBtn = new Button("Compare");
        
        compareBtn.setOnAction((ActionEvent e)->{
            if( contestantComboBox.getValue() != null ){
                String id = contestantId.get(contestantComboBox.getValue());
                for( int i = 0; i<contestantList.size(); i++ ){
                    if( contestantList.get(i).regNo.matches(id) == true ){
                        Contestant contestant2 = contestantList.get(i);
                        Compare compare = new Compare(contestant, contestant2);
                        compare.show();
                    }
                }
            }
        });
        
        HBox container = new HBox(10);
        container.setAlignment(Pos.CENTER);
        container.setMinHeight(100);
        container.setMinWidth(400);
        container.getChildren().addAll(contestantComboBox, compareBtn);
        
        return container;
        
    }
    
}
