package cn.hotdoor.kxt.Beans;

/**
 * Created by fancy on 2015/8/3.
 */
public class MessageItemBean {
    String Date,Message,Phone;
    int Icon;

    public MessageItemBean(String date, int icon, String message, String phone) {
        Date = date;
        Icon = icon;
        Message = message;
        Phone = phone;
    }

    public String getDate() {

        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getIcon() {
        return Icon;
    }

    public void setIcon(int icon) {
        Icon = icon;
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
}
