// custom look for JSliders

import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.Color;

public class CustomSliderUI extends BasicSliderUI {
    Color trackColor;

    public CustomSliderUI(JSlider b, Color trackColor) {
        super(b);

        this.trackColor = trackColor;
    }

    @Override
    protected Color getShadowColor() {
       return trackColor;
    }

    @Override
    protected Color getHighlightColor() {
        return trackColor.brighter();
    }

    @Override
    protected Color getFocusColor() {
        return trackColor.darker();
    }
}
