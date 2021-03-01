package org.hubjs.sdk.dispatcher;

import org.hubjs.sdk.Tracker;

public interface DispatcherFactory {
    Dispatcher build(Tracker tracker);
}
