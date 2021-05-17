package com.liyuji.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liyuji.app.R;
import com.liyuji.app.adapter.BrowseAdapter;
import com.liyuji.app.utils.OkHttpCallback;
import com.liyuji.app.utils.OkHttpUtils;
import com.liyuji.app.utils.Util;
import com.liyuji.app.vo.BrowseVO;

import java.util.ArrayList;
import java.util.List;

public class BrowseActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView reBack;

    int userId = 0;
    ListView listView;
    List<BrowseVO> browseVOList = new ArrayList<>();
    BrowseAdapter adapter;
    LayoutInflater inflater;
    int articleId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        inflater = getLayoutInflater();
        listView = findViewById(R.id.browse_list);

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);

        OkHttpUtils.get(Util.SERVER_ADDR + "/selBrowse?userId=" + userId, new OkHttpCallback() {

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
                        System.out.println(jsonObject);
                        BrowseVO browseVO = new BrowseVO();

                        browseVO.userBrowseId = jsonObject.getInteger("userBrowseId");
                        browseVO.userId = jsonObject.getInteger("userId");
                        browseVO.articleId = jsonObject.getInteger("articleId");
                        browseVO.userBrowseDate = jsonObject.getDate("userBrowseDate");
                        browseVO.userNickName = jsonObject.getString("userNickName");
                        browseVO.userHeadImg = jsonObject.getString("userHeadImg");
                        browseVO.articleContent = jsonObject.getString("articleContent");
                        browseVO.articleImg = jsonObject.getString("articleImg");
                        browseVO.articleDate = jsonObject.getDate("articleDate");

                        System.out.println(browseVO);
                        browseVOList.add(browseVO);
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new BrowseAdapter(inflater, browseVOList);
                        listView.setAdapter(adapter);
                    }
                });
            }
        });

        reBack = findViewById(R.id.reBack);

        reBack.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Adapter itemAdapter = parent.getAdapter();
                BrowseVO map = (BrowseVO) itemAdapter.getItem(position);
                articleId = map.getArticleId();

                Intent intent = new Intent(BrowseActivity.this, ArticleActivity.class);
                intent.putExtra("articleId", articleId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reBack:
                finish();
                break;
            default:
                break;
        }
    }
}