package org.example.observer.events;

import javax.swing.*;

public class SetLooknFeelEvent extends Event {
    public SetLooknFeelEvent(JRadioButton lookAndFeel) {
        LookAndFeel = lookAndFeel;
    }

    public String getLookAndFeel() {
        JRadioButton btn = LookAndFeel;
        return btn.getText();
    }

    JRadioButton LookAndFeel;

}
