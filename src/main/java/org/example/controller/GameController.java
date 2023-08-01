package org.example.controller;

import org.example.model.Card;
import org.example.model.CardImpl;
import org.example.observer.ObservableImpl;
import org.example.observer.Observer;
import org.example.observer.events.*;
import org.example.view.GameView;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.pow;

public class GameController extends ObservableImpl implements Observer {


    private List<Card> cards = new ArrayList<>();
    private List<Integer> cardsChosen = new ArrayList<>();
    private Integer remain;
    //private HashMap<Card, Integer> cardsChosen = new HashMap<>();

    GameView view;


    public void gameOver() {
        // бизнес логика завершения игры
        view.FreeScreen();
        System.exit(0);
    }

    public void gameStart() {
        view.showPanel(0);
    }

    @Override
    public void handle(Event e) {
        if (e instanceof GameStartEvent) {
            gameStart();
        }
        if (e instanceof PlayEvent) {
            view.showPanel(1);
        }
        if (e instanceof SettingsEvent) {
            view.showPanel(2);
        }
        if (e instanceof SetLooknFeelEvent) {
            view.SetLooknFeel((SetLooknFeelEvent) e);
        }
        if (e instanceof GameOverEvent) {
            this.gameOver();
        }
        if (e instanceof GameSessionEvent) {
            if (e instanceof CardChosenEvent) {
                ChooseCard(((CardChosenEvent) e).getCard_index());
            }
            if (e instanceof GameSessionStartEvent) {
                SessionStart(((GameSessionStartEvent) e).getMod());
            }
        }
    }

    private void ChooseCard(Integer card_index) {
        cardsChosen.add(card_index);
        view.getSession().setCard(cards.get(card_index), card_index);
        if (cardsChosen.size() == 3 || remain == 2) {
            int i = cardsChosen.get(0);
            int j = cardsChosen.get(1);
            if (Objects.equals(cards.get(i).getPath(), cards.get(j).getPath()) && i != j) {
                remain -= 2;
                view.getSession().hideCards(i, j);
                cardsChosen = new ArrayList<>();
                cardsChosen.add(card_index);
            } else {
                view.getSession().flipCards(i, j);
                cardsChosen = new ArrayList<>();
                cardsChosen.add(card_index);
            }
        }
        if (remain == 0) {
            gameRestart();
        }
    }

    private void gameRestart() {
        view.resetSession();
        view.showPanel(0);
    }

    public GameController() {
        PlaySoundLoop();
        view = new GameView();
        view.register(this);
        this.register(view);
    }

    private void PlaySoundLoop() {
        InputStream in = getClass().getResourceAsStream("/music/nyantheme.wav");
        Clip clip;
        try {
            clip = AudioSystem.getClip();
            assert in != null;
            AudioInputStream ais = AudioSystem.getAudioInputStream(in);
            clip.open(ais);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
        clip.loop(Clip.LOOP_CONTINUOUSLY);

    }

    private void SessionStart(Integer mod) {
        cards = new ArrayList<>();
        for (int i = 0; i < (mod + 1) * 8; i++) {
            remain = (mod + 1) * 8;
            String path = "/images/cards/" + i % ((mod + 1) * 4) + ".png";
            CardImpl card = new CardImpl();
            int basewidth = (int) (278.41 * pow(mod + 1, -0.628));
            int baseheight =  (83 * (mod + 1) * (mod + 1) - 407 * (mod + 1) + 652);
            card.setPath(path);
            card.setHeight(baseheight);
            card.setWidth(basewidth);
            card.setImage(path);
            cards.add(card);
        }
        Collections.shuffle(cards);
        view.createField(mod);
        view.showPanel(3);
    }
}
