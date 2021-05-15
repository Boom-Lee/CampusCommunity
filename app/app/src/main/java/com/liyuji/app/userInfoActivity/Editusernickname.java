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
import com.liyuji.app.utils.SharedPreferencesUtil;
import com.liyuji.app.utils.Util;

public class Editusernickname extends AppCompatActivity implements View.OnClickListener {

    int userId;
    String userNickname;
    private TextView mReBack;
    private TextView mUserNickname;
    private EditText mEditUsernickname;
    private Button mCertain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editusernickname);

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        userNickname = intent.getStringExtra("userNickname");
        System.out.println("得到的用户编号：" + userId + " 得到的用户昵称：" + userNickname);

        mReBack = findViewById(R.id.reBack);
        mUserNickname = findViewById(R.id.user_nickname);
        mEditUsernickname = findViewById(R.id.edit_usernickname);
        mCertain = findViewById(R.id.certain);

        if (userNickname != null) {
            mUserNickname.setText(userNickname);
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
                String Editusernickname = mEditUsernickname.getText().toString();
                mEditUsernickname.setError(null);

                if (TextUtils.isEmpty(Editusernickname)) {
                    mEditUsernickname.setError("昵称不能为空");
                    mEditUsernickname.requestFocus();
                    return;
                }

                update(userId, Editusernickname);
                    SharedPreferencesUtil util = SharedPreferencesUtil.getInstance(this);
                    util.delete("userNickname");
                    util.putString("userNickname",Editusernickname);
                break;
            default:
                break;
        }
    }

    private void update(int userId, String editusernickname) {
        OkHttpUtils.get(Util.SERVER_ADDR + "/updateUserNickname?userId=" + userId + "&userNickname=" + editusernickname,
                new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        super.onFinish(status, msg);

                        Intent intent = new Intent(Editusernickname.this, EditinfoActivity.class);
                        intent.putExtra("userId",userId);
                        startActivity(intent);
                    }
                });
    }
}