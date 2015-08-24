package cn.hotdoor.kxt.Beans;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * Created by Administrator on 2015/8/24.
 */
@Table(name = "comment_table")
public class MyCommentBeen {

   // @Id(column="myId")
    private String timeString;
    private String commentString;



    private int id;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String getCommentString() {
        return commentString;
    }

    public void setCommentString(String commentString) {
        this.commentString = commentString;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }
}
