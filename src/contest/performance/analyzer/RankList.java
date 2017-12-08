/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Make the Contest Rank List View
 * 
 * @author Shahriar
 * @version 1.0
 */
public class RankList {
    
    ObservableList<Person> data = FXCollections.observableArrayList();
    
    /**
     * receive the rank List which will be displayed
     * 
     * @param rankList a Contestant list which will be displayed
     * @see Contestant
     */
    RankList( List<Contestant> rankList ){
        for( int i = 0; i<rankList.size(); i++ ){
            data.add( new Person( i+1, rankList.get(i).name, rankList.get(i).regNo, rankList.get(i).performance.get(0).solved, rankList.get(i).performance.get(0).tried, rankList.get(i).performance.get(0).submission, rankList.get(i).performance.get(0).timePenalty ) );
        }
    }
    
    /**
     * Make a Rank List View
     * 
     * @return a Table object which contains the Rank List
     */
    TableView getRankListTable(){
        TableView table = new TableView();
        table.getStyleClass().add("rank-list-table");
        table.setEditable(false);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setMaxWidth(720);
        table.setMaxHeight(data.size()*40+26.5);
        
        TableColumn rankCol = new TableColumn("Rank");
        rankCol.setMinWidth(80);
        rankCol.setCellValueFactory( new PropertyValueFactory<>("rank"));
//        rankCol.impl_setReorderable(false);
        
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setMinWidth(120);
        nameCol.setCellValueFactory( new PropertyValueFactory<>("name"));
        nameCol.setSortable(false);
//        nameCol.impl_setReorderable(false);
        
        TableColumn regNoCol = new TableColumn("Reg No");
        regNoCol.setMinWidth(100);
        regNoCol.setCellValueFactory( new PropertyValueFactory<>("regNo"));
        regNoCol.setSortable(false);
//        regNoCol.impl_setReorderable(false);
        
        TableColumn solvedCol = new TableColumn("Solved");
        solvedCol.setMinWidth(100);
        solvedCol.setCellValueFactory( new PropertyValueFactory<>("solved"));
        solvedCol.setSortable(false);
//        solvedCol.impl_setReorderable(false);
        
        TableColumn triedCol = new TableColumn("Tried");
        triedCol.setMinWidth(100);
        triedCol.setCellValueFactory( new PropertyValueFactory<>("tried"));
        triedCol.setSortable(false);
//        triedCol.impl_setReorderable(false);
        
        TableColumn submissionCol = new TableColumn("Submission");
        submissionCol.setMinWidth(100);
        submissionCol.setCellValueFactory( new PropertyValueFactory<>("submission"));
        submissionCol.setSortable(false);
//        submissionCol.impl_setReorderable(false);
        
        TableColumn penaltyCol = new TableColumn("Penalty");
        penaltyCol.setMinWidth(100);
        penaltyCol.setCellValueFactory( new PropertyValueFactory<>("timePenalty"));
        penaltyCol.setSortable(false);
//        penaltyCol.impl_setReorderable(false);
        
        table.getColumns().addAll( rankCol, nameCol, regNoCol, solvedCol, triedCol, submissionCol, penaltyCol );
        table.setItems(data);
        
        return table;
    }
    
}
