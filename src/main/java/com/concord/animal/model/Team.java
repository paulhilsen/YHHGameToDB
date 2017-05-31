package com.concord.animal.model;

/**
 * Created by paul.hilsen on 9/6/2016.
 */
public class Team {

    int partition_id;
    String team_name;
    String team_league_name;
    String team_division_name;
    String team_website;
    String team_city;
    String team_state;
    String is_unrankable;
    String sex;
    String age;
    String level;




    //Getter and Setter
    public int getPartition_id() {return partition_id;}
    public void setPartition_id(int partition_id) {this.partition_id = partition_id;}
    public String getTeam_name() {return team_name;}
    public void setTeam_name(String team_name) {this.team_name = team_name;}
    public String getTeam_league_name() {return team_league_name;}
    public void setTeam_league_name(String team_league_name) {this.team_league_name = team_league_name;}
    public String getIs_unrankable() {return is_unrankable;}
    public void setIs_unrankable(String is_unrankable) {this.is_unrankable = is_unrankable;}
    public String getTeam_division_name() {return team_division_name;}
    public void setTeam_division_name(String team_division_name) {this.team_division_name = team_division_name;}
    public String getTeam_website() {return team_website;}
    public void setTeam_website(String team_website) {this.team_website = team_website;}
    public String getTeam_city() {return team_city;}
    public void setTeam_city(String team_city) {this.team_city = team_city;}
    public String getTeam_state() {return team_state;}
    public void setTeam_state(String team_state) {this.team_state = team_state;}
    public String getSex() {return sex;}
    public void setSex(String sex) {this.sex = sex;}
    public String getAge() {return age;}
    public void setAge(String age) {this.age = age;}
    public String getLevel() {return level;}
    public void setLevel(String level) {this.level = level;}

    //Constructors
    public Team() {}

    public Team(String team_name) {
        this.team_name = team_name;
    }

    public Team(String team_name, String team_league_name) {
        this.team_name = team_name;
        this.team_league_name = team_league_name;
    }

    public Team(int partition_id, String team_name, String team_league_name, String is_unrankable) {
        this.partition_id = partition_id;
        this.team_name = team_name;
        this.team_league_name = team_league_name;
        this.is_unrankable = is_unrankable;
    }









}
