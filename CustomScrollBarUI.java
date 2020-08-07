import javax.swing.JButton;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.Dimension;

public class CustomScrollBarUI extends BasicScrollBarUI {
    Controller controller;
    public CustomScrollBarUI(Controller controller){
        this.controller = controller;
    }

    @Override
    public void configureScrollBarColors(){
        thumbColor = controller.getFieldColor("buttons");
        thumbLightShadowColor = controller.getFieldColor("buttons").brighter();
        thumbDarkShadowColor = controller.getFieldColor("buttons").darker();
        trackColor = controller.getFieldColor("list");
        trackHighlightColor = controller.getFieldColor("background1");
    }

    @Override
    public JButton createDecreaseButton(int orientation){
        JButton nullButton = new JButton();
        nullButton.setPreferredSize(new Dimension(0,0));
        return nullButton;
    }

    @Override
    public JButton createIncreaseButton(int orientation){
        JButton nullButton = new JButton();
        nullButton.setPreferredSize(new Dimension(0,0));
        return nullButton;
    }
}