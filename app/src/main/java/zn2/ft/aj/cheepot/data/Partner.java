package zn2.ft.aj.cheepot.data;

/**
 * Created by yusuf on 13/11/2017.
 */

public class Partner {
    public String name;
    public String familyName;
    public String birthDate;
    public String password;
    public String gender;
    public String mail;
    public String namefirm;
    public String matricule;
    public String comptebancaire;

    public Partner(String name, String familyName, String birthDate, String password , String gender, String mail, String namefirm , String matricule ,String comptebancaire){
        this.name = name;
        this.familyName = familyName;
        this.birthDate = birthDate;
        this.password = password;
        this.gender = gender;
        this.mail = mail;
        this.namefirm = namefirm;
        this.matricule = matricule;
        this.comptebancaire = comptebancaire;




    }
    public Partner(){
    }
}
