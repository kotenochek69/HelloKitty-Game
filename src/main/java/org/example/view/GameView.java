package org.example.view;

import org.example.observer.Observable;
import org.example.observer.Observer;
import org.example.observer.events.Event;
import org.example.observer.events.SetLooknFeelEvent;
import org.example.view.panels.MainPanel;
import org.example.view.panels.ModsPanel;
import org.example.view.panels.SessionPanel;
import org.example.view.panels.SettingsPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class GameView extends Component implements Observer, Observable {
    private final JFrame frame;
    private final List<JPanel> panels = new ArrayList<>();
    private static final Integer x_resolution = 1920;
    private static final Integer y_resolution = 180;

    public GameView() {

        MainPanel mainPanel = new MainPanel(this);
        panels.add(mainPanel);
        ModsPanel modsPanel = new ModsPanel(this);
        panels.add(modsPanel);
        SettingsPanel settingsPanel = new SettingsPanel(this);
        panels.add(settingsPanel);
        SessionPanel sessionPanel = new SessionPanel(this);
        panels.add(sessionPanel);


        frame = new JFrame("HelloKitty Game");
        frame.setBackground(Color.pink);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setLocation(0, 0);
        frame.setPreferredSize(new Dimension(x_resolution, y_resolution));
        frame.setMinimumSize(new Dimension(x_resolution, y_resolution));
        frame.setVisible(true);
    }

    @Override
    public void handle(Event e) {
        if (e.getClass().getSimpleName().equals("GameStartEvent")) {
            System.out.println("Завершение игры. Показываем сообщение пользователю и блокируем экран");
        } else {
            System.out.println("Игнорируем другое событие : " + e.getClass());
        }

    }

    private final List<Observer> observers = new ArrayList<>();

    public void register(Observer o) {
        observers.add(o);
    }

    public void remove(Observer o) {
        observers.remove(o);
    }

    public void notify(Event e) {
        for (Observer o : observers) {
            o.handle(e);
        }
    }

    public void showPanel(Integer n) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panels.get(n));
        frame.revalidate();
        frame.repaint();

    }


    public void SetLooknFeel(SetLooknFeelEvent e) {
        String LnFName = null;
        System.out.println(e.getLookAndFeel());
        if (Objects.equals(e.getLookAndFeel(), "Windows")) {
            LnFName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        }
        if (Objects.equals(e.getLookAndFeel(), "Metal")) {
            LnFName = "javax.swing.plaf.metal.MetalLookAndFeel";
        }
        if (Objects.equals(e.getLookAndFeel(), "Motion")) {
            LnFName = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
        }
        if (LnFName != null) {
            try {
                UIManager.setLookAndFeel(LnFName);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     UnsupportedLookAndFeelException ex) {
                throw new RuntimeException(ex);
            }
        }
        SwingUtilities.updateComponentTreeUI(frame);

    }

    public void FreeScreen() {
        frame.dispose();
    }

    public void createField(Integer mod) {
        ((SessionPanel) panels.get(3)).createField(mod);
    }

    public SessionPanel getSession() {
        return (SessionPanel) panels.get(3);
    }

    public void resetSession() {
        panels.set(3, new SessionPanel(this));
    }
}

