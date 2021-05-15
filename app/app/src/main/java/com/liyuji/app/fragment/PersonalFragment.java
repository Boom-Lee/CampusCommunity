package com.liyuji.app.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.liyuji.app.activity.BrowseActivity;
import com.liyuji.app.activity.EditpasswordActivity;
import com.liyuji.app.activity.FollowActivity;
import com.liyuji.app.activity.LikeActivity;
import com.liyuji.app.activity.LoginActivity;
import com.liyuji.app.R;
import com.liyuji.app.userInfoActivity.EditinfoActivity;
import com.liyuji.app.utils.OkHttpCallback;
import com.liyuji.app.utils.OkHttpUtils;
import com.liyuji.app.utils.SharedPreferencesUtil;
import com.liyuji.app.utils.Util;
import com.liyuji.app.vo.ServerResponse;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author L
 */
public class PersonalFragment extends Fragment implements View.OnClickListener {


    private static final String TAG = "PersonalFragment";
    TextView userNickname;
    TextView Follow_count;
    TextView Like_count;
    TextView Browse_count;
    LinearLayout browse_btn, like_btn, follow_btn;
    CircleImageView headImg;
    Uri ImgUri;
    int userId;
    String userHeadImg;
    String userNickNameS;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_personal, container, false);

        initView(root);

        SharedPreferencesUtil util = SharedPreferencesUtil.getInstance(getActivity());
        userId = util.readInt("userId");
        userHeadImg = util.readString("userHeadImg");
        userNickNameS = util.readString("userNickname");

        get_browse_count(userId);
        get_like_count(userId);
        get_follow_count(userId);

        // 设置用户昵称
        userNickname.setText(userNickNameS);
        // 头像显示
        Glide.with(PersonalFragment.this)
                .load(userHeadImg)
                .fitCenter()
                .into(headImg);
        Log.d(TAG, "onCreateView: 头像显示  " + userHeadImg);

        // 更换头像
        root.findViewById(R.id.headImg).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                        dialog.setTitle("添加图片");
//                        dialog.setItems(new String[]{"拍照", "相册"},
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        // 点击后，具体处理，
//                                        Log.i("tag", "which " + which);
//                                        Log.i("tag", "dialog " + dialog);
//                                        dialog.dismiss();
//                                        switch (which) {
//                                            case 0:
//                                                break;
//                                            case 1:
//                                                //打开相册
//                                                gotoPickImage();
//                                                break;
//                                            default:
//                                                break;
//                                        }
//                                    }
//                                });
                        dialog.setItems(new String[]{"相册"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                gotoPickImage();
                            }
                        });
                        dialog.show();
                    }
                });

        //修改密码
        root.findViewById(R.id.editPassword).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), EditpasswordActivity.class);
                        startActivity(intent);
                    }
                });
        //修改个人资料
        root.findViewById(R.id.editUserInfo).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), EditinfoActivity.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    }
                });


        return root;
    }

    private void initView(View root) {
        userNickname = root.findViewById(R.id.userNickname);
        Follow_count = root.findViewById(R.id.follow_count);
        Browse_count = root.findViewById(R.id.browse_count);
        Like_count = root.findViewById(R.id.like_count);
        browse_btn = root.findViewById(R.id.browse_btn);
        like_btn = root.findViewById(R.id.like_btn);
        follow_btn = root.findViewById(R.id.follow_btn);
        headImg = root.findViewById(R.id.headImg);
        LinearLayout exit = root.findViewById(R.id.exit);

        browse_btn.setOnClickListener(this);
        like_btn.setOnClickListener(this);
        follow_btn.setOnClickListener(this);
        exit.setOnClickListener(this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = null;
            if (requestCode == 1) {
                //选择的图片的Uri
                ImgUri = data.getData();

                try {
                    bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(ImgUri));
                    Glide.with(PersonalFragment.this)
                            .load(bitmap)
                            .fitCenter()
                            .into(headImg);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                File file = BitmapToFile(bitmap);

                OkHttpUtils.upload(Util.SERVER_ADDR + "uploadHeadImg", userId, file.getAbsolutePath(), new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        super.onFinish(status, msg);
                        Log.e(TAG, "onFinish: " + msg);
                        Gson gson = new Gson();
                        ServerResponse serverResponse = gson.fromJson(msg, ServerResponse.class);
                        String headImgE = (String) serverResponse.getData();
                        // 跳回主线程 更新UI
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(PersonalFragment.this)
                                        .load(headImgE)
                                        .fitCenter()
                                        .into(headImg);
                            }
                        });

                        SharedPreferencesUtil util = SharedPreferencesUtil.getInstance(getActivity());
                        util.delete("userHeadImg");
                        util.putString("userHeadImg", headImgE);

                        Looper.prepare();
                        Toast.makeText(getActivity(), serverResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });

                System.out.println("ImgUri:" + ImgUri + "   " + file.getAbsolutePath());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private File BitmapToFile(Bitmap bitmap) {
        //将要保存图片的路径
        File file = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private void gotoPickImage() {
        //选择相册
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //回调路径
        startActivityForResult(intent, 1);
    }

    private void get_browse_count(int userId) {
        OkHttpUtils.get(Util.SERVER_ADDR + "countUserBrowse?userId=" + userId,
                new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        super.onFinish(status, msg);
                        JSONObject response = JSONObject.parseObject(msg);
                        String browse_count = response.getString("data");
                        // 跳回主线程 更新UI
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (browse_count != null) {
                                    Browse_count.setText(browse_count);
                                }
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
                        JSONObject response = JSONObject.parseObject(msg);
                        String like_count = response.getString("data");
                        // 跳回主线程 更新UI
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (like_count != null) {
                                    Like_count.setText(like_count);
                                }
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
                        JSONObject response = JSONObject.parseObject(msg);
                        String follow_count = response.getString("data");
                        // 跳回主线程 更新UI
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (follow_count != null) {
                                    Follow_count.setText(follow_count);
                                }
                            }
                        });
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.browse_btn:
                Intent intentB = new Intent(getActivity(), BrowseActivity.class);
                //在Intent对象当中添加一个键值对
                intentB.putExtra("userId", userId);
                startActivity(intentB);
                break;
            case R.id.follow_btn:
                Intent intentF = new Intent(getActivity(), FollowActivity.class);
                //在Intent对象当中添加一个键值对
                intentF.putExtra("userId", userId);
                startActivity(intentF);
                break;
            case R.id.like_btn:
                Intent intentL = new Intent(getActivity(), LikeActivity.class);
                //在Intent对象当中添加一个键值对
                intentL.putExtra("userId", userId);
                startActivity(intentL);
                break;
            case R.id.exit:
                SharedPreferencesUtil.getInstance(getActivity()).clear();
                Intent intentE = new Intent(getActivity(), LoginActivity.class);
                startActivity(intentE);
                Toast.makeText(getActivity(), "退出登录成功！", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}