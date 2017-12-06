package zn2.ft.aj.cheepot.data;

import java.util.Date;

/**
 * Created by yusuf on 05/12/2017.
 */

public class Pot {
    public int money;
    public String name;
    public String description;
    public int type;
    public String dateCreation;
    public Date dateFin;
    public String creatorUid;

    public Pot(int money, String name, String description, int type, Date dateFin, String creatorUid){
        this.money = money;
        this.name = name;
        this.type = type;
        this.description = description;
        this.dateCreation = new Date().toString();
        this.dateFin = dateFin;
        this.creatorUid = creatorUid;
    }

    public Pot(int money, String name, int type, String creatorUid){
        this.money = money;
        this.name = name;
        this.type = type;
        this.dateCreation = new Date().toString();
        this.creatorUid = creatorUid;
    }
    public Pot(){

    }
}
