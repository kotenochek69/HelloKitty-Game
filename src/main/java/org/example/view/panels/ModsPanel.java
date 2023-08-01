package org.example.view.panels;

import org.example.observer.events.GameSessionStartEvent;
import org.example.view.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

public class ModsPanel extends JPanel {
    private final GameView parent;

    private JButton CreateButton(String name, int y, GridBagConstraints constraints) {
        JButton button = new JButton(name);
        button.setPreferredSize(new Dimension(400, 100));
        button.setMinimumSize(new Dimension(400, 100));

        constraints.gridy = y;


        button.setBackground(Color.CYAN);
        InputStream is = getClass().getResourceAsStream("/fonts/nubes.ttf");
        try {
            assert is != null;
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            button.setFont(font.deriveFont(Font.PLAIN, 74));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        return button;
    }

    private void MenuButtons() {

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 3;
        constraints.gridy = 1;

        JButton easyBtn = CreateButton("Easy", 2, constraints);
        this.add(easyBtn, constraints);
        JButton mediumBtn = CreateButton("Medium", 3, constraints);
        this.add(mediumBtn, constraints);
        JButton hardBtn = CreateButton("Hard", 4, constraints);
        this.add(hardBtn, constraints);

        easyBtn.setVisible(true);
        mediumBtn.setVisible(true);
        hardBtn.setVisible(true);

        easyBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parent.notify(new GameSessionStartEvent(0));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                easyBtn.setBackground(Color.decode("#EE2B95"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                easyBtn.setBackground(Color.CYAN);
            }
        });
        mediumBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parent.notify(new GameSessionStartEvent(1));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mediumBtn.setBackground(Color.decode("#EE2B95"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mediumBtn.setBackground(Color.CYAN);
            }
        });

        hardBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parent.notify(new GameSessionStartEvent(2));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                hardBtn.setBackground(Color.decode("#EE2B95"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hardBtn.setBackground(Color.CYAN);
            }
        });
    }

    public ModsPanel(GameView parent) {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.pink);
        MenuButtons();
        this.parent = parent;
    }
}
