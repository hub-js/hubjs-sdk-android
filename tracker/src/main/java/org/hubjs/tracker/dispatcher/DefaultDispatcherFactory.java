package org.hubjs.tracker.dispatcher;

import org.hubjs.tracker.Tracker;
import org.hubjs.tracker.tools.Connectivity;

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
