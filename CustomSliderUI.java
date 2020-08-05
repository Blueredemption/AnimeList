import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.Color;

public class CustomSliderUI extends BasicSliderUI {
    Color trackColor;

    public CustomSliderUI(JSlider b, Color trackColor) {
        super(b);

        this.trackColor = trackColor;
    }


    protected Color getShadowColor() {
       return trackColor;
    }

    protected Color getHighlightColor() {
        return trackColor.brighter();
    }

    protected Color getFocusColor() {
        return trackColor.darker();
    }
}