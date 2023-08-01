package org.example.observer;

import org.example.observer.events.Event;

import java.util.ArrayList;
import java.util.List;

public class ObservableImpl implements Observable {
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void register(Observer o) {
        observers.add(o);
    }

    @Override
    public void remove(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notify(Event e) {
        for (Observer o : observers) {
            o.handle(e);
        }
    }
}
