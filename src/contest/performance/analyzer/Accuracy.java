/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author USER
 */
public class Accuracy {
    
    Contestant contestant;
//    List<Contest> contestDataList;
    
    Accuracy(Contestant contestant){
        this.contestant = contestant;
//        contestDataList = SqlConnection.getContestList();
    }
    
    /**
     * make a VBox which contains all Accuracy Data of "Contestant"
     * 
     * @return a VBox containing Accuracy Pie Chart, Accuracy Line Chart
     */
    VBox getAccuracyView(){
        
        VBox accuracyContainer = new VBox(20);
        accuracyContainer.setAlignment(Pos.TOP_CENTER);
        
        HBox pieChartContainer = new HBox();
        pieChartContainer.getStyleClass().add("pie-chart-container");
        pieChartContainer.setAlignment(Pos.TOP_CENTER);
        pieChartContainer.setMinWidth(880);
        
        try{
            PieChart accuracyChart = getAccuracyChart();
            accuracyChart.getStyleClass().add("accuracy-chart");
            accuracyChart.setPadding(new Insets(20,20,20,60));
            pieChartContainer.getChildren().addAll(accuracyChart);
        }catch(Exception e){
            Label error = new Label("May be no submissions encountered");
            error.setFont(Font.font("Arial", 22));
            pieChartContainer.getChildren().add(error);
        }
        
        LineChart<Number, Number> lineChart = getLineChart();
        lineChart.setTitle("Accuracy in All Contest");
        lineChart.getStyleClass().add("line-chart");
        lineChart.setMinHeight(400);
        lineChart.setMaxHeight(400);
        lineChart.setMinWidth(750);
        lineChart.setMaxWidth(750);
        
        HBox accuracyLineChartContainer = new HBox();
        accuracyLineChartContainer.getStyleClass().add("curve-container");
        accuracyLineChartContainer.getChildren().add(lineChart);
        accuracyLineChartContainer.setPadding( new Insets( 20, 10, 10, 10 ) );
        accuracyLineChartContainer.setLayoutX(33);
        accuracyLineChartContainer.setLayoutY(290);
        accuracyLineChartContainer.setMinWidth(800);
        accuracyLineChartContainer.setMaxWidth(800);
        accuracyLineChartContainer.setMinHeight(440);
        accuracyLineChartContainer.setMaxHeight(440);
        VBox.setMargin(accuracyLineChartContainer, new Insets(0,0,20,0));
        
        accuracyContainer.getChildren().addAll(pieChartContainer, accuracyLineChartContainer);
        
        return accuracyContainer;
    }
    
    /**
     * make Pie Chart of accuracy in all Contests by this "Contestant"
     * 
     * @return Accuracy Pie Chart
     */
    private PieChart getAccuracyChart(){
        
        double success = contestant.getOverallAccuracy();
        double failed = 100.0 - success;
        
        List<PieChart.Data> data = new ArrayList<PieChart.Data>();
        data.add( new PieChart.Data("Successfull Submissions", success));
        data.add( new PieChart.Data("Unsuccessfull Submissions", failed));
        
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList(data);
        
        PieChart accuracyChart = new PieChart(list);
        accuracyChart.setTitle("Accuracy Chart");
        accuracyChart.setLegendVisible(true);
        accuracyChart.setLegendSide(Side.LEFT);
        accuracyChart.setStartAngle(90.0);
        accuracyChart.setMinHeight(290);
        accuracyChart.setMaxHeight(290);
        accuracyChart.setMinWidth(600);
//        accuracyChart.setMaxWidth(230);
        accuracyChart.setLabelsVisible(false);
        
        return accuracyChart;
        
    }
    
    /**
     * make a Line Chart of Accuracy in all Contest
     * 
     * @return Accuracy Line Chart
     */
    private LineChart getLineChart(){
        NumberAxis xAxis = new NumberAxis( 0, OverView.getTotalContest()+1, 1 );
        xAxis.setLabel("Number of Contest");
        xAxis.setMinorTickVisible(false);
        NumberAxis yAxis = new NumberAxis( 0, 105, 20 );
        yAxis.setLabel("Accuracy");
        
        LineChart<Number, Number> lineChart = new LineChart( xAxis, yAxis );
        lineChart.setLegendVisible(true);
        lineChart.setAlternativeRowFillVisible(false);
        
        XYChart.Series series = contestant.getAccuracySeries();
        series.setName(contestant.name);
        
        lineChart.getData().add(series);
        lineChart.setAlternativeRowFillVisible(true);
        lineChart.setAlternativeColumnFillVisible(false);
        lineChart.setCreateSymbols(true);
        lineChart.setAnimated(true);
        
        return lineChart;
    }
   
}
