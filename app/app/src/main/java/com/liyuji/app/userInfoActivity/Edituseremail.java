package com.liyuji.app.userInfoActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.liyuji.app.R;
import com.liyuji.app.utils.OkHttpCallback;
import com.liyuji.app.utils.OkHttpUtils;
import com.liyuji.app.utils.Util;

public class Edituseremail extends AppCompatActivity implements View.OnClickListener {

    int userId;
    String userEmail;
    private TextView mReBack;
    private EditText mUserEmail;
    private Button mCertain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editemail);

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        userEmail = intent.getStringExtra("userEmail");
        System.out.println("得到的用户编号：" + userId + " 得到的邮箱：" + userEmail);

        mReBack = findViewById(R.id.reBack);
        mUserEmail = findViewById(R.id.user_email);
        mCertain = findViewById(R.id.certain);

        if (userEmail != null) {
            mUserEmail.setText(userEmail);
        }

        mReBack.setOnClickListener(this);
        mCertain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reBack:
                finish();
                break;
            case R.id.certain:
                String EditUserEmail = mUserEmail.getText().toString();
                mUserEmail.setError(null);

                if (TextUtils.isEmpty(EditUserEmail)) {
                    mUserEmail.setError("邮箱不能为空");
                    mUserEmail.requestFocus();
                    return;
                }

                update(userId, EditUserEmail);
                break;
            default:
                break;
        }
    }

    private void update(int userId, String EditUserEmail) {
        OkHttpUtils.get(Util.SERVER_ADDR + "/updateUserEmail?userId=" + userId + "&userEmail=" + EditUserEmail,
                new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        super.onFinish(status, msg);

                        Intent intent = new Intent(Edituseremail.this, EditinfoActivity.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    }
                });
    }
}