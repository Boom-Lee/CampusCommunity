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
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.liyuji.app.R;
import com.liyuji.app.utils.OkHttpCallback;
import com.liyuji.app.utils.OkHttpUtils;
import com.liyuji.app.utils.SharedPreferencesUtil;
import com.liyuji.app.utils.Util;
import com.liyuji.app.vo.ServerResponse;
import com.liyuji.app.vo.UserVO;

/**
 * @author L
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    public EditText username_editText;
    public EditText password_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //设置View为登录界面
        setContentView(R.layout.activity_login);

        if (!Util.isNetworkAvailable(LoginActivity.this)) {
            Toast.makeText(getApplicationContext(), Util.NET_UNCONNECTION, Toast.LENGTH_LONG).show();
        }

        username_editText = findViewById(R.id.username);
        password_editText = findViewById(R.id.password);
        TextView forgetPassword = findViewById(R.id.forgetPassword);
        Button register = findViewById(R.id.register);
        Button login = findViewById(R.id.login);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (Util.isNetworkAvailable(LoginActivity.this)) {
                    String username = username_editText.getText().toString();
                    String password = password_editText.getText().toString();
                    username_editText.setError(null);
                    password_editText.setError(null);

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
                    Login(username, password);
                } else {
                    Toast.makeText(getApplicationContext(), Util.NET_UNCONNECTION, Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.register:
                if (Util.isNetworkAvailable(LoginActivity.this)) {
                    Intent intent2 = new Intent(this, RegisterActivity.class);
                    startActivity(intent2);
                } else {
                    Toast.makeText(getApplicationContext(), Util.NET_UNCONNECTION, Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.forgetPassword:
                if (Util.isNetworkAvailable(LoginActivity.this)) {
                    Intent intent3 = new Intent(this, ForgetActivity.class);
                    startActivity(intent3);
                    break;
                } else {
                    Toast.makeText(getApplicationContext(), Util.NET_UNCONNECTION, Toast.LENGTH_LONG).show();
                }
            default:
                break;
        }
    }

    private void Login(String username, String password) {
        OkHttpUtils.get(Util.SERVER_ADDR + "/login?userName=" + username + "&userPassword=" + password,
                new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        super.onFinish(status, msg);
                        //解析
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                        ServerResponse<UserVO> serverResponse = gson.fromJson(msg, new TypeToken<ServerResponse<UserVO>>() {
                        }.getType());
                        int status1 = serverResponse.getStatus();

                        //登陆成功
                        if (status1 == 0) {

                            SharedPreferencesUtil util = SharedPreferencesUtil.getInstance(LoginActivity.this);
                            util.delete("isLogin");
                            util.delete("userId");
                            util.delete("userNickname");

                            util.putBoolean("isLogin", true);
                            util.putInt("userId", serverResponse.getData().getUserId());
                            util.putString("userNickname", serverResponse.getData().getUserNickname());
                            util.putString("userHeadImg", serverResponse.getData().getUserHeadImg());

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        Looper.prepare();
                        Toast.makeText(LoginActivity.this, serverResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
    }


}