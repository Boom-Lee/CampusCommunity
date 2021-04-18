package com.liyuji.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.liyuji.app.R;

public class ArticleActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView reBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        reBack = findViewById(R.id.reBack);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reBack:
                finish();
                break;
            default:
                break;
        }
    }
}