package dev.midnightcoder.engine.util;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-17
 */
public class DelayedTask {
    private static final Timer t = new Timer();

    public static TimerTask schedule(final Runnable r, long delay) {
        final TimerTask task = new TimerTask() {
            public void run() {
                r.run();
            }
        };
        t.schedule(task, delay);
        return task;
    }
}
