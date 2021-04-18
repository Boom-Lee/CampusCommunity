package com.liyuji.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.liyuji.app.R;

public class DeliverarticleActivity extends AppCompatActivity implements View.OnClickListener {

    TextView deliver_return;
    TextView deliver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverarticle);

        deliver_return = findViewById(R.id.deliver_return);
        deliver = findViewById(R.id.deliver);
        deliver_return.setOnClickListener(this);
        deliver.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deliver_return:
                finish();
                break;
            case R.id.deliver:
                Intent intent = new Intent(this,ArticleActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}