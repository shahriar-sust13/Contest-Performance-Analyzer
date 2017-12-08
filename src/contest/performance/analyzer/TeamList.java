/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Process all the data in all Contest,
 * Then generate teams according to the Rules.
 * Store the teams in a List
 * 
 * @author Shahriar
 * @version 1.0
 */
public class TeamList {
    
    List<Contestant> contestantList;
    List<Team> teamList;
    
    /**
     * Receive a list of Contestant object to processed the Data.
     * 
     * @param list Contestant List to bre processed
     * @see Contestant
     */
    TeamList(List<Contestant> list){
        contestantList = list;
        teamList = new ArrayList<Team>();
        generateTeams();
    }
    
    /**
     * Generate teams based on the Performance of all Contestant.
     * Then store it in a List.
     * 
     * @see Contestant
     */
    void generateTeams(){
        contestantList.sort( new Comparator<Contestant>() {
            public int compare(Contestant ob1, Contestant ob2) {
		if( ob1.totalSolved == ob2.totalSolved )
                    return (ob1.totalTimePenalty-ob2.totalTimePenalty);
                return (ob2.totalSolved - ob1.totalSolved);
            }
        });
        Team team = new Team();
        for( int i = 0; i<contestantList.size(); i++ ){
            team.addTeamMember( new Student(contestantList.get(i).name, contestantList.get(i).regNo) );
            if( i%3 == 2 ){
                teamList.add(team);
                team = new Team();
            }
        }
        if( team.teamMember.size()>0 )
            teamList.add(team);
    }
    
    /**
     * Send the Team List
     * 
     * @return a list which contains all team data
     */
    List<Team> getTeamList(){
        return teamList;
    }
    
    /*
    *   This Method will be used for Debugging
    */
    void print(){
        for( int i = 0; i<teamList.size(); i++ ){
            Team tt = teamList.get(i);
            System.out.println( tt.teamMember.get(0).name + " " + tt.teamMember.get(1).name + " " + tt.teamMember.get(2).name );
        }
    }
    
}
