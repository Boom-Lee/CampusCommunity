package com.liyuji.app.comment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.liyuji.app.R;
import com.liyuji.app.activity.ArticleActivity;
import com.liyuji.app.activity.PersonalpageActivity;
import com.liyuji.app.utils.MyTimeUtils;
import com.liyuji.app.utils.OkHttpCallback;
import com.liyuji.app.utils.OkHttpUtils;
import com.liyuji.app.utils.SharedPreferencesUtil;
import com.liyuji.app.utils.Util;
import com.liyuji.app.vo.ArticleVO;
import com.liyuji.app.vo.CommentVO;
import com.liyuji.app.vo.ReplyVO;
import com.liyuji.app.vo.ServerResponse;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mReBack;
    private Button mCommentDel;
    private CircleImageView mHeadImg;
    private TextView mUserNickname;
    private TextView mCommentTime;
    private TextView mCommentContent;
    private ListView mReplyList;
    private LinearLayout mReplyBtn;

    public List<ReplyVO> replyVOList = new ArrayList<>();
    private LayoutInflater inflater;
    ReplyAdapter replyAdapter;

    /**
     * 评论编号
     */
    int commentId = 0;
    /**
     * 当前用户编号
     */
    int userId = 0;
    /**
     * 评论发布用户编号
     */
    int checkUserId = 0;
    /**
     * 当前文章发布用户编号
     */
    int articleCheckUserId = 0;
    /**
     * 文章编号
     */
    int articleId = 0;

    String userHeadImg = null;
    String userNickname = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        initView();
    }

    private void initView() {

        inflater = getLayoutInflater();

        Intent intent = getIntent();
        commentId = intent.getIntExtra("commentId", 0);

        SharedPreferencesUtil util = SharedPreferencesUtil.getInstance(CommentActivity.this);
        userId = util.readInt("userId");

        mReBack = findViewById(R.id.reBack);
        mCommentDel = findViewById(R.id.comment_del);
        mHeadImg = findViewById(R.id.headImg);
        mUserNickname = findViewById(R.id.userNickname);
        mCommentTime = findViewById(R.id.comment_time);
        mCommentContent = findViewById(R.id.comment_content);
        mReplyList = findViewById(R.id.reply_list);
        mReplyBtn = findViewById(R.id.reply_btn);

        mReBack.setOnClickListener(this);
        mUserNickname.setOnClickListener(this);
        mHeadImg.setOnClickListener(this);
        mCommentDel.setOnClickListener(this);
        mReplyBtn.setOnClickListener(this);

        initData();
    }

    private void initData() {
        OkHttpUtils.get(Util.SERVER_ADDR + "commentByCom?commentId=" + commentId, new OkHttpCallback() {
            @Override
            public void onFinish(String status, String msg) {
                super.onFinish(status, msg);

                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                ServerResponse<CommentVO> serverResponse = gson.fromJson(msg, new TypeToken<ServerResponse<CommentVO>>() {
                }.getType());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String headImg = serverResponse.getData().getUserHeadImg();
                        String userNickname = serverResponse.getData().getUserNickname();
                        String commentContent = serverResponse.getData().getCommentContent();
                        articleId = serverResponse.getData().getArticleId();
                        checkUserId = serverResponse.getData().getFromUid();
                        String commentTime = MyTimeUtils.dateToYmdHms(serverResponse.getData().getCommentDate());

                        // 用户头像设置
                        Glide.with(CommentActivity.this)
                                .load(headImg)
                                .into(mHeadImg);
                        // 用户昵称设置
                        mUserNickname.setText(userNickname);
                        // 评论内容设置
                        mCommentContent.setText(commentContent);
                        // 评论时间设置
                        mCommentTime.setText(commentTime);

                        checkDel();

                    }
                });
            }
        });
        // 设置回复列表
        showReplyList();
    }

    // 设置回复列表
    private void showReplyList() {
        // 设置回复内容
        OkHttpUtils.get(Util.SERVER_ADDR + "replyListByCom?commentId=" + commentId, new OkHttpCallback() {
            @Override
            public void onFinish(String status, String msg) {
                //将字符转换成JSONOBJECT对象
                JSONObject response = JSONObject.parseObject(msg);
                //得到里面data的值
                JSONArray jsonArray = response.getJSONArray("data");
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        // 放入 object
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ReplyVO replyVO = new ReplyVO();
                        replyVO.replyId = jsonObject.getInteger("replyId");
                        replyVO.commentId = jsonObject.getInteger("commentId");
                        replyVO.replyContent = jsonObject.getString("replyContent");
                        replyVO.fromUid = jsonObject.getInteger("fromUid");
                        replyVO.userNickname = jsonObject.getString("userNickname");
                        replyVO.userHeadImg = jsonObject.getString("userHeadImg");

                        userHeadImg = jsonObject.getString("userHeadImg");
                        userNickname = jsonObject.getString("userNickname");

                        replyVOList.add(replyVO);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            replyAdapter = new ReplyAdapter(replyVOList, inflater);
                            mReplyList.setAdapter(replyAdapter);
                            setListViewHeightBasedOnChildren(mReplyList);
                        }
                    });
                }
            }
        });

    }

    // 跳出弹窗
    private void showReplyDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(CommentActivity.this);
        View view = LayoutInflater.from(CommentActivity.this).inflate(R.layout.dialog_bottomsheet, null);
        dialog.setContentView(view);
        deliverReply(dialog);
        //显示
        dialog.show();
    }

    // 发送回复
    private void deliverReply(BottomSheetDialog dialog) {
        dialog.findViewById(R.id.deliver_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText deliverContent = dialog.findViewById(R.id.deliver_content);

                String deliverContentS = deliverContent.getText().toString();
                deliverContent.setError(null);

                if (TextUtils.isEmpty(deliverContentS)) {
                    deliverContent.setError("回复不能为空");
                    deliverContent.requestFocus();
                    return;
                }
                ReplyVO replyVO = new ReplyVO();
                replyVO.setCommentId(commentId);
                replyVO.setFromUid(userId);
                replyVO.setUserHeadImg(userHeadImg);
                replyVO.setUserNickname(userNickname);
                replyVO.setReplyContent(deliverContentS);

                String json = JSONObject.toJSONString(replyVO);
                OkHttpUtils.post(Util.SERVER_ADDR + "addReply", json, new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        super.onFinish(status, msg);
                        Gson gson = new Gson();
                        ServerResponse serverResponse = gson.fromJson(msg, ServerResponse.class);

                        Looper.prepare();
                        Toast.makeText(CommentActivity.this, serverResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                });

//                System.out.println(replyVOList + "添加成功");
                replyVOList.clear();
//                System.out.println(replyVOList + "清除成功");
                replyVOList.add(replyVO);
//                replyAdapter.notifyDataSetChanged();
                showReplyList();
            }
        });
    }

    private void checkDel() {
        OkHttpUtils.get(Util.SERVER_ADDR + "showArticle?articleId=" + articleId, new OkHttpCallback() {
            @Override
            public void onFinish(String status, String msg) {
                super.onFinish(status, msg);

                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                ServerResponse<ArticleVO> serverResponse = gson.fromJson(msg, new TypeToken<ServerResponse<ArticleVO>>() {
                }.getType());
                articleCheckUserId = serverResponse.getData().getUserId();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 判断是否为当前用户发布
                        if (userId == checkUserId || articleCheckUserId == userId) {
                            mCommentDel.setVisibility(View.VISIBLE);
                        }
                        System.out.println("当前评论接受编号：" + commentId + " 当前评论发布的用户编号：" + checkUserId + " 当前文章发布的用户编号：" + articleCheckUserId);
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reBack:
                toAritcle();
                break;
            case R.id.comment_del:
                //删除
                OkHttpUtils.get(Util.SERVER_ADDR + "delComment?commentId=" + commentId, new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        Gson gson = new Gson();
                        ServerResponse serverResponse = gson.fromJson(msg, ServerResponse.class);

                        Looper.prepare();
                        Toast.makeText(CommentActivity.this, serverResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
                toAritcle();
                break;
            case R.id.headImg:
            case R.id.userNickname:
                Intent intent = new Intent(CommentActivity.this, PersonalpageActivity.class);
                intent.putExtra("userId", checkUserId);
                System.out.println("当前点击用户编号：" + checkUserId);
                startActivity(intent);
                break;
            case R.id.reply_btn:
                showReplyDialog();
                break;
            default:
                break;
        }
    }

    private void toAritcle() {
        Intent intentD = new Intent(CommentActivity.this, ArticleActivity.class);
        intentD.putExtra("articleId", articleId);
        startActivity(intentD);
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