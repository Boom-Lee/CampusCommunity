package com.liyuji.app.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSONObject;
import com.liyuji.app.Activity.EditpasswordActivity;
import com.liyuji.app.Activity.LoginActivity;
import com.liyuji.app.R;
import com.liyuji.app.utils.OkHttpCallback;
import com.liyuji.app.utils.OkHttpUtils;
import com.liyuji.app.utils.SharedPreferencesUtil;
import com.liyuji.app.vo.UserVO;
import com.liyuji.app.utils.Util;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author L
 */
public class PersonalFragment extends Fragment implements View.OnClickListener {

    TextView userNickname;
    TextView Follow_count;
    TextView Like_count;
    TextView Browse_count;
    CircleImageView headImg;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_personal, container, false);

        userNickname = root.findViewById(R.id.userNickname);
        Follow_count = root.findViewById(R.id.follow_count);
        Browse_count = root.findViewById(R.id.browse_count);
        Like_count = root.findViewById(R.id.like_count);
        headImg = root.findViewById(R.id.headImg);

        SharedPreferencesUtil.getInstance(getActivity()).readString("user");
        UserVO userVO = (UserVO) SharedPreferencesUtil.getInstance(getActivity()).readObject("user", UserVO.class);
        userNickname.setText(userVO.getUserNickname());
        int userId = userVO.getUserId();

        get_browse_count(userId);
        get_like_count(userId);
        get_follow_count(userId);

        root.findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtil.getInstance(getActivity()).clear();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "退出登录成功！", Toast.LENGTH_SHORT).show();
            }
        });

        root.findViewById(R.id.editPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditpasswordActivity.class);
                startActivity(intent);
            }
        });

        root.findViewById(R.id.headImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setItems(new String[]{"拍照", "相册"},
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击后，具体处理，
                                Log.i("tag","which " + which);
                                Log.i("tag","dialog " + dialog);
                                // 判断 which 从而判断用户点击的是第几个
                                if (which == 0) {
                                    // 调用系统相机权限 弹出是否同意授权
                                    requestPermissions(new String[]{
                                            Manifest.permission.CAMERA,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                                    },10);// 动态申请权限
                                } else if (which == 1) {
                                    // 调用系统相机 直接打开
                                    Intent intent = new Intent();
                                    // ACTION_GET_CONTENT = "android.intent.action.GET_CONTENT"
                                    intent.setAction(Intent.ACTION_PICK); // 设置跳转到相册
                                    intent.setType("image/*"); // 设置类型 选图片
                                    startActivityForResult(intent,3);
                                }
                            }
                        });
                dialog.show();
            }
        });

        return root;
    }

    private void get_browse_count(int userId) {
        OkHttpUtils.get(Util.SERVER_ADDR + "countUserBrowse?userId=" + userId,
                new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        super.onFinish(status, msg);
                        //解析 Gson 解析int会变成double
                        //serverResponse response = JSON.parseObject(msg, ServerResponse.class);
                        //System.out.println(response.getData());
                        JSONObject response = JSONObject.parseObject(msg);
                        String browse_count = response.getString("data");
// 跳回主线程 更新UI
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Browse_count.setText(browse_count);
                            }
                        });
                    }
                });
    }

    private void get_like_count(int userId) {
        OkHttpUtils.get(Util.SERVER_ADDR + "countUserLike?userId=" + userId,
                new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        super.onFinish(status, msg);
//                        //解析 Gson 解析int会变成double
//                        ServerResponse response = JSON.parseObject(msg, ServerResponse.class);
//                        System.out.println(response.getData());
                        JSONObject response = JSONObject.parseObject(msg);
                        String like_count = response.getString("data");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Like_count.setText(like_count);
                            }
                        });
                    }
                });
    }


    private void get_follow_count(int userId) {
        //关注
        OkHttpUtils.get(Util.SERVER_ADDR + "countUserFollow?userId=" + userId,
                new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        super.onFinish(status, msg);
                        //解析 Gson 解析int会变成double
//                        ServerResponse response = JSON.parseObject(msg, ServerResponse.class);
//                        System.out.println(response.getData());
                        JSONObject response = JSONObject.parseObject(msg);
                        String follow_count = response.getString("data");

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Follow_count.setText(follow_count);
                            }
                        });
                    }
                });
    }

    @Override
    public void onClick(View v) {

    }
}