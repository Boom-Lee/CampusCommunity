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

public class Edituserintro extends AppCompatActivity implements View.OnClickListener {

    int userId;
    String userIntro;
    private TextView mReBack;
    private EditText mUserIntro;
    private Button mCertain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edituserintro);

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        userIntro = intent.getStringExtra("userIntro");
        System.out.println("得到的用户编号：" + userId + " 得到的简介：" + userIntro);

        mReBack = findViewById(R.id.reBack);
        mUserIntro = findViewById(R.id.user_intro);
        mCertain = findViewById(R.id.certain);

        if (userIntro != null) {
            mUserIntro.setText(userIntro);
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
                String EditUserIntro = mUserIntro.getText().toString();
                mUserIntro.setError(null);

                if (TextUtils.isEmpty(EditUserIntro)) {
                    mUserIntro.setError("简介不能为空");
                    mUserIntro.requestFocus();
                    return;
                }

                update(userId, EditUserIntro);
                break;
            default:
                break;
        }
    }

    private void update(int userId, String EditUserIntro) {
        OkHttpUtils.get(Util.SERVER_ADDR + "/updateUserIntro?userId=" + userId + "&userIntro=" + EditUserIntro,
                new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        super.onFinish(status, msg);

                        Intent intent = new Intent(Edituserintro.this, EditinfoActivity.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    }
                });
    }
}