package cn.hotdoor.kxt.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.dd.CircularProgressButton;

import cn.hotdoor.kxt.Data.GlobleData;
import cn.hotdoor.kxt.R;
import me.drakeet.materialdialog.MaterialDialog;

public class InfoActivity extends AppCompatActivity {
    private CircularProgressButton logoutBtn;
    private MaterialDialog logoutDialog;
    private RelativeLayout commentRl;
    private RelativeLayout accountRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        init();
        logoutDialogMethod();
        logoutBtnMethod();
        commentRlMethod();
        accountRlMethod();

    }

    private void accountRlMethod() {
        accountRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InfoActivity.this,ChangePasswordActivity.class));
            }
        });
    }

    private void commentRlMethod() {
        commentRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InfoActivity.this,CommentActivity.class));
            }
        });
    }

    private void logoutBtnMethod() {
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutBtn.setIndeterminateProgressMode(true);
                logoutBtn.setProgress(50);
                logoutDialog.show();
            }
        });
    }

    private void logoutDialogMethod() {
        logoutDialog = new MaterialDialog(this).setTitle("确认退出？").setPositiveButton("注销", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pre = getSharedPreferences("login", MODE_APPEND);
                SharedPreferences.Editor edit = pre.edit();
                edit.putString("fg", "0");
                edit.commit();

                startActivity(new Intent(InfoActivity.this, LoginActivity.class));
                InfoActivity.this.finish();
               // GlobleData.mess.finish();

            }
        }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog.dismiss();
                logoutBtn.setProgress(0);
            }
        }).setMessage("退出后将接收不到推送");
    }

    private void init() {
        logoutBtn = (CircularProgressButton) findViewById(R.id.info_btn_logout);
        commentRl = (RelativeLayout) findViewById(R.id.info_rl_comment);
        accountRl = (RelativeLayout) findViewById(R.id.info_rl_account);
    }
}
