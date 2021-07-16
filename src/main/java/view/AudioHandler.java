package view;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;


public class AudioHandler extends FatherAudio {
    protected static FatherAudio playing;
    protected static AudioPath playingAudioPath;

    static {
        playing = null;
        playingAudioPath = null;
    }

    public AudioHandler(AudioPath audioPath) {
        super(audioPath);
        playingAudioPath = audioPath;
    }

    public static AudioPath getPlayingAudioPath() {
        return playingAudioPath;
    }

    public static FatherAudio getPlaying() {
        return playing;
    }

    @Override
    public void play() {
        mediaPlayer.play();
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        playing = this;
    }

    public static void muteThePlaying() {
        playing.getMediaPlayer().setMute(true);
    }

    public static void unmuteThePlaying() {
        playing.getMediaPlayer().setMute(false);
    }
}



