package com.liyuji.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.liyuji.app.R;
import com.liyuji.app.utils.OkHttpCallback;
import com.liyuji.app.utils.OkHttpUtils;
import com.liyuji.app.vo.ServerResponse;
import com.liyuji.app.vo.UserVO;
import com.liyuji.app.utils.Util;

/**
 * @author L
 */
public class ForgetActivity extends AppCompatActivity implements View.OnClickListener {


    public EditText username_editText;
    public EditText password_editText;
    public EditText getPassword_editText;
    public TextView reBack;
    private Button editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forget);

        username_editText = findViewById(R.id.username);
        password_editText = findViewById(R.id.password);
        getPassword_editText = findViewById(R.id.passwordCheck);
        reBack = findViewById(R.id.reBack);
        editPassword = findViewById(R.id.editPassword);

        editPassword.setOnClickListener(this);
        reBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reBack:
                jumpPage();
                break;
            case R.id.editPassword:
                String username = username_editText.getText().toString();
                String password = password_editText.getText().toString();
                String passwordCheck = getPassword_editText.getText().toString();
                username_editText.setError(null);
                password_editText.setError(null);
                getPassword_editText.setError(null);

                // 校验用户名
                if (TextUtils.isEmpty(username)) {
                    username_editText.setError("用户名不能为空");
                    username_editText.requestFocus();
                    return;
                }
                // 校验密码
                if (TextUtils.isEmpty(password) || password.length() <= 8) {
                    password_editText.setError("密码不少于9位");
                    password_editText.requestFocus();
                    return;
                }
                if (!passwordCheck.equals(password)) {
                    getPassword_editText.setError("两次密码需要一致");
                    getPassword_editText.requestFocus();
                    return;
                }
                forgetPassword(username, password);
                break;
            default:
                break;
        }

    }

    private void forgetPassword(String username, String password) {
        OkHttpUtils.get(Util.SERVER_ADDR + "/forgetPassword?userName=" + username + "&userPassword=" + password,
                new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        super.onFinish(status, msg);

                        Gson gson = new Gson();
                        ServerResponse<UserVO> serverResponse = gson.fromJson(msg, ServerResponse.class);
                        String msg1 = serverResponse.getMsg();

                        Looper.prepare();
                        Toast.makeText(ForgetActivity.this, msg1 + "", Toast.LENGTH_SHORT).show();
                        Looper.loop();

                    }
                });
    }

    public void jumpPage() {
        Intent intent = new Intent(ForgetActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
