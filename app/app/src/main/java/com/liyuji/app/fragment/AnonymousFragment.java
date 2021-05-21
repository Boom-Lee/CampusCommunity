package com.liyuji.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liyuji.app.R;
import com.liyuji.app.activity.ArticleActivity;
import com.liyuji.app.activity.DeliverarticleActivity;
import com.liyuji.app.adapter.AnonymousAdapter;
import com.liyuji.app.utils.OkHttpCallback;
import com.liyuji.app.utils.OkHttpUtils;
import com.liyuji.app.utils.Util;
import com.liyuji.app.vo.ArticleVO;

import java.util.ArrayList;
import java.util.List;

public class AnonymousFragment extends Fragment {

    private ListView listView;
    List<ArticleVO> articleVOList = new ArrayList<>();
    int COMMUNITY_ID = 2;
    AnonymousAdapter adapter;
    int articleId = 0;
    int checkUserId = 0;
    int COMMUNITY_STATUS = 0;
    View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        selComStatus();
        if (COMMUNITY_STATUS == 0) {
            root = inflater.inflate(R.layout.fragment_anonymous, container, false);
            articleVOList.clear();
            listView = root.findViewById(R.id.anonymous_list);

            //发布
            root.findViewById(R.id.article_deliver).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DeliverarticleActivity.class);
                    intent.putExtra("COMMUNITY_ID", COMMUNITY_ID);
                    startActivity(intent);
                }
            });

            OkHttpUtils.get(Util.SERVER_ADDR + "articleListByCom?communityId=" + COMMUNITY_ID, new OkHttpCallback() {
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
                            ArticleVO articleVO = new ArticleVO();

                            articleVO.articleId = jsonObject.getInteger("articleId");
                            articleVO.userId = jsonObject.getInteger("userId");
                            articleVO.articleContent = jsonObject.getString("articleContent");
                            articleVO.articleImg = jsonObject.getString("articleImg");
                            articleVO.articleDate = jsonObject.getDate("articleDate");
                            articleVO.articleStatus = jsonObject.getString("articleStatus");
                            articleVO.communityId = jsonObject.getInteger("communityId");
                            articleVO.userNickname = jsonObject.getString("userNickname");
                            articleVO.userHeadImg = jsonObject.getString("userHeadImg");

                            articleVOList.add(articleVO);
                        }
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //加载ListView
                                adapter = new AnonymousAdapter(articleVOList, inflater);
                                listView.setAdapter(adapter);
                            }
                        });
                    }
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int firstVisiblePosition = listView.getFirstVisiblePosition(); //屏幕内当前可以看见的第一条数据
                    if (position - firstVisiblePosition >= 0) {
                        View itemView = listView.getChildAt(position - firstVisiblePosition);
                        Adapter itemAdapter = parent.getAdapter();
                        ArticleVO map = (ArticleVO) itemAdapter.getItem(position);
                        articleId = map.getArticleId();
                        checkUserId = map.getUserId();
                        System.out.println("当前匿名动态点击编号：" + articleId + " 当前匿名动态发布的用户编号：" + checkUserId);
                    }
                    Intent intent = new Intent(getContext(), ArticleActivity.class);
                    intent.putExtra("articleId", articleId);
                    intent.putExtra("checkUserId", checkUserId);
                    startActivity(intent);
                }
            });
        } else {
            root = inflater.inflate(R.layout.com_status, container, false);
        }

        return root;
    }


    /**
     * 判断社区状态
     */
    private void selComStatus() {
        OkHttpUtils.get(Util.SERVER_ADDR + "showComStatus?communityId=" + COMMUNITY_ID, new OkHttpCallback() {
            @Override
            public void onFinish(String status, String msg) {
                super.onFinish(status, msg);
                JSONObject response = JSONObject.parseObject(msg);
                COMMUNITY_STATUS = response.getInteger("data");
                System.out.println("当前页面的COMMUNITY_STATUS:" + COMMUNITY_STATUS);
            }
        });
    }
}