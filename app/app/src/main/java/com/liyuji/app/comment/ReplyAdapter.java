package com.liyuji.app.comment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.liyuji.app.R;
import com.liyuji.app.vo.ReplyVO;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author L
 */
public class ReplyAdapter extends BaseAdapter {
    public List<ReplyVO> replyVOList;
    private LayoutInflater inflater;

    ViewHolder viewHolder = null;


    public ReplyAdapter(List<ReplyVO> replyVOList, LayoutInflater inflater) {
        this.replyVOList = replyVOList;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return replyVOList.size();
    }

    @Override
    public Object getItem(int position) {
        return replyVOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            System.out.println("当前getView: " + position + "   " + getCount());
            viewHolder = new ReplyAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.listitem_reply, null);

            viewHolder.mReplyHeadImg = convertView.findViewById(R.id.reply_headImg);
            viewHolder.mReplyNickName = convertView.findViewById(R.id.reply_nickName);
            viewHolder.mReplyContent = convertView.findViewById(R.id.reply_content);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ReplyVO replyVO = (ReplyVO) getItem(position);
        if (replyVO.getUserHeadImg() != null) {
            Glide.with(parent.getContext())
                    .load(replyVO.getUserHeadImg())
                    .into(viewHolder.mReplyHeadImg);
        }

        if (replyVO.getUserNickname() != null) {
            viewHolder.mReplyNickName.setText(replyVO.getUserNickname());
        }
        if (replyVO.getReplyContent() != null) {
            viewHolder.mReplyContent.setText(replyVO.getReplyContent());
        }
        return convertView;
    }

    class ViewHolder {


        CircleImageView mReplyHeadImg;
        TextView mReplyNickName;
        TextView mReplyContent;

    }

}
