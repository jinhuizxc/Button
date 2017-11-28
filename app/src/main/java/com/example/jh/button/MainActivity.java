package com.example.jh.button;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Android 防止过快点击造成多次事件执行（防止按钮重复点击）
 */
public class MainActivity extends AppCompatActivity {

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btn);

        // 方式1
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Utils.isFastClick()) {
//                    // 进行点击事件后的逻辑操作
//                    Toast.makeText(MainActivity.this, "按钮被点击", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        // 方式2
        btn.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                // 进行点击事件后的逻辑操作
                Toast.makeText(MainActivity.this, "按钮被点击", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
