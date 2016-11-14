package com.shenshenff.mediaplayer;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * Created by Felix on 2016/11/14.
 */

public class MediaPlayerActivity extends Activity implements View.OnClickListener {

    private final int MEDIAPLAYER_PAUSE = 0;//暂停
    private final int MEDIAPLAYER_PALY = 1;//播放中
    private final int MEDIAPLAYER_STOP = 2;//停止
    private int mediaSate = 0;
    private MediaPlayer mediaPlayer;
    private int currentTime;
    private int musicMaxTime;
    private int currentVo1;
    private int setTime = 5000;
    private AudioManager am;

    //view

    TextView tv1, tv2;
    Button btn_play,
            btn_kj,
            btn_kt,
            btn_zj,
            btn_js;

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 3001){
                currentVo1 = am.getStreamVolume(AudioManager.STREAM_MUSIC);
                tv1.setText("当前音量："+ currentVo1);
            } else if (msg.what == 3002) {
                currentTime = mediaPlayer.getCurrentPosition();
                tv2.setText("当前播放时间（毫秒）/总时间（毫秒）"+ currentTime + "/"+ musicMaxTime);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediaplayer);

        initView();
        initData();

    }

    private void initView() {
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        btn_play = (Button) findViewById(R.id.btn_play);
        btn_kj = (Button) findViewById(R.id.btn_kj);
        btn_kt = (Button) findViewById(R.id.btn_kt);
        btn_zj = (Button) findViewById(R.id.btn_zj);
        btn_js = (Button) findViewById(R.id.btn_js);
        btn_play.setOnClickListener(this);
        btn_kj.setOnClickListener(this);
        btn_kt.setOnClickListener(this);
        btn_zj.setOnClickListener(this);
        btn_js.setOnClickListener(this);
    }

    private void initData() {
        mediaPlayer = MediaPlayer.create(this, R.raw.ss111);
        mediaPlayer.setLooping(true);//设置循环播放
        musicMaxTime = mediaPlayer.getDuration();//获取音乐文件的总时间
        am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        if (mediaPlayer != null) {
            currentTime = mediaPlayer.getCurrentPosition();
            currentVo1 = am.getStreamVolume(AudioManager.STREAM_MUSIC);
            tv1.setText("当前音量："+ currentVo1);
            tv2.setText("当前播放时间（毫秒）/总时间（毫秒）"+ currentTime + "/"+ musicMaxTime);
        } else {
            currentTime = 0;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                try {
                    switch (mediaSate) {
                        case MEDIAPLAYER_PALY:
                            Log.i(TAG, "MEDIAPLAYER_PALY ");
                            mediaPlayer.pause();
                            mediaSate = MEDIAPLAYER_PAUSE;
                            break;
                        case MEDIAPLAYER_PAUSE:
                            Log.i(TAG, "MEDIAPLAYER_PAUSE ");
                            mediaPlayer.start();
                            mediaSate = MEDIAPLAYER_PALY;
                            break;
                        case MEDIAPLAYER_STOP:
                            Log.i(TAG, "MEDIAPLAYER_STOP ");
                            if (mediaPlayer != null) {
                                mediaPlayer.pause();
                                mediaPlayer.stop();
                            }
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                            mediaSate = MEDIAPLAYER_PALY;
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_kj:
                if (currentTime - setTime <= 0){
                    mediaPlayer.seekTo(0);
                } else {
                    mediaPlayer.seekTo(currentTime - setTime);
                    handler.sendEmptyMessage(3002);
                }
                break;
            case R.id.btn_kt:
                if (currentTime + setTime >= musicMaxTime){
                    mediaPlayer.seekTo(musicMaxTime);
                }else {
                    mediaPlayer.seekTo(currentTime+setTime);
                    handler.sendEmptyMessage(3002);
                }
                break;
            case R.id.btn_zj:
                am.setStreamVolume(AudioManager.STREAM_MUSIC, currentVo1 + 1, AudioManager.FLAG_PLAY_SOUND);
                handler.sendEmptyMessage(3001);
                break;
            case R.id.btn_js:
                am.setStreamVolume(AudioManager.STREAM_MUSIC, currentVo1 - 1, AudioManager.FLAG_PLAY_SOUND);
                handler.sendEmptyMessage(3001);
                break;

        }

    }
}
