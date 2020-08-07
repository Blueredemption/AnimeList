//*****************************************//
// Created by Cooper Eisnor                //
//*****************************************//
// this class handles the design (and functionality) of the scroll pane inside of the dropdown dropdown

import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.*; // replace these
import java.awt.*;

public class CustomComboBoxUI extends BasicComboBoxUI{
    private Color background;
    private Color buttonColor;
    private Color textColor;

    public CustomComboBoxUI(Color background, Color buttonColor, Color textColor) {
        super();
        this.background = background;
        this.buttonColor = buttonColor;
        this.textColor = textColor;
    }
    @Override // custom dropdown button
    protected JButton createArrowButton() {
        JButton button = new JButton();
        button.setText(String.valueOf("\u2B9F"));
        button.setBackground(buttonColor);
        button.setForeground(textColor);
        button.setFont(new Font("Dialog", Font.BOLD, 10));
        button.setBorder(BorderFactory.createLineBorder(buttonColor));
        button.setFocusPainted(false);
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