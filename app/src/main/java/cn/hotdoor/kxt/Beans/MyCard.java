package cn.hotdoor.kxt.Beans;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.hotdoor.kxt.Data.GlobleData;
import cn.hotdoor.kxt.R;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by Fancy on 2015/8/11.
 */
public class MyCard extends Card {

        public String Date,Message,Phone;
        public String Icon;

    public MyCard(Context context) {
        super(context);
    }

    public MyCard(Context context, int innerLayout) {
        super(context, innerLayout);
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



    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        ImageView iconIV= (ImageView) parent.findViewById(R.id.message_iv_icon);
        TextView messageTV= (TextView) parent.findViewById(R.id.message_tv_content);
        TextView phoneTV= (TextView) parent.findViewById(R.id.message_tv_phone);
        TextView dateTV= (TextView) parent.findViewById(R.id.message_tv_date);
       // iconIV.setImageResource(getIcon());

        Bitmap bmp = BitmapFactory.decodeFile(GlobleData.FilepathMusic +getIcon());
        // imageview.setImageBitmap(bmp);
        iconIV.setImageBitmap(bmp);
        messageTV.setText(getMessage());
        phoneTV.setText(getPhone());
        dateTV.setText(getDate());
    }
}
