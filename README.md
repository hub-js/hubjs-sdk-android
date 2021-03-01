Hubjs SDK for Android
========================

## Quickstart
* Include the library in your app modules `build.gradle` file
```groovy
    implementation 'org.hubjs.sdk:tracker:<latest-version>'
```

* Now you need to initialize your `Tracker`. It's recommended to store it as singleton. You can extend `HubjsApplication` or create and store a `Tracker` instance yourself:
```java
import org.hubjs.sdk.TrackerBuilder;

public class YourApplication extends Application {
    private Tracker tracker;
    public synchronized Tracker getTracker() {
        if (tracker == null){
            tracker = TrackerBuilder.createDefault("https://vtc-analytics.hub-js.com/tracking.php", 1).build(Hubjs.getInstance(this));
        }
        return tracker;
    }
}
```

* The `TrackHelper` class is the easiest way to submit events to your tracker:
```java
// The `Tracker` instance from the previous step
Tracker tracker = ((HubjsApplication) getApplication()).getTracker();
// Track a screen view
TrackHelper.track().screen("/activity_main/activity_settings").title("Settings").with(tracker);
// Monitor your app installs
TrackHelper.track().download().with(tracker);
```
