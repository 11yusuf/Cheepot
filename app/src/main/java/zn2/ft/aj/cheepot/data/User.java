package zn2.ft.aj.cheepot.data;

/**
 * Created by yusuf on 13/11/2017.
 */

public class User {
    public String name;
    public String familyName;
    public String birthDate;
    public String password;
    public String gender;
    public int money;

    public User(String name, String familyName, String birthDate, String password, String gender, int money) {
        this.name = name;
        this.familyName = familyName;
        this.birthDate = birthDate;
        this.password = password;
        this.gender = gender;
        this.money = money;
    }
    public User(){
    }
    public User(User user){
        this.name = user.name;
        this.familyName = user.familyName;
    }
    public void takeMoney(int money){
        this.money -= money;
    }

    public void addmoney(int money){
        this.money += money;
    }
}
