package org.hubjs.sdk.dispatcher;

import org.hubjs.sdk.Tracker;
import org.hubjs.sdk.tools.Connectivity;

public class DefaultDispatcherFactory implements DispatcherFactory {
    public Dispatcher build(Tracker tracker) {
        return new DefaultDispatcher(
                new EventCache(new EventDiskCache(tracker)),
                new Connectivity(tracker.getHubjs().getContext()),
                new PacketFactory(tracker.getAPIUrl()),
                new DefaultPacketSender()
        );
    }
}
