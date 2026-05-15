package dev.midnightcoder.engine.audio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.InputStream;
import java.util.List;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-15
 */
public abstract class Audio {
    protected static final Logger log = LoggerFactory.getLogger(Audio.class);

    protected Clip clip;
    protected List<InputStream> sources;

    protected float volume = 1.0f;

    public abstract void loadAudioFiles();

    public abstract void loop();
    public abstract void play();
    public abstract void stop();

    public void mute() {
        setVolume(0.0f);
    }

    public void setVolume(float volume) {
        if (volume < 0.0f || volume > 1.0f) {
            log.debug("Volume must be between 0.0 and 1.0");
            throw new IllegalArgumentException("Volume must be between 0.0 and 1.0");
        }
        this.volume = volume;
        applyVolume();
    }

    private void applyVolume() {
        if (clip == null || !clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            log.debug("Clip is null or does not support volume control");
            return;
        }

        var gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        if (volume == 0.0f) {
            gainControl.setValue(gainControl.getMinimum());
            return;
        }
        var decibels = (float) (20.0 * Math.log10(volume));
        decibels = Math.clamp(decibels, gainControl.getMinimum(), gainControl.getMaximum());

        gainControl.setValue(decibels);
    }
}
