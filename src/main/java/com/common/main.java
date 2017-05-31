package com.common;



import com.concord.animal.dao.impl.JDBCGameDao;
import com.concord.animal.model.Game;
import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by paul.hilsen on 8/22/2016.
 */


//////////////////////////////////////////////////////
    //Things to do:
    //1: Change file path of csv in CSVREADER
    //2. Change partition ID and season ID in JDBCGameDao


public class main {

    public static void main(String[] args) throws SQLException {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//       // dataSource.setUrl("jdbc:mysql://sandboxdbinstance.cmjsuzesdyzn.us-west-2.rds.amazonaws.com");  //sandbox db.
//        dataSource.setUrl("jdbc:mysql://nrdevinst.cim6dq27uyhc.us-west-2.rds.amazonaws.com");    // production db
//        dataSource.setUsername("nrdev");       //production db
//       // dataSource.setUsername("hockeydev");    // for use with NOW hockey for sandboxdb
//        dataSource.setPassword("pa55word");

        BasicDataSource connectionPool = new BasicDataSource();
        connectionPool.setDriverClassName("com.mysql.jdbc.Driver");

        // dataSource.setUrl("jdbc:mysql://sandboxdbinstance.cmjsuzesdyzn.us-west-2.rds.amazonaws.com");  //sandbox db.
        // dataSource.setUsername("hockeydev");    // for use with NOW hockey for sandboxdb
        // connectionPool.setPassword("pa55word"); // sandbox db

//        connectionPool.setUrl("jdbc:mysql://nrdevinst.cim6dq27uyhc.us-west-2.rds.amazonaws.com");    // production db
//        connectionPool.setUsername("nrdev");       //production db
//        connectionPool.setPassword("pa55word"); // production db

        connectionPool.setUrl("jdbc:mysql://qadb.nowsportsrankings.com"); //QA DB
        connectionPool.setUsername("root"); //QA
        connectionPool.setPassword("cmonpikachu"); // QA


        connectionPool.setInitialSize(1);




        JDBCGameDao gameDAO = new JDBCGameDao();
        gameDAO.setJdbcTemplate(connectionPool);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //*************************************************************************************************************************************
        //Make sure hardcode partition ID and Season ID are correct before running. Located in JDVCGameDAo
        //*************************************************************************************************************************************
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //************************************************************************************************************************************
        //Process from CSV--> 1. read in a csv (reader.readit(), 2. delete duplicates (reader.DeleteCSVGameduplicates(gamelist)),
        // 3. parse date (game3.parsedates(list<Game>); 4. date -> weighting factor 5. parse classification
        // 6. format everything for the game table and insert (insertBatch(list<Game>)

        CSVReader reader = new CSVReader();
        List<Game> gamelist = reader.readit();  // 1. Read CSV File // Change the file path in readit() of where to read

        int totalGames =  gamelist.size();  //count how many games there are
        List<Game> gameNoDupes = reader.DeleteCSVGameDuplicates_TeamNames(gamelist); // 2. Delete Duplicates

        System.out.println("BEFORE duplicate deletion size = " + totalGames);  //Quick sanity check
        System.out.println("AFTER duplicate deletion size = " + gameNoDupes.size()); //Quick sanity check

        Game game3 = new Game();
        game3.parseDate(gameNoDupes);  // 3. Parse date into the correct format for the game table
        game3.date2WeightingFactor(gameNoDupes);  // 4. Date -> weightingfactor
        reader.splitClassification(gameNoDupes); // 5. parse classification into separate age and level variables

        //reader.writeIt(gameNoDupes);


         gameDAO.insertBatch(gameNoDupes);  //6. Format everything else and insert
        // ******************************************************************************************************************


        System.out.println("DONE");




    }



}
