package org.example.observer.events;

public class GameSessionStartEvent extends GameSessionEvent {
    public Integer getMod() {
        return mod;
    }

    public GameSessionStartEvent(Integer mod) {
        this.mod = mod;
    }

    private final Integer mod;
}
