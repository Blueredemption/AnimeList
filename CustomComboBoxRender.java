//*****************************************//
// Created by Cooper Eisnor                //
//*****************************************//
// this class handles the design (and functionality) of the dropdown dropdown

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

 
public class CustomComboBoxRender extends JLabel implements ListCellRenderer<Object> {
    private static final long serialVersionUID = 1L;
    private Color background;
    private Color text;

    public CustomComboBoxRender(Color background, Color text) {
        super();
        this.background = background;
        this.text = text;

        changeFields();
    }

    public void changeFields(){
        setOpaque(true);
        setFont(new Font("Dialog", Font.BOLD, 12));
        setBackground(background);
        setForeground(text);
    }


    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.toString());
        return this;
    }
 
}