package com.liyuji.app.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.liyuji.app.R;
import com.liyuji.app.utils.OkHttpCallback;
import com.liyuji.app.utils.OkHttpUtils;
import com.liyuji.app.utils.SharedPreferencesUtil;
import com.liyuji.app.utils.Util;
import com.liyuji.app.vo.Article;
import com.liyuji.app.vo.ServerResponse;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DeliverarticleActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView mDeliverReturn;
    private Button mDeliver;
    private EditText mDeliverContent;
    private TextView mDeliverImg;
    private ImageView mShowImg;
    Uri ImgUri;
    String articleImg;
    int COMMUNITY_ID;
    int ANONYMOUS_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverarticle);

        mDeliverReturn = findViewById(R.id.deliver_return);
        mDeliver = findViewById(R.id.deliver);
        mDeliverContent = findViewById(R.id.deliver_content);
        mDeliverImg = findViewById(R.id.deliver_img);

        mShowImg = findViewById(R.id.show_img);


        Intent intent = getIntent();
        COMMUNITY_ID = intent.getIntExtra("COMMUNITY_ID", 0);
        System.out.println("当前得到的COMMUNITY_ID值为： " + COMMUNITY_ID);
        mDeliverReturn.setOnClickListener(this);
        mDeliver.setOnClickListener(this);
        mDeliverImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deliver_return:
                finish();
                break;
            case R.id.deliver:
                String deliverContent = mDeliverContent.getText().toString();
                mDeliverContent.setError(null);

                // 校验用户名
                if (TextUtils.isEmpty(deliverContent)) {
                    mDeliverContent.setError("内容不能为空");
                    mDeliverContent.requestFocus();
                    return;
                }
                deliverArticle(deliverContent);
                Intent intentD = new Intent(DeliverarticleActivity.this, MainActivity.class);
                intentD.putExtra("id",Util.ARITCLEFRAGMENT);
                startActivity(intentD);
                break;
            case R.id.deliver_img:
                AlertDialog.Builder dialog = new AlertDialog.Builder(DeliverarticleActivity.this);
                dialog.setTitle("添加图片");
                dialog.setItems(new String[]{"相册"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        gotoPickImage();
                    }
                });
                dialog.show();
                break;
            default:
                break;
        }
    }

    private void deliverArticle(String deliverContent) {
        SharedPreferencesUtil util = SharedPreferencesUtil.getInstance(this);
        int userId = util.readInt("userId");

        Article article = new Article();
        article.setUserId(userId);
        article.setCommunityId(COMMUNITY_ID);
        article.setArticleContent(deliverContent);
        article.setArticleImg(articleImg);

        String json = JSONObject.toJSONString(article);
        OkHttpUtils.post(Util.SERVER_ADDR + "addArticle", json, new OkHttpCallback() {
            @Override
            public void onFinish(String status, String msg) {
                super.onFinish(status, msg);
                Gson gson = new Gson();
                ServerResponse serverResponse = gson.fromJson(msg, ServerResponse.class);

                Looper.prepare();
                Toast.makeText(DeliverarticleActivity.this, serverResponse.getMsg(), Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = null;
            if (requestCode == 1) {
                //选择的图片的Uri
                ImgUri = data.getData();

                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(ImgUri));
                    Glide.with(DeliverarticleActivity.this)
                            .load(bitmap)
                            .fitCenter()
                            .into(mShowImg);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                File file = BitmapToFile(bitmap);

                OkHttpUtils.upload(Util.SERVER_ADDR + "uploadImg", file.getAbsolutePath(), new OkHttpCallback() {
                    @Override
                    public void onFinish(String status, String msg) {
                        super.onFinish(status, msg);
                        Gson gson = new Gson();
                        ServerResponse serverResponse = gson.fromJson(msg, ServerResponse.class);

                        articleImg = (String) serverResponse.getData();

                    }
                });

                System.out.println("ImgUri:" + ImgUri + "   " + file.getAbsolutePath());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private File BitmapToFile(Bitmap bitmap) {
        //将要保存图片的路径
        File file = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private void gotoPickImage() {
        //选择相册
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //回调路径
        startActivityForResult(intent, 1);
    }

}