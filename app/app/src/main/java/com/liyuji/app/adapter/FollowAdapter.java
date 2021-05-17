package com.liyuji.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.liyuji.app.R;
import com.liyuji.app.utils.OkHttpCallback;
import com.liyuji.app.utils.OkHttpUtils;
import com.liyuji.app.utils.SharedPreferencesUtil;
import com.liyuji.app.utils.Util;
import com.liyuji.app.vo.FollowVO;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author L
 */
public class FollowAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    List<FollowVO> followVoList = new ArrayList<>();
    TextView userNickname;
    CircleImageView headImg;
    Button user_follow;
    String followStatus;
    int countFollow = 0;
    int userId = 0;
    int followedId = 0;
    int userFollowId = 0;
    int checkUserId = 0;

    public FollowAdapter(LayoutInflater mInflater, List<FollowVO> followVoList) {
        this.mInflater = mInflater;
        this.followVoList = followVoList;
    }

    @Override
    public int getCount() {
        return followVoList.size();
    }

    @Override
    public Object getItem(int position) {
        return followVoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FollowVO followVO = (FollowVO) getItem(position);
        View view = mInflater.inflate(R.layout.listitem_follow, null);

        userNickname = view.findViewById(R.id.userNickname);
        headImg = view.findViewById(R.id.headImg);
        user_follow = view.findViewById(R.id.user_follow);

        userId = followVO.getUserId();
        followedId = followVO.getFollowedId();
        userFollowId = followVO.getUserFollowId();
        String userHeadImg = followVO.getUserHeadImg();
        String userNickName = followVO.getUserNickname();

        SharedPreferencesUtil util = SharedPreferencesUtil.getInstance(view.getContext());
        checkUserId = util.readInt("userId");
        if(userId != checkUserId){
            user_follow.setVisibility(View.GONE);
        }

        // 用户昵称设置
        userNickname.setText(userNickName);
        // 用户头像设置
        Glide.with(parent.getContext())
                .load(userHeadImg)
                .into(headImg);
        // 设置关注状态
        selIsFollow();

        // 更新关注状态
        user_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFollow();
            }
        });

        return view;
    }

    private void updateFollow() {
        if (countFollow != 0) {
            delFollow();
        } else {
            addFollow();
        }
    }

    private void changeFollow(int countFollow) {
        if (countFollow == 1) {
            followStatus = "取消关注";
        } else {
            followStatus = "关注";
        }
        System.out.println("当前设置状态   " + followStatus);
        user_follow.setText(followStatus);
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
                        changeFollow(countFollow);
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
                        changeFollow(countFollow);

                    }
                });
            }
        }).start();
    }

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
                        user_follow.setText(followStatus);
                    }
                }).start();
            }
        });
    }

}