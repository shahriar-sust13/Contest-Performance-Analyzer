package contest.performance.analyzer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import javafx.scene.chart.XYChart;

/**
 * This class contains the Data a Contestant
 * 
 * @author Shahriar
 * @version 1.0
 */
public class Contestant extends Student{

    int numberOfContestAttended, performanceCount;
    int totalSolved, totalTried, totalSubmission, totalTimePenalty;
    List<Performance> performance = new ArrayList<Performance>();
    List<Contest> contestDataList;
    
    /**
     * receive the name and registration no. of a Contestant
     * 
     * @param name name of the Contestant
     * @param reg registration no. of a Contestant
     */
    Contestant( String name, String reg ){
        super( name, reg );
        numberOfContestAttended = 0;
        performanceCount = totalSolved = totalTried = totalSubmission = totalTimePenalty = 0;
        contestDataList = SqlConnection.getContestList();
    }
    
    /**
     *   This will add a new contest performance to the Contestants Object
     *   It has a Performances object parameter and no return value
     * @param p Performance object which will be added to the Contestant Object
     * @see Performance
    */
    void addPerformance( Performance p ){
        numberOfContestAttended++;
        totalSolved += p.solved;
        totalTried += p.tried;
        totalSubmission += p.submission;
        totalTimePenalty += p.timePenalty;
        performance.add(p);
    }
    
    /**
     * This Method will return Average Solved in each Contest by this Contestant
     * 
     * @return average solved problem in all Contest by this Contestant  
    */
    int getAverageSolved(){
        int sum = 0;
        for( int i = 0; i<numberOfContestAttended; i++ )
            sum += performance.get(i).solved;
        double avg = (double)sum/numberOfContestAttended;
        return (int)Math.ceil(avg);
    }
    
    /*
    *   Use this method for Debugging
    */
    void print(){
        System.out.println(name + " " + regNo);
    }
    
    /**
     * sort all Performance of this Contestant according the Contest Date
     * 
     * @see Contest
     * @see ContestDate
     * @see Performance
     */
    void sortPerformance(){
        
        if( this.performance.size() == 0 )
            return;
        
        List<Contest> contestDataList = SqlConnection.getContestList();
        
        HashMap<Integer, Integer> contestSerial = new HashMap<Integer, Integer>();
        
        for( int i = 0; i<contestDataList.size(); i++ ){
            contestSerial.put(contestDataList.get(i).contestId, i+1);
        }    
        performance.sort( new Comparator<Performance>() {
            public int compare(Performance ob1, Performance ob2) {
                return (contestSerial.get(ob1.contestId) - contestSerial.get(ob2.contestId));
            }
        });
        
    }
    
    /**
     * calculate the position of the Contest identified by "contestId" in sorted Contest List(Sorted according their date)
     * @param contestId id of the Contest
     * @return position of the Contest
     * @see Contest
     * @see ContestDate
     */
    int getContestSerial(int contestId){
        int i;
        for( i = 0; i<contestDataList.size(); i++ )
            if( contestDataList.get(i).contestId == contestId )
                break;
        return (i+1);
    }
    
    
    /**
     * calculate Overall Accuracy
     * 
     * @return OverAll Accuracy
     * @throws ArithmeticException if the Contestant have no submission
     */
    double getOverallAccuracy() throws ArithmeticException{
        double accuracy = (double)totalSolved/totalSubmission;
        accuracy = (accuracy*100);
        return accuracy;
    }
    
    /**
     * Series of Accuracy in all Contest
     * 
     * @return Series object which contains Accuracy
     * @see XYChart.Series
     */
    XYChart.Series getAccuracySeries(){
        XYChart.Series series = new XYChart.Series();
        series.setName(this.name);
        
        series.getData().add( new XYChart.Data( 0, 0 ) );
        for( int i = 0; i<this.numberOfContestAttended; i++ ){
            series.getData().add( new XYChart.Data( getContestSerial(this.performance.get(i).contestId), this.performance.get(i).getAccuracy() ) );
        }
        
        return series;
    }
    
    /**
     * Series of Performance in all Contest
     * 
     * @return Series object which contains rating
     * @see XYChart.Series
     */
    XYChart.Series getPerformanceSeries(){
        XYChart.Series series = new XYChart.Series();
        series.setName(this.name);
        
        series.getData().add( new XYChart.Data( 0, 0 ) );
        for( int i = 0; i<this.numberOfContestAttended; i++ ){
            series.getData().add( new XYChart.Data( getContestSerial(this.performance.get(i).contestId), this.performance.get(i).getRating() ) );
        }
        
        return series;
    }
    
    /**
     * Series of Cumulative Solve in all Contest
     * 
     * @return Series object which contains Cumulative Solve
     * @see XYChart.Series
     */
    XYChart.Series getCommulativeSolveSeries(){
        XYChart.Series series = new XYChart.Series();
        series.setName(this.name);
        
        int sum = 0;
        series.getData().add( new XYChart.Data( 0, 0 ) );
        for( int i = 0; i<this.numberOfContestAttended; i++ ){
            sum += this.performance.get(i).solved;
            series.getData().add( new XYChart.Data( getContestSerial(this.performance.get(i).contestId), sum ) );
        }
        
        return series;
    }
    
    /*
    *   This Method will return a list of Accuracy in all contest
    */
    
    
}