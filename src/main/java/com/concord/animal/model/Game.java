package com.concord.animal.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by paul.hilsen on 8/29/2016.
 */
public class Game {

    //Variables
    private String teamA, teamB;
    private int partition_id, season_id, classification_id, teamAID, teamBID, team1_score, team2_score;
    private String game_played_date;
    private String team1_result_code;
    private String team2_result_code;
    private String entered_by;
    private String weighting_factor;
    private String division_name;
    private String classification_name;
    private String classification_age;
    private String classification_level;
    private String sex;


    //Constructors
    public Game() {}

    public Game(int partition_id, int season_id, int classification_id, int teamAID, int team1_score, String team1_result_code,
                int teamBID, int team2_score, String team2_result_code, String game_played_date, String entered_by, String weighting_factor) {

        this.partition_id = partition_id;
        this.season_id = season_id;
        this.classification_id = classification_id;
        this.teamAID = teamAID;
        this.team1_score = team1_score;
        this.team1_result_code = team1_result_code;
        this.teamBID = teamBID;
        this.team2_score = team2_score;
        this.team2_result_code = team2_result_code;
        this.game_played_date = game_played_date;
        this.entered_by = entered_by;
        this.weighting_factor = weighting_factor;
    }


    public Game(String teamA, String teamB, int team1_score, int team2_score, String game_played_date) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.team1_score = team1_score;
        this.team2_score = team2_score;
        this.game_played_date = game_played_date;
    }

    public Game(int teamAID, int teamBID, int team1_score, int team2_score, String game_played_date) {
        this.teamAID = teamAID;
        this.teamBID = teamBID;
        this.team1_score = team1_score;
        this.team2_score = team2_score;
        this.game_played_date = game_played_date;
    }


    //Getters and Setters
    public String getTeamA() {return teamA;}
    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }
    public String getTeamB() {return teamB;}
    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }
    public int getTeam1_score() {
        return team1_score;
    }
    public void setTeam1_score(int team1_score) {
        this.team1_score = team1_score;
    }
    public int getTeam2_score() {
        return team2_score;
    }
    public void setTeam2_score(int team2_score) {
        this.team2_score = team2_score;
    }
    public String getGame_played_date() {
        return game_played_date;
    }
    public void setGame_played_date(String game_played_date) {
        this.game_played_date = game_played_date;
    }
    public int getTeamAID() {
        return teamAID;
    }
    public void setTeamAID(int teamAID) {
        this.teamAID = teamAID;
    }
    public int getTeamBID() {
        return teamBID;
    }
    public void setTeamBID(int teamBID) {
        this.teamBID = teamBID;
    }
    public int getPartition_id() {return partition_id;}
    public void setPartition_id(int partition_id) {
        this.partition_id = partition_id;
    }
    public int getSeason_id() {
        return season_id;
    }
    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }
    public int getClassification_id() {
        return classification_id;
    }
    public void setClassification_id(int classification_id) {
        this.classification_id = classification_id;
    }
    public String getTeam1_result_code() {
        return team1_result_code;
    }
    public void setTeam1_result_code(String team1_result_code) {
        this.team1_result_code = team1_result_code;
    }
    public String getTeam2_result_code() {
        return team2_result_code;
    }
    public void setTeam2_result_code(String team2_result_code) {
        this.team2_result_code = team2_result_code;
    }
    public String getEntered_by() {
        return entered_by;
    }
    public void setEntered_by(String entered_by) {
        this.entered_by = entered_by;
    }
    public String getWeighting_factor() {
        return weighting_factor;
    }
    public void setWeighting_factor(String weighting_factor) {
        this.weighting_factor = weighting_factor;
    }
    public void setDivision_name(String Division_name) {
        this.division_name = division_name;
    }
    public String getDivision_name() {
        return division_name;
    }
    public String getClassification_name() {return classification_name;}
    public void setClassification_name(String classification_name) {
        this.classification_name = classification_name;
    }
    public String getClassification_age() {return classification_age;}
    public void setClassification_age(String classification_age) {this.classification_age = classification_age;}
    public String getClassification_level() {return classification_level;}
    public void setClassification_level(String classification_level) {this.classification_level = classification_level;}
    public String getSex() {return sex;}
    public void setSex(String sex) {this.sex = sex; }




    //************************************************
    //Make printing out pretty/actually make it work
    //
    @Override
    public String toString() {

        if (teamA != null) {
            return "Game{" + "TeamA: " + teamA + " | " + " A_Score: " + team1_score +
                    " | " + "TeamB: " + teamB + " | " + "B_Score: " + team2_score + " | " +
                    "Date: " + game_played_date + "}";
        } else {
            return "Game{" + "TeamA ID: " + teamAID + " | " + " A_Score: " + team1_score +
                    " | " + "TeamB ID: " + teamBID + " | " + "B_Score: " + team2_score + " | " +
                    "Date: " + game_played_date + "}";

        }


    }

    //**********************************************************************************************************************

    //*************************************************************************************************************************************************

    public Integer getKeyFromValue(String team) {

        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "Minnetonka Skippers AA");
        map.put(2, "Edina Hornets AA");
        map.put(3, "Blaine Bengals AA");
        map.put(4, "Cloquet Lumberjacks AA");
        map.put(5, "Wayzata Trojans AA");
        map.put(6, "Moorhead Spuds AA");
        map.put(7, "Eden Prairie Eagles AA");
        map.put(8, "Andover Huskies AA");
        map.put(9, "Brainerd Warriors AA");
        map.put(10, "Sibley Warriors AA");
        map.put(11, "Osseo Maple Grove AA");
        map.put(12, "White Bear Lake Bears AA");
        map.put(13, "Rochester (Red) AA");
        map.put(14, "Prior Lake Savage Lakers AA");
        map.put(15, "Mahtomedi Zephyrs AA");
        map.put(16, "Elk River Elks AA");
        map.put(17, "Hermantown Hawks AA");
        map.put(18, "Stillwater Stallions AA");
        map.put(19, "Rosemount Irish AA");
        map.put(20, "Minneapolis Storm AA");
        map.put(21, "Eagan Wildcats AA");
        map.put(22, "Lakeville North AA");
        map.put(23, "Roseau Rams AA");
        map.put(24, "St Michael-Albertville Knights AA");
        map.put(25, "Lakeville South AA");
        map.put(26, "Buffalo Bison AA");
        map.put(27, "Duluth Lakers AA");
        map.put(28, "Chaska Chanhassen AA");
        map.put(29, "Eastview Lightning AA");
        map.put(30, "Centennial Cougars AA");
        map.put(31, "St Paul Capitals AA");
        map.put(32, "Bloomington Jefferson Jaguars AA");
        map.put(33, "St Cloud AA");
        map.put(34, "Hastings Raiders AA");
        map.put(35, "Woodbury Predators AA");
        map.put(36, "Rogers Royals AA");
        map.put(37, "Roseville Raiders AA");
        map.put(38, "Bemidji Lumberjacks AA");
        map.put(39, "Tartan Titans AA");
        map.put(40, "St Louis Park Orioles AA");
        map.put(41, "Forest Lake Rangers AA");
        map.put(42, "Champlin Park Rebels AA");
        map.put(43, "Grand Rapids Thunderhawks AA");
        map.put(44, "Milwaukee Jr Admirals 01 AAA");
        map.put(45, "Jersey Hitmen 01 AAA");
        map.put(46, "Hamilton Jr Bulldogs 01 AAA");
        map.put(47, "Colorado Thunderbirds 01 AAA");
        map.put(48, "Compuware 01 AAA");
        map.put(49, "Grand Forks Aviators AA");
        map.put(50, "Lakeville South A");
        map.put(51, "Elk River Elks A");
        map.put(52, "Rochester (Red) A");
        map.put(53, "Alexandria Cardinals A");
        map.put(54, "Apple Valley Eagles A");
        map.put(55, "Roseau Rams A");
        map.put(56, "Sibley Warriors A");
        map.put(57, "Osseo Maple Grove Crimson A");
        map.put(58, "Rosemount Irish A");
        map.put(59, "Woodbury Predators A");
        map.put(60, "Edina Hornets (White)A	");
        map.put(61, "Mahtomedi Zephyrs A");
        map.put(62, "Andover Huskies A");
        map.put(63, "Chaska Chanhassen A");
        map.put(64, "Moorhead Spuds A");
        map.put(65, "Lakeville South A");
        map.put(66, "Edina Hornets (Green) A");
        map.put(67, "Hermantown Hawks A");
        map.put(68, "Stillwater Ponies A");
        map.put(69, "White Bear Lake Bears A");
        map.put(70, "Minnetonka Skippers A");
        map.put(71, "Champlin Park Rebels A");
        map.put(72, "St Michael-Albertville Knights A");
        map.put(73, "Grand Rapids Thunderhawks A");
        map.put(74, "Eastview Lightning A");
        map.put(75, "Orono Spartans A");
        map.put(76, "Coon Rapids Cardinals A");
        map.put(77, "Eden Prairie Eagles A");
        map.put(78, "Warroad Warriors A");
        map.put(79, "Prior Lake-Savage Lakers A");
        map.put(80, "Wayzata Trojans A");
        map.put(81, "Lakeville North A");
        map.put(82, "Rogers Royals A");
        map.put(83, "Farmington Tigers A");
        map.put(84, "Fergus Falls Otters A");
        map.put(85, "Buffalo Bison A");
        map.put(86, "Burnsville Blaze A");
        map.put(87, "Mound Westonka White Hawks A");
        map.put(88, "Shakopee Sabres A");
        map.put(89, "St Cloud Huskies A");
        map.put(90, "Blaine Bengals A");
        map.put(91, "Minneapolis Storm A");
        map.put(92, "Tartan Titans A");
        map.put(93, "Centennial CougarsA	");
        map.put(94, "Delano Tigers A");
        map.put(95, "Sartell Sabres A");
        map.put(96, "Bloomington Jefferson Jaguars A");
        map.put(97, "Cottage Grove Wolfpack A");
        map.put(98, "Monticello (MAML) Moose A");
        map.put(99, "Bemidji Lumberjacks A");
        map.put(100, "Johnson Como NSP A");
        map.put(101, "Luverne Cardinals A");
        map.put(102, "Hastings Raiders A");
        map.put(103, "Mounds View Irondale A");
        map.put(104, "Brainerd Warriors A");
        map.put(105, "Roseville Raiders A");
        map.put(106, "Thief River Falls A");
        map.put(107, "New Prague Trojans A");
        map.put(108, "Hutchinson Tigers A");
        map.put(109, "East Grand Forks A");
        map.put(110, "Anoka Tornadoes A");
        map.put(111, "Forest Lake Rangers A");
        map.put(112, "Litchfield/Dassel-Cokato A");
        map.put(113, "Rum River Stars A");
        map.put(114, "St Paul Capitals A");
        map.put(115, "Mankato Mavericks A");
        map.put(116, "Albert Lea Tigers A");
        map.put(117, "Eagan Wildcats A");
        map.put(118, "Proctor Rails A");
        map.put(119, "Northern Lakes Lightning A");
        map.put(120, "River Lakes Stars A");
        map.put(121, "Detroit Lakes Lakers A");
        map.put(122, "Bloomington Kennedy / Richfield A");
        map.put(123, "Waconia Wildcats A");
        map.put(124, "Hopkins Royals A");
        map.put(125, "Little Falls Flyers A");


        for (int i = 1; i <= map.size(); i++) {
            if (map.get(i).equals(team)) {
                return i;
            }
        }
        return 500; // if team isn't found??? Gotta find a better way...
    }



    //********************************************************************************************************************
    //Change date to be in the correct format for inputting into the database (MM/dd/YYYY)
    public void parseDate(List<Game> games) {

        for (Game g : games) {
            String startDateString = g.getGame_played_date();
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            Date startDate;
            try {
                startDate = df.parse(startDateString);
                String newDateString = df.format(startDate);
                String newDate2 = parseDateVer2(newDateString);
                g.setGame_played_date(newDate2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }


    public String parseDateVer2(String date1) {

        String dateReceivedFromUser = date1;
        DateFormat userDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = userDateFormat.parse(dateReceivedFromUser);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String convertedDate = dateFormatNeeded.format(date);
        return convertedDate;


    }

    //*********************************************************************************************************************
    public void date2WeightingFactor(List<Game> games) {

        for(Game game: games) {

            String month = game.getGame_played_date().substring(5, 7);

            if (Objects.equals(month,"08")) {
                game.setWeighting_factor("AUG");
            }
            else if (Objects.equals(month,"09")) {
                game.setWeighting_factor("SEP");
            }
            else if (Objects.equals(month,"10")) {
                game.setWeighting_factor("OCT");
            }
            else if (Objects.equals(month,"11")) {
                game.setWeighting_factor("NOV");
            }
            else if (Objects.equals(month,"12")) {
                game.setWeighting_factor("DEC");
            }
            else if (Objects.equals(month,"01")) {
                game.setWeighting_factor("JAN");
            }
            else if (Objects.equals(month,"02")) {
                game.setWeighting_factor("FEB");
            }
            else if (Objects.equals(month,"03")) {
                game.setWeighting_factor("MAR");
            }
            else if (Objects.equals(month,"04")) {
                game.setWeighting_factor("APR");
            }
            else {
                System.out.println("Likely error --> " + game + " has month of: " + month + " which is not handled in method: game.date2WeightingFactor");

            }
        }

    }
}