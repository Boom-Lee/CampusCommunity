package com.liyuji.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.liyuji.app.R;
import com.liyuji.app.adapter.ArticleAdapter;
import com.liyuji.app.utils.OkHttpCallback;
import com.liyuji.app.utils.OkHttpUtils;
import com.liyuji.app.utils.SharedPreferencesUtil;
import com.liyuji.app.utils.Util;
import com.liyuji.app.vo.ArticleVO;
import com.liyuji.app.vo.ServerResponse;
import com.liyuji.app.vo.UserVO;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author L
 */
public class PersonalpageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mReBack;
    private CircleImageView mUserHeadImg;
    private LinearLayout mLikeBtn;
    private TextView mLikeCount;
    private LinearLayout mFollowBtn;
    private TextView mFollowCount;
    private TabPageIndicator mIndicator;
    private ViewPager mVp;
    private LinearLayout mUserFollowLayout;
    private Button mUserFollow;
    private TextView mUserNickName;

    ListView listView;
    List<ArticleVO> articleVOList = new ArrayList<>();
    ArticleAdapter adapter;
    LayoutInflater inflater;

//    private List<Fragment> fragmentList;
//    private static final String[] TITLE = new String[]{"动态", "喜欢"};

    int userId = 0;
    int followedId = 0;
    int countFollow = 0;
    int userFollowId = 0;
    int articleId = 0;
    int checkUserId = 0;
    String followStatus = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalpage);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        followedId = intent.getIntExtra("userId", 0);
        System.out.println("当前个人主页用户编号: " + followedId);

        SharedPreferencesUtil util = SharedPreferencesUtil.getInstance(PersonalpageActivity.this);
        userId = util.readInt("userId");
        System.out.println("当前登录用户编号: " + userId);

        mReBack = findViewById(R.id.reBack);
        mUserHeadImg = findViewById(R.id.user_headImg);
        mLikeBtn = findViewById(R.id.like_btn);
        mLikeCount = findViewById(R.id.like_count);
        mFollowBtn = findViewById(R.id.follow_btn);
        mFollowCount = findViewById(R.id.follow_count);
//        mIndicator = findViewById(R.id.indicator);
//        mVp = findViewById(R.id.vp);
        mUserFollowLayout = findViewById(R.id.user_follow_layout);
        mUserFollow = findViewById(R.id.user_follow);
        mUserNickName = findViewById(R.id.user_nickName);

        inflater = getLayoutInflater();
        listView = findViewById(R.id.article_list);

        if (followedId != userId) {
            selIsFollow();
        } else {
            mUserFollowLayout.setVisibility(View.GONE);
        }

        initData();

        mReBack.setOnClickListener(this);
        mFollowBtn.setOnClickListener(this);
        mLikeBtn.setOnClickListener(this);
        mUserFollow.setOnClickListener(this);
    }

    private void initData() {
        getUserInfo();
        getLikeCount();
        getFollowCount();
        getArticleShow();
//        getViewPager();
    }

    private void getArticleShow() {
        OkHttpUtils.get(Util.SERVER_ADDR + "articleListByUser?userId=" + followedId, new OkHttpCallback() {
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //加载ListView
                            adapter = new ArticleAdapter(articleVOList, inflater);
                            listView.setAdapter(adapter);
                            setListViewHeightBasedOnChildren(listView);
                        }
                    });
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Adapter itemAdapter = parent.getAdapter();
                ArticleVO map = (ArticleVO) itemAdapter.getItem(position);
                articleId = map.getArticleId();
                checkUserId = map.getUserId();
                System.out.println("当前动态点击编号：" + articleId + " 当前动态发布的用户编号：" + checkUserId);
                Intent intent = new Intent(PersonalpageActivity.this, ArticleActivity.class);
                intent.putExtra("articleId", articleId);
                intent.putExtra("checkUserId", checkUserId);
                startActivity(intent);
            }
        });
    }

