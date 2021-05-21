package com.liyuji.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
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
import com.liyuji.app.comment.CommentActivity;
import com.liyuji.app.comment.CommentAdapter;
import com.liyuji.app.utils.MyTimeUtils;
import com.liyuji.app.utils.OkHttpCallback;
import com.liyuji.app.utils.OkHttpUtils;
import com.liyuji.app.utils.SharedPreferencesUtil;
import com.liyuji.app.utils.Util;
import com.liyuji.app.vo.ArticleVO;
import com.liyuji.app.vo.CommentVO;
import com.liyuji.app.vo.ServerResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author L
 */
public class ArticleActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mReBack;
    private CircleImageView mHeadImg;
    private TextView mUserNickname;
    private TextView mArticleDelivertime;
    private TextView mBrowseText;
    private TextView mContent;
    private ImageView mArticleImg;
    private LinearLayout mLikeBtn;
    private ImageView mLikeImg;
    private LinearLayout mCommentBtn;
    private ListView mCommentList;
    private Button mArticleDel;
    public List<CommentVO> commentVOList = new ArrayList<>();
    private LayoutInflater inflater;
    private CommentAdapter adapter;

    int userId = 0;
    int checkUserId = 0;
    int articleId = 0;
    int likeStatus = 0;
    int commentId = 0;

    String userHeadImg = null;
    String userNickname = null;
    Date commentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        inflater = getLayoutInflater();

        Intent intent = getIntent();
        articleId = intent.getIntExtra("articleId", 0);

        SharedPreferencesUtil util = SharedPreferencesUtil.getInstance(ArticleActivity.this);
        userId = util.readInt("userId");

        showArticle();

        mReBack = findViewById(R.id.reBack);
        mHeadImg = findViewById(R.id.headImg);
        mUserNickname = findViewById(R.id.userNickname);
        mArticleDelivertime = findViewById(R.id.article_delivertime);
        mBrowseText = findViewById(R.id.browse_text);
        mContent = findViewById(R.id.content);
        mArticleImg = findViewById(R.id.article_img);
        mLikeBtn = findViewById(R.id.like_btn);
        mLikeImg = findViewById(R.id.like_img);
        mCommentBtn = findViewById(R.id.comment_btn);
        mCommentList = findViewById(R.id.comment_list);
        mArticleDel = findViewById(R.id.article_del);

        mCommentBtn.setOnClickListener(this);
        mLikeBtn.setOnClickListener(this);
        mReBack.setOnClickListener(this);
        mHeadImg.setOnClickListener(this);
        mUserNickname.setOnClickListener(this);
        mArticleDel.setOnClickListener(this);

        mCommentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Adapter itemAdapter = parent.getAdapter();
                CommentVO map = (CommentVO) itemAdapter.getItem(position);
                commentId = map.getCommentId();
                System.out.println("当前评论点击的编号：" + commentId);
                Intent intentC = new Intent(ArticleActivity.this, CommentActivity.class);
                intentC.putExtra("commentId", commentId);
                startActivity(intentC);
