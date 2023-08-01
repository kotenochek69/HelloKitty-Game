package org.example.observer;

import org.example.observer.events.Event;

public interface Observable {

    void register(Observer o);

    void remove(Observer o);

    void notify(Event e);

}
