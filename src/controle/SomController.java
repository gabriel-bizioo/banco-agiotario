package controle;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SomController {
    private Clip clip;

    public void tocarSom(String caminho, boolean loop) {
        try {
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }

            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(caminho));
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void pararSom() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
