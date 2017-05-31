package com.concord.animal.model;

/**
 * Created by paul.hilsen on 9/8/2016.
 */
public class Team_In_Classification_Season {

    int partition_id;
    int classification_id;
    int season_id;
    int team_id;
    String is_unrankable;








    //Getter and Setter
    public int getPartition_id() {
        return partition_id;
    }

    public void setPartition_id(int partition_id) {
        this.partition_id = partition_id;
    }

    public int getClassification_id() {
        return classification_id;
    }

    public void setClassification_id(int classification_id) {
        this.classification_id = classification_id;
    }

    public int getSeason_id() {
        return season_id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getIs_unrankable() {
        return is_unrankable;
    }

    public void setIs_unrankable(String is_unrankable) {
        this.is_unrankable = is_unrankable;
    }





}
