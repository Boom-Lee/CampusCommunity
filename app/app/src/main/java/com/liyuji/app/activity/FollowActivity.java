package com.liyuji.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liyuji.app.R;
import com.liyuji.app.adapter.FollowAdapter;
import com.liyuji.app.utils.OkHttpCallback;
import com.liyuji.app.utils.OkHttpUtils;
import com.liyuji.app.utils.Util;
import com.liyuji.app.vo.FollowVO;

import java.util.ArrayList;
import java.util.List;

public class FollowActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView reBack;

    int userId = 0;
    ListView listView;
    LayoutInflater inflater;
    FollowAdapter adapter;
    List<FollowVO> followVOList = new ArrayList<>();
    int followedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);

        listView = findViewById(R.id.follow_list);
        reBack = findViewById(R.id.reBack);

        inflater = getLayoutInflater();

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);

        setAdapter(userId);

        reBack.setOnClickListener(this);
    }

    private void setAdapter(int userId) {
        OkHttpUtils.get(Util.SERVER_ADDR + "/selFollow?userId=" + userId, new
                OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        super.onFinish(status, msg);
                        //将字符转换成JSONOBJECT对象
                        JSONObject response = JSONObject.parseObject(msg);
                        //得到里面data的值
                        JSONArray jsonArray = response.getJSONArray("data");
                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.size(); i++) {
                                // 放入 object
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                FollowVO followVO = new FollowVO();

                                followVO.userFollowId = jsonObject.getInteger("userFollowId");
                                followVO.userId = jsonObject.getInteger("userId");
                                followVO.followedId = jsonObject.getInteger("followedId");
                                followVO.followDate = jsonObject.getDate("followDate");
                                followVO.userNickname = jsonObject.getString("userNickname");
                                followVO.userHeadImg = jsonObject.getString("userHeadImg");

                                followVOList.add(followVO);
                            }
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new FollowAdapter(inflater, followVOList);
                                listView.setAdapter(adapter);
                            }
                        });
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reBack:
                Intent intentR = new Intent(FollowActivity.this, MainActivity.class);
                intentR.putExtra("id", Util.PERSONALFRAGMENT);
                startActivity(intentR);
                break;
            default:
                break;
        }
    }

}