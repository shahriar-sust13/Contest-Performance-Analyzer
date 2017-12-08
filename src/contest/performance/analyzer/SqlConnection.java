/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * This Class Perform all types query to Data Base
 * 
 * @author Shahriar
 * @version 1.0
 */
public class SqlConnection {
    
    /**
     * Connect with "ContestDataBase".
     * @return a Connection object after successfully connection with DB
     */
    public static Connection DbConnector(){
        try{
            Connection conn = null;
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:ContestDataBase.sqlite");
//            System.out.println("Successfully Conntected to DataBase");
            return conn;
        }catch( ClassNotFoundException | SQLException e ){
            
        }
        return null;
    }
    
    /**
     * Connect with "BackupDataBase".
     * @return a Connection object after successfully connection with Back Up DB 
     */
    static Connection backUpDbConnector(){
        try{
            Connection conn = null;
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:BackupDataBase.sqlite");
//            System.out.println("Successfully Conntected to BackUp DataBase");
            return conn;
        }catch( ClassNotFoundException | SQLException e ){
            
        }
        return null;
    }
    
    /***
     * This Method will upload Contest Data on Database.
     * 
     * @param contest Contest Object which contains the data to be uploaded on DB
     * @see Contest
     */
    public static void uploadContest( Contest contest ){
        Connection conn = DbConnector();
        String query;
        int contestId;
        Statement statement = null;
        
        /*
        *   For new Updated DB Table
        */
        try{
            query = "INSERT INTO contest (visible, name, day, month, year) VALUES(1, \"" + contest.contestName + "\", " + contest.date.day + ", " + contest.date.month + ", " + contest.date.year + ") ";
//            System.out.println(query);
            statement = conn.createStatement();
            statement.executeQuery(query);
        }
        catch(Exception e){}
        finally{
            try{
                statement.close();
            }catch(Exception e){}
        }
        
        try{
            query = "SELECT id FROM contest ORDER BY id DESC";
            statement = conn.createStatement();
            ResultSet rst  = statement.executeQuery(query);
            rst.next();
            contestId = rst.getInt("id");
        }
        catch(Exception e){
            contestId = 1;
        }
        finally{
            try{
                statement.close();
            }catch(Exception e){}
        }
  
        for( int i = 0; i<contest.totalContestant; i++ ){
            query = "INSERT INTO rank VALUES (" + contestId + ",\"" + contest.participant[i].name + "\",\"" + contest.participant[i].regNo + "\"," + contest.performance[i].solved + "," + contest.performance[i].tried + "," + contest.performance[i].submission + "," + contest.performance[i].timePenalty + ")";
            try{
                statement = conn.createStatement();
                statement.execute(query);
            }
            catch(Exception e){}
            finally{
                try{
                    statement.close();
                }catch(Exception e){}
            }
        }
        
        try{
            conn.close();
        }catch(Exception e){}
    }
    
