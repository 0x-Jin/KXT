package cn.hotdoor.kxt.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dd.CircularProgressButton;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tuenti.smsradar.Sms;
import com.tuenti.smsradar.SmsListener;
import com.tuenti.smsradar.SmsRadar;
import com.wrapp.floatlabelededittext.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

import cn.hotdoor.kxt.Data.GlobleData;
import cn.hotdoor.kxt.R;
import cn.hotdoor.kxt.Utils.ConnectUtils;
import cn.hotdoor.kxt.Utils.DialogUtils;
import cn.hotdoor.kxt.Utils.NormalPostRequest;

public class LoginActivity extends AppCompatActivity {
    public CircularProgressButton getcodeBtn,loginBtn;
    private EditText phoneEt,passEt;
    private MyTimeCount timeCount;
    //Context context;
    private long exitTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        XGPushConfig.enableDebug(this, true);
        GlobleData.context = getApplicationContext();
        isPermit();
        init();
        smsRadarMethod();
        loginBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(LoginActivity.this,MessageActivity.class));
                return true;
            }
        });

    }

    @Override
    protected void onDestroy() {
        SmsRadar.stopSmsRadarService(this);
        super.onDestroy();

    }

    private void smsRadarMethod() {
        SmsRadar.initializeSmsRadarService(LoginActivity.this, new SmsListener() {
            @Override
            public void onSmsSent(Sms sms) {

            }

            @Override
            public void onSmsReceived(Sms sms) {
                String message = sms.getMsg();
                if (message.contains("维信互动") && message.contains("验证码")) {
                    String code = message.substring(13, 17);
                    Log.i("code", code);
                    passEt.setText(code);
                }
            }
        });
    }

    private void init() {
        getcodeBtn = (CircularProgressButton) findViewById(R.id.login_btn_getcode);
        loginBtn = (CircularProgressButton) findViewById(R.id.login_btn_login);
        loginBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(LoginActivity.this, MessageActivity.class));
                finish();
                return false;
            }
        });
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
                String passNumber = passEt.getText().toString();
                GlobleData.mobile=phoneNumber;

                Map<String,String> map = new HashMap<String,String>();
                map.put("mobile",phoneNumber);
                map.put("code",passNumber);
                loginMethod(map);




                /*XGPushManager.registerPush(LoginActivity.this, phoneNumber, new XGIOperateCallback() {
                    @Override
                    public void onSuccess(Object data, int flag) {
                        Toast.makeText(GlobleData.context, "成功", Toast.LENGTH_SHORT).show();
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
                });*/


            }

            private void loginMethod(Map<String, String> map) {

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                Request<JSONObject> request = new NormalPostRequest(GlobleData.loginUrl, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG, "response -> " + response.toString());
                       // Toast.makeText(GlobleData.context,response.toString(), Toast.LENGTH_LONG).show();

                        try {
                            GlobleData.token = response.getString("token");

                            String  result = response.getString("errcode");
                            Toast.makeText(GlobleData.context,result, Toast.LENGTH_LONG).show();
                            switch (result){
                            case "0":loginSeccess();break;
                                case "4045":loginFail();break;
                                default:Toast.makeText(GlobleData.context,"default", Toast.LENGTH_LONG).show();break;
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

            private void loginSeccess() {
                SharedPreferences pre = getSharedPreferences("login", MODE_APPEND);
                //String user = pre.getString("number", "");
                SharedPreferences.Editor edit = pre.edit();
                edit.putString("fg", "1");
                edit.commit();

                SharedPreferences account = getSharedPreferences("account", MODE_APPEND);
                //String user = pre.getString("number", "");
                edit = account.edit();
                edit.putString("mobile",GlobleData.mobile);
                edit.putString("token",GlobleData.token);
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



        });
    }

    private void loginFail() {
        loginBtn.setProgress(-1);
        loginBtn.postDelayed(new Runnable() {
            @Override
            public void run() {
                loginBtn.setProgress(0);
            }
        }, 2000);
        Toast.makeText(GlobleData.context,"验证码或密码错误",Toast.LENGTH_SHORT).show();
    }




    private void getcodeBtnMethod() {
      //  Handler handler = new HandlerExtension(LoginActivity.this);
      //  Message m  = handler.obtainMessage();
        WeakReference<LoginActivity> mActivity;
        mActivity = new WeakReference<LoginActivity>(LoginActivity.this);
        LoginActivity theActivity = mActivity.get();
        final String xgtoken =(XGPushConfig.getToken(theActivity));

        timeCount=new MyTimeCount(5000,1000);

        //Toast.makeText(GlobleData.context,xgtoken, Toast.LENGTH_SHORT).show();
        getcodeBtn.setIndeterminateProgressMode(true);
        getcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile=phoneEt.getText().toString();
                    Map<String,String> map = new HashMap<String,String>();
                    map.put("mobile",mobile);
                map.put("xgtoken",xgtoken);
                if(getcodeBtn.getProgress()==0){
                    getCode(map);
                    getcodeBtn.setProgress(50);

//                    getcodeBtn.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            getcodeBtn.setProgress(100);
//                            timeCount.start();
//                        }
//                    },1800);

                    Toast.makeText(LoginActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();


                }


//                if (getcodeBtn.getProgress() == 0) {
//                    getcodeBtn.setProgress(50);
//                    String mobile=phoneEt.getText().toString();
//                    Map<String,String> map = new HashMap<String,String>();
//                    map.put("mobile",mobile);
//                    map.put("xgtoken",xgtoken);
//                    getCode(map);
//                } else if (getcodeBtn.getProgress() == 100) {
//                    getcodeBtn.setProgress(0);
//                } else {
//                    getcodeBtn.setProgress(100);
//                }
            }



            private void getCode(Map<String, String> map) {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                Request<JSONObject> request = new NormalPostRequest(GlobleData.captchaUrl, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG, "response -> " + response.toString());
                        //Toast.makeText(GlobleData.context,response.toString(), Toast.LENGTH_LONG).show();
                        try {
                            String  result = response.getString("errcode");
                            Toast.makeText(GlobleData.context,result, Toast.LENGTH_LONG).show();
                            switch (result){
                                case "0":getcodeBtn.setProgress(100);timeCount.start();break;
                                case "4045":getcodeBtn.setProgress(-1);getcodeBtn.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        getcodeBtn.setProgress(0);
                                    }
                                },1800);break;
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
    class MyTimeCount extends CountDownTimer{
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyTimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getcodeBtn.setClickable(false);
            getcodeBtn.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            getcodeBtn.setProgress(0);
            getcodeBtn.setClickable(true);
        }
    }
}







