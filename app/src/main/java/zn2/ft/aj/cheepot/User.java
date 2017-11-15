package zn2.ft.aj.cheepot;

/**
 * Created by yusuf on 13/11/2017.
 */

public class User {
    public String name;
    public String familyName;
    public String birthDate;
    public String password;
    public String gender;

    public User(String name, String familyName, String birthDate, String password , String gender){
        this.name = name;
        this.familyName = familyName;
        this.birthDate = birthDate;
        this.password = password;
        this.gender = gender;
    }
    public User(){
    }
}
