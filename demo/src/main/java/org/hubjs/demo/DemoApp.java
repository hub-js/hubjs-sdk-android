package org.hubjs.demo;

import android.os.StrictMode;

import org.hubjs.sdk.TrackerBuilder;
import org.hubjs.sdk.extra.DimensionQueue;
import org.hubjs.sdk.extra.DownloadTracker;
import org.hubjs.sdk.extra.HubjsApplication;
import org.hubjs.sdk.extra.TrackHelper;

import timber.log.Timber;

public class DemoApp extends HubjsApplication {
    private DimensionQueue mDimensionQueue;

    @Override
    public TrackerBuilder onCreateTrackerConfig() {
        return TrackerBuilder.createDefault("https://analytics.hub-js.com/tracking.php", 10);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
        onInitTracker();
    }


    private void onInitTracker() {
        // Print debug output when working on an app.
        Timber.plant(new Timber.DebugTree());

        // When working on an app we don't want to skew tracking results.
        // getHubjs().setDryRun(BuildConfig.DEBUG);

        // If you want to set a specific userID other than the random UUID token, do it NOW to ensure all future actions use that token.
        // Changing it later will track new events as belonging to a different user.
        // String userEmail = ....preferences....getString
        // getTracker().setUserId(userEmail);

        TrackHelper.track().download().identifier(new DownloadTracker.Extra.ApkChecksum(this)).with(getTracker());
        // Alternative:
        // getTracker().download();

        mDimensionQueue = new DimensionQueue(getTracker());

        // This will be send the next time something is tracked.
        mDimensionQueue.add(0, "test");

        getTracker().addTrackingCallback(trackMe -> {
            Timber.i("Tracker.Callback.onTrack(%s)", trackMe);
            return trackMe;
        });
    }
}
