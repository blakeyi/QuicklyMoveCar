package com.example.myapplication;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicPlayer {

    private MediaPlayer mediaPlayer;
    private boolean isPrepared; // 标记是否已经准备好播放


    public MusicPlayer(Context ctx, int resID) {

        mediaPlayer = MediaPlayer.create(ctx, resID);
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(true);
        }
    }

    public void start() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public boolean isPlaying() {
        if (mediaPlayer != null) {
           return mediaPlayer.isPlaying();
        }
        return true;
    }



    // 其他 MediaPlayer 相关的方法，例如 pause, seekTo, etc.
}