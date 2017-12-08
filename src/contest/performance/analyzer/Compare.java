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
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Compare class receive two Contestant, 
 * Then it shows Comparison between this Two Contestant based on their data
 * 
 * @author Shahriar
 * @version 1.0
 */
public class Compare {
    
    Contestant contestant1, contestant2;
    
    Compare(Contestant c1, Contestant c2){
        contestant1 = c1;
        contestant2 = c2;
        contestant1.sortPerformance();
        contestant2.sortPerformance();
    }
    
    /**
     * Shows the Comparison between two contestant "contestant1" and "contestant2" based on their Performance in all Contest
     * 
     * @see Contest
     * @see Contestant
     * @see Performance
     */
    public void show(){
        
        TopBar topBar = new TopBar("Comparison");
        HBox topBarView = topBar.getTopBarNode();
        
        HBox contestantIdentityBar = getContestantIdentityBar();
        
        CompareItem compareTotalSolved = new CompareItem(contestant1.totalSolved, contestant2.totalSolved, "Total Solved");
        HBox totalSolved = compareTotalSolved.getItem();
        
        CompareItem compareTotalTried = new CompareItem(contestant1.totalTried, contestant2.totalTried, "Total Tried");
        HBox totalTried = compareTotalTried.getItem();
        
        CompareItem compareTotalSubmission = new CompareItem(contestant1.totalSubmission, contestant2.totalSubmission, "Total Submissions");
        HBox totalSubmission = compareTotalSubmission.getItem();
        
        CompareItem compareTotalPenalty = new CompareItem(contestant1.totalTimePenalty, contestant2.totalTimePenalty, "Time Penalty");
        HBox totalPenalty = compareTotalPenalty.getItem();
        
        CompareItem compareAverage = new CompareItem(contestant1.getAverageSolved(), contestant2.getAverageSolved(), "Average Solved");
        HBox average = compareAverage.getItem();
        
        CompareItem compareTotalContest = new CompareItem(contestant1.numberOfContestAttended, contestant2.numberOfContestAttended, "Total Contest Attended");
        HBox totalContest = compareTotalContest.getItem();
        
        VBox accuracy = getAccuracyView();
        accuracy.setMinWidth(1080); 
        
        VBox performance = getPerformanceView();
        performance.setMinWidth(1080);
        
        HBox emptySpace = new HBox();
        emptySpace.setMinHeight(20);
        
        VBox compareSection = new VBox();
        compareSection.setAlignment(Pos.TOP_CENTER);
        
        compareSection.getChildren().addAll(totalSolved, totalTried, totalSubmission, totalPenalty, average, totalContest, accuracy, performance, emptySpace);
        
        ScrollPane scrollPane = new ScrollPane(compareSection);
        scrollPane.setMinHeight(480);
        scrollPane.setMinWidth(1080);
        scrollPane.setStyle("-fx-background-color:transparent;");
        
        VBox compareContainer = new VBox();
        compareContainer.setAlignment(Pos.TOP_CENTER);
        compareContainer.setMinHeight(680);
        compareContainer.setMinWidth(1080);
        
        compareContainer.getChildren().addAll(topBarView, contestantIdentityBar, scrollPane);
        
        Scene scene = new Scene(compareContainer, 1080, 680);
        scene.getStylesheets().add("/contest/performance/analyzer/resources/style.css");
        Stage compareWindow = new Stage();
        compareWindow.setTitle("Compare Window");
        compareWindow.setScene(scene);
        compareWindow.show();
        
    }
    
    /**
     * make a HBox which contains identity of two Contestants
     * 
     * @return HBox with Contestant basic info
     * @see Contestant
     */
    HBox getContestantIdentityBar(){
        String name = String.format("%16s", contestant1.name);
        Label id1 = new Label(name + "\n" + contestant1.regNo);
        id1.setAlignment(Pos.CENTER_RIGHT);
        id1.setFont(Font.font("Georgia", 22));
        
        Label id2 = new Label(contestant2.name + "\n" + contestant2.regNo);
        id2.setAlignment(Pos.CENTER);
        id2.setFont(Font.font("Georgia", 22));
        
        HBox identityBar = new HBox(300);
        identityBar.setStyle("-fx-background-color: #798984;");
        identityBar.setAlignment(Pos.CENTER);
        identityBar.setMinHeight(100);
        identityBar.setMinWidth(1080);
        
        identityBar.getChildren().addAll(id1, id2);
        
        return identityBar;
    }
    
