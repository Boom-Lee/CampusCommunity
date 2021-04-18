package com.liyuji.app.Activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.liyuji.app.R;

import java.util.Calendar;

public class DeliverscheduleActivity extends AppCompatActivity implements View.OnClickListener {

    TextView deliver_return;
    TextView schedule_starttime, schedule_endtime;
    //选择日期Dialog
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverschedule);

        deliver_return = findViewById(R.id.deliver_return);
        schedule_endtime = findViewById(R.id.userSchedule_endtime);
        schedule_starttime = findViewById(R.id.userSchedule_starttime);

        deliver_return.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deliver_return:

                finish();
                break;
            case R.id.userSchedule_starttime:
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(this, (DatePickerDialog.OnDateSetListener) this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            break;
            case R.id.userSchedule_endtime:
                break;
            default:
                break;

        }
    }
}