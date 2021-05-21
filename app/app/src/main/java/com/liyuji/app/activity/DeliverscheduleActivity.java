package com.liyuji.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.liyuji.app.R;
import com.liyuji.app.utils.MyTimeUtils;
import com.liyuji.app.utils.OkHttpCallback;
import com.liyuji.app.utils.OkHttpUtils;
import com.liyuji.app.utils.SharedPreferencesUtil;
import com.liyuji.app.utils.Util;
import com.liyuji.app.vo.ServerResponse;

import java.util.Date;

/**
 * @author L
 */
public class DeliverscheduleActivity extends AppCompatActivity implements View.OnClickListener {

    EditText userSchedule_title, userSchedule_content, userSchedule_location;
    TextView deliver_return, schedule_starttime, schedule_endtime;
    Button deliver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverschedule);


        deliver_return = findViewById(R.id.deliver_return);
        userSchedule_title = findViewById(R.id.userSchedule_title);
        userSchedule_content = findViewById(R.id.userSchedule_content);
        userSchedule_location = findViewById(R.id.userSchedule_location);
        schedule_starttime = findViewById(R.id.userSchedule_starttime);
        schedule_endtime = findViewById(R.id.userSchedule_endtime);
        deliver = findViewById(R.id.deliver);

        deliver_return.setOnClickListener(this);
        schedule_starttime.setOnClickListener(this);
        schedule_endtime.setOnClickListener(this);
        deliver.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deliver_return:
                finish();
                break;
            case R.id.userSchedule_starttime:
                TimePicker(schedule_starttime);
                break;
            case R.id.userSchedule_endtime:
                TimePicker(schedule_endtime);
                break;
            case R.id.deliver:
                SharedPreferencesUtil util = SharedPreferencesUtil.getInstance(this);
                int userId = util.readInt("userId");
                String scheduleTitle = userSchedule_title.getText().toString();
                String scheduleContent = userSchedule_content.getText().toString();
                String scheduleLocation = userSchedule_location.getText().toString();
                String scheduleStarttime = schedule_starttime.getText().toString();
                String scheduleEndtime = schedule_endtime.getText().toString();
//                System.out.println("数据:   " + userId + scheduleTitle + scheduleContent + scheduleLocation + scheduleStarttime + scheduleEndtime);
//                JSONArray scheduleVO = new JSONArray();
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("userId", userId);
//                map.put("scheduleTitle", scheduleTitle);
//                map.put("scheduleContent", scheduleContent);
//                map.put("scheduleLocation", scheduleLocation);
//                map.put("scheduleStarttime", scheduleStarttime);
//                map.put("scheduleEndtime", scheduleEndtime);
//
//                scheduleVO.add(map);
//                String json = scheduleVO.toString();

                OkHttpUtils.get(Util.SERVER_ADDR + "scheduleAdd?userId=" + userId + "&scheduleTitle=" + scheduleTitle + "&scheduleContent="
                                + scheduleContent + "&scheduleLocation=" + scheduleLocation + "&scheduleStarttime=" + scheduleStarttime + "&scheduleEndtime=" + scheduleEndtime
                        , new OkHttpCallback() {
                            @Override
                            public void onFinish(String status, String msg) {
                                super.onFinish(status, msg);
                                Gson gson = new Gson();
                                ServerResponse serverResponse = gson.fromJson(msg, ServerResponse.class);

                                Looper.prepare();
                                Toast.makeText(DeliverscheduleActivity.this, serverResponse.getMsg(), Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        });
                Intent intent = new Intent(DeliverscheduleActivity.this, MainActivity.class);
                intent.putExtra("id",Util.SCHEDULERAGMENT);
                startActivity(intent);
                break;
            default:
                break;

        }

    }

    public void TimePicker(TextView textView) {
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                textView.setText(MyTimeUtils.dateToTimePickerYmdHm
                        (date));
            }
        })
                // 默认全部显示
                .setType(new boolean[]{true, true, true, true, true, false})
                //取消按钮文字
                .setCancelText("取消")
                //确认按钮文字
                .setSubmitText("确定")
                //点击屏幕，点在控件外部范围时，是否取消显示
                .setOutSideCancelable(true)
                //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isCenterLabel(false)
                .build();

        pvTime.show();
    }

}