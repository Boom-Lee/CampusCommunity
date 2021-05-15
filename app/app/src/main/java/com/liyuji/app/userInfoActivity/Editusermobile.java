package com.liyuji.app.userInfoActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.liyuji.app.R;
import com.liyuji.app.utils.OkHttpCallback;
import com.liyuji.app.utils.OkHttpUtils;
import com.liyuji.app.utils.Util;

public class Editusermobile extends AppCompatActivity implements View.OnClickListener {

    int userId;
    long userMobile;
    String userMobileS;
    private TextView mReBack;
    private EditText mUserMobile;
    private Button mCertain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editusermobile);

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        userMobile = intent.getLongExtra("userMobile", 0);
        System.out.println("得到的用户编号：" + userId + " 得到的手机号：" + userMobile);

        userMobileS = userMobile + "";

        mReBack = findViewById(R.id.reBack);
        mUserMobile = findViewById(R.id.user_mobile);
        mCertain = findViewById(R.id.certain);

        if (userMobileS != null && userMobile != 0) {
            mUserMobile.setText(userMobileS);
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
                String EditUserMobile = mUserMobile.getText().toString() + "";
                mUserMobile.setError(null);

                if (EditUserMobile.length() != 11) {
                    mUserMobile.setError("手机号应为11位");
                    mUserMobile.requestFocus();
                    return;
                }

                long EditUserMobileL = Long.valueOf(EditUserMobile).longValue();
                update(userId, EditUserMobileL);
                break;
            default:
                break;
        }
    }

    private void update(int userId, long EditUserMobileL) {
        OkHttpUtils.get(Util.SERVER_ADDR + "/updateUserMobile?userId=" + userId + "&userMobile=" + EditUserMobileL,
                new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        super.onFinish(status, msg);

                        Intent intent = new Intent(Editusermobile.this, EditinfoActivity.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    }
                });
    }
}