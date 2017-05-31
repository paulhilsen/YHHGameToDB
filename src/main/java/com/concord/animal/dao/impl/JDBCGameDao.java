package com.concord.animal.dao.impl;


import com.concord.animal.model.Game;
import com.concord.animal.model.Team;
import com.concord.animal.model.Team_In_Classification_Season;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by paul.hilsen on 8/30/2016.
 */
public class JDBCGameDao {


    //Values hardcoded in for quickness:
////////////////////////////////////////////////
    private int partitionID_HARDCODE = 1;
    private int seasonID_HARDCODE = 1;
    private String defaultSex = "Boys";
//////////////////////////////////////////////////


    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcTemplate simpleJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsertGame;

    public void setJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
        this.simpleJdbcInsertGame = new SimpleJdbcInsert(dataSource).withTableName("game").
                withSchemaName("NowRankings").usingGeneratedKeyColumns("game_id");
    }


//////////////////////////////////
//METHODS...
///////////////


    //********************************************************************************************************************
    //*****Send partition ID and gameID --->Return team 1 ID , team 2 ID, and score******************
    public Game getGame(int partition_id, int gameId) {
        String sql = "Select team1_id, team1_score, team2_id, team2_score,game_played_date FROM nrtstdb.game"
                + "where (partition_id,game_id) = (?,?);";
        // WHY DOESN'T THIS WORK FOR THE FIRST ?: (select partition_id from now_partition where partition_name='MN')

        Game result = (Game) jdbcTemplate.queryForObject(sql, new Object[]{partition_id, gameId}, new GameRowMapper());
        return result;

    }

    // ****Mapper for getGame() Method getting information from the database*****
    public class GameRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Game game = new Game();
            game.setTeamAID(rs.getInt("team1_id"));
            game.setTeam1_score(rs.getInt("team1_score"));
            game.setTeamBID(rs.getInt("team2_id"));
            game.setTeam2_score(rs.getInt("team2_score"));
            game.setGame_played_date(rs.getString("game_played_date"));
            return game;
        }
    }
    //*****************************************************************************************************************


    //***************************************************************************************
    //Insert a single game by passing in every single parameter

    public void insertGame(int partition_id, int season_id, int classification_id, int teamAID, int team1_score, char team1_result_code,
                           int teamBID, int team2_score, char team2_result_code, String game_played_date, String entered_by, String weighting_factor) {

        String sql = "INSERT INTO nrtstdb.game " +
                "(partition_id, season_id, classification_id, team1_id, team1_score, team1_result_code," +
                "team2_id,team2_score, team2_result_code, game_played_date, entered_by, weighting_factor_code)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        //define query arguments
        Object[] params = {partition_id, season_id, classification_id, teamAID, team1_score, team1_result_code,
                teamBID, team2_score, team2_result_code, game_played_date, entered_by, weighting_factor};

        //define SQL types of the arguements
        int[] types = {Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.CHAR, Types.INTEGER, Types.INTEGER,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};

        jdbcTemplate.update(sql, params, types);
        System.out.println("Method used: insertGame. Use insertGame(Object) if a last inserted id is desired");

    }


    //**************************************************************************************************************************
    // Insert a single game by passing in the Game object
    public Number insertGame(Game game) {

        String sql = "INSERT INTO nrtstdb.game " +
                "(partition_id, season_id, classification_id, team1_id, team1_score, team1_result_code," +
                "team2_id,team2_score, team2_result_code, game_played_date, entered_by, weighting_factor_code)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //*******Commented out section below executes the sql code WITHOUT returning the last inserted id************
//        Object[] params = {game.getPartition_id(),game.getSeason_id(), game.getClassification_id(),  game.getTeamAID(), game.getTeam1_score(), game.getTeam1_result_code(),
//                game.getTeamBID(), game.getTeam2_score(), game.getTeam2_result_code(), game.getGame_played_date(), game.getEntered_by(),game.getWeighting_factor()};
//        int[] types = {Types.INTEGER,Types.INTEGER,Types.INTEGER,Types.INTEGER,Types.INTEGER,Types.CHAR,Types.INTEGER,Types.INTEGER,
//                Types.CHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
//        jdbcTemplate.update(sql, params, types);   // one way to execute the sql code, but without the id being returned
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        //******The following code uses SIMPLEJDBC to execute the sql *AND* return last inserted ID********
        //map parameters in order to use simplejdbe
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("partition_id", game.getPartition_id());
        parameters.put("season_id", game.getSeason_id());
        parameters.put("classification_id", game.getClassification_id());
        parameters.put("team1_id", game.getTeamAID());
        parameters.put("team1_score", game.getTeam1_score());
        parameters.put("team1_result_code", game.getTeam1_result_code());
        parameters.put("team2_id", game.getTeamBID());
        parameters.put("team2_score", game.getTeam2_score());
        parameters.put("team2_result_code", game.getTeam2_result_code());
        parameters.put("game_played_date", game.getGame_played_date());
        parameters.put("entered_by", game.getEntered_by());
        parameters.put("weighting_factor_code", game.getWeighting_factor());

        //execute sql code
        Number newId = simpleJdbcInsertGame.usingColumns("partition_id", "season_id", "classification_id", "team1_id",
                "team1_score", "team1_result_code", "team2_id", "team2_score", "team2_result_code", "game_played_date", "entered_by",
                "weighting_factor_code").executeAndReturnKey(parameters);


        System.out.println("Method used: InsertGame with simpleJDBC");
        return newId;
    }
    //*****************************************************************************************************************************************


    //*****************************************************************************************************************************************
    //*********batch insert***********
    public void insertBatchBAD_butWorking(final List<Game> games) {

        String sql = "INSERT INTO nrtstdb.game " +
                "(partition_id, season_id, classification_id, team1_id, team1_score, team1_result_code," +
                "team2_id,team2_score, team2_result_code, game_played_date, entered_by, weighting_factor_code)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Game game = games.get(i);
                ps.setInt(1, game.getPartition_id());
                ps.setInt(2, game.getSeason_id());
                ps.setInt(3, game.getClassification_id());
                ps.setInt(4, game.getTeamAID());
                ps.setInt(5, game.getTeam1_score());
                ps.setString(6, game.getTeam1_result_code());  //should be char/string ********
                ps.setInt(7, game.getTeamBID());
                ps.setInt(8, game.getTeam2_score());
                ps.setString(9, game.getTeam2_result_code());    //should be char or String ***********
                ps.setString(10, game.getGame_played_date());
                ps.setString(11, game.getEntered_by());
                ps.setString(12, game.getWeighting_factor());
            }

            @Override
            public int getBatchSize() {
                return games.size();
            }
        });
    }
    //*************************************************************************************************************************************


    //*************************************************************************************************************************************
    //*****Single Query see if game exist: Flips Checks if a record exist, then flips team1 and team2 to see if that record exist******************
    public int checkifExist(Game game) {


        String sql = "SELECT * FROM nrtstdb.game where game.partition_id = ? and game.team1_id = ? and game.team1_score = ? and game.team2_id = ? and game.team2_score = ? and game.game_played_date = ?"
                + " Union " +
                "SELECT * FROM nrtstdb.game where game.partition_id = ? and game.team1_id = ? and game.team1_score = ? and game.team2_id = ? and game.team2_score = ? and game.game_played_date = ?";

        // WHY DOESN'T THIS WORK FOR THE FIRST ?: (select partition_id from now_partition where partition_name='MN')


        Object[] params = {game.getPartition_id(), game.getTeamAID(), game.getTeam1_score(), game.getTeamBID(), game.getTeam2_score(),
                game.getGame_played_date(), game.getPartition_id(),
                game.getTeamAID(), game.getTeam2_score(), game.getTeamAID(),
                game.getTeam1_score(), game.getGame_played_date()};


        List<Game> result = jdbcTemplate.query(sql, params, new GameCheckIfExistRowMapper());

        //System.out.print("    --> " + result.size() + " instances of that game in the DB");

        return result.size();
    }


    // ****Mapper for getGame() Method getting information from the database*****
    public class GameCheckIfExistRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Game game = new Game();
            game.setPartition_id(rs.getInt("partition_id"));
            game.setTeamAID(rs.getInt("team1_id"));
            game.setTeam1_score(rs.getInt("team1_score"));
            game.setTeamBID(rs.getInt("team2_id"));
            game.setTeam2_score(rs.getInt("team2_score"));
            game.setGame_played_date(rs.getString("game_played_date"));
            return game;
        }
    }


    //**************************************************************************************************************************
    // Insert a single Team by passing in the Game object
    public void insertTeam(Team team, Game g) {

        // if these values were not set then give some default values
        if (team.getPartition_id() == 0) {
            team.setPartition_id(partitionID_HARDCODE);
        }

        //Assuming if I need to insert a team it is a placebo team
        team.setSex(g.getSex());
        team.setAge(g.getClassification_age());
        team.setLevel(g.getClassification_level());
        team.setTeam_league_name("Placebo");
        team.setTeam_division_name("Placebo");

        if(team.getSex() == null || team.getSex().isEmpty()) {
            team.setSex(defaultSex);
        }

        String sql = "INSERT INTO nrtstdb.team " +
                "(partition_id, team_name, team_league_name, team_division_name, sex, age, level) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Object[] params = {team.getPartition_id(), team.getTeam_name(), team.getTeam_league_name(),team.getTeam_division_name(),team.getSex(), team.getAge(), team.getLevel()};
        int[] types = {Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.VARCHAR};

        jdbcTemplate.update(sql, params, types);   // one way to execute the sql code, but without the id being returned
        System.out.println("Method used: insertTeam");
    }
    //*********************************************************************************************************************************

    //**************************************************************************************************************************
    // Insert a single Team by passing in the Game object
    public void insert2Team_in_Classification(int teamID, Game g) {

        //Set Values to insert to DB

        Team_In_Classification_Season teamClass = new Team_In_Classification_Season();
        teamClass.setPartition_id(g.getPartition_id());
        teamClass.setSeason_id(g.getSeason_id());
        teamClass.setClassification_id(g.getClassification_id());
        //Assume if a team is being added they are unrankable
        teamClass.setIs_unrankable("Y");



        // if these values were not set then give some default values
        if (teamClass.getPartition_id() == 0) {
            teamClass.setPartition_id(partitionID_HARDCODE);
        }
        if (teamClass.getSeason_id() == 0) {
            teamClass.setSeason_id(seasonID_HARDCODE);
        }



        String sql = "INSERT INTO nrtstdb.team_in_classification_season " +
                "(partition_id, classification_id, season_id, team_id, is_unrankable) VALUES (?, ?, ?, ?, ?)";

        Object[] params = {teamClass.getPartition_id(), teamClass.getClassification_id(), teamClass.getSeason_id(), teamID, teamClass.getIs_unrankable()};
        int[] types = {Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR};

        jdbcTemplate.update(sql, params, types);
        System.out.println("Method used: insert2Team_in_Classification()");
    }
    //*********************************************************************************************************************************

    public Integer getClassificationIDByClassificationName(String classificationName, String sex, String age, String level) {
        //will need to check this with two paramters....classification+sex...its just classification now
        String sqlTeam = "select (classification_id) from nrtstdb.classification where (partition_id, sex, age, level) = (?,?,?,?)";

        Object[] params = {partitionID_HARDCODE, sex, age, level};
        List<Integer> classifID = jdbcTemplate.query(sqlTeam, params, new SelectClassificationIdMapper());


        if (classifID.isEmpty() || classifID == null) {
            System.out.println("No such classification name: --> " + classificationName + " <-- error in getClassificationIDByClassifcationName");
            return null;
        }

        return classifID.get(0);
    }

    // ****Mapper for getGame() Method getting information from the database*****
    private class SelectClassificationIdMapper implements RowMapper {

        @Override
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getInt("classification_id");
        }
    }

    //*******************************************************************************************************************************


    //******************************************************************************************************************************************

    public List<Integer> teamIDFromClassification(int teamID, Game g) {

        String sqlTeam = "SELECT count(*) FROM nrtstdb.team_in_classification_season WHERE (partition_id,team_id,season_id)" +
                " = (?, ?,?)";

        Object[] params = {g.getPartition_id(), teamID, g.getSeason_id()};
        List<Integer> countofID = jdbcTemplate.query(sqlTeam, params, new SelectTeamIdMapperFromTeam_in_Class());

        return countofID;

    }

    // ****Mapper for getGame() Method getting information from the database*****
    private class SelectTeamIdMapperFromTeam_in_Class implements RowMapper {

        @Override
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getInt("count(*)");
        }
    }
        //*****************************************************************************************************************


    //**********************************************************************************************************
     private Integer getTeamIDByTeamName(String teamName, Game g) {

        int partition_id = g.getPartition_id();
         String sex = g.getSex();
         String age = g.getClassification_age();
         String level = g.getClassification_level();

        String sqlTeam = "select team_id from nrtstdb.team where (partition_id,team_name,sex,age,level)" +
                " = (?,?,?,?,?)";

        Object[] params = {partition_id, teamName,sex,age,level};
        List<Integer> teamID = jdbcTemplate.query(sqlTeam, params, new SelectTeamIdMapper());

        System.out.println("Team name: " + teamName + " | TeamID = " + teamID.get(0));
        return teamID.get(0);
    }
    //**********************************************************************************************************************





        //*****************************************************************************************************************

        private Integer getTeamIDByTeamNameAlsoInsert2TeamInClass(String teamName, Game g) {

            int partition_id = g.getPartition_id();
            String classAge = g.getClassification_age();
            String classLevel = g.getClassification_level();
            String sex = g.getSex();

            String sqlTeam = "select team_id from nrtstdb.team where (partition_id,team_name, age, level, sex)" +
                    " = (?, ?, ?, ?,?)";

            Object[] params = {partition_id, teamName, classAge, classLevel,sex};
            List<Integer> teamID = jdbcTemplate.query(sqlTeam, params, new SelectTeamIdMapper());


            if (teamID.isEmpty() || teamID == null || teamID.get(0).equals(0)) {
                // Add the team to team table and the team_in_classification_season table
                //But first eliminte the classLevel and search to see if the team can be found in the Class age
                List<Integer> teamID1 = getTeamIDby_TeamName_Age_only(partition_id, teamName ,classAge, sex);
                if (teamID1.size() == 1) {
                    return teamID1.get(0);
                }
                else if (teamID1.isEmpty() || teamID1.get(0).equals(0) || teamID1 == null) {
                    Team team = new Team(teamName);
                    insertTeam(team, g);
                    int teamID2 = getTeamIDByTeamName(teamName, g);
                    insert2Team_in_Classification(teamID2, g);
                    return teamID2;
                }
                else {
                    System.out.println("Error with team " + teamName + " more than one instance found in database");
                    return null;
                }
            }
            else if(teamID.size() > 1 ) {
                System.out.println("Error with team " + teamName + "m ore than one instance found in database");
                return null;
            }
            else{
                //See if team is in team_in_classification_season table if they are already in team table
                List<Integer> teamIDFromClassSeasTable = teamIDFromClassification(teamID.get(0), g);
                    if (teamIDFromClassSeasTable.isEmpty() || teamIDFromClassSeasTable.get(0).equals(0))
                        {insert2Team_in_Classification(teamID.get(0),g);}
            }

            System.out.println("Team name: " + teamName + " | TeamID = " + teamID.get(0) + " Classification: " + g.getClassification_name());
            return teamID.get(0);
        }

        // ****Mapper for getGame() Method getting information from the database*****
        private class SelectTeamIdMapper implements RowMapper {

            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("team_id");
            }
        }
        //******************************************************************************************************************************************

        private List<Integer> getTeamIDby_TeamName_Age_only(int partition_id, String teamName, String classAge,String sex ) {

            String sqlTeam = "select team_id from nrtstdb.team where (partition_id,team_name, age,sex)" +
                    " = (?, ?, ?,?)";


            Object[] params = {partition_id, teamName, classAge, sex};
            List<Integer> teamID = jdbcTemplate.query(sqlTeam, params, new SelectTeamIdMapper());

            return teamID;


        }



        //*****************************************************************************************************************************************
        //*********batch insert***********
        public void insertBatch(final List<Game> games) {
            int count = 1;

            for (Game g : games) {

                System.out.println("Processing game: " + count + "/" + games.size());
                count ++;

                //****************Check this for new classifications --> Info is hardcoded in ****************
                //////////////////////////////////////////////////////////////
                // Get Partition ID...cheating and putting in 1 for now
                //Get season_id ... cheating and putting in 1 for now
                g.setPartition_id(partitionID_HARDCODE );
                g.setSeason_id(seasonID_HARDCODE);
                g.setEntered_by("DrLoveBucket88@aol.com");
                //////////////////////////////////////////////////////////////////////

                //Get Classification ID
                g.setClassification_id(getClassificationIDByClassificationName(g.getClassification_name(),g.getSex(),g.getClassification_age(),g.getClassification_level()));


                //Add a W or L to the result code for win or lose
                String Team1LW;
                String Team2LW;
                if (g.getTeam1_score() > g.getTeam2_score()) {
                    Team1LW = "W";
                    Team2LW = "L";
                } else if (g.getTeam1_score() < g.getTeam2_score()) {
                    Team1LW = "L";
                    Team2LW = "W";
                } else {
                    Team1LW = "T";
                    Team2LW = "T";
                }

                //Set Result code, check if there is "OT" or "SO" to add the "W" or "L" to.
                g.setTeam1_result_code(g.getTeam1_result_code().trim().length() == 0? Team1LW : Team1LW + g.getTeam1_result_code());
                g.setTeam2_result_code(g.getTeam2_result_code().trim().length() == 0? Team2LW : Team2LW + g.getTeam2_result_code());

                //Get TeamID's --> add if not in team table and/or add info to team_in_classification_season table
                g.setTeamAID(getTeamIDByTeamNameAlsoInsert2TeamInClass(g.getTeamA(), g));
                g.setTeamBID(getTeamIDByTeamNameAlsoInsert2TeamInClass(g.getTeamB(), g));



            }

            System.out.println("Batch Inserting Games....");

            //Check if game is already in database if so remove it from the Game object:
            for (int i = 0; i < games.size(); i++) {
                int checkSize = checkifExist(games.get(i));
                if ( checkSize != 0) {

                    System.out.println("    --> Game already in the database --> " + games.get(i));
                    games.remove(i);
                    i--; // removing one game decreases the size of games.size() -> counter by doing i-- to get all;
                }
           }


            String sql = "INSERT INTO nrtstdb.game " +
                    "(partition_id, season_id, classification_id, team1_id, team1_score, team1_result_code," +
                    "team2_id,team2_score, team2_result_code, game_played_date, entered_by, weighting_factor_code)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Game game = games.get(i);
                    ps.setInt(1, game.getPartition_id());
                    ps.setInt(2, game.getSeason_id());
                    ps.setInt(3, game.getClassification_id());
                    ps.setInt(4, game.getTeamAID());
                    ps.setInt(5, game.getTeam1_score());
                    ps.setString(6, game.getTeam1_result_code());  //should be char/string ********
                    ps.setInt(7, game.getTeamBID());
                    ps.setInt(8, game.getTeam2_score());
                    ps.setString(9, game.getTeam2_result_code());    //should be char or String ***********
                    ps.setString(10, game.getGame_played_date());
                    ps.setString(11, game.getEntered_by());
                    ps.setString(12, game.getWeighting_factor());
                }

                @Override
                public int getBatchSize() {
                    return games.size();
                }
            });

            System.out.println("Completed batchInsert");
            System.out.println("Inserted " + games.size() + " games into the database");
        }


        //*********************************************************************************************************************************************************


}