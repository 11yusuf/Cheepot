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
    public Date dateCreation;
    public Date dateFin;
    public String creatorUid;

    public Pot(String potName, String description, int type, Date dateFin, String creatorUid){
        this.money = 0;
        this.potName = potName;
        this.type = type;
        this.description = description;
        this.dateCreation = new Date();
        this.dateFin = dateFin;
        this.creatorUid = creatorUid;
    }

    public void setter (String description, Date dateFin){
        this.money = 0;
        this.description = description;
        this.dateCreation = new Date();
        this.dateFin = dateFin;
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
