package com.shenshenff.mediaplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Created by Felix on 2016/11/14.
 */

public class SoundPlayer {

    private SoundPool sp;
    private int sound;

    public SoundPlayer(Context context, int soundId) {
        sp = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
        sound = sp.load(context, soundId, 1);

    }
    public void play(){
        sp.play(sound, 1f, 1f, 0, 0, 1);
    }

}
