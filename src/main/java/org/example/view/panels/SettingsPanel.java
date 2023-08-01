package org.example.view.panels;

import org.example.observer.events.GameStartEvent;
import org.example.observer.events.SetLooknFeelEvent;
import org.example.view.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

public class SettingsPanel extends JPanel {
    GameView parent;

    JRadioButton CreateRadioButton(String name, int y, GridBagConstraints constraints) {
        JRadioButton button = new JRadioButton(name);
        button.setPreferredSize(new Dimension(400, 100));
        button.setMinimumSize(new Dimension(400, 100));

        constraints.gridy = y;
        return button;
    }

    JButton CreateButton(String name, int y, GridBagConstraints constraints) {
        JButton button = new JButton(name);
        button.setPreferredSize(new Dimension(200, 50));
        button.setMinimumSize(new Dimension(200, 50));

        constraints.gridy = y;
        button.setBackground(Color.CYAN);
        InputStream is = getClass().getResourceAsStream("/fonts/nubes.ttf");
        try {
            assert is != null;
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            button.setFont(font.deriveFont(Font.PLAIN, 50));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        return button;
    }

    void LookAndFeelButtons() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 3;
        constraints.gridy = 1;

        JLabel title = new JLabel("Look and feel");
        title.setFont(title.getFont().deriveFont(60F));
        title.setForeground(Color.CYAN);
        title.setPreferredSize(new Dimension(400, 100));
        title.setMinimumSize(new Dimension(400, 100));
        this.add(title, constraints);

        ButtonGroup group = new ButtonGroup();
        JRadioButton windBtn = CreateRadioButton("Windows", 2, constraints);
        this.add(windBtn, constraints);
        group.add(windBtn);
        JRadioButton metBtn = CreateRadioButton("Metal", 3, constraints);
        this.add(metBtn, constraints);
        group.add(metBtn);
        JRadioButton motBtn = CreateRadioButton("Motion", 4, constraints);
        this.add(motBtn, constraints);
        group.add(motBtn);
        JButton exitBtn = CreateButton("EXIT", 5, constraints);
        this.add(exitBtn, constraints);

        windBtn.setVisible(true);
        metBtn.setVisible(true);
        motBtn.setVisible(true);
        exitBtn.setVisible(true);
        title.setVisible(true);

        windBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parent.notify(new SetLooknFeelEvent((JRadioButton) e.getSource()));
            }
        });
        metBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parent.notify(new SetLooknFeelEvent((JRadioButton) e.getSource()));
            }
        });
        motBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parent.notify(new SetLooknFeelEvent((JRadioButton) e.getSource()));
            }
        });
        exitBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parent.notify(new GameStartEvent());
            }
        });
    }


    public SettingsPanel(GameView parent) {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.pink);
        LookAndFeelButtons();
        this.parent = parent;
    }
}