    /**
     * Check whether a Contestant has been added to a List
     * 
     * @param contestantList List in which it will search
     * @param regNo contestant registration no. for which it will search for
     * @return an Integer indicating the index of searching Contestant in the List, if it is found. Otherwise -1.
     * @see Contestant
     */
    private static int isContestantAdded( List<Contestant> contestantList, String regNo ){
        int i;
        for( i = 0; i<contestantList.size(); i++ ){
            String tmp = contestantList.get(i).regNo;
            if( regNo.equals(tmp) ){
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Make a List of all Unique Contestant after merging all Rank List
     * 
     * @return a Contestant List
     * @see Contestant
     */
    public static List<Contestant> getContestantList(){
        List<Contestant> contestantList = new ArrayList<Contestant>();
        Connection conn = DbConnector();
        String query;
        Statement stmt;
        ResultSet rs;
        
            query = "SELECT * FROM rank ORDER BY contestId ASC, solved DESC, penalty ASC";
            try{
                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);
                int position = 0;
                int previousContestId = 0;
                int presentContestId;
                while( rs.next() ){
                    position++;
                   
                    presentContestId = rs.getInt("contestId");
                    
                    /*
                    *   Start of a New Contest Rank List
                    */
                    if( previousContestId != presentContestId ) position = 1;
                    
                    Performance performance = new Performance( presentContestId, position, rs.getInt("solved"), rs.getInt("tried"), rs.getInt("submission"), rs.getInt("penalty") );
                    int p = isContestantAdded( contestantList, rs.getString("regNo") );
                    if( p == -1 ){
                        Contestant contestant = new Contestant( rs.getString("name"), rs.getString("regNo") );
                        contestant.addPerformance(performance);
                        contestantList.add(contestant);
                    }
                    else{
                        contestantList.get(p).addPerformance(performance);
                    }
                    previousContestId = presentContestId;
                }
            }
            catch( Exception e ){}
            
        OverView.totalContestant = contestantList.size();
        OverView.contestantList = contestantList;
        
        try{
            conn.close();
        }catch(Exception e){}
//        for( i = 0; i<contestantList.size(); i++ ){
//            System.out.println( contestantList.get(i).numberOfContestAttended + " " + contestantList.get(i).name + " " + contestantList.get(i).regNo + " " + contestantList.get(i).totalSolved );
//        }
        return contestantList;
    }
    
    /**
     * calculate the number of Contestant in a Contest which id is "contestId".
     * 
     * @param contestId id of the Contest
     * @return an integer indicating total number of Contestant in the Contest
     * @see Contest
     */
    public static int getTotalContestant( int contestId ){
        int ret = 0;
        Connection conn = DbConnector();
        String query = "SELECT Count(*) FROM rank WHERE contestId = " + contestId;
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            ret = rs.getInt(1);
        }
        catch( Exception e ){};
        
        try{
            conn.close();
        }catch(Exception e){}
        
        return ret;
    }
    
    /**
     * Calculate total number of Contest has been arranged
     * 
     * @return an integer indicating total number of Contest arranged
     */
    static int getTotalContest(){
        Connection conn = DbConnector();
        String query = "SELECT Count(*) FROM contest WHERE visible = 1";
        try{
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            return resultSet.getInt(1);
        }catch(Exception e){return 0;}
        finally{
            try{
                conn.close();
            }catch(Exception e){}
        }
    }
    
    /**
     * Calculate maximum solve in a Contest.
     * 
     * @param contestId id of the Contest
     * @return an integer maximum solve in the Contest
     */
    public static int getMaxSolve( int contestId ){
        int ret = 0;
        Connection conn = DbConnector();
        String query = "SELECT * FROM contest" + contestId;
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while( rs.next() )
                ret = Math.max( ret, rs.getInt("solved") );
        }
        catch( Exception e ){};
        
        try{
            conn.close();
        }catch(Exception e){}
        
        return ret;
    }
    
    /**
     * Make Rank List of a Contest which id is "contestId".
     * 
     * @param contestId id of the Contest for which it will make rank list
     * @return a Contestant list according their position in the Contest Standings
     * @see Contest
     * @see Contestant
     */
    public static List<Contestant> getContestRankList( int contestId ){
        int rank = 0;
        List<Contestant> rankList = new ArrayList<Contestant>();
        Connection conn = SqlConnection.DbConnector();
        String query = "SELECT * FROM rank WHERE contestId = " + contestId + " ORDER BY solved DESC, penalty ASC";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while( rs.next() ){
                Contestant contestant = new Contestant( rs.getString("name"), rs.getString("regNo") );
                Performance performance = new Performance( contestId, ++rank, rs.getInt("solved"), rs.getInt("tried"), rs.getInt("submission"), rs.getInt("penalty") );
                contestant.addPerformance(performance);
                rankList.add(contestant);
            }
        }
        catch( Exception e ){}
        
