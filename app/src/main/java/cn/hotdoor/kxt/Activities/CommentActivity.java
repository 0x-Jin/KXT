package cn.hotdoor.kxt.Activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

import cn.hotdoor.kxt.R;
import it.gmariotti.cardslib.library.cards.topcolored.TopColoredCard;
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
    private CardArrayAdapter mycardadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        init();
        initMaterialDialog();
        commentFabMethod();
    }

    private void initMaterialDialog() {
        commentV = View.inflate(this, R.layout.comment_material_dialog, null);
        commentEt = (EditText) commentV.findViewById(R.id.et_comment);
        commentEt.setFocusable(true);
        commentEt.setFocusableInTouchMode(true);
        commentEt.requestFocus();
        commentMd = new MaterialDialog(this).setTitle("意见反馈").setContentView(commentV).setPositiveButton("提交反馈", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CommentActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                addCommentCard(commentEt.getText().toString());
                commentMd.dismiss();
            }
        }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CommentActivity.this, "取消", Toast.LENGTH_SHORT).show();
                commentMd.dismiss();
            }
        });
    }

    private void addCommentCard(String commentString) {
        if (commentString.length() > 0) {
            initCommentCards(commentString);
            mycardadapter = new CardArrayAdapter(this, comments);
            commentLv.setAdapter(mycardadapter);
        } else
            Toast.makeText(CommentActivity.this, "请输入反馈", Toast.LENGTH_SHORT).show();
    }

    private void initCommentCards(String Content) {
        Time time = new Time();
        time.setToNow();
        int year = time.year;
        int month = time.month;
        int date = time.monthDay;
        int hour = time.hour;
        int minute = time.minute;
        String currentTime = year + "年" + month + "月" + date + "日" + hour + "时" + minute + "分";
        int RandomColors[] = {R.color.random_card_color_blue, R.color.random_card_color_green, R.color.random_card_color_orange, R.color.random_card_color_purple, R.color.random_card_color_red};
        Random random = new Random();
        TopColoredCard card = TopColoredCard.with(CommentActivity.this).setColorResId(RandomColors[random.nextInt(5)]).setTitleOverColor(currentTime).setSubTitleOverColor(Content).build();
        card.setId(ID++ + "");
        card.setSwipeable(true);
        card.setOnLongClickListener(new Card.OnLongCardClickListener() {
            @Override
            public boolean onLongClick(final Card card, View view) {
                confirmMd = new MaterialDialog(CommentActivity.this).setTitle("确认删除此条反馈？").setPositiveButton("删除", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        comments.remove(card);
                        confirmMd.dismiss();
                        commentLv.setAdapter(mycardadapter);
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        card.onUndoSwipeListCard();
                        confirmMd.dismiss();
                    }
                });
                confirmMd.show();
                return true;
            }
        });
        comments.add(card);
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
    }

}
