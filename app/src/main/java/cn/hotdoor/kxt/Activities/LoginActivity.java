package cn.hotdoor.kxt.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import java.sql.Time;

import cn.hotdoor.kxt.R;
import cn.hotdoor.kxt.Utils.DialogUtils;

public class LoginActivity extends AppCompatActivity {
    private CircularProgressButton getcodeBtn,loginBtn;
    private EditText phoneEt,passEt;
    Context context;
    private long exitTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        XGPushConfig.enableDebug(this, true);
        context = getApplicationContext();
        isPermit();
        init();

    }

    private void init() {
        getcodeBtn = (CircularProgressButton) findViewById(R.id.login_btn_getcode);
        loginBtn = (CircularProgressButton) findViewById(R.id.login_btn_login);
        phoneEt = (EditText)findViewById(R.id.login_et_phone);
        passEt = (EditText)findViewById(R.id.login_et_code);
        getcodeBtnMethod();
        loginBtnMethod();
    }

    private void isPermit() {
        SharedPreferences pre = getSharedPreferences("login", MODE_APPEND);
        if(pre.getString("fg", "").equals("1")){
            startActivity(new Intent(LoginActivity.this,MessageActivity.class));
            finish();
        }
    }

    //haha
    private void loginBtnMethod() {
        loginBtn.setIndeterminateProgressMode(true);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setProgress(50);
                String phoneNumber = phoneEt.getText().toString();
                XGPushManager.registerPush(context, phoneNumber, new XGIOperateCallback() {
                    @Override
                    public void onSuccess(Object data, int flag) {
                        Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
                        //  progressButton.setProgress(100);

                        SharedPreferences pre = getSharedPreferences("login", MODE_APPEND);
                        //String user = pre.getString("number", "");
                        SharedPreferences.Editor edit = pre.edit();
                        edit.putString("fg", "1");
                        edit.commit();

                        // edit.putString("cookie", l.getCookie());

                        try {
                            Thread.sleep(700);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        startActivity(new Intent(LoginActivity.this, MessageActivity.class));
                        finish();
                    }

                    @Override
                    public void onFail(Object data, int errCode, String msg) {
                        loginBtn.setProgress(-1);
                        loginBtn.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loginBtn.setProgress(0);
                            }
                        }, 2000);
                    }
                });

            }
        });
    }

    private void getcodeBtnMethod() {
        getcodeBtn.setIndeterminateProgressMode(true);
        getcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getcodeBtn.getProgress() == 0) {
                    getcodeBtn.setProgress(50);

                } else if (getcodeBtn.getProgress() == 100) {
                    getcodeBtn.setProgress(0);
                } else {
                    getcodeBtn.setProgress(100);
                }
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
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
