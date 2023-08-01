package org.example.observer.events;

public class CardChosenEvent extends GameSessionEvent {
    public CardChosenEvent(Integer card_index) {
        this.card_index = card_index;
    }

    public Integer getCard_index() {
        return card_index;
    }

    private final Integer card_index;
}
