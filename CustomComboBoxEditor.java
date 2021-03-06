// this class handles the design (and functionality) of the dropdown box
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxEditor;
 
public class CustomComboBoxEditor extends BasicComboBoxEditor {
    private JLabel label;
    private JPanel panel;
    private Object selectedItem;
    private Color background;
    private Color border;
    private Color text;
     
    public CustomComboBoxEditor(Controller controller) { // constructor
        super();
        label = new JLabel();
        panel = new JPanel();
        background = controller.getFieldColor("background3");
        border = controller.getFieldColor("background2");
        text = controller.getFieldColor("text");
         
        changeFields();
    }

    public void changeFields(){
        label.setOpaque(false);
        label.setFont(new Font("Dialog", Font.BOLD, 12));
        label.setForeground(text);
        
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
        panel.add(label);
        panel.setBackground(background);
        panel.setBorder(BorderFactory.createLineBorder(border));
    }
     
    public Component getEditorComponent() {
        return this.panel;
    }
     
    public Object getItem() {
        return this.selectedItem.toString();
    }
     
    public void setItem(Object item) {
        this.selectedItem = item;
        if (item != null) label.setText(item.toString());
        else label.setText("");
    }
}
