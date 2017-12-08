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
    public String creatorUid;

    public Pot(String potName, String description, int type, String dateFin, String creatorUid){
        this.money = 0;
        this.potName = potName;
        this.type = type;
        this.description = description;
        this.dateCreation = new Date().toString();
        this.dateFin = dateFin;
        this.creatorUid = creatorUid;
    }

    public void setter (String description, int year, int month, int day){
        this.description = description;
        this.money = 0;
        this.dateCreation= new Date().toString();
        this.dateFin = String.format("%d/%d/%d", year, month, day);
    }
    public Pot(String potName, int type, String creatorUid){
        this.potName = potName;
        this.type = type;
        this.creatorUid = creatorUid;
    }
    public Pot(){  this.money = 0;
    }

    public void addMoney( int money){
        this.money += money;
    }
}
