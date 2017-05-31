package com.common;

import com.concord.animal.model.Game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by paul.hilsen on 8/29/2016.
 */
public class CSVReader {

    String csvFile = "C:\\Clients\\NowRankings\\Scripts\\DataPopulation\\ScrapedData\\CSV\\Combined Games\\AllPartition1Combined.csv"; //if using the cmd copy *.csv 1.csv to combine csv files remember to remove the [] that gets added to the last row
    BufferedReader br = null;
    String line = "";
    String cvsSplitBy = ",";


    public List<Game> readit() {
        List<Game> games = null;
        try {

            br = new BufferedReader(new FileReader(csvFile));

            games = new ArrayList<>();

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] gameinfo = line.split(cvsSplitBy);

                Game game = new Game();
                game.setClassification_name(gameinfo[0]);
                game.setTeamA(gameinfo[1]);
                game.setTeam1_score(Integer.parseInt(gameinfo[2]));
                game.setTeamB(gameinfo[3]);
                game.setTeam2_score(Integer.parseInt(gameinfo[4]));
                game.setTeam1_result_code(gameinfo[5]);   //will be empty if just a W or L --> only shows OT, SO
                game.setTeam2_result_code(gameinfo[5]);   //will be empty if just a W or L --> only shows OT, SO
                game.setGame_played_date(gameinfo[6]);
                game.setSex(gameinfo[7]);

                games.add(game);

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return games;
    }


    //******************************************************************************************************************


    //****************************************************************************************************************
    public void splitClassification(List<Game> games) {

        for(Game g: games) {
            String age;
            String level;
            String newClassName;

            String currentClassName = g.getClassification_name();

            switch (currentClassName) {
                case "Minnesota HS -  Class A": // this one has a girls too. But should be fine. Setting gender independently
                    age = "HS";
                    level = "A";
                    newClassName = "HS - Class A";
                    break;

                case "Minnesota HS -  Class AA":  // this one has a girls too. But should be fine. Setting gender independently
                    age = "HS";
                    level = "AA";
                    newClassName = "HS - Class AA";
                    break;

                case "Minnesota Girls 12U A" :
                    age = "12U";
                    level = "A";
                    newClassName = "Girls 12U A";
                    break;

                case "Minnesota Girls 15U A" :
                    age = "15U";
                    level = "A";
                    newClassName = "Girls 15U A";
                    break;

                case "USA Midget 16U Tier 1":
                    age = "16U";
                    level = "T1";
                    newClassName = "Midget 16U Tier 1";
                    break;

                default:
                    age = g.getClassification_name().split(" ")[1];
                    level = g.getClassification_name().split(" ")[2];
                    newClassName = age + " " + level;
                    break;
            }

                g.setClassification_age(age);
                g.setClassification_level(level);
                g.setClassification_name(newClassName);




        }

    }

    //*****************************************************************************************************************



    //*****************************************************************************************************************
    public List<Game> DeleteCSVGameDuplicates(List<Game> games) {
        List<Game> gameNoDups = games;  // do i need to do this? I do not want to alter "games" because mumble muble...

        for (int i = 0; i < games.size(); i++) {
            Game currentGame = games.get(i);

            for (int n = i + 1; n < games.size(); n++) {
                Game checkGame = games.get(n);

                if (currentGame.getClassification_name() == checkGame.getClassification_name() && currentGame.getTeamAID() == checkGame.getTeamAID() && currentGame.getTeamBID() == checkGame.getTeamBID()
                        && currentGame.getTeam1_score() == checkGame.getTeam1_score() && currentGame.getTeam2_score() == checkGame.getTeam2_score()
                        && currentGame.getGame_played_date() == checkGame.getGame_played_date()
                        || currentGame.getClassification_name() == checkGame.getClassification_name() && currentGame.getTeamAID() == checkGame.getTeamBID() && currentGame.getTeamBID() == checkGame.getTeamAID()
                        && currentGame.getTeam1_score() == checkGame.getTeam2_score() && currentGame.getTeam2_score() == checkGame.getTeam1_score()
                        && currentGame.getGame_played_date() == checkGame.getGame_played_date()) {
                    System.out.println("Removed Duplicate Game --> Team 1:" + checkGame.getTeamAID() +
                            "Team 2: " + checkGame.getTeamBID() + "Date: " + checkGame.getGame_played_date());

                    gameNoDups.remove(n);  // same as removing the checkGame
                }
            }

        }

        return gameNoDups;
    }

