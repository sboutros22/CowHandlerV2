package com.clagroup.cowhandlerv2;

import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class NewCow extends AppCompatActivity {
    private String CowId;
    private String Descriptions;
    private int herdNum;

    public NewCow(String CowId, String Descriptions, int herdNum){

       this.CowId = CowId;
       this.Descriptions = Descriptions;
       this.herdNum = herdNum;
    }
    public NewCow(){

    }

    public NewCow(String cowId, String longhorn, int birthdaydDt, String Descriptions, int i, int i1) {
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

    public int getHerdNum() {
        return herdNum;
    }

    public void setHerdNum(int herdNum) {
        this.herdNum = herdNum;
    }
}
