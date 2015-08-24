package cn.hotdoor.kxt.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.CircularProgressButton;

import cn.hotdoor.kxt.R;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText pwd1Et,pwd2Et;
    private CircularProgressButton confirmBtn;
    private TextWatcher pwdTw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
        passwordMethod();
        confirmBtnMethod();
    }

    private TextWatcher pwdTwMethod() {
        pwdTw=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!pwd1Et.getText().toString().equals(pwd2Et.getText().toString()))
                {
                    confirmBtn.setProgress(-1);
                    confirmBtn.setText("两次输入密码不一致");
                    confirmBtn.setClickable(false);
                }
                else if (pwd1Et.getText().toString().equals("")){
                    confirmBtn.setProgress(-1);
                    confirmBtn.setText("密码不能为空");
                    confirmBtn.setClickable(false);
                }
                else {
                    confirmBtn.setProgress(0);
                    confirmBtn.setClickable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        return pwdTw;
    }

    private void confirmBtnMethod() {
        confirmBtn.setIndeterminateProgressMode(true);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmBtn.getProgress()==0&&pwd1Et.length()>0){
                    Toast.makeText(ChangePasswordActivity.this, "修改成功（test）", Toast.LENGTH_SHORT).show();
                }
                else if (pwd1Et.length()==0||pwd2Et.length()==0)
                {
                    confirmBtn.setText("密码不能为空");
                }

            }
        });
    }

    private void passwordMethod() {
        pwd1Et.addTextChangedListener(pwdTwMethod());
        pwd2Et.addTextChangedListener(pwdTwMethod());
        
    }

    private void init() {
        pwd1Et= (EditText) findViewById(R.id.changepassword_et_pwd1);
        pwd2Et= (EditText) findViewById(R.id.changepassword_et_pwd2);
        confirmBtn= (CircularProgressButton) findViewById(R.id.changepassword_btn_confirmchange);
    }


}
