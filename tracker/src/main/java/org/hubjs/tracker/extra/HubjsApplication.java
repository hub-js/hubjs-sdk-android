package org.hubjs.tracker.extra;

import android.app.Application;

import org.hubjs.tracker.Hubjs;
import org.hubjs.tracker.Tracker;
import org.hubjs.tracker.TrackerBuilder;

public abstract class HubjsApplication extends Application {
    private Tracker mHubjsTracker;

    public Hubjs getHubjs() {
        return Hubjs.getInstance(this);
    }

    /**
     * Gives you an all purpose thread-safe persisted Tracker.
     *
     * @return a shared Tracker
     */
    public synchronized Tracker getTracker() {
        if (mHubjsTracker == null) mHubjsTracker = onCreateTrackerConfig().build(getHubjs());
        return mHubjsTracker;
    }

    /**
     * See {@link TrackerBuilder}.
     * You may be interested in {@link TrackerBuilder#createDefault(String, int)}
     *
     * @return the tracker configuration you want to use.
     */
    public abstract TrackerBuilder onCreateTrackerConfig();

    @Override
    public void onLowMemory() {
        if (mHubjsTracker != null) mHubjsTracker.dispatch();
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        if ((level == TRIM_MEMORY_UI_HIDDEN || level == TRIM_MEMORY_COMPLETE) && mHubjsTracker != null) {
            mHubjsTracker.dispatch();
        }
        super.onTrimMemory(level);
    }

}
