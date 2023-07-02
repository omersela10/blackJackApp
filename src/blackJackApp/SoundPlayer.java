package blackJackApp;


import javax.sound.sampled.*;
import java.io.File;

public class SoundPlayer {

    private final String filePath;
    private Clip clip;

    private Boolean isMuted = false;
    public SoundPlayer(String filePath) {
        this.filePath = filePath;
        loadClip();
    }

    private void loadClip() {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filePath)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null) {
            if (clip.isRunning())
                clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void loop() {
        if (clip != null) {
            if (clip.isRunning())
                clip.stop();
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void mute() {
        if (clip != null) {

            if (!isMuted) {
                clip.stop();
                isMuted = true;
            } else {
                clip.start();
                isMuted = false;
            }

        }
    }
}
