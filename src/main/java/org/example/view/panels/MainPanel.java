package org.example.view.panels;

import org.example.observer.events.GameOverEvent;
import org.example.observer.events.PlayEvent;
import org.example.observer.events.SettingsEvent;
import org.example.view.GameView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class MainPanel extends JPanel {

    private final GameView parent;

    private JButton CreateButton(String name, int y, GridBagConstraints constraints) {
        JButton button = new JButton(name);
        button.setPreferredSize(new Dimension(400, 100));
        button.setMinimumSize(new Dimension(400, 100));

        constraints.gridy = y;


        button.setBackground(Color.decode("#EE2B95"));
        InputStream is = getClass().getResourceAsStream("/fonts/nubes.ttf");
        try {
            assert is != null;
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            button.setFont(font.deriveFont(Font.PLAIN, 72));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        return button;
    }

    private void MenuButtons() {

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.weightx = 0.5;
        constraints.gridx = GridBagConstraints.CENTER - 4;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 1;

        JButton playBtn = CreateButton("Play", 3, constraints);
        this.add(playBtn, constraints);
        JButton setBtn = CreateButton("Settings", 4, constraints);
        this.add(setBtn, constraints);
        JButton exitBtn = CreateButton("Exit", 5, constraints);
        this.add(exitBtn, constraints);

        playBtn.setVisible(true);
        setBtn.setVisible(true);
        exitBtn.setVisible(true);

        playBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parent.notify(new PlayEvent());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                playBtn.setBackground(Color.CYAN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                playBtn.setBackground(Color.decode("#EE2B95"));
            }
        });

        setBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parent.notify(new SettingsEvent());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBtn.setBackground(Color.CYAN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBtn.setBackground(Color.decode("#EE2B95"));
            }
        });

        exitBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parent.notify(new GameOverEvent());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                exitBtn.setBackground(Color.CYAN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitBtn.setBackground(Color.decode("#EE2B95"));
            }
        });

        this.repaint();
        this.setVisible(true);
    }

    private void CreateLabels() {
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/pic1.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image image = bufferedImage.getScaledInstance(375, 137, Image.SCALE_AREA_AVERAGING);
        ImageIcon icon = new ImageIcon(image);
        JLabel pic1 = new JLabel(icon);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.weightx = 0.5;
        constraints.gridx = GridBagConstraints.CENTER - 4;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 0;

        this.add(pic1, constraints);
    }

    public MainPanel(GameView parent) {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.pink);
        MenuButtons();
        CreateLabels();
        this.parent = parent;
    }
}