    //*************************************************************************************************************************************************

    //*****************************************************************************************************************
    public List<Game> DeleteCSVGameDuplicates_TeamNames(List<Game> games) {
        List<Game> gameNoDups = games;  // do i need to do this? I do not want to alter "games" because mumble muble...

        for (int i = 0; i < games.size(); i++) {
            Game currentGame = games.get(i);

            for (int n = i + 1; n < games.size(); n++) {
                Game checkGame = games.get(n);

                if ( (currentGame.getTeamA().equals(checkGame.getTeamA()) && currentGame.getTeamB().equals(checkGame.getTeamB())
                        && currentGame.getTeam1_score() == checkGame.getTeam1_score() && currentGame.getTeam2_score() == checkGame.getTeam2_score()
                        && currentGame.getGame_played_date().equals(checkGame.getGame_played_date())  )
                        || (currentGame.getTeamA().equals(checkGame.getTeamB()) && currentGame.getTeamB().equals(checkGame.getTeamA())
                        && currentGame.getTeam1_score() == checkGame.getTeam2_score() && currentGame.getTeam2_score() == checkGame.getTeam1_score()
                        && currentGame.getGame_played_date().equals(checkGame.getGame_played_date()) ))  {
//                    System.out.println("Removed Duplicate Game --> Team 1: " + checkGame.getTeamA() +
//                            " | Team 2: " + checkGame.getTeamB() + " |  Date: " + checkGame.getGame_played_date());

                    gameNoDups.remove(n);  // same as removing the checkGame
                }
            }

        }

        return gameNoDups;
    }

    //*************************************************************************************************************************************************


    //**************************************************************************************************************************************************
    private static final String CSV_SEPARATOR = ",";


    public static void writeIt(List<Game> games) {

        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("GamesNoDupesCSV.csv"), "UTF-8"));
            for (Game game : games)
            {

                //Add a W or L to the result code for win or lose
                String Team1LW;
                String Team2LW;
                if (game.getTeam1_score() > game.getTeam2_score())
                {Team1LW = "W";
                 Team2LW = "L";}
                else if(game.getTeam1_score() < game.getTeam2_score())
                {Team1LW = "L";
                 Team2LW = "W";}
                 else {Team1LW = "T";
                       Team2LW = "T";}


                StringBuffer oneLine = new StringBuffer();
                oneLine.append(game.getClassification_name());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(game.getTeamA());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(game.getTeam1_score());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(game.getTeam1_result_code().trim().length() == 0? Team1LW : Team1LW + game.getTeam1_result_code());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(game.getTeamB());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(game.getTeam2_score());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(game.getTeam2_result_code().trim().length() == 0? Team2LW : Team2LW + game.getTeam2_result_code());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(game.getGame_played_date());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(game.getSex());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(game.getClassification_age());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(game.getClassification_level());
                bw.write(oneLine.toString());
                bw.newLine();

            }
            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){}
        catch (IOException e){}
    }




//**********************************************************************************************************************************************
    public static void writeGameDBFormat(List<Game> games) {

        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("ALLGamesCSVDBFormat.csv"), "UTF-8"));
            for (Game game : games)
            {

                //Add a W or L to the result code for win or lose
                String Team1LW;
                String Team2LW;
                if (game.getTeam1_score() > game.getTeam2_score())
                {Team1LW = "W";
                    Team2LW = "L";}
                else if(game.getTeam1_score() < game.getTeam2_score())
                {Team1LW = "L";
                    Team2LW = "W";}
                else {Team1LW = "T";
                    Team2LW = "T";}

                //////////////////////////////////////////
                //DUMMY FACTORS FOR NOW
                game.setEntered_by("AllHailPaul@illumi.org");
                game.setWeighting_factor("OCT");
                ////////////////////////////////////

                StringBuffer oneLine = new StringBuffer();
                oneLine.append(game.getPartition_id());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(game.getSeason_id());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(game.getClassification_id());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(game.getTeamAID());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(game.getTeam1_score());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(game.getTeam1_result_code().trim().length() == 0? Team1LW : Team1LW + game.getTeam1_result_code());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(game.getTeamBID());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(game.getTeam2_score());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(game.getTeam2_result_code().trim().length() == 0? Team2LW : Team2LW + game.getTeam2_result_code());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(game.getGame_played_date());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(game.getEntered_by());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(game.getWeighting_factor());
                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){}
        catch (IOException e){}
    }

//**********************************************************************************************************************************************



}