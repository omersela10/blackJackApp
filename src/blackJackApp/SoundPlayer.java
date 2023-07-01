package blackJackApp;


import javax.sound.sampled.*;
import java.io.File;

public class SoundPlayer {
	
    private String filePath;
    private Clip clip;

    public SoundPlayer(String filePath) {
        this.filePath = filePath;
        loadClip();
    }

    private void loadClip() {
        try {
//            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(filePath));
//            clip = AudioSystem.getClip();
//            clip.open(audioInputStream);
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
}
