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

public class Edituserschool extends AppCompatActivity implements View.OnClickListener {

    int userId;
    String userSchool;
    private TextView mReBack;
    private EditText mUserSchool;
    private Button mCertain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editschool);

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        userSchool = intent.getStringExtra("userSchool");
        System.out.println("得到的用户编号：" + userId + " 得到的学校：" + userSchool);

        mReBack = findViewById(R.id.reBack);
        mUserSchool = findViewById(R.id.user_school);
        mCertain = findViewById(R.id.certain);

        if (userSchool != null) {
            mUserSchool.setText(userSchool);
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
                String EditUserSchool = mUserSchool.getText().toString();
                mUserSchool.setError(null);

                if (TextUtils.isEmpty(EditUserSchool)) {
                    mUserSchool.setError("学校不能为空");
                    mUserSchool.requestFocus();
                    return;
                }

                update(userId, EditUserSchool);
                break;
            default:
                break;
        }
    }

    private void update(int userId, String EditUserSchool) {
        OkHttpUtils.get(Util.SERVER_ADDR + "/updateUserSchool?userId=" + userId + "&userSchool=" + EditUserSchool,
                new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        super.onFinish(status, msg);

                        Intent intent = new Intent(Edituserschool.this, EditinfoActivity.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    }
                });
    }

}