//    private void getViewPager() {
//        FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(getSupportFragmentManager());
//        mVp.setAdapter(adapter);
//        mIndicator.setViewPager(mVp);
//        //如果要设置监听ViewPager中包含的Fragment的改变(滑动切换页面)，使用OnPageChangeListener为它指定一个监听器，那么不能像之前那样直接设置在ViewPager上了，而要设置在Indicator上，
//        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int arg0) {
//                Toast.makeText(getApplicationContext(), TITLE[arg0], Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onPageScrolled(int arg0, float arg1, int arg2) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int arg0) {
//
//            }
//        });
//    }
//
//    /**
//     * 定义ViewPager的适配器
//     */
//    class TabPageIndicatorAdapter extends FragmentPagerAdapter {
//        public TabPageIndicatorAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            setFragment();
//            return fragmentList.get(position);
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return TITLE[position];
//        }
//
//        @Override
//        public int getCount() {
//            return TITLE.length;
//        }
//    }
//
//    private void setFragment(){
//        fragmentList = new ArrayList<>();
//        fragmentList.add(new UserArticleFragment());
//        fragmentList.add(new UserLikeFragment());
//    }

    private void selIsFollow() {
        OkHttpUtils.get(Util.SERVER_ADDR + "selIsFollow?userId=" + userId + "&followedId=" + followedId, new OkHttpCallback() {
            @Override
            public void onFinish(String status, String msg) {
                super.onFinish(status, msg);
                JSONObject response = JSONObject.parseObject(msg);
                JSONObject jsonObject = response.getJSONObject("data");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (jsonObject != null) {
                            countFollow = 1;
                            userFollowId = jsonObject.getInteger("userFollowId");
                            followStatus = "取消关注";
                        } else {
                            followStatus = "关注";
                        }
                        System.out.println("一开始得到(0为需要关注，1为取消关注): " + countFollow);
                        System.out.println("当前设置状态   " + followStatus);
                        mUserFollow.setText(followStatus);
                    }
                }).start();
            }
        });
    }

    private void addFollow() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils.get(Util.SERVER_ADDR + "addFollow?userId=" + userId + "&followedId=" + followedId, new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        super.onFinish(status, msg);
                        JSONObject jsonObject = JSONObject.parseObject(msg);
                        countFollow = jsonObject.getInteger("status");
                        JSONObject jsonData = jsonObject.getJSONObject("data");
                        System.out.println("添加Follow得到(0为成功，1为失败): " + countFollow);
                        // 成功返回0
                        if (countFollow == 0) {
                            countFollow = 1;
                            userFollowId = jsonData.getInteger("userFollowId");
                        }
                        System.out.println("添加Follow判断后得到(0为关注，1为取消关注): " + countFollow);
                        changeFollow();
                    }
                });
            }
        }).start();
    }

    private void delFollow() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils.get(Util.SERVER_ADDR + "delFollow?userFollowId=" + userFollowId, new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        JSONObject jsonObject = JSONObject.parseObject(msg);
                        countFollow = jsonObject.getInteger("status");
                        System.out.println("删除Follow得到(0为成功，1为失败): " + countFollow);
                        //成功返回0
                        if (countFollow != 0) {
                            countFollow = 1;
                        }
                        System.out.println("删除Follow判断后得到(0为关注，1为取消关注): " + countFollow);
                        changeFollow();
                    }
                });
            }
        }).start();
    }

    private void changeFollow() {
        if (countFollow == 1) {
            followStatus = "取消关注";
        } else {
            followStatus = "关注";
        }
        System.out.println("当前设置状态   " + followStatus);
        mUserFollow.setText(followStatus);
    }

    private void getUserInfo() {
        OkHttpUtils.get(Util.SERVER_ADDR + "showInfo?userId=" + followedId, new
                OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                        ServerResponse<UserVO> serverResponse = gson.fromJson(msg, new TypeToken<ServerResponse<UserVO>>() {
                        }.getType());

                        String userNickname = serverResponse.getData().getUserNickname();
                        String userHeadImg = serverResponse.getData().getUserHeadImg();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (userNickname != null) {
                                    mUserNickName.setText(userNickname);
                                }
                                if (userHeadImg != null) {
                                    Glide.with(PersonalpageActivity.this)
                                            .load(userHeadImg)
                                            .into(mUserHeadImg);
                                }
                            }
                        });
                    }
                });
    }

    private void getLikeCount() {
        OkHttpUtils.get(Util.SERVER_ADDR + "countUserLike?userId=" + followedId,
                new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        super.onFinish(status, msg);
                        JSONObject response = JSONObject.parseObject(msg);
                        String like_count = response.getString("data");
                        // 跳回主线程 更新UI
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (like_count != null) {
                                    mLikeCount.setText(like_count);
                                }
                            }
                        });
                    }
                });
    }

    private void getFollowCount() {
        //关注
        OkHttpUtils.get(Util.SERVER_ADDR + "countUserFollow?userId=" + followedId,
                new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        super.onFinish(status, msg);
                        JSONObject response = JSONObject.parseObject(msg);
                        String follow_count = response.getString("data");
                        // 跳回主线程 更新UI
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (follow_count != null) {
                                    mFollowCount.setText(follow_count);
                                }
                            }
                        });
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reBack:
                finish();
                break;
            case R.id.user_follow:
                if (countFollow != 0) {
                    delFollow();
                } else {
                    addFollow();
                }
                break;
            case R.id.like_btn:
                Intent intentL = new Intent(PersonalpageActivity.this, LikeActivity.class);
                //在Intent对象当中添加一个键值对
                intentL.putExtra("userId", followedId);
                startActivity(intentL);
                break;
            case R.id.follow_btn:
                Intent intentF = new Intent(PersonalpageActivity.this, FollowActivity.class);
                //在Intent对象当中添加一个键值对
                intentF.putExtra("userId", followedId);
                startActivity(intentF);
                break;
            default:
                break;
        }
    }

    /**
     * 动态设置ListView的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) {
            return;
        }
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}