import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundPlayer {

    public static void play(String fileName) {
        try {
            URL soundUrl = SoundPlayer.class.getResource("/" + fileName);
            if (soundUrl == null) {
                System.err.println("NOT FOUND on classpath: " + fileName);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundUrl);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            System.err.println("UNSUPPORTED FORMAT (must be WAV, not MP3): " + fileName);
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}