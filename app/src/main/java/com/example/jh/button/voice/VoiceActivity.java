package com.example.jh.button.voice;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.jh.button.R;

/**
 * Created by jinhui on 2017/12/13.
 *
 * 系统声音控制类
 * 调节音量的各个方法——AudioManager的使用
 * //获取AudioManager实例对象
 AudioManager audioManage = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
 //获取最大音量和当前音量，参数：STREAM_VOICE_CALL（通话）、STREAM_SYSTEM（系统声音）、STREAM_RING（铃声）、STREAM_MUSIC（音乐）和STREAM_ALARM（闹铃）
 int max = audioManager.getStreamMaxVolume(int streamType);
 int current = audioManager.getStreamVolume(int streamType);
 //获取当前的铃声模式，返回值：RINGER_MODE_NORMAL（普通）、RINGER_MODE_SILENT（静音）或者RINGER_MODE_VIBRATE（震动）
 int rMode = audioManager.getRingerMode();
 //获取当前音频模式，返回值：MODE_NORMAL（普通）、MODE_RINGTONE（铃声）、MODE_IN_CALL（呼叫）或者MODE_IN_COMMUNICATION（通话）
 int mode = audioManager.getMode();

 //设置音量大小，第一个参数：STREAM_VOICE_CALL（通话）、STREAM_SYSTEM（系统声音）、STREAM_RING（铃声）、STREAM_MUSIC（音乐）和STREAM_ALARM（闹铃）；第二个参数：音量值，取值范围为0-7；第三个参数：可选标志位，用于显示出音量调节UI（AudioManager.FLAG_SHOW_UI）。
 audioManager.setStreamVolume(int streamType, int index, int flags);
 //设置铃声模式，参数：RINGER_MODE_NORMAL（普通）、RINGER_MODE_SILENT（静音）或者RINGER_MODE_VIBRATE（震动）
 audioManager.getRingerMode(int ringerMode);
 //设置音频模式，参数：MODE_NORMAL（普通）、MODE_RINGTONE（铃声）、MODE_IN_CALL（呼叫）或者MODE_IN_COMMUNICATION（通话）
 audioManager.setMode(int mode);
 //设置静音/取消静音，第二个参数：请求静音状态，true（静音）false（取消静音）
 audioManager.setStreamMute (int streamType, boolean state);

 //调节手机音量大小，第二个参数：调整音量的方向，可取ADJUST_LOWER（降低）、ADJUST_RAISE（升高）、ADJUST_SAME（不变）。
 audioManager.adjustStreamVolume(int streamType, int direction, int flags);
 */

public class VoiceActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int RINGER_MODE_SILENT = 0;
    public static final int RINGER_MODE_VIBRATE = 1;
    public static final int RINGER_MODE_NORMAL = 2;

    AudioManager audio;
    Button button1, button2, button3, button4, button5, button6;
    // 铃声音量
    int ringerVolume = 20;
    //震动
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

        audio = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);//获取系统震动服务

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);

        /**
         * 主界面上一个button，点击之后启动静音模式，
         * 在N之前的版本，这样做是没问题的，但是在N版本上，会出现Fatal Exception。
         */
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在改变铃声状态前加入权限判断和申请逻辑
                NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                assert notificationManager != null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                        && !notificationManager.isNotificationPolicyAccessGranted()) {
                    Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    getApplicationContext().startActivity(intent);
                    return;
                }
                // 通过以下代码将声音更改为 静音了。
                assert audio != null;
                audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            }
        });

        // 设置为静音后在变成普通状态可以通过调用一下代码：
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                audio.setStreamVolume(AudioManager.RINGER_MODE_NORMAL, ringerVolume, 0);
            }
        });
        // 震动
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
    }


    int vibrate_setting = -1;
    int ring_mode = -1;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button3: // 总是振动
                vibrate_setting = AudioManager.VIBRATE_SETTING_ON;
                ring_mode = 1;
                audio.setRingerMode(ring_mode);
                // 调节震动
                vibrator.vibrate(500);
                break;
            case R.id.button4: //一律不振动
                vibrate_setting = AudioManager.VIBRATE_SETTING_OFF;
                ring_mode = 0;
                audio.setRingerMode(ring_mode);
                break;
            case R.id.button5: //静音下振动
                vibrate_setting = AudioManager.VIBRATE_SETTING_ONLY_SILENT;
                ring_mode = 1;
                audio.setRingerMode(ring_mode);
                break;
            case R.id.button6: //非静音下振动
                vibrate_setting = AudioManager.VIBRATE_SETTING_ON;
                ring_mode = 0;
                audio.setRingerMode(ring_mode);
                break;
        }
//        Settings.System.putInt(resolver, "vibrate_in_silent", ring_mode);
        audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, vibrate_setting);
    }
}
