package zn2.ft.aj.cheepot.data;

import java.io.Serializable;

/**
 * Created by yusuf on 13/11/2017.
 */

public class Partner implements Serializable {
    public String name;
    public String familyName;
    public String birthDate;
    public String password;
    public String gender;
    public String nameFirm;
    public String matricule;
    public String compteBancaire;
    public String siteWeb;

    public Partner(String name, String familyName, String birthDate, String password , String gender){
        this.name = name;
        this.familyName = familyName;
        this.birthDate = birthDate;
        this.password = password;
        this.gender = gender;

    }

    public void setter( String namefirm , String siteWeb, String matricule ,String comptebancaire){
        this.nameFirm = namefirm;
        this.matricule = matricule;
        this.compteBancaire = comptebancaire;
        this.siteWeb = siteWeb;
    }


    public Partner(){
    }
}
