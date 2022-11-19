package com.clagroup.cowhandlerv2;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
/*
This class handles the process of getting the user input from
CowEntry into the database
 */

public class NewCow {
    private String CowId;
    private String Description;
    private String Species;
    private String mother;
    private String father;
    private String birthDate;
    private String Vac1;
    private String Vac2;
    private String gender;
    private int  age, weight;
    private String pathToCowPicture;


    public NewCow(String cowId, String birthdayDt, int age, String species, int weight, String gender, String description, String vac1, String vac2, String mother, String father, String pathToCowPicture) {
        this.CowId = cowId;
        this.Species = species;
        this.Description = description;
        this.birthDate = birthdayDt;
        this.Vac1 = vac1;
        this.Vac2 = vac2;
        this.age = age;
        this.weight = weight;
        this.gender = gender;
        this.mother = mother;
        this.father = father;
        this.pathToCowPicture = pathToCowPicture;
    }

    public String getCowId() {
        return CowId;
    }

    public void setCowId(String cowId) {
        CowId = cowId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSpecies() {
        return Species;
    }

    public void setSpecies(String species) {
        Species = species;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getVac1() {
        return Vac1;
    }

    public void setVac1(String vac1) {
        Vac1 = vac1;
    }

    public String getVac2() {
        return Vac2;
    }

    public void setVac2(String vac2) {
        Vac2 = vac2;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getpathToCowPicture() {
        return pathToCowPicture;
    }

    public void setpathToCowPicture(String pathToCowPicture) {
        this.pathToCowPicture = pathToCowPicture;
    }

}
