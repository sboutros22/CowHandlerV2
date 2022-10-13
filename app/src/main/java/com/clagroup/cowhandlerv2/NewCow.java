package com.clagroup.cowhandlerv2;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class NewCow extends AppCompatActivity {
    private String CowId;
    private String Descriptions;
    private String Species;
    private String mother;
    private String father;
    private String birthDate;
    private RadioButton Vac1;
    private RadioButton Vac2;
    private RadioButton gender;
    private int herdNum, age, weight;


    public NewCow(String cowId, String birthdayDt, int age, String species, int weight, RadioButton gender, String description, RadioButton vac1, RadioButton vac2, String mother, String father, int herdNumber) {
        this.CowId = cowId;
        this.Species = species;
        this.Descriptions = description;
        this.birthDate = birthdayDt;
        this.Vac1 = vac1;
        this.Vac2 = vac2;
        this.age = age;
        this.weight = weight;
        this.herdNum = herdNumber;
        this.gender = gender;
        this.mother = mother;
        this.father = father;
    }
    public NewCow(){

    }

    public String getCowId() {
        return CowId;
    }

    public void setCowId(String cowId) {
        CowId = cowId;
    }

    public String getDescriptions() {
        return Descriptions;
    }

    public void setDescriptions(String descriptions) {
        Descriptions = descriptions;
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

    public RadioButton getVac1() {
        return Vac1;
    }

    public void setVac1(RadioButton vac1) {
        Vac1 = vac1;
    }

    public RadioButton getVac2() {
        return Vac2;
    }

    public void setVac2(RadioButton vac2) {
        Vac2 = vac2;
    }

    public RadioButton getGender() {
        return gender;
    }

    public void setGender(RadioButton gender) {
        this.gender = gender;
    }

    public int getHerdNum() {
        return herdNum;
    }

    public void setHerdNum(int herdNum) {
        this.herdNum = herdNum;
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

}
