// this class handles the design (and functionality) of the scroll pane inside of the dropdown dropdown

import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.*; // replace these
import java.awt.*;

public class CustomComboBoxUI extends BasicComboBoxUI{
    private Controller controller;
    private Color buttonColor;
    private Color textColor;
    private Color dropdowns;

    public CustomComboBoxUI(Controller controller) { // constructor
        super();
        this.controller = controller;

        buttonColor = controller.getFieldColor("buttons");
        textColor = controller.getFieldColor("text");
        dropdowns = controller.getFieldColor("background3");
    }

    public CustomComboBoxUI(Controller controller, Color buttons, Color dropdowns, Color text) { // constructor for colorpicker
        super();
        this.controller = controller;

        buttonColor = buttons;
        this.dropdowns = dropdowns;
        textColor = text;
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
                scroller.getVerticalScrollBar().setUI(new CustomScrollBarUI(controller, buttonColor, dropdowns));
                scroller.getVerticalScrollBar().setPreferredSize(new Dimension(5,50));
                return scroller;
            }
        };
    }
}
