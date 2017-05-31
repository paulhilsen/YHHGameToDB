package com.concord.animal.model;

/**
 * Created by paul.hilsen on 9/6/2016.
 */
public class Classification {
    int partition_id;
    String classification_name;
    String sex,age,level;




    //Getter and Setter
    public int getPartition_id() {
        return partition_id;
    }
    public void setPartition_id(int partition_id) {
        this.partition_id = partition_id;
    }
    public String getClassification_name() {
        return classification_name;
    }
    public void setClassification_name(String classification_name) {
        this.classification_name = classification_name;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }



    //Constructors
    public Classification() {}

    public Classification(String classification_name) {
        this.classification_name = classification_name;
    }

    public Classification(String classification_name, String sex, String age, String level) {
        this.classification_name = classification_name;
        this.sex = sex;
        this.age = age;
        this.level = level;
    }

    public Classification(int partition_id, String classification_name, String sex, String age, String level) {
        this.partition_id = partition_id;
        this.classification_name = classification_name;
        this.sex = sex;
        this.age = age;
        this.level = level;
    }





}