        try{
            conn.close();
        }catch(Exception e){}
        
        return rankList;
    }
    
    /**
     * Make a List of all Contest has been arranged.
     * 
     * @return list of Contest
     * @see Contest
     */
    public static List<Contest> getContestList(){
        List<Contest> list = new ArrayList<Contest>();
        Connection conn = DbConnector();
        String query = "SELECT * FROM contest WHERE visible = 1 ORDER BY year ASC, month ASC, day ASC";
        try{
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while( resultSet.next() ){
                Integer id = resultSet.getInt("id");
                int totalContestant = getTotalContestant(id);
                if( totalContestant == 0 ){
                    try{
                        String updateQuery = "UPDATE contest SET visible = 0 WHERE id = " + id;
                        Statement stmt2 = conn.createStatement();
                        stmt2.executeQuery(updateQuery);
                    }catch(Exception e){}
                }
                else{
                    ContestDate date = new ContestDate(resultSet.getInt("day"), resultSet.getInt("month"), resultSet.getInt("year"));
                    list.add(new Contest(id, resultSet.getString("name"), date));
                }
            }
        }catch(Exception e){}
        finally{
            try{
                conn.close();
            }catch(Exception e){}
        }
        return list;
    }
    
    /**
     * It deletes a Contest from "ContestDataBase" which id is "contestId".
     * 
     * @param contestId id of the Contest which will be Deleted
     * @see Contest
     */
    public static void delete(int contestId){
         Connection conn = DbConnector();
         String query = "UPDATE contest SET visible = 0 WHERE id = " + contestId;
         try{
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
         }catch(Exception e){}
         
         try{
             query = "DELETE FROM rank WHERE contestId = " + contestId;
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
         }catch(Exception e){}
         finally{
             try{
                 conn.close();
             }catch(Exception e){}
         }
    }
    
    /**
     * Deletes all data of a Contestant from "ContestDataBase".
     * 
     * @param contestant a Contestant object which data will be deleted
     * @see Contestant
     */
    public static void delete(Contestant contestant){
        Connection conn = DbConnector();
        String query = "DELETE FROM rank WHERE regNo = " + contestant.regNo;
        try{
            Statement statement = conn.createStatement();
            statement.executeQuery(query);
        }catch(Exception e){}
        finally{
            try{conn.close();}catch(Exception e){}
        }
    }
    
    /**
     * Find the Largest id of a Contest in "ContestDataBase"
     * 
     * @return the largest contest id
     */
    static int getMaxContestId(){
        Connection conn = DbConnector();
        String query = "SELECT Count(*) FROM contest";
        try{
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            return resultSet.getInt(1);
        }catch(Exception e){return 0;}
        finally{
            try{
                conn.close();
            }catch(Exception e){}
        }
    }
    
    /**
     * Make a back up of all stored in "ContestDataBase" to "BackupDataBase"
     */
    public static void backUp(){
        Connection conn = DbConnector();
        Connection backConn = backUpDbConnector();
        String query;
        
        // For Reseting BackUp DB
        try{
            query = "DELETE FROM contest";
            Statement statement = backConn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            
        }catch(Exception e){}
        
        try{
            query = "DELETE FROM rank";
            Statement statement = backConn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
        }catch(Exception e){}
        // End of Reseting
        
        try{
            query = "SELECT * FROM contest";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            
            while( resultSet.next() ){
                int contestId = resultSet.getInt("id");
                
                /*
                *   It will be true, If the Contest is deleted on DB
                */
                if( resultSet.getBoolean("visible") == false ){
                    try{
                        query = "INSERT INTO contest VALUES (" + contestId + ", 0, \"" + resultSet.getString("name") + "\", " + resultSet.getInt("day") + ", " + resultSet.getInt("month") + ", " + resultSet.getInt("year") + ")";
                        Statement statement3 = backConn.createStatement();
                        ResultSet resultSet3 = statement3.executeQuery(query);
                    }catch(Exception e){}
                    continue;
                }
                
                try{
                    query = "INSERT INTO contest VALUES (" + contestId + ", 1, \"" + resultSet.getString("name") + "\", " + resultSet.getInt("day") + ", " + resultSet.getInt("month") + ", " + resultSet.getInt("year") + ")";
                    Statement statement3 = backConn.createStatement();
                    ResultSet resultSet3 = statement3.executeQuery(query);
                }catch(Exception e){}
                query = "SELECT * FROM rank WHERE contestId = " + contestId;
                try{
                    Statement statement3 = conn.createStatement();
                    ResultSet resultSet3 = statement3.executeQuery(query);
                    while(resultSet3.next()){
                        query = "INSERT INTO rank VALUES (" + resultSet3.getInt("contestId") + ",\"" + resultSet3.getString("name") + "\",\"" + resultSet3.getString("regNo") + "\"," + resultSet3.getInt("solved") + "," + resultSet3.getInt("tried") + "," + resultSet3.getInt("submission") + "," + resultSet3.getInt("penalty") + ")";
                        try{
                            Statement statement4 = backConn.createStatement();
                            ResultSet resultSet4 = statement4.executeQuery(query);
                        }catch(Exception e){}
                    }
                }catch(Exception e){}
            }
        }catch(Exception e){}
        finally{
            try{
                conn.close();
                backConn.close();
            }catch(Exception e){}
        }
        
    }
    
    /**
     * Import data from "BackupDataBase" to "ContestDataBase"
     */
    public static void importBackUp(){
        Connection conn = DbConnector();
        Connection backConn = backUpDbConnector();
        String query;
        try{
            query = "DELETE FROM contest";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            
        }catch(Exception e){}
        
        try{
            query = "DELETE FROM rank";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
        }catch(Exception e){}
        
        try{
            query = "SELECT * FROM contest";
            Statement statement = backConn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                int contestId = resultSet.getInt("id");
                query = "INSERT INTO contest (id, visible, name, day, month, year) VALUES (" + contestId + ", " + resultSet.getInt("visible") + ", \"" + resultSet.getString("name") + "\", " + resultSet.getInt("day") + ", " + resultSet.getInt("month") + ", " + resultSet.getInt("year") + ")";
                try{
                    Statement statement2 = conn.createStatement();
                    ResultSet resultSet2 = statement2.executeQuery(query);
                }catch(Exception e){}
            }
        }catch(Exception e){}
        
        try{
                query = "SELECT * FROM rank";
                Statement statement3 = backConn.createStatement();
                ResultSet resultSet3 = statement3.executeQuery(query);
                while( resultSet3.next() ){
                    try{
                        query = "INSERT INTO rank VALUES (" + resultSet3.getInt("contestId") + ",\"" + resultSet3.getString("name") + "\",\"" + resultSet3.getString("regNo") + "\"," + resultSet3.getInt("solved") + "," + resultSet3.getInt("tried") + "," + resultSet3.getInt("submission") + "," + resultSet3.getInt("penalty") + ")";
                        Statement statement4 = conn.createStatement();
                        ResultSet resultSet4 = statement4.executeQuery(query);
                    }catch(Exception e){}
                }
            }catch(Exception e){}

        try{
            conn.close();
        }catch(Exception e){}
        try{
            backConn.close();
        }catch(Exception e){}
    }
    
    /**
     * Find the name of a Contest which id is "contestId".
     * 
     * @param contestId id of the Contest
     * @return a string contains the name of the Contest
     */
    public static String getContestName(int contestId){
        Connection conn = DbConnector();
        String query = "SELECT name FROM contest WHERE id = " + contestId;
        try{
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet.getString("name");
        }catch(Exception e){}
        finally{
            try{
                conn.close();
            }catch(Exception e){}
        }
        return null;
    }
    
}
