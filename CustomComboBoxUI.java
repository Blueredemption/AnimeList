//*****************************************//
// Created by Cooper Eisnor                //
//*****************************************//
// this class handles the design (and functionality) of the scroll pane inside of the dropdown dropdown

import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.*; // replace these
import java.awt.*;

public class CustomComboBoxUI extends BasicComboBoxUI{
    private Color background;
    private Color buttonColor;

    public CustomComboBoxUI(Color background, Color buttonColor) {
        super();
        this.background = background;
        this.buttonColor = buttonColor;
    }
    @Override // custom dropdown button
    protected JButton createArrowButton() {
        BasicArrowButton button = new BasicArrowButton(BasicArrowButton.SOUTH,
                                    buttonColor,
                                    buttonColor.darker(),
                                    buttonColor.darker(),
                                    buttonColor.brighter());
        button.setName("ComboBox.arrowButton");
        return button;
    }

    @Override // custom scroller
    protected BasicComboPopup createPopup() {
        return new BasicComboPopup(comboBox) {
            private static final long serialVersionUID = 1L;
            
            @Override
            protected JScrollPane createScroller() {
                JScrollPane scroller = new JScrollPane(list, 20, 31); // 20 > scrollbar as needed, // 31 no horizontal bar ever
                scroller.getVerticalScrollBar().setUI(new BasicScrollBarUI() {    
                    @Override protected JButton createDecreaseButton(int orientation) {return createNullButton();} // remove the arrow up 
                    @Override protected JButton createIncreaseButton(int orientation) {return createNullButton();} // remove the arrow down 
                    @Override public Dimension getPreferredSize(JComponent c) {return new Dimension(10, super.getPreferredSize(c).height);}
                    
                    @Override protected void configureScrollBarColors() {
                        thumbColor = buttonColor;
                        thumbHighlightColor = buttonColor.brighter();
                        thumbLightShadowColor = buttonColor.darker();
                        thumbDarkShadowColor = buttonColor.darker().darker();
                        trackColor = background;
                        trackHighlightColor = background.brighter();
                    }

                    private JButton createNullButton() { // creates a 0,0 button
                        JButton button = new JButton();
                        button.setPreferredSize(new Dimension(0,0));
                        return button;
                    }
                });
                
                return scroller;
            }
        };
    }
}