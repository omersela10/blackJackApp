package blackJackApp;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public class CustomSliderUI extends BasicSliderUI {

    private final ImageIcon knobIcon;

    public CustomSliderUI(ImageIcon knobIcon) {
        super(null);
        this.knobIcon = knobIcon;
    }

    @Override
    public void paintThumb(Graphics g) {
        Rectangle knobBounds = thumbRect;
        knobIcon.paintIcon(slider, g, knobBounds.x, knobBounds.y);
    }
}

