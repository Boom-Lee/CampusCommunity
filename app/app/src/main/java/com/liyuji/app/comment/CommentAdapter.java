package com.liyuji.app.comment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.liyuji.app.R;
import com.liyuji.app.utils.MyTimeUtils;
import com.liyuji.app.utils.OkHttpCallback;
import com.liyuji.app.utils.OkHttpUtils;
import com.liyuji.app.utils.Util;
import com.liyuji.app.vo.CommentVO;
import com.liyuji.app.vo.ReplyVO;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author L
 */
public class CommentAdapter extends BaseAdapter {

    public List<CommentVO> commentVOList;
    private LayoutInflater inflater;
    public List<ReplyVO> replyVOList = new ArrayList<>();
    private LayoutInflater mInflater;

    ViewHolder viewHolder = null;


    public CommentAdapter(List<CommentVO> commentVOList, LayoutInflater inflater) {
        this.commentVOList = commentVOList;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return commentVOList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentVOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            System.out.println("当前getView: " + position + "   " + getCount());
            viewHolder = new CommentAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.listitem_comment, null);

            viewHolder.mHeadImg = convertView.findViewById(R.id.headImg);
            viewHolder.mUserNickname = convertView.findViewById(R.id.userNickname);
            viewHolder.mCommentContent = convertView.findViewById(R.id.comment_content);
            viewHolder.mReplyList = convertView.findViewById(R.id.reply_list);
            viewHolder.mCommentTime = convertView.findViewById(R.id.comment_time);
            viewHolder.mReplyList = convertView.findViewById(R.id.reply_list);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CommentVO commentVO = (CommentVO) getItem(position);

        Glide.with(parent.getContext())
                .load(commentVO.getUserHeadImg())
                .into(viewHolder.mHeadImg);

        viewHolder.mUserNickname.setText(commentVO.getUserNickname());
        viewHolder.mCommentContent.setText(commentVO.getCommentContent());

        if(convertView.getContext()!= null){
            System.out.println("不为空");
            mInflater = LayoutInflater.from(convertView.getContext());

        }
        showReplyList(commentVO.getCommentId());

        viewHolder.mCommentTime.setText(MyTimeUtils.DateToYmdHms(commentVO.getCommentDate()));

        return convertView;
    }

    private void showReplyList(int commentId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils.get(Util.SERVER_ADDR + "replyListByCom?commentId=" + commentId, new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        //将字符转换成JSONOBJECT对象
                        JSONObject response = JSONObject.parseObject(msg);
                        //得到里面data的值
                        JSONArray jsonArray = response.getJSONArray("data");
                        System.out.println(jsonArray + "json");
                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.size(); i++) {
                                // 放入 object
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                ReplyVO replyVO = new ReplyVO();
                                //replyVO.replyId = jsonObject.getInteger("replyId");
                                //replyVO.commentId = jsonObject.getInteger("commentId");
                                replyVO.userNickname = jsonObject.getString("userNickname");
                                replyVO.replyContent = jsonObject.getString("replyContent");
                                replyVO.fromUid = jsonObject.getInteger("fromUid");

                                replyVOList.add(replyVO);
                            }

                            System.out.println(replyVOList + " 1234567 " + mInflater.toString() );
                            ReplyAdapter adapters = new ReplyAdapter(replyVOList, mInflater);
                            if(adapters == null){
                                System.out.println("adpters不为空");
                            }
//                            viewHolder.mReplyList.setAdapter(adapters);

                        }
//                        else {
//                            viewHolder.mReplyList.setVisibility(View.GONE);
//                        }
                    }
                });
            }
        }).start();
    }

    class ViewHolder {
        CircleImageView mHeadImg;
        TextView mUserNickname;
        TextView mCommentContent;
        ListView mReplyList;
        TextView mCommentTime;
    }
}
