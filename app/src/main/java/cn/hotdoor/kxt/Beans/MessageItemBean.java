package cn.hotdoor.kxt.Beans;

import net.tsz.afinal.annotation.sqlite.Id;

/**
 * Created by fancy on 2015/8/3.
 */
public class MessageItemBean {
    @Id(column = "myId")
    private int id;
    public String Date, Message, Phone;
    public String Icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }


    /*public MessageItemBean(String date, int icon, String message, String phone) {
        Date = date;
        Icon = icon;
        Message = message;
        Phone = phone;
    }*/

}