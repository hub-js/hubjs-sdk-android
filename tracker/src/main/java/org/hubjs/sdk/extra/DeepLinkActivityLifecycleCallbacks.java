package org.hubjs.sdk.extra;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.hubjs.sdk.QueryParams;
import org.hubjs.sdk.TrackMe;

class DeepLinkActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    public void onActivityStarted(@NonNull Activity activity) {

    }

    public void onActivityResumed(@NonNull Activity activity) {

    }

    public void onActivityPaused(@NonNull Activity activity) {

    }

    public void onActivityStopped(@NonNull Activity activity) {

    }

    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    public void onActivityDestroyed(@NonNull Activity activity) {

    }

    public void onActivityPreCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        HubjsApplication app = (HubjsApplication) activity.getApplication();
        if (app.isFirstOpened()) {
            Intent intent = activity.getIntent();
            Uri data = intent.getData();
            if (data != null) {
                String uri = data.toString();
                TrackHelper.track(new TrackMe().set(QueryParams.REFERRER, uri)).screen(activity).with(app.getTracker());
            }
            app.setFirstOpened();
        }
    }

    public void onActivityPostCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
    }

    public void onActivityPreStarted(@NonNull Activity activity) {

    }

    public void onActivityPostStarted(@NonNull Activity activity) {

    }

    public void onActivityPreResumed(@NonNull Activity activity) {

    }

    public void onActivityPostResumed(@NonNull Activity activity) {

    }

    public void onActivityPrePaused(@NonNull Activity activity) {

    }

    public void onActivityPostPaused(@NonNull Activity activity) {

    }

    public void onActivityPreStopped(@NonNull Activity activity) {

    }

    public void onActivityPostStopped(@NonNull Activity activity) {

    }

    public void onActivityPreSaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    public void onActivityPostSaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    public void onActivityPreDestroyed(@NonNull Activity activity) {

    }

    public void onActivityPostDestroyed(@NonNull Activity activity) {

    }
}
