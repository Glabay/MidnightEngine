package dev.midnightcoder.engine.audio;

import javax.sound.sampled.Clip;
import java.io.InputStream;
import java.util.List;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-15
 */
public abstract class Audio {

    protected Clip clip;
    protected List<InputStream> sources;

    public abstract void loadAudioFiles();

    public abstract void loop();
    public abstract void play();
    public abstract void stop();

}
