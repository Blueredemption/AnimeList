// generates and handles the colorpicker in the SettingsPanel
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorPickerPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    JSlider sliderR, sliderG, sliderB, sliderO;
    JLabel title, labelR, labelG, labelB, labelO;
    JTextField tFieldR, tFieldG, tFieldB, tFieldO;
    Color navigation, buttons, buttonBorders, background, inputFields, dropdowns, defaultList, text;
    String field;

    JLayeredPane showPanel;
    JPanel showRegularPanel, showNavPanel, showListPanel;
    JButton showButton;
    JComboBox<String> showBox; 
    JTextField showTextField;
    JLabel showLabel, showLabel2;

    Controller controller;

    public ColorPickerPanel(String field, Controller controller) { // constructor
        this.controller = controller;
        this.field = field;

        navigation = controller.getFieldColor("navigation");
        buttons = controller.getFieldColor("buttons");
        buttonBorders = controller.getFieldColor("buttonBorder");
        background = controller.getFieldColor("background1");
        inputFields = controller.getFieldColor("background2");
        dropdowns = controller.getFieldColor("background3");
        defaultList = controller.getFieldColor("list");
        text = controller.getFieldColor("text");

        setBackground(copyOf(background));
        setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
        loadSliders();
        placeSlidersAndUI();
        generateAndAddShowPanel();
        
    }

    // GETTERS
    public Color getPickedColor() {
        switch (field) {
            case "buttons": return buttons;
            case "background1": return background;
            case "background2": return inputFields;
            case "background3": return dropdowns;
            case "list": return defaultList;
            case "navigation": return navigation;
            case "text": return text;
            case "buttonBorder": return buttonBorders;
            default: return Color.BLACK;
        }
    }

    public String getField(){
        return field;
    }

    // SETTER
    private void setPickedColor(Color newColor) { // private because only this class should use this method
        switch (field) {
            case "buttons":
                buttons = newColor;
                break;
            case "background1":
                background = newColor;
                break;
            case "background2":
                inputFields = newColor;
                break;
            case "background3":
                dropdowns = newColor;
                break;
            case "list":
                defaultList = newColor;
                break;
            case "navigation":
                navigation = newColor;
                break;
            case "text":
                text = newColor;
                break;
            case "buttonBorder":
                buttonBorders = newColor;
                break;
            default:
                System.out.println(field + " is not a valid choice in setPickedColor() ColorPickerPanel");
                break;
        }
    }

    // linecount reducing methods for constructors
    public Color copyOf(Color color) { // makes sure the fields of the base UI aren't changing like in the showPanel
                                       // (copying the object not the reference)
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public void loadSliders() {
        sliderR = generateSlider(0, 255, getPickedColor().getRed(), 50, 5);
        sliderG = generateSlider(0, 255, getPickedColor().getGreen(), 50, 5);
        sliderB = generateSlider(0, 255, getPickedColor().getBlue(), 50, 5);
        sliderO = generateSlider(0, 255, getPickedColor().getAlpha(), 50, 5);
    }
    // UI methods
    public void placeSlidersAndUI() {
        title = new JLabel();
        title.setPreferredSize(new Dimension(620, 60));
        title.setForeground(copyOf(text));
        title.setFont(new Font("Dialog", Font.BOLD, 20));
        title.setVerticalAlignment(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setText(getFieldName());
        add(title);
        //////////////////
        labelR = new JLabel();
        labelR.setPreferredSize(new Dimension(15, 60));
        labelR.setForeground(copyOf(text));
        labelR.setFont(new Font("Dialog", Font.BOLD, 12));
        labelR.setVerticalAlignment(JLabel.CENTER);
        labelR.setHorizontalAlignment(JLabel.CENTER);
        labelR.setText("R");
        add(labelR);

        tFieldR = new JTextField();
        tFieldR.setPreferredSize(new Dimension(30, 20));
        tFieldR.setBackground(copyOf(inputFields));
        tFieldR.setForeground(copyOf(text));
        tFieldR.setCaretColor(copyOf(text));
        tFieldR.setBorder(BorderFactory.createLineBorder(inputFields));
        tFieldR.setFont(new Font("Dialog", Font.BOLD, 12));
        tFieldR.addActionListener(new inputActionListener());
        add(tFieldR);

        add(sliderR);
        tFieldR.setText(sliderR.getValue() + "");
        //////////////////
        labelG = new JLabel();
        labelG.setPreferredSize(new Dimension(15, 60));
        labelG.setForeground(copyOf(text));
        labelG.setFont(new Font("Dialog", Font.BOLD, 12));
        labelG.setVerticalAlignment(JLabel.CENTER);
        labelG.setHorizontalAlignment(JLabel.CENTER);
        labelG.setText("G");
        add(labelG);

        tFieldG = new JTextField();
        tFieldG.setPreferredSize(new Dimension(30, 20));
        tFieldG.setBackground(copyOf(inputFields));
        tFieldG.setForeground(copyOf(text));
        tFieldG.setCaretColor(copyOf(text));
        tFieldG.setBorder(BorderFactory.createLineBorder(inputFields));
        tFieldG.setFont(new Font("Dialog", Font.BOLD, 12));
        tFieldG.addActionListener(new inputActionListener());
        add(tFieldG);

        add(sliderG);
        tFieldG.setText(sliderG.getValue() + "");
        //////////////////
        labelB = new JLabel();
        labelB.setPreferredSize(new Dimension(15, 60));
        labelB.setForeground(copyOf(text));
        labelB.setFont(new Font("Dialog", Font.BOLD, 12));
        labelB.setVerticalAlignment(JLabel.CENTER);
        labelB.setHorizontalAlignment(JLabel.CENTER);
        labelB.setText("B");
        add(labelB);

        tFieldB = new JTextField();
        tFieldB.setPreferredSize(new Dimension(30, 20));
        tFieldB.setBackground(copyOf(inputFields));
        tFieldB.setForeground(copyOf(text));
        tFieldB.setCaretColor(copyOf(text));
        tFieldB.setBorder(BorderFactory.createLineBorder(inputFields));
        tFieldB.setFont(new Font("Dialog", Font.BOLD, 12));
        tFieldB.addActionListener(new inputActionListener());
        add(tFieldB);

        add(sliderB);
        tFieldB.setText(sliderB.getValue() + "");
        //////////////////
        labelO = new JLabel();
        labelO.setPreferredSize(new Dimension(15, 60));
        labelO.setForeground(copyOf(text));
        labelO.setFont(new Font("Dialog", Font.BOLD, 12));
        labelO.setVerticalAlignment(JLabel.CENTER);
        labelO.setHorizontalAlignment(JLabel.CENTER);
        labelO.setText("O");
        add(labelO);

        tFieldO = new JTextField();
        tFieldO.setPreferredSize(new Dimension(30, 20));
        tFieldO.setBackground(copyOf(inputFields));
        tFieldO.setForeground(copyOf(text));
        tFieldO.setCaretColor(copyOf(text));
        tFieldO.setBorder(BorderFactory.createLineBorder(inputFields));
        tFieldO.setFont(new Font("Dialog", Font.BOLD, 12));
        tFieldO.addActionListener(new inputActionListener());
        add(tFieldO);

        add(sliderO);
        tFieldO.setText(sliderO.getValue() + "");
        ///////////////////

        if (field.equals("default"))
            disableSliders();

        if (!field.equals("navigation")){
            sliderO.setEnabled(false);
            tFieldO.setEnabled(false);
        }
    }

    public void disableSliders() {
        sliderR.setEnabled(false);
        sliderG.setEnabled(false);
        sliderB.setEnabled(false);
        sliderO.setEnabled(false);
        tFieldR.setEnabled(false);
        tFieldG.setEnabled(false);
        tFieldB.setEnabled(false);
        tFieldO.setEnabled(false);
    }

    public JSlider generateSlider(int min, int max, int initializer, int tickSpacing, int tockSpacing) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, initializer);
        slider.setUI(new CustomSliderUI(slider, inputFields));
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(tickSpacing);
        slider.setMinorTickSpacing(tockSpacing);
        slider.setPaintLabels(true);
        slider.addChangeListener(new sliderListener());
        slider.setPreferredSize(new Dimension(560, 60));
        slider.setBackground(copyOf(background));
        slider.setForeground(copyOf(text));
        return slider;
    }

    // action for action listener below to perform
    public void setValueForColor(int value, char color) { // takes the new field and applies it to the correct color
                                                          // object
        switch (color) {// this looks this way because color object doesn't seem to have setters.
            case 'R':
                setPickedColor(new Color(value, getPickedColor().getGreen(), getPickedColor().getBlue(),
                        getPickedColor().getAlpha()));
                tFieldR.setText(value + "");
                break;
            case 'G':
                setPickedColor(new Color(getPickedColor().getRed(), value, getPickedColor().getBlue(),
                        getPickedColor().getAlpha()));
                tFieldG.setText(value + "");
                break;
            case 'B':
                setPickedColor(new Color(getPickedColor().getRed(), getPickedColor().getGreen(), value,
                        getPickedColor().getAlpha()));
                tFieldB.setText(value + "");
                break;
            case 'O':
                setPickedColor(new Color(getPickedColor().getRed(), getPickedColor().getGreen(),
                        getPickedColor().getBlue(), value));
                tFieldO.setText(value + "");
                break;
        }
        repaint(); // repaint and revalidate are here to prevent duplications of objects from
                   // showing up upon moving sliders
        revalidate();
    }

    public String getFieldName() {
        String start = "Editing: ";
        switch (field) {
            case "buttons":
                return start +"Buttons";
            case "background1":
                return start +"Background";
            case "background2":
                return start +"Input Fields";
            case "background3":
                return start +"Dropdowns";
            case "list":
                return start +"Default List";
            case "navigation":
                return start +"Navigation Tab";
            case "text":
                return start +"Text";
            case "buttonBorder":
                return start +"Button Borders";
            default:
                return "Color Picker:";
        }
    }

    // example display related methods
    public void generateAndAddShowPanel() {
        showPanel = new JLayeredPane();
        showPanel.setPreferredSize(new Dimension(600, 120));
        showPanel.setLayout(new BorderLayout());
        showPanel.setBackground(background);
        showPanel.setOpaque(true);
        add(showPanel);

        showRegularPanel = new JPanel();
        showRegularPanel.setPreferredSize(new Dimension(600, 120));
        showRegularPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        showRegularPanel.setBackground(new Color(0,0,0,0));
        showPanel.add(showRegularPanel,BorderLayout.WEST); // needs to be west/east, center forces things around above it in a layered pane...

        String[] exampleList = {"Example Entry 1","Example Entry 2","Example Entry 3","Example Entry 4","Example Entry 5","Example Entry 6","Example Entry 7","Example Entry 8","Example Entry 9"};
        showBox = new JComboBox<String>(exampleList);
        showBox.setRenderer(new CustomComboBoxRenderer(controller));
        showBox.setEditor(new CustomComboBoxEditor(controller));
        showBox.setUI(new CustomComboBoxUI(controller));
        showBox.setPreferredSize(new Dimension(200,26));
        showBox.setFont(new Font("Dialog", Font.BOLD, 12));
        showBox.setForeground(text);
        showBox.setBackground(dropdowns);
        showBox.setBorder(BorderFactory.createLineBorder(buttonBorders));
        showBox.setFocusable(false);
        showRegularPanel.add(showBox);

        showButton = new JButton();
        showButton.setText("Example");
        showButton.setPreferredSize(new Dimension(80,26));
        showButton.setBackground(buttons);
        showButton.setForeground(text);
        showButton.setBorder(BorderFactory.createLineBorder(buttonBorders));
        showButton.setFocusPainted(false);
        showRegularPanel.add(showButton);

        showTextField = new JTextField("Example Text");
        showTextField.setPreferredSize(new Dimension(135, 25));
        showTextField.setBackground(inputFields);
        showTextField.setForeground(text);
        showTextField.setCaretColor(text);
        showTextField.setFont(new Font("Dialog", Font.BOLD, 12));
        showTextField.setBorder(BorderFactory.createLineBorder(inputFields));
        showRegularPanel.add(showTextField);

        showNavPanel = new JPanel();
        showNavPanel.setPreferredSize(new Dimension(120,120));
        showNavPanel.setBackground(navigation);
        showPanel.add(showNavPanel,BorderLayout.EAST);
        showPanel.moveToFront(showNavPanel);

        showLabel = new JLabel("This is example text. Don't take what it says seriously. Seriously. Now, I got info on the goods and...");
        showLabel.setPreferredSize(new Dimension(550, 25));
        showLabel.setForeground(text);
        showRegularPanel.add(showLabel);

        showListPanel = new JPanel();
        showListPanel.setPreferredSize(new Dimension(550,40));
        showListPanel.setBackground(defaultList);
        showRegularPanel.add(showListPanel);
    }
    
    public void refreshShowPanel() {
        showPanel.setBackground(background);
        showBox.setForeground(text);
        showBox.setBackground(dropdowns);
        showBox.setBorder(BorderFactory.createLineBorder(buttonBorders));
        showBox.setUI(new CustomComboBoxUI(controller, buttons, dropdowns, text));
        showBox.setRenderer(new CustomComboBoxRenderer(dropdowns,text));
        showButton.setBackground(buttons);
        showButton.setForeground(text);
        showButton.setBorder(BorderFactory.createLineBorder(buttonBorders));
        showTextField.setBackground(inputFields);
        showTextField.setForeground(text);
        showLabel.setForeground(text);
        showListPanel.setBackground(defaultList);
        showNavPanel.setBackground(navigation);
        showPanel.moveToFront(showNavPanel);
    }

    // listeners
    class sliderListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            JSlider slider = (JSlider) e.getSource();
            int value = slider.getValue();

            if (slider == sliderR) {
                setValueForColor(value, 'R');
            } else if (slider == sliderG) {
                setValueForColor(value, 'G');
            } else if (slider == sliderB) {
                setValueForColor(value, 'B');
            } else { // (slider == sliderO)
                setValueForColor(value, 'O');
            }

            refreshShowPanel();
        }
    }

    private class inputActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField area = (JTextField) e.getSource();
            String value = area.getText();
            int nValue;
            try{
                nValue = Integer.parseInt(value);
                if ((nValue >= 0)&&(nValue <= 255)){
                    if (e.getSource() == tFieldR) {
                        setValueForColor(nValue, 'R');
                        sliderR.setValue(nValue);
                    }
                    else if (e.getSource() == tFieldG) {
                        setValueForColor(nValue, 'G');
                        sliderG.setValue(nValue);
                    }
                    else if (e.getSource() == tFieldB) {
                        setValueForColor(nValue, 'B');
                        sliderB.setValue(nValue);
                    }
                    else if (e.getSource() == tFieldO) {
                        setValueForColor(nValue, 'O');
                        sliderO.setValue(nValue);
                    }

                    refreshShowPanel();
                }
                else System.out.println("invalid input to textField");
            }
            catch (Exception E){
                System.out.println("invalid input to textField");
            }

        }
  }
}
