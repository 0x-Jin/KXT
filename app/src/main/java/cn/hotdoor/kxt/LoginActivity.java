package cn.hotdoor.kxt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.dd.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {
    private CircularProgressButton getcodeBtn,loginBtn;
    private EditText phoneEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getcodeBtn= (CircularProgressButton) findViewById(R.id.login_btn_getcode);
        loginBtn= (CircularProgressButton) findViewById(R.id.login_btn_login);
        phoneEt= (EditText) findViewById(R.id.login_et_phone);
        getcodeBtnMethod();
        loginBtnMethod();

    }

    private void loginBtnMethod() {
        loginBtn.setIndeterminateProgressMode(true);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setProgress(50);

            }
        });
    }

    private void getcodeBtnMethod() {
        getcodeBtn.setIndeterminateProgressMode(true);
        getcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity();
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
}
