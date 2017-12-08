/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Processing a Contestant Data,
 * It makes Profile view of a Contestant
 * 
 * @author Shahriar
 * @version 1.0
 */
public class Profile {
    
    Contestant contestant;
    List<Contest> contestDataList;
    
    /**
     * receive a Contestant which data will be processed
     * 
     * @param cc a Contestant Object
     * @see Contestant
     */
    Profile( Contestant cc ){
        contestant = cc;
        contestDataList = SqlConnection.getContestList();
        contestant.sortPerformance();
    }
    
    /**
     * make a profile for a Contestant using its Data
     * 
     * @return a Group object which contains the Contestant Profile
     * @see Contestant
     */
    Group getProfileContainer(){
        Group group = new Group();
        
        HBox tab = new HBox();
        tab.getStyleClass().add("contest-tab");
        tab.setLayoutX(0);
        tab.setLayoutY(200);
        tab.setMinHeight(60);
        tab.setMinWidth(900);
        tab.setAlignment(Pos.CENTER);
        tab.setVisible(true);
         
        Button summaryTab = new Button("Summary");
        summaryTab.getStyleClass().add("tab-item21");
        Button performanceTab = new Button("Performance");
        performanceTab.getStyleClass().add("tab-item22");
        Button accuracyTab = new Button("Accuracy");
        accuracyTab.getStyleClass().add("tab-item23");
        Button comparisonTab = new Button("Comparison");
        comparisonTab.getStyleClass().add("right-most-tab2");
        
        tab.getChildren().addAll( summaryTab, performanceTab, accuracyTab, comparisonTab );
        
        Summary summary = new Summary(contestant);
        VBox summaryContainer = summary.getSummary();
        summaryContainer.setLayoutX(300);
        summaryContainer.setLayoutY(300);
        summaryContainer.setVisible(true);
        
        LineChart<Number, Number> lineChart = getLineChart();
        lineChart.setTitle("Performance in All Contest");
        lineChart.getStyleClass().add("line-chart");
        lineChart.setMaxHeight(400);
        lineChart.setMinWidth(750);
        lineChart.setMaxWidth(750);
        
        HBox curveContainer = new HBox();
        curveContainer.getStyleClass().add("curve-container");
        curveContainer.setPadding( new Insets( 20, 10, 10, 10 ) );
        curveContainer.setLayoutX(33);
        curveContainer.setLayoutY(290);
        curveContainer.setMinWidth(810);
        curveContainer.setMaxHeight(400);
        curveContainer.setVisible(false);
        
        curveContainer.getChildren().addAll(lineChart);
        
        LineChart<Number, Number> commulativeSolveChart = getCommulativeSolveChart();
        commulativeSolveChart.setTitle("Commulative Solve in All Contest");
        commulativeSolveChart.getStyleClass().add("line-chart");
        commulativeSolveChart.setMaxHeight(400);
        commulativeSolveChart.setMinWidth(750);
        commulativeSolveChart.setMaxWidth(750);
        
        HBox solveContainer = new HBox();
        solveContainer.getStyleClass().add("curve-container");
        solveContainer.setPadding( new Insets( 20, 10, 10, 10 ) );
        solveContainer.setLayoutX(33);
        solveContainer.setLayoutY(720);
        solveContainer.setMinWidth(810);
        solveContainer.setMaxHeight(400);
        solveContainer.setVisible(false);
        
        solveContainer.getChildren().add(commulativeSolveChart);
        
        VBox emptySpace = new VBox();
        emptySpace.setLayoutY(1130);
        emptySpace.setVisible(false);
        
        Accuracy accuracy = new Accuracy(contestant);
        
        VBox accuracyContainer = accuracy.getAccuracyView();
        accuracyContainer.setVisible(false);
        accuracyContainer.setLayoutX(0);
        accuracyContainer.setLayoutY(300);
        
        Comparison comparison = new Comparison(contestant, SqlConnection.getContestantList());
        HBox comparisonContainer = comparison.getComparisonContainer();
        comparisonContainer.setVisible(false);
        comparisonContainer.setLayoutX(250);
        comparisonContainer.setLayoutY(350);
        
        summaryTab.setOnAction((ActionEvent e) ->{
            curveContainer.setVisible(false);
            emptySpace.setVisible(false);
            accuracyContainer.setVisible(false);
            comparisonContainer.setVisible(false);
            solveContainer.setVisible(false);
            summaryContainer.setVisible(true);
        });
        
        performanceTab.setOnAction((ActionEvent e)->{
            summaryContainer.setVisible(false);
            accuracyContainer.setVisible(false);
            comparisonContainer.setVisible(false);
            emptySpace.setVisible(true);
            curveContainer.setVisible(true);
            solveContainer.setVisible(true);
        });
        
        accuracyTab.setOnAction((ActionEvent e)->{
            summaryContainer.setVisible(false);
            curveContainer.setVisible(false);
            comparisonContainer.setVisible(false);
            solveContainer.setVisible(false);
            emptySpace.setVisible(true);
            accuracyContainer.setVisible(true);
        });
        
        comparisonTab.setOnAction((ActionEvent e)->{
            summaryContainer.setVisible(false);
            curveContainer.setVisible(false);
            emptySpace.setVisible(false);
            accuracyContainer.setVisible(false);
            solveContainer.setVisible(false);
            comparisonContainer.setVisible(true);
        });
        
        group.getChildren().addAll( tab, summaryContainer, curveContainer, emptySpace, accuracyContainer, comparisonContainer, solveContainer );
        
        return group;
    }
    
