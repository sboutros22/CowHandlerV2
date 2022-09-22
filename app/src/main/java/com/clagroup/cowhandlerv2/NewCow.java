package com.clagroup.cowhandlerv2;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class NewCow extends AppCompatActivity {
    private String CowId;
    private String Descriptions;
    private String Species;
    private int birthDate;
    private int Vac1;
    private int Vac2;
    private int herdNum;

    public NewCow(){

    }

    public NewCow(String cowId, String Species, int birthdaydDt, String Descriptions, int vax1, int vax2) {
        this.CowId = cowId;
        this.Species = Species;
        this.Descriptions = Descriptions;
        this.birthDate = birthdaydDt;
        this.Vac1 = vax1;
        this.Vac2 = vax2;

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

    public int getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(int birthDate) {
        this.birthDate = birthDate;
    }

    public int getVac1() {
        return Vac1;
    }

    public void setVac1(int vac1) {
        Vac1 = vac1;
    }

    public int getVac2() {
        return Vac2;
    }

    public void setVac2(int vac2) {
        Vac2 = vac2;
    }

    public int getHerdNum() {
        return herdNum;
    }

    public void setHerdNum(int herdNum) {
        this.herdNum = herdNum;
    }
}
