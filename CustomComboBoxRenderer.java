// this class handles the design (and functionality) of the dropdown dropdown (modified copy of source)

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

class CustomComboBoxRenderer extends JLabel implements ListCellRenderer<Object> {
    private static final long serialVersionUID = 1L;
    Color dropdown;
    Color text;

    public CustomComboBoxRenderer(Controller controller) {
        setOpaque(true);
        dropdown = controller.getFieldColor("background3");
        text = controller.getFieldColor("text");
    }

    public CustomComboBoxRenderer(Color dropdown, Color text) {
        setOpaque(true);
        this.dropdown = dropdown;
        this.text = text;
    }

    @SuppressWarnings("rawtypes") // warning due to generic list object
    public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus) {
        setText(" " +value.toString());

        Color background;
        Color foreground;

        // check if this cell represents the current DnD drop location
        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null && !dropLocation.isInsert() && dropLocation.getIndex() == index) {
            background = dropdown;
            foreground = text;
        } // check if this cell is selected
        else if (isSelected) {
            background = dropdown.darker();
            foreground = text.darker();
        } // unselected, and not the DnD drop location
        else {
            background = dropdown;
            foreground = text;
        };

        setBackground(background);
        setForeground(foreground);

        return this;
    }
}