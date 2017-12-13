package zn2.ft.aj.cheepot.data;

import org.joda.time.DateTime;

/**
 * Created by lenovo-pc on 13/12/2017.
 */

public class Notification {

    public String notifierId;
    public String notifierName;
    public String potId;
    public String potName;
    public String toNotifyId;
    public String notifId;
    public int type;
    public String creationDate;
    public int moneyAdded;
    public int previousMoney;
    public int followersNumber;


    public Notification(String notifierId, String notifierName, String potId,
                        String potName, String toNotifyId, String notifId,
                        int type, String creationDate, int moneyAdded, int previousMoney, int followersNumber) {

        this.notifierId = notifierId;
        this.notifierName = notifierName;
        this.potId = potId;
        this.potName = potName;
        this.toNotifyId = toNotifyId;
        this.notifId = notifId;
        this.type = type;
        this.creationDate = creationDate;
        this.moneyAdded = moneyAdded;
        this.previousMoney = previousMoney;
        this.followersNumber = followersNumber;
    }

    public Notification(String notifierId, String notifierName, String potId,
                        String potName) {
        this.notifierId = notifierId;
        this.notifierName = notifierName;
        this.potId = potId;
        this.potName = potName;
    }

    public void setterNotif(String toNotifyId, String notifId,
                            int type, String creationDate, int moneyAdded, int previousMoney, int followersNumber) {
        this.toNotifyId = toNotifyId;
        this.notifId = notifId;
        this.type = type;
        this.creationDate = creationDate;
        this.moneyAdded = moneyAdded;
        this.previousMoney = previousMoney;
        this.followersNumber = followersNumber;
    }

    public Notification() {

    }

}