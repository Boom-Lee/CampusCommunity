package com.liyuji.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.liyuji.app.R;
import com.liyuji.app.utils.MyTimeUtils;
import com.liyuji.app.vo.ScheduleVO;

import java.util.List;

/**
 * @author L
 */
public class ScheduleAdapter extends BaseAdapter {


    /**
     * 日程列表
     */
    public List<ScheduleVO> scheduleVOList;
    private LayoutInflater mInflater;

    public ScheduleAdapter(List<ScheduleVO> scheduleVOList, LayoutInflater mInflater) {
        this.scheduleVOList = scheduleVOList;
        this.mInflater = mInflater;
    }

    /**
     * 确认行数
     */
    @Override
    public int getCount() {
        return scheduleVOList.size();
    }

    @Override
    public Object getItem(int position) {
        return scheduleVOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            System.out.println("当前getView: " + position + "   " + getCount());
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.listitem_schedule, null);
            ScheduleVO scheduleVO = (ScheduleVO) getItem(position);

            viewHolder.mScheduleTitle = convertView.findViewById(R.id.schedule_title);
            viewHolder.mScheduleStarttime = convertView.findViewById(R.id.schedule_starttime);
            viewHolder.mScheduleEndtime = convertView.findViewById(R.id.schedule_endtime);

            viewHolder.mScheduleTitle.setText(scheduleVO.getScheduleTitle());
            viewHolder.mScheduleStarttime.setText(MyTimeUtils.DateToTimePickerYmdHm(scheduleVO.getScheduleStarttime()));
            viewHolder.mScheduleEndtime.setText(MyTimeUtils.DateToTimePickerYmdHm(scheduleVO.getScheduleEndtime()));
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder {
        TextView mScheduleStarttime;
        TextView mScheduleEndtime;
        TextView mScheduleTitle;
    }
}
