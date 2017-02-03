package com.hyl.mis.hylviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


    }

    public void jumpActivity2(View v) {
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(intent);
    }

    public void jumpMaskActivity(View view) {
        Intent intent = new Intent(MainActivity.this, MaskActivity.class);
        startActivity(intent);
    }

    public void jumptiaoxingtuActivity(View view) {
        Intent intent = new Intent(MainActivity.this, TiaoXingTuActivity.class);
        startActivity(intent);
    }
    public void jumpActivity3(View v) {
        Intent intent=new Intent(MainActivity.this,GuaGuaLeActivity.class);
        startActivity(intent);
    }
}
