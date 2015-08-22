package cn.hotdoor.kxt.Beans;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import cn.hotdoor.kxt.R;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by Fancy on 2015/8/22.
 */
public class MyCommentCard extends Card {
    private String timeString;
    private String commentString;

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    public String getCommentString() {
        return commentString;
    }

    public void setCommentString(String commentString) {
        this.commentString = commentString;
    }

    public MyCommentCard(Context context) {
        super(context);
    }

    public MyCommentCard(Context context, int innerLayout) {
        super(context, innerLayout);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        TextView time= (TextView) parent.findViewById(R.id.card_tv_time);
        TextView content= (TextView) parent.findViewById(R.id.card_tv_comment);
        time.setText(timeString);
        content.setText(commentString);
    }
}
