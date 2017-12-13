package com.example.jh.button;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Android 防止过快点击造成多次事件执行（防止按钮重复点击）
 */
public class MainActivity extends AppCompatActivity {

    Button btn;

    private SoundPool sp;//声明一个SoundPool
    private int music;//定义一个整型用load（）；来设置suondID

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

        Button styleBt = (Button)findViewById(R.id.style_button_id);
        styleBt.setBackgroundColor(Color.parseColor("#b9e3d9"));

        styleBt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO 自动生成的方法存根
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //v.setBackgroundResource(R.drawable.bar_color);
                    v.setBackgroundColor(Color.WHITE);
                }
                else if(event.getAction()==MotionEvent.ACTION_UP){
                    v.setBackgroundColor(Color.parseColor("#b9e3d9"));
                }
                return false;
            }
        });

        sp= new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        music = sp.load(this, R.raw.delete,1); //把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级

        Button musicBt = (Button)findViewById(R.id.music_button_id);
        musicBt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                sp.play(music, 1, 1, 0, 0, 1);
            }
        });

    }



    /**当用户点击按钮时，Android系统调用buttonListener(View)方法。
     * 为了正确执行，这个方法必须是public并且仅接受一个View类型的参数
     * @param v button传过来的view对象
     * 需要注意的是这个方法必须符合三个条件：
    　　		1.public
    　　		2.返回void
    　　		3.只有一个参数View，这个View就是被点击的这个控件。
     */
    public void buttonListener(View v){
        switch (v.getId()) {
            case R.id.my_button_id:
                Toast.makeText(this, "button自己绑定一个触发函数", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }


}
