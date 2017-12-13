package zn2.ft.aj.cheepot.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yusuf on 05/12/2017.
 */

public class Pot implements Serializable {
    public int money;
    public String potName;
    public String description;
    public int type;
    public String dateCreation;
    public String dateFin;
    public String potId;
    public String creatorUid;
    public String creatorName;
    public int followers;

    public Pot(String potName, String description, int type, String dateFin, String creatorUid, String creatorName){
        this.money = 0;
        this.potName = potName;
        this.type = type;
        this.description = description;
        this.dateCreation = new Date().toString();
        this.dateFin = dateFin;
        this.creatorUid = creatorUid;
        this.creatorName = creatorName;
        this.followers = 0;
    }

    public void setter (String description, int year, int month, int day, String potId){
        this.description = description;
        this.money = 0;
        this.dateCreation= new Date().toString();
        this.dateFin = String.format("%d/%d/%d", day, month, year);
        this.potId = potId;
        this.followers = 0;
    }
    public Pot(String potName, int type, String creatorUid, String creatorName){
        this.potName = potName;
        this.type = type;
        this.creatorUid = creatorUid;
        this.creatorName = creatorName;
    }

    public Pot(String potName, int type, String creatorUid){
        this.potName = potName;
        this.type = type;
        this.followers = 0;
        this.creatorUid = creatorUid;
    }
    public Pot(){  this.money = 0;
            this.followers = 0;
    }

    public void addMoney( int money){
        this.money += money;
    }
    public void addFollower () { this.followers += 1; }
}