    /**
     * Make a Line Chart of a Contestant Performance rating in all Contest
     * 
     * @return a LineChart object which contains the Contestant performance rating
     */
    LineChart<Number, Number> getLineChart(){
        NumberAxis xAxis = new NumberAxis( 0, OverView.getTotalContest()+1, 1 );
        xAxis.setLabel("Number of Contest");
        xAxis.setMinorTickVisible(false);
        OverView.totalContestant = 30;
        NumberAxis yAxis = new NumberAxis( 0, OverView.totalContestant*15+10, (OverView.totalContestant*15+10)/5 );
        yAxis.setLabel("Rating");
        
        LineChart<Number, Number> lineChart = new LineChart( xAxis, yAxis );
        lineChart.setLegendVisible(true);
        lineChart.setAlternativeRowFillVisible(false);
        
        XYChart.Series series = contestant.getPerformanceSeries();
        series.setName(contestant.name);
        
        lineChart.getData().add(series);
        lineChart.setAlternativeRowFillVisible(true);
        lineChart.setAlternativeColumnFillVisible(false);
        lineChart.setCreateSymbols(true);
        lineChart.setAnimated(true);
        
        return lineChart;
    }
    
    /**
     * Make a Line Chart of a Contestant Cumulative Solve in all Contest
     * 
     * @return a LineChart object which contains the Contestant Cumulative Solve
     */
    LineChart<Number, Number> getCommulativeSolveChart(){
        NumberAxis xAxis = new NumberAxis( 0, OverView.getTotalContest()+1, 1 );
        xAxis.setLabel("Number of Contest");
        xAxis.setMinorTickVisible(false);
        NumberAxis yAxis = new NumberAxis( 0, OverView.getTotalContest()*12+2, 10 );
        yAxis.setLabel("Solve");
        
        LineChart<Number, Number> lineChart = new LineChart( xAxis, yAxis );
        lineChart.setLegendVisible(true);
        lineChart.setAlternativeRowFillVisible(false);
        
        XYChart.Series series = contestant.getCommulativeSolveSeries();
        series.setName(contestant.name);
        
        lineChart.getData().add(series);
        lineChart.setAlternativeRowFillVisible(true);
        lineChart.setAlternativeColumnFillVisible(false);
        lineChart.setCreateSymbols(true);
        lineChart.setAnimated(true);
        
        return lineChart;
    }
    
}