    /**
     * make a view of difference between Accuracy of two Contestant in all Contest
     * 
     * @return VBox which contains difference of Accuracy of two Contestant
     */
    VBox getAccuracyView(){
        Label title = new Label("Accuracy");
        title.setFont(Font.font("Times New Roman", 22));
        title.setAlignment(Pos.CENTER);
        title.setMinHeight(60);
        title.setMinWidth(700);
        title.getStyleClass().add("accuracy-title-box");
        VBox.setMargin(title, new Insets(60,0,0,0));
        
        PieChart chart1 = getAccuracyChart(contestant1);
        chart1.getStyleClass().add("accuracy-chart-comparison1");
        
        PieChart chart2 = getAccuracyChart(contestant2);
        chart2.getStyleClass().add("accuracy-chart-comparison2");
        
        HBox chartSection = new HBox();
        chartSection.setAlignment(Pos.CENTER);
        chartSection.setMinWidth(1080);
        
        chartSection.getChildren().addAll(chart1, chart2);
        
        Image legend = new Image("/contest/performance/analyzer/resources/v2/pie-chart-legend.jpg", 344, 35, false, true);
        ImageView legendView = new ImageView(legend);
        
        HBox accuracyLineChart = getAccuracyLineChart();
        
        VBox accuracyView = new VBox(30);
        accuracyView.setAlignment(Pos.CENTER);
        
        accuracyView.getChildren().addAll(title, chartSection, legendView, accuracyLineChart);
        
        return accuracyView;
    }
    
    /**
     * show Accuracy of a Contestant using a Pie Chart
     * 
     * @param contestant which Accuracy will be shown
     * @return PieChart showing the Accuracy
     * @see PieChart
     */
    private PieChart getAccuracyChart(Contestant contestant){
        
        double success = contestant.getOverallAccuracy();
        double failed = 100.0 - success;
        
        List<PieChart.Data> data = new ArrayList<PieChart.Data>();
        data.add( new PieChart.Data("Successfull Submissions", success));
        data.add( new PieChart.Data("Unsuccessfull Submissions", failed));
        
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList(data);
        
        PieChart accuracyChart = new PieChart(list);
        accuracyChart.setLegendVisible(false);
        accuracyChart.setStartAngle(90.0);
        accuracyChart.setMinHeight(220);
        accuracyChart.setMaxHeight(220);
        accuracyChart.setMinWidth(220);
        accuracyChart.setMaxWidth(220);
        
        return accuracyChart;
        
    }
    
    /**
     * shows Accuracy difference in all Contest using Line Chart
     * 
     * @return HBox contains accuracy LineChart of two Contestant
     * @see Contestant
     * @see LineChart
     */
    private HBox getAccuracyLineChart(){
        NumberAxis xAxis = new NumberAxis( 0, OverView.getTotalContest()+1, 1 );
        xAxis.setLabel("Number of Contest");
        xAxis.setMinorTickVisible(false);
        NumberAxis yAxis = new NumberAxis( 0, 100, 20 );
        yAxis.setLabel("Accuracy");
        
        LineChart<Number, Number> lineChart = new LineChart( xAxis, yAxis );
        lineChart.setLegendVisible(true);
        lineChart.setAlternativeRowFillVisible(false);
        
        XYChart.Series series1 = contestant1.getAccuracySeries();
        XYChart.Series series2 = contestant2.getAccuracySeries();
        
        lineChart.getData().addAll(series1, series2);
        lineChart.setAlternativeRowFillVisible(true);
        lineChart.setAlternativeColumnFillVisible(false);
        lineChart.setCreateSymbols(true);
        lineChart.setAnimated(true);
        lineChart.setTitle("Accuracy in All Contest");
        lineChart.getStyleClass().add("comparison-line-chart");
        lineChart.setMinHeight(400);
        lineChart.setMaxHeight(400);
        lineChart.setMinWidth(750);
        lineChart.setMaxWidth(750);
        
        HBox accuracyLineChartContainer = new HBox();
        accuracyLineChartContainer.getStyleClass().add("curve-container");
        accuracyLineChartContainer.setPadding( new Insets( 20, 10, 10, 10 ) );
        accuracyLineChartContainer.setMinWidth(800);
//        accuracyLineChartContainer.setMaxWidth(800);
        accuracyLineChartContainer.setMinHeight(440);
        accuracyLineChartContainer.setMaxHeight(440);
        
        accuracyLineChartContainer.getChildren().add(lineChart);
        
        HBox ret = new HBox();
        ret.setAlignment(Pos.CENTER);
        ret.setMinWidth(1080);
        
        ret.getChildren().add(accuracyLineChartContainer);
        
        return ret;
    }
    
    /**
     * shows difference between Performance of two Contestant
     * 
     * @return VBox which contains Performance LineChart, Cumulative Solve LineChart
     * @see Contestant
     * @see LineChart
     * @see Performance
     */
    private VBox getPerformanceView(){
        Label title = new Label("Performance");
        title.setFont(Font.font("Times New Roman", 22));
        title.setAlignment(Pos.CENTER);
        title.setMinHeight(60);
        title.setMinWidth(700);
        title.getStyleClass().add("accuracy-title-box");
        VBox.setMargin(title, new Insets(60,0,0,0));
        
        HBox performanceLineChart = getPerformanceLineChart();
        
        HBox commulativeSolveChart = getCommulativeSolveChart();
        
        VBox performanceView = new VBox(40);
        performanceView.setAlignment(Pos.CENTER);
        
        performanceView.getChildren().addAll(title, performanceLineChart, commulativeSolveChart);
        
        return performanceView;
    }
    
