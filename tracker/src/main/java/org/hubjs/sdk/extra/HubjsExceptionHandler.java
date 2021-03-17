package org.hubjs.sdk.extra;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.hubjs.sdk.Hubjs;
import org.hubjs.sdk.TrackMe;
import org.hubjs.sdk.Tracker;
import org.hubjs.sdk.dispatcher.DispatchMode;

import timber.log.Timber;

/**
 * An exception handler that wraps the existing exception handler and dispatches event to a {@link Tracker}.
 * <p>
 * Also see documentation for {@link TrackHelper#uncaughtExceptions()}
 */
public class HubjsExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = Hubjs.tag(HubjsExceptionHandler.class);
    private final Tracker mTracker;
    private final TrackMe mTrackMe;
    private final Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    public HubjsExceptionHandler(@NonNull Tracker tracker, @Nullable TrackMe trackMe) {
        mTracker = tracker;
        mTrackMe = trackMe;
        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    public Tracker getTracker() {
        return mTracker;
    }

    /**
     * This will give you the previous exception handler that is now wrapped.
     */
    public Thread.UncaughtExceptionHandler getDefaultExceptionHandler() {
        return mDefaultExceptionHandler;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            String excInfo = ex.getMessage();

            Tracker tracker = getTracker();

            // Force the tracker into offline mode to ensure events are written to disk
            tracker.setDispatchMode(DispatchMode.EXCEPTION);

            TrackHelper.track(mTrackMe).exception(ex).description(excInfo).fatal(true).with(tracker);

            // Immediately dispatch as the app might be dying after rethrowing the exception and block until the dispatch is completed
            tracker.dispatchBlocking();
        } catch (Exception e) {
            Timber.tag(TAG).e(e, "Couldn't track uncaught exception");
        } finally {
            // re-throw critical exception further to the os (important)
            if (getDefaultExceptionHandler() != null && getDefaultExceptionHandler() != this) {
                getDefaultExceptionHandler().uncaughtException(thread, ex);
            }
        }
    }
}
