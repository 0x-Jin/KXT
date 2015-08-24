package cn.hotdoor.kxt.Activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.melnykov.fab.FloatingActionButton;

import net.tsz.afinal.FinalDb;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import cn.hotdoor.kxt.Beans.MyCommentBeen;
import cn.hotdoor.kxt.Beans.MyCommentCard;
import cn.hotdoor.kxt.Data.GlobleData;
import cn.hotdoor.kxt.R;
import cn.hotdoor.kxt.Utils.NormalPostRequest;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import me.drakeet.materialdialog.MaterialDialog;

public class CommentActivity extends AppCompatActivity {
    private FloatingActionButton commentFab;
    private MaterialDialog commentMd;
    private View comment;
    private EditText commentEt;
    private CardListView commentLv;
    private ArrayList<Card> comments = new ArrayList<>();
    private View commentV;
    private int ID = 0;
    private MaterialDialog confirmMd;
    private CommentAdapter mycardadapter;
    String currentTime;
    List<MyCommentBeen> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        init();
        initCommentCards();
        initMaterialDialog();
        commentFabMethod();
    }
    private void  initMaterialDialog() {
        commentV = View.inflate(this, R.layout.comment_material_dialog, null);
        commentEt = (EditText) commentV.findViewById(R.id.et_comment);
        commentEt.setFocusable(true);
        commentEt.setFocusableInTouchMode(true);
        commentEt.requestFocus();
        commentMd = new MaterialDialog(this).setTitle("意见反馈").setContentView(commentV).setPositiveButton("提交反馈", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(CommentActivity.this, "提交成功", Toast.LENGTH_SHORT).show();

                addCommentCard(commentEt.getText().toString());
                commentMd.dismiss();
               // startActivity(new Intent(CommentActivity.this,CommentActivity.class));
               // finish();
                reload();



            }
        }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CommentActivity.this, "取消", Toast.LENGTH_SHORT).show();
                commentMd.dismiss();
            }
        });
    }

    private void reload() {

        if(mycardadapter==null||mycardadapter.isEmpty()){
            initCommentCards();

        }
        else{
            mycardadapter.clear();
            mycardadapter.notifyDataSetChanged();
            initCommentCards();
        }

    }

    private void addCommentCard(String commentString) {
        if (commentString.length() > 0) {
            Time time = new Time();
            time.setToNow();
            int year = time.year;
            int month = time.month;
            int date = time.monthDay;
            int hour = time.hour;
            int minute = time.minute;
            currentTime = year + "年" + month + "月" + date + "日" + hour + "时" + minute + "分";
            feedback(commentString);
            saveComment(commentString,currentTime);
            commentEt.setText("");
        } else
            Toast.makeText(CommentActivity.this, "请输入反馈", Toast.LENGTH_SHORT).show();
    }

    private void feedback(String comment) {
        SharedPreferences account = getSharedPreferences("account", MODE_APPEND);
        String mobile=account.getString("mobile","");
        String token=account.getString("token","");
        Map<String,String> map = new HashMap<String,String>();
        map.put("token",token);
        map.put("mobile",mobile);
        map.put("content",comment);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Request<JSONObject> request = new NormalPostRequest(GlobleData.feedback, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Log.d(TAG, "response -> " + response.toString());
                // Toast.makeText(GlobleData.context,response.toString(), Toast.LENGTH_LONG).show();

                try {


                    String  result = response.getString("errcode");
                    Toast.makeText(CommentActivity.this,result, Toast.LENGTH_LONG).show();
                   // switch (result){
                       // case "0":loginSeccess();break;
                      //  case "4045":loginFail();break;
                       // default:Toast.makeText(GlobleData.context,"default", Toast.LENGTH_LONG).show();break;
                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.e(TAG, error.getMessage(), error);
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, map);
        requestQueue.add(request);
        requestQueue.start();

    }

    private void saveComment(String comment,String time) {

        MyCommentBeen been1= new MyCommentBeen();

        been1.setTimeString(time);
        been1.setCommentString(comment);

        GlobleData.db_comment.save(been1);
        ///data/data/cn.hotdoor.kxt/databases/afinal.db

    }

    private void initCommentCards() {
        MyCommentCard card;
        data.clear();
        data.addAll(GlobleData.db.findAll(MyCommentBeen.class));
        int size=data.size();
       if(size>0){
            for(int i=(size-1);i>-1;i--) {
                card=new MyCommentCard(this,R.layout.card_comment);
                card.setTimeString(data.get(i).getTimeString());
                card.setCommentString(data.get(i).getCommentString());
                comments.add(card);
            }
           mycardadapter = new CommentAdapter(this, comments);
           commentLv.setAdapter(mycardadapter);
        }


    }

    private void commentFabMethod() {
        commentFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentMd.show();
            }
        });
    }

    private void init() {
        commentFab = (FloatingActionButton) findViewById(R.id.comment_fab_comment);
        commentLv = (CardListView) findViewById(R.id.comment_clv_comment);
        GlobleData.db_comment = FinalDb.creat(CommentActivity.this);
    }


    class CommentAdapter extends CardArrayAdapter{

        public CommentAdapter(Context context, List<Card> cards) {

            super(context, cards);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            //View item= View.inflate(getApplicationContext(),R.layout.card_comment,null);

            Card card = (Card)this.getItem(position);
            card.setOnLongClickListener(new Card.OnLongCardClickListener() {
                @Override
                public boolean onLongClick(Card card, View view) {

                    confirmMd = new MaterialDialog(CommentActivity.this).setTitle("删除反馈？").setMessage("确认删除此条反馈？").setPositiveButton("删除", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            int sum=data.size()-1;
                            MyCommentBeen del =data.get((sum-position));
                            GlobleData.db_comment.deleteById(del);
                            reload();

                            confirmMd.dismiss();
                            Toast.makeText(CommentActivity.this,position+"", Toast.LENGTH_SHORT).show();
                            commentLv.setAdapter(mycardadapter);
                        }
                    }).setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            confirmMd.dismiss();
                        }
                    });
                    confirmMd.show();




                    return true;
                }
            });
            return super.getView(position, convertView, parent);
        }


    }

}
