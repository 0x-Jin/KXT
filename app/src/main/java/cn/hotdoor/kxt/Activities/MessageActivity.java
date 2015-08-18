/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package cn.hotdoor.kxt.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;

import java.util.ArrayList;

import cn.hotdoor.kxt.Beans.MyCard;
import cn.hotdoor.kxt.R;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;


public class MessageActivity extends AppCompatActivity{
    private PtrFrameLayout refreshPtr;
    private StoreHouseHeader header;
    private ImageView settingIv;
    private CardListView messageCardLv;
    FloatingActionButton funFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initHeader();//初始化header
        initRefreshPtr();
        CardArrayAdapter cardArrayAdapter=new CardArrayAdapter(MessageActivity.this,initCards());
        messageCardLv= (CardListView) findViewById(R.id.message_cardlv_message);

        View head=getLayoutInflater().inflate(R.layout.header_main,messageCardLv,false);
        messageCardLv.addHeaderView(head);

        settingIv= (ImageView) head.findViewById(R.id.header_iv_setting);
        settingIvMethod();

        if (messageCardLv!=null){
            messageCardLv.setAdapter(cardArrayAdapter);
        }
        fabMethod();



    }

    private void fabMethod() {
        funFab= (FloatingActionButton) findViewById(R.id.message_fab_funbutton);
        funFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPtr.autoRefresh();
            }
        });
        funFab.attachToListView(messageCardLv, new ScrollDirectionListener() {
            @Override
            public void onScrollDown() {
                if (messageCardLv.getFirstVisiblePosition()<1){
                    funFab.setImageResource(R.drawable.ic_refresh_white_48dp);
                    funFab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            refreshPtr.autoRefresh();
                        }
                    });
                }
                else {
                    funFab.setImageResource(R.drawable.ic_keyboard_arrow_up_white_48dp);
                    funFab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            messageCardLv.smoothScrollToPositionFromTop(0,0,300);
                        }
                    });
                }
            }

            @Override
            public void onScrollUp() {

            }
        });
    }

    private void settingIvMethod() {
        settingIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageActivity.this, InfoActivity.class));
            }
        });
    }

    private ArrayList<Card> initCards() {
        ArrayList<Card> cards=new ArrayList<>();
        //card test
        for (int i=0;i<1000;i++)
        {
            int[] ic={R.drawable.i1,R.drawable.i2,R.drawable.i3,R.drawable.i4,R.drawable.i5,R.drawable.i6};
            cards.add(makecard("2015年8月" + i + "日",
                    "1372625" + i,"This is a message you see for test hahahaha\n(╯‵□′)╯︵┻━┻  \n（╯－＿－）╯╧╧\n" + i,ic[i*10%6]));
        }
        //test
        return cards;

    }

    private Card makecard(String Date, String Phone, String Message, int Icon) {
        MyCard newCard=new MyCard(MessageActivity.this,R.layout.message_item);
        newCard.setDate(Date);
        newCard.setPhone(Phone);
        newCard.setIcon(Icon);
        newCard.setMessage(Message);
        return newCard;
    }

    private void initRefreshPtr() {
        refreshPtr= (PtrFrameLayout) findViewById(R.id.message_pfl_pullltorefresh);
        refreshPtr.setHeaderView(header);
        refreshPtr.addPtrUIHandler(header);
        refreshPtr.setPtrHandler(new MyPtrHandler());
    }

    private void initHeader() {
        header=new StoreHouseHeader(MessageActivity.this);
        header.setPadding(0, PtrLocalDisplay.dp2px(20), 0, PtrLocalDisplay.dp2px(20));
        header.initWithString("Loading", 50);
        header.setTextColor(Color.GRAY);
        header.setLineWidth(4);
    }
    class MyPtrHandler implements PtrHandler{
        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view1) {
            return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout,view,view1);
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
            refreshPtr.postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshPtr.refreshComplete();
                }
            },1800);
        }
    }

}
