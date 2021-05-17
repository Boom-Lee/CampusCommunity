package com.liyuji.app.comment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.liyuji.app.R;
import com.liyuji.app.utils.MyTimeUtils;
import com.liyuji.app.vo.CommentVO;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author L
 */
public class CommentAdapter extends BaseAdapter {

    public List<CommentVO> commentVOList;
    private LayoutInflater inflater;

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

            viewHolder.mReplyBtn = convertView.findViewById(R.id.reply_btn);
            viewHolder.mHeadImg = convertView.findViewById(R.id.headImg);
            viewHolder.mUserNickname = convertView.findViewById(R.id.userNickname);
            viewHolder.mCommentTime = convertView.findViewById(R.id.comment_time);
            viewHolder.mCommentContent = convertView.findViewById(R.id.comment_content);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CommentVO commentVO = (CommentVO) getItem(position);
        // 设置头像
        Glide.with(parent.getContext())
                .load(commentVO.getUserHeadImg())
                .into(viewHolder.mHeadImg);
        // 设置评论用户名
        viewHolder.mUserNickname.setText(commentVO.getUserNickname());
        // 设置评论内容
        viewHolder.mCommentContent.setText(commentVO.getCommentContent());
        // 设置评论时间
        viewHolder.mCommentTime.setText(MyTimeUtils.DateToYmdHms(commentVO.getCommentDate()));
        return convertView;
    }

    class ViewHolder {
        CircleImageView mHeadImg;
        TextView mUserNickname;
        TextView mCommentTime;
        TextView mCommentContent;
        TextView mReplyBtn;
    }

}
