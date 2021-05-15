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
import com.liyuji.app.adapter.LikeAdapter;
import com.liyuji.app.utils.OkHttpCallback;
import com.liyuji.app.utils.OkHttpUtils;
import com.liyuji.app.utils.Util;
import com.liyuji.app.vo.ArticleLikeVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author L
 */
public class LikeActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView reBack;

    int userId = 0;
    ListView listView;
    List<ArticleLikeVO> articleLikeVOList = new ArrayList<>();
    LikeAdapter adapter;
    LayoutInflater inflater;
    int articleId = 0;
    int checkUserId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        inflater = getLayoutInflater();
        listView = findViewById(R.id.like_list);


        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);

        OkHttpUtils.get(Util.SERVER_ADDR + "/selLike?userId=" + userId, new OkHttpCallback() {

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
                        ArticleLikeVO articleLikeVO = new ArticleLikeVO();

                        articleLikeVO.articleLikeId = jsonObject.getInteger("articleLikeId");
                        articleLikeVO.userId = jsonObject.getInteger("userId");
                        articleLikeVO.articleId = jsonObject.getInteger("articleId");
                        articleLikeVO.articleLikeDate = jsonObject.getDate("articleLikeDate");
                        articleLikeVO.userNickname = jsonObject.getString("userNickname");
                        articleLikeVO.userHeadImg = jsonObject.getString("userHeadImg");
                        articleLikeVO.articleContent = jsonObject.getString("articleContent");
                        articleLikeVO.articleImg = jsonObject.getString("articleImg");
                        articleLikeVO.articleDate = jsonObject.getDate("articleDate");

                        System.out.println(articleLikeVO);
                        articleLikeVOList.add(articleLikeVO);
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new LikeAdapter(inflater, articleLikeVOList);
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
                ArticleLikeVO map = (ArticleLikeVO) itemAdapter.getItem(position);
                articleId = map.getArticleId();
                //artcle需要判断 所以随便给个值
                checkUserId = 0;
                System.out.println("当前动态点击编号：" + articleId + " 当前动态发布的用户编号：" + checkUserId);
                Intent intent = new Intent(LikeActivity.this, ArticleActivity.class);
                intent.putExtra("articleId",articleId);
                intent.putExtra("checkUserId",checkUserId);
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