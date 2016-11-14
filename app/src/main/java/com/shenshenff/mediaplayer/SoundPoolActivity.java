package com.shenshenff.mediaplayer;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Felix on 2016/11/14.
 */

public class SoundPoolActivity extends Activity implements View.OnClickListener {

    private SoundPool sp;
    private int soundId_long;
    private int soundId_short;
    private Button btn_long,btn_short;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soundpool);

        sp = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
        soundId_long = sp.load(this, R.raw.nowifi_zh, 1);
        soundId_short = sp.load(this, R.raw.ss2, 1);

        btn_long = (Button) findViewById(R.id.btn_long);
        btn_short = (Button) findViewById(R.id.btn_short);
        btn_long.setOnClickListener(this);
        btn_short.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_long:
                sp.play(soundId_long, 1f, 1f, 0, 0, 1);
                break;
            case R.id.btn_short:
                sp.play(soundId_short, 2f, 2f, 0, 0, 1);
                break;
        }
    }
}
