package com.liyuji.app.Activity;

import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liyuji.app.R;
import com.liyuji.app.utils.OkHttpCallback;
import com.liyuji.app.utils.OkHttpUtils;
import com.liyuji.app.utils.SharedPreferencesUtil;
import com.liyuji.app.vo.ServerResponse;
import com.liyuji.app.vo.UserVO;
import com.liyuji.app.utils.Util;

/**
 * @author L
 */
public class EditpasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EditpasswordActivity";
    public EditText userPassword_editText;
    public EditText userPasswordCheck_editText;
    public TextView conform;
    public TextView reBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editpassword);

        SharedPreferencesUtil util = SharedPreferencesUtil.getInstance(EditpasswordActivity.this);

        String lala = util.readString("user");


        Log.d(TAG, "onCreate: " + lala);

        userPassword_editText = findViewById(R.id.userPassword);
        userPasswordCheck_editText = findViewById(R.id.userPasswordCheck);
        conform = findViewById(R.id.conform_edit);
        reBack = findViewById(R.id.reBack);


        conform.setOnClickListener(this);
        reBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.conform_edit:
                String userPassword = userPassword_editText.getText().toString();
                String userPasswordCheck = userPasswordCheck_editText.getText().toString();
                userPassword_editText.setError(null);
                userPasswordCheck_editText.setError(null);

                // 校验密码
                if (TextUtils.isEmpty(userPassword) || userPassword.length() <= 8) {
                    userPassword_editText.setError("密码不少于9位");
                    userPassword_editText.requestFocus();
                    return;
                }
                if (!userPasswordCheck.equals(userPassword)) {
                    userPasswordCheck_editText.setError("两次密码需要一致");
                    userPasswordCheck_editText.requestFocus();
                    return;
                }
                updatePassword(userPassword);
                break;
            case R.id.reBack:
                finish();
                break;
            default:
                break;

        }

    }

    private void updatePassword(String userPassword) {
        OkHttpUtils.get(Util.SERVER_ADDR + "/updatePassword?userPassword=" + userPassword,
                new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        super.onFinish(status, msg);
//解析
                        Gson gson = new Gson();
                        ServerResponse<UserVO> serverResponse = gson.fromJson(msg, new TypeToken<ServerResponse<UserVO>>() {
                        }.getType());
                        int status1 = serverResponse.getStatus();
//                       修改成功
                        if (status1 == 0) {

                            finish();
                        }
                        Looper.prepare();
                        Toast.makeText(EditpasswordActivity.this, serverResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
    }


}