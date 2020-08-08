//*****************************************//
// Created by Cooper Eisnor                //
//*****************************************//
// custom look for JscrollBars

import javax.swing.JButton;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.Dimension;
import java.awt.Color;

public class CustomScrollBarUI extends BasicScrollBarUI {
    Controller controller;
    boolean bool;
    Color buttons;
    Color dropdowns;
    public CustomScrollBarUI(Controller controller){ // constructor
        this.controller = controller;
        bool = true;
    }

    public CustomScrollBarUI(Controller controller, Color buttons, Color dropdowns){ // constructor for the JComboBox call
        this.controller = controller;
        this.buttons = buttons;
        this.dropdowns = dropdowns;
        bool = false;
    }

    @Override
    public void configureScrollBarColors(){
        if (bool){
            thumbColor = controller.getFieldColor("buttons");
            thumbLightShadowColor = controller.getFieldColor("buttons").brighter();
            thumbDarkShadowColor = controller.getFieldColor("buttons").darker();
            trackColor = controller.getFieldColor("list");
            trackHighlightColor = controller.getFieldColor("background1");
        }
        else{
            thumbColor = buttons;
            thumbLightShadowColor = buttons.brighter();
            thumbDarkShadowColor = buttons.darker();
            trackColor = dropdowns;
            trackHighlightColor = dropdowns;
        }
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