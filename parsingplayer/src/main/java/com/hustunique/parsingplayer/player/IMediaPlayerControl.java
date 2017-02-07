package com.hustunique.parsingplayer.player;

import com.hustunique.parsingplayer.parser.ExtractException;

/**
 * Created by JianGuo on 1/27/17.
 */

public interface IMediaPlayerControl {
    void    start();
    void    pause();
    int     getDuration();
    int     getCurrentPosition();
    void    seekTo(int pos);
    boolean isPlaying();
    int     getBufferPercentage();
    boolean canPause();
    boolean canSeekBackward();
    boolean canSeekForward();

    /**
     * As ParsingPlayer animated to parse video url and play real video address,
     * the param videoUrl is limited.
     * @param videoUrl
     */
    void play(String videoUrl);

    /**
     * Get the audio session id for the player used by this VideoView. This can be used to
     * apply audio effects to the audio track of a video.
     * @return The audio session, or 0 if there was an error.
     */
    int     getAudioSessionId();
    void chooseQuality();
}
