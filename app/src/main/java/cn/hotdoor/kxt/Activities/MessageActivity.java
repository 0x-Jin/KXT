/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package cn.hotdoor.kxt.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;

import net.tsz.afinal.FinalDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.hotdoor.kxt.Beans.MessageItemBean;
import cn.hotdoor.kxt.Beans.MyCard;
import cn.hotdoor.kxt.Data.Filemake;
import cn.hotdoor.kxt.Data.GlobleData;
import cn.hotdoor.kxt.Dialog.MyMenu;
import cn.hotdoor.kxt.R;
import cn.hotdoor.kxt.Utils.DialogUtils;
import cn.hotdoor.kxt.Utils.NormalPostRequest;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

public class MessageActivity extends AppCompatActivity implements cn.hotdoor.kxt.Dialog.MenuItem {
    private PtrFrameLayout refreshPtr;
    private StoreHouseHeader header;
    private ImageView settingIv;
    private ImageView icon;
    private CardListView messageCardLv;
    FloatingActionButton funFab;
    private long exitTime = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        GlobleData.db = FinalDb.creat(getApplicationContext());
        GlobleData.mess=this;
        initPic();
        initHeader();//初始化header
        initRefreshPtr();

        messageCardLv = (CardListView) findViewById(R.id.message_cardlv_message);
        View head = getLayoutInflater().inflate(R.layout.header_main, messageCardLv, false);
        messageCardLv.addHeaderView(head);
        settingIv = (ImageView) head.findViewById(R.id.header_iv_setting);
        icon=(ImageView) head.findViewById(R.id.message_iv_account);


        settingIvMethod();
        changedIcMethod();
        initIcMethod();
        loadMessage();
        fabMethod();


    }

    private void initIcMethod() {
        SharedPreferences preicon = getSharedPreferences("icon", MODE_APPEND);
        if(preicon.getString("fg", "").equals("0")) {
            icon.setImageResource(R.drawable.man_1);
        }
        else if(preicon.getString("fg", "").equals("1")) {
            icon.setImageResource(R.drawable.man_2);
        }
        else if (preicon.getString("fg", "").equals("2")){
            icon.setImageResource(R.drawable.man_3);
        }
        else if (preicon.getString("fg", "").equals("3")){
            icon.setImageResource(R.drawable.girl_1);
        }
        else if (preicon.getString("fg", "").equals("4")){
            icon.setImageResource(R.drawable.girl_2);
        }
        else if (preicon.getString("fg", "").equals("5")){
            icon.setImageResource(R.drawable.girl_3);
        }
        else {
        }
    }

    private void changedIcMethod() {
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int myMenuBit[] = { R.drawable.man_1, R.drawable.man_2, R.drawable.man_3, R.drawable.girl_1, R.drawable.girl_2, R.drawable.girl_3};
                new MyMenu(MessageActivity.this, myMenuBit,MessageActivity.this).show();

            }
        });

    }

    private void loadMessage() {
        CardArrayAdapter cardArrayAdapter = new CardArrayAdapter(MessageActivity.this, initCards());

        if (messageCardLv != null) {
            messageCardLv.setAdapter(cardArrayAdapter);
        }
    }

    private void initPic() {
        Filemake f = new Filemake(0);
        try {
            f.makeDir(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                if (messageCardLv.getFirstVisiblePosition() < 1) {
                    funFab.setImageResource(R.drawable.ic_refresh_white_48dp);
                    funFab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            refreshPtr.autoRefresh();
                        }
                    });
                } else {
                    funFab.setImageResource(R.drawable.ic_keyboard_arrow_up_white_48dp);
                    funFab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            messageCardLv.smoothScrollToPositionFromTop(0, 0, 300);
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
        ArrayList<Card> cards = new ArrayList<>();
        //card test
        List<MessageItemBean> data = new ArrayList<>();
        //data.addAll(GlobleData.db.findAll(MessageItemBean.class));
        data.addAll(GlobleData.db.findAll(MessageItemBean.class));
        //  data.get(0).getMessage();
        if (data.size() > 0) {
            for (int i = (data.size() - 1); i > -1; i--) {
                cards.add(makecard(data.get(i).getDate(),
                        data.get(i).getPhone(), data.get(i).getMessage(),
                        data.get(i).getIcon()));
            }
        }
        return cards;
    }



    private Card makecard(String Date, String Phone, String Message, String Icon) {
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

    @Override
    public void ItemClickListener(int position) {
        //Toast.makeText(this, "你选中第" + (position + 1) + "个", Toast.LENGTH_SHORT)
        //   .show();
        SharedPreferences preicon = getSharedPreferences("icon", MODE_APPEND);
        SharedPreferences.Editor edit = preicon.edit();
        if(position==0)
        {icon.setImageResource(R.drawable.man_1);

            edit.putString("fg","0");
            edit.commit();}
        else if (position==1)
        {icon.setImageResource(R.drawable.man_2);

            edit.putString("fg","1");
            edit.commit();}
        else if (position==2) {
            icon.setImageResource(R.drawable.man_3);

            edit.putString("fg","2");
            edit.commit();}
        else if (position==3){
            icon.setImageResource(R.drawable.girl_1);

            edit.putString("fg","3");
            edit.commit();}
        else if (position==4){
            icon.setImageResource(R.drawable.girl_2);

            edit.putString("fg","4");
            edit.commit();}
        else  {icon.setImageResource(R.drawable.girl_3);

            edit.putString("fg","5");
            edit.commit();}


    }

    class MyPtrHandler implements PtrHandler{
        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view1) {
            return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout,view,view1);
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
            /*refreshPtr.postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshPtr.refreshComplete();
                }
            },1800);*/
            SharedPreferences account = getSharedPreferences("account", MODE_APPEND);
            String mobile=account.getString("mobile","");
            String token=account.getString("token","");
            Map<String,String> map = new HashMap<String,String>();
            map.put("mobile",mobile);
            map.put("token",token);
            getMessageMethod(map);

            refreshPtr.refreshComplete();
            loadMessage();
        }

        private void getMessageMethod(Map<String, String> map) {

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            Request<JSONObject> request = new NormalPostRequest(GlobleData.getMessage, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Log.d(TAG, "response -> " + response.toString());
                    // Toast.makeText(GlobleData.context,response.toString(), Toast.LENGTH_LONG).show();

                    try {
                        String errorCode = response.getString("errcode");
                        String count = response.getString("count");
                        Toast.makeText(GlobleData.context, count, Toast.LENGTH_SHORT).show();
                        int sum = Integer.parseInt(count);
                        Toast.makeText(GlobleData.context,errorCode, Toast.LENGTH_SHORT).show();
                        if(errorCode.equals("0")){

                            JSONObject jsonObject=new JSONObject( response.toString());
                            JSONArray jsonArray=jsonObject.getJSONArray("data");

                            //这里获取的是装载有所有pet对象的数组

                            for(int i=0;i<2;i++){
                                JSONObject item = jsonArray.getJSONObject(i);
                                MessageItemBean been= new MessageItemBean();
                                been.setIcon(item.getString("picid"));
                                been.setPhone(item.getString("mobile"));
                                been.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                        .format(new Date(Long.valueOf((item.getString("date")))*1000L)));
                                been.setMessage(item.getString("content"));
                                //Toast.makeText(context,notifiShowedRlt.getContent() , Toast.LENGTH_SHORT).show();
                                GlobleData.db.save(been);

                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Log.e(TAG, error.getMessage(), error);
                    Toast.makeText(GlobleData.context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }, map);
            requestQueue.add(request);
            requestQueue.start();
        }


    }
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                DialogUtils.showToast(this, "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}
