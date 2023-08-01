package org.example.observer;

import org.example.observer.events.Event;

public interface Observer {

    void handle(Event e);
}
