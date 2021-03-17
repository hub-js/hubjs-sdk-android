package org.hubjs.tracker.dispatcher;

import org.hubjs.tracker.Tracker;

public interface DispatcherFactory {
    Dispatcher build(Tracker tracker);
}
