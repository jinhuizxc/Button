package com.example.jh.button.vibrate;

import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.jh.button.MainActivity;
import com.example.jh.button.R;

/**
 * Created by jinhui on 2017/12/13.
 */

public class VibrateActivity extends AppCompatActivity {

    //创建震动服务对象
    private Vibrator mVibrator;
    private ToggleButton toggleButton1, toggleButton2, toggleButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibrate);
        init();
        //获取手机震动服务
        mVibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);

        toggleButton1.setOnClickListener(new myOnClickListener1());
        toggleButton2.setOnClickListener(new myOnClickListener2());
        toggleButton3.setOnClickListener(new myOnClickListener3());
    }

    private void init() {
        toggleButton1 = (ToggleButton) findViewById(R.id.button1);
        toggleButton2 = (ToggleButton) findViewById(R.id.button2);
        toggleButton3 = (ToggleButton) findViewById(R.id.button3);
    }

    private class myOnClickListener1 implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //判断是否开启
            if (toggleButton1.isChecked()) {
                //设置震动周期，数组表示时间：等待+执行，单位是毫秒，下面操作代表:等待100，执行100，等待100，执行1000，
                //后面的数字如果为-1代表不重复，之执行一次，其他代表会重复，0代表从数组的第0个位置开始
                mVibrator.vibrate(new long[]{100, 100, 100, 1000}, -1);
                Toast.makeText(VibrateActivity.this, getString(R.string.str_ok), Toast.LENGTH_SHORT).show();
            } else {
                //取消震动
                mVibrator.cancel();
                Toast.makeText(VibrateActivity.this, getString(R.string.str_end), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class myOnClickListener2 implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (toggleButton2.isChecked()) {
                mVibrator.vibrate(new long[]{100, 10, 100, 1000}, 0);
                Toast.makeText(VibrateActivity.this, getString(R.string.str_ok), Toast.LENGTH_SHORT).show();
            } else {
                mVibrator.cancel();
                Toast.makeText(VibrateActivity.this, getString(R.string.str_end), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class myOnClickListener3 implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (toggleButton3.isChecked()) {
                mVibrator.vibrate(new long[]{1000, 50, 1000, 50, 1000}, 0);
                Toast.makeText(VibrateActivity.this, getString(R.string.str_ok), Toast.LENGTH_SHORT).show();
            } else {
                mVibrator.cancel();
                Toast.makeText(VibrateActivity.this, getString(R.string.str_end), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
