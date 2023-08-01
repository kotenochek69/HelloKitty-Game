package org.example.view.panels;

import org.example.model.Card;
import org.example.observer.events.CardChosenEvent;
import org.example.view.GameView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;

public class SessionPanel extends JPanel {
    private final GameView parent;
    private final List<JButton> cards = new ArrayList<>();
    private String cardBack;


    public void createField(Integer mod) {
        int size = (mod + 1) * 8;
        int height = size / (4 + 4 * ((size) / 24));
        int width = size / height;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                setDefaultCard(mod, i, j);
            }
        }
    }

    private void setDefaultCard(Integer mod, int i, int j) {
        GridBagConstraints constraints = getCardConstraints(i, j);
        JButton card = new JButton();
        cardBack = "/images/cards/12.png";
        ImageIcon card_icon = getDefaultCardIcon(mod, cardBack);
        card.setIcon(card_icon);
        //card.setBackground(Color.CYAN);
        card.setOpaque(true);
        card.setMinimumSize(new Dimension(card_icon.getIconWidth(), card_icon.getIconHeight()));
        card.setPreferredSize(new Dimension(card_icon.getIconWidth(), card_icon.getIconHeight()));
        AddCardListener(card);
        this.cards.add(card);
        this.addImpl(card, constraints, cards.size() - 1);
    }

    private static GridBagConstraints getCardConstraints(int i, int j) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = j;
        constraints.gridy = i;
        constraints.weightx = 0.9;
        constraints.weighty = 0.7;
        return constraints;
    }

    private void ShowChosenCard(Card card, Integer index) {
        this.remove(cards.get(index));
        cards.get(index).setIcon(card.getImage());
        cards.get(index).setOpaque(true);
        cards.get(index).setBorder(null);
        cards.get(index).setBackground(Color.pink);
        int i = index / (4 + 4 * ((cards.size()) / 24));
        int j = index % (4 + 4 * ((cards.size()) / 24));
        this.add(cards.get(index), getCardConstraints(i, j));
        this.revalidate();
        this.repaint();
    }

    private void AddCardListener(JButton card) {
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parent.notify(new CardChosenEvent(cards.indexOf(e.getSource())));
            }
        });
    }

    private ImageIcon getDefaultCardIcon(Integer mod, String path) {
        int basewidth = (int) (278.41 * pow(mod + 1, -0.628));
        int baseheight = 83 * (mod + 1) * (mod + 1) - 407 * (mod + 1) + 652;
        InputStream stream = getClass().getResourceAsStream(path);
        try {
            assert stream != null;
            return new ImageIcon(ImageIO.read(stream).getScaledInstance(basewidth, baseheight, BufferedImage.SCALE_SMOOTH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCard(Card card, Integer index) {
        this.remove(cards.get(index));
        ShowChosenCard(card, index);
        this.revalidate();
        this.repaint();
    }

    public SessionPanel(GameView parent) {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.pink);
        this.parent = parent;
    }

    public void flipCards(int i, int j) {
        cards.get(i).setIcon(getDefaultCardIcon((cards.size() / 8) - 1, cardBack));
        cards.get(j).setIcon(getDefaultCardIcon((cards.size() / 8) - 1, cardBack));
        repaint();
    }

    public void hideCards(int i, int j) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.remove(cards.get(i));
        this.remove(cards.get(j));
//        cards.remove(i);
//        cards.remove(j);
        repaint();
    }

}
