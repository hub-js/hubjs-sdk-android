package org.hubjs.sdk.extra;

import android.app.Application;

import org.hubjs.sdk.Hubjs;
import org.hubjs.sdk.Tracker;
import org.hubjs.sdk.TrackerBuilder;

public abstract class HubjsApplication extends Application {
    private Tracker mHubjsTracker;
    private boolean firstOpened = true;

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

    @Override
    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.registerActivityLifecycleCallbacks(callback);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new DeepLinkActivityLifecycleCallbacks());
    }

    public boolean isFirstOpened() {
        return firstOpened;
    }

    public void setFirstOpened() {
        firstOpened = false;
    }
}

