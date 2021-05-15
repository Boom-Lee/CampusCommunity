package com.liyuji.app.comment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.liyuji.app.R;
import com.liyuji.app.vo.ReplyVO;

import java.util.List;

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
            ReplyVO replyVO = (ReplyVO) getItem(position);

            viewHolder.mReplyUserNickname = convertView.findViewById(R.id.reply_userNickname);
            viewHolder.mReplyContent = convertView.findViewById(R.id.reply_content);

            viewHolder.mReplyUserNickname.setText(replyVO.getUserNickname());
            viewHolder.mReplyContent.setText(replyVO.getReplyContent());

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder {
        TextView mReplyUserNickname;
        TextView mReplyContent;
    }
}