    /**
     * shows the difference between rating in all contest using "LineChart"
     * 
     * @return HBox contains rating LineChart
     * @see Contestant
     * @see LineChart
     */
    private HBox getPerformanceLineChart(){
        NumberAxis xAxis = new NumberAxis( 0, OverView.getTotalContest()+1, 1 );
        xAxis.setLabel("Number of Contest");
        xAxis.setMinorTickVisible(false);
        OverView.totalContestant = 30;
        NumberAxis yAxis = new NumberAxis( 0, OverView.totalContestant*15+10, (OverView.totalContestant*15+10)/5 );
        yAxis.setLabel("Rating");
        
        LineChart<Number, Number> lineChart = new LineChart( xAxis, yAxis );
        lineChart.setLegendVisible(true);
        lineChart.setAlternativeRowFillVisible(false);
        
        XYChart.Series series1 = contestant1.getPerformanceSeries();
        XYChart.Series series2 = contestant2.getPerformanceSeries();
        
        lineChart.getData().addAll(series1, series2);
        lineChart.setAlternativeRowFillVisible(true);
        lineChart.setAlternativeColumnFillVisible(false);
        lineChart.setCreateSymbols(true);
        lineChart.setAnimated(true);
        lineChart.setTitle("Performance in All Contest");
        lineChart.getStyleClass().add("comparison-line-chart");
        lineChart.setMinHeight(400);
        lineChart.setMaxHeight(400);
        lineChart.setMinWidth(750);
        lineChart.setMaxWidth(750);
        
        HBox accuracyLineChartContainer = new HBox();
        accuracyLineChartContainer.getStyleClass().add("curve-container");
        accuracyLineChartContainer.setPadding( new Insets( 20, 10, 10, 10 ) );
        accuracyLineChartContainer.setMinWidth(800);
//        accuracyLineChartContainer.setMaxWidth(800);
        accuracyLineChartContainer.setMinHeight(440);
        accuracyLineChartContainer.setMaxHeight(440);
        
        accuracyLineChartContainer.getChildren().add(lineChart);
        
        HBox ret = new HBox();
        ret.setAlignment(Pos.CENTER);
        ret.setMinWidth(1080);
        
        ret.getChildren().add(accuracyLineChartContainer);
        
        return ret;
    }
    
    /**
     * shows the difference between Cumulative solve of two Contestant using LineChart
     * 
     * @return HBox which contains Cumulative solve LineChart
     * @see Contestant
     * @see LineChart
     */
    private HBox getCommulativeSolveChart(){
        NumberAxis xAxis = new NumberAxis( 0, OverView.getTotalContest()+1, 1 );
        xAxis.setLabel("Number of Contest");
        xAxis.setMinorTickVisible(false);
        OverView.totalContestant = 30;
        NumberAxis yAxis = new NumberAxis( 0, OverView.getTotalContest()*12, 20 );
        yAxis.setLabel("Solved");
        
        LineChart<Number, Number> lineChart = new LineChart( xAxis, yAxis );
        lineChart.setLegendVisible(true);
        lineChart.setAlternativeRowFillVisible(false);
        
        XYChart.Series series1 = contestant1.getCommulativeSolveSeries();
        XYChart.Series series2 = contestant2.getCommulativeSolveSeries();
        
        lineChart.getData().addAll(series1, series2);
        lineChart.setAlternativeRowFillVisible(true);
        lineChart.setAlternativeColumnFillVisible(false);
        lineChart.setCreateSymbols(true);
        lineChart.setAnimated(true);
        lineChart.setTitle("Commulative Solve");
        lineChart.getStyleClass().add("comparison-line-chart");
        lineChart.setMinHeight(400);
        lineChart.setMaxHeight(400);
        lineChart.setMinWidth(750);
        lineChart.setMaxWidth(750);
        
        HBox lineChartContainer = new HBox();
        lineChartContainer.getStyleClass().add("curve-container");
        lineChartContainer.setPadding( new Insets( 20, 10, 10, 10 ) );
        lineChartContainer.setMinWidth(800);
//        accuracyLineChartContainer.setMaxWidth(800);
        lineChartContainer.setMinHeight(440);
        lineChartContainer.setMaxHeight(440);
        
        lineChartContainer.getChildren().add(lineChart);
        
        HBox ret = new HBox();
        ret.setAlignment(Pos.CENTER);
        ret.setMinWidth(1080);
        
        ret.getChildren().add(lineChartContainer);
        
        return ret;
    }
    
}
