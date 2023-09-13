package com.example.ulan;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;

public class MediaHandler {
    MediaPlayer player;

    public void play(Context context,int song) {
        if (player == null) {
            player = MediaPlayer.create(context,song);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer(context,song);
                }
            });
        }

        player.start();
    }
    public void pause() {
        if (player != null) {
            player.pause();
        }
    }

    private void stopPlayer(Context context,int song) {
        if (player != null) {
            player.release();
            player = null;
            play(context,song);
        }

    }
}