//                showReplyDialog();
            }
        });
    }

    private void showArticle() {
        OkHttpUtils.get(Util.SERVER_ADDR + "showArticleByA?articleId=" + articleId + "&userId=" + userId, new OkHttpCallback() {
            @Override
            public void onFinish(String status, String msg) {
                super.onFinish(status, msg);

                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                ServerResponse<ArticleVO> serverResponse = gson.fromJson(msg, new TypeToken<ServerResponse<ArticleVO>>() {
                }.getType());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String headImg = serverResponse.getData().getUserHeadImg();
                        String userNickname = serverResponse.getData().getUserNickname();
                        String articleDeliverTime = MyTimeUtils.dateToYmdHms(serverResponse.getData().getArticleDate());
                        String content = serverResponse.getData().getArticleContent();
                        String articleImg = serverResponse.getData().getArticleImg();
                        int browseCount = serverResponse.getData().getBrowseCount();
                        String browseCountS = browseCount + "";
                        int communityId = serverResponse.getData().getCommunityId();
                        checkUserId = serverResponse.getData().getUserId();

                        // 用户头像设置
                        Glide.with(ArticleActivity.this)
                                .load(headImg)
                                .into(mHeadImg);
                        // 用户昵称设置
                        mUserNickname.setText(userNickname);
                        // 发布时间设置
                        mArticleDelivertime.setText(articleDeliverTime);
                        // 发布内容设置
                        mContent.setText(content);
                        if (articleImg != null) {
                            Glide.with(ArticleActivity.this)
                                    .load(articleImg)
                                    .into(mArticleImg);
                        } else {
                            mArticleImg.setVisibility(View.GONE);
                        }

                        // 浏览数量设置
                        mBrowseText.setText(browseCountS);

                        // 判断是否为当前用户发布
                        if (userId == checkUserId) {
                            mArticleDel.setVisibility(View.VISIBLE);
                        }
                        System.out.println("当前动态接受编号：" + articleId + " 当前动态发布的用户编号：" + checkUserId);

                    }
                });
            }
        });

        likeStatusFunction();

        showCommentList();
    }

    private void showCommentList() {
        System.out.println("做show");
        OkHttpUtils.get(Util.SERVER_ADDR + "commentListByArt?articleId=" + articleId, new OkHttpCallback() {
            @Override
            public void onFinish(String status, String msg) {
                //将字符转换成JSONOBJECT对象
                JSONObject response = JSONObject.parseObject(msg);
                //得到里面data的值
                JSONArray jsonArray = response.getJSONArray("data");
                System.out.println(jsonArray);
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        // 放入 object
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        CommentVO commentVO = new CommentVO();
                        commentVO.commentId = jsonObject.getInteger("commentId");
                        commentVO.articleId = jsonObject.getInteger("articleId");
                        commentVO.fromUid = jsonObject.getInteger("fromUid");
                        commentVO.userNickname = jsonObject.getString("userNickname");
                        commentVO.commentDate = jsonObject.getDate("commentDate");
                        commentVO.commentContent = jsonObject.getString("commentContent");
                        commentVO.userHeadImg = jsonObject.getString("userHeadImg");

                        userHeadImg = jsonObject.getString("userHeadImg");
                        userNickname = jsonObject.getString("userNickname");
                        commentDate =   jsonObject.getDate("commentDate");

                        commentVOList.add(commentVO);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new CommentAdapter(commentVOList, inflater);
                            mCommentList.setAdapter(adapter);
                            setListViewHeightBasedOnChildren(mCommentList);
                        }
                    });
                }
            }
        });
    }

    private void likeStatusFunction() {
        OkHttpUtils.get(Util.SERVER_ADDR + "selIsLike?userId=" + userId + "&articleId=" + articleId, new OkHttpCallback() {
            @Override
            public void onFinish(String status, String msg) {
                super.onFinish(status, msg);
                JSONObject response = JSONObject.parseObject(msg);
                //得到里面data的值
                likeStatus = response.getInteger("data");
                System.out.println(likeStatus);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (likeStatus != 0) {
                            mLikeImg.setImageResource(R.drawable.like1);
                            System.out.println("已点赞");
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
                Intent intentR = new Intent(ArticleActivity.this, MainActivity.class);
                intentR.putExtra("id", Util.ARITCLEFRAGMENT);
                startActivity(intentR);
                break;
            case R.id.like_btn:
                if (likeStatus != 0) {
                    OkHttpUtils.get(Util.SERVER_ADDR + "delLike?articleId=" + articleId, new OkHttpCallback() {
                        @Override
                        public void onFinish(String status, String msg) {
                            super.onFinish(status, msg);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    likeStatus = 0;
                                    mLikeImg.setImageResource(R.drawable.like);
                                    System.out.println("取消点赞");
                                }
                            });
                        }
                    });
                } else {
                    OkHttpUtils.get(Util.SERVER_ADDR + "addLike?userId=" + userId + "&articleId=" + articleId, new OkHttpCallback() {
                        @Override
                        public void onFinish(String status, String msg) {
                            super.onFinish(status, msg);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    likeStatus = 1;
                                    mLikeImg.setImageResource(R.drawable.like1);
                                    System.out.println("点赞");
                                }
                            });
                        }
                    });
                }
                break;
            case R.id.comment_btn:
                showCommentDialog();
                break;
            case R.id.headImg:
            case R.id.userNickname:
                Intent intent = new Intent(ArticleActivity.this, PersonalpageActivity.class);
                intent.putExtra("userId", checkUserId);
                System.out.println("当前点击用户编号：" + checkUserId);
                startActivity(intent);
                break;
            case R.id.article_del:
                //删除
                OkHttpUtils.get(Util.SERVER_ADDR + "delArticle?articleId=" + articleId, new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        Gson gson = new Gson();
                        ServerResponse serverResponse = gson.fromJson(msg, ServerResponse.class);

                        Looper.prepare();
                        Toast.makeText(ArticleActivity.this, serverResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
                Intent intentD = new Intent(ArticleActivity.this, MainActivity.class);
                intentD.putExtra("id", Util.ARITCLEFRAGMENT);
                startActivity(intentD);
                break;
            default:
                break;
        }
    }

    // 跳出弹窗
    private void showCommentDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(ArticleActivity.this);
        View view = LayoutInflater.from(ArticleActivity.this).inflate(R.layout.dialog_bottomsheet, null);
        dialog.setContentView(view);
        deliverContent(dialog);
        //显示
        dialog.show();
    }

    // 发送评论内容
    private void deliverContent(BottomSheetDialog dialog) {
        dialog.findViewById(R.id.deliver_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText deliverContent = dialog.findViewById(R.id.deliver_content);

                String deliverContentS = deliverContent.getText().toString();
                deliverContent.setError(null);

                if (TextUtils.isEmpty(deliverContentS)) {
                    deliverContent.setError("评论不能为空");
                    deliverContent.requestFocus();
                    return;
                }
                CommentVO commentVO = new CommentVO();
                commentVO.setArticleId(articleId);
                commentVO.setFromUid(userId);
                commentVO.setUserHeadImg(userHeadImg);
                commentVO.setUserNickname(userNickname);
                commentVO.setCommentDate(commentDate);
                commentVO.setCommentContent(deliverContentS);

                String json = JSONObject.toJSONString(commentVO);
                OkHttpUtils.post(Util.SERVER_ADDR + "addComment", json, new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        super.onFinish(status, msg);
                        Gson gson = new Gson();
                        ServerResponse serverResponse = gson.fromJson(msg, ServerResponse.class);

                        Looper.prepare();
                        Toast.makeText(ArticleActivity.this, serverResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                });
//                System.out.println(replyVOList + "添加成功");
                commentVOList.clear();
//                System.out.println(replyVOList + "清除成功");
                commentVOList.add(commentVO);
//                replyAdapter.notifyDataSetChanged();
                showCommentList();
            }
        });
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

