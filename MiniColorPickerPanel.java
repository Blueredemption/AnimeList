import javax.swing.*; // make these imports more specific later
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MiniColorPickerPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    JSlider sliderR, sliderG, sliderB;
    JLabel title, labelR, labelG, labelB;
    JTextField tFieldR, tFieldG, tFieldB;
    String reference, textAreaText;

    AnimePanel abovePanel;

    JLabel showLabel;

    Controller controller;

    JButton cancelButton, commitButton;

    public MiniColorPickerPanel(String textAreaText, String reference, Controller controller, AnimePanel inPanel) { // constructor
        this.controller = controller;
        this.reference = reference;
        this.abovePanel = inPanel;
        this.textAreaText = textAreaText;


        setPreferredSize(new Dimension(300,300));
        setBackground(controller.getAnimeColor(reference));
        setLayout(new FlowLayout(FlowLayout.CENTER,3,5));
        loadSliders();
        placeSlidersAndUI();
    }

    // GETTER
    public Color getPickedColor() {
        return new Color(sliderR.getValue(),sliderG.getValue(),sliderB.getValue());
    }

    // SETTER
    private void setPickedColor(Color newColor) { // private because only this class should use this method
    }

    public void loadSliders() {
        sliderR = generateSlider(0, 255, controller.getAnimeColor(reference).getRed(), 50, 5);
        sliderG = generateSlider(0, 255, controller.getAnimeColor(reference).getGreen(), 50, 5);
        sliderB = generateSlider(0, 255, controller.getAnimeColor(reference).getBlue(), 50, 5);
    }
    // UI methods
    public void placeSlidersAndUI() {
        labelR = new JLabel();
        labelR.setPreferredSize(new Dimension(15, 50));
        labelR.setForeground(controller.getFieldColor("text"));
        labelR.setFont(new Font("Dialog", Font.BOLD, 12));
        labelR.setVerticalAlignment(JLabel.CENTER);
        labelR.setHorizontalAlignment(JLabel.CENTER);
        labelR.setText("R");
        add(labelR);

        tFieldR = new JTextField();
        tFieldR.setPreferredSize(new Dimension(30, 20));
        tFieldR.setBackground(controller.getAnimeColor(reference).brighter());
        tFieldR.setForeground(controller.getFieldColor("text"));
        tFieldR.setCaretColor(controller.getFieldColor("text"));
        tFieldR.setBorder(BorderFactory.createLineBorder(controller.getAnimeColor(reference).brighter()));
        tFieldR.setFont(new Font("Dialog", Font.BOLD, 12));
        tFieldR.addActionListener(new inputActionListener());
        add(tFieldR);

        add(sliderR);
        tFieldR.setText(sliderR.getValue() + "");
        //////////////////
        labelG = new JLabel();
        labelG.setPreferredSize(new Dimension(15, 50));
        labelG.setForeground(controller.getFieldColor("text"));
        labelG.setFont(new Font("Dialog", Font.BOLD, 12));
        labelG.setVerticalAlignment(JLabel.CENTER);
        labelG.setHorizontalAlignment(JLabel.CENTER);
        labelG.setText("G");
        add(labelG);

        tFieldG = new JTextField();
        tFieldG.setPreferredSize(new Dimension(30, 20));
        tFieldG.setBackground(controller.getAnimeColor(reference).brighter());
        tFieldG.setForeground(controller.getFieldColor("text"));
        tFieldG.setCaretColor(controller.getFieldColor("text"));
        tFieldG.setBorder(BorderFactory.createLineBorder(controller.getAnimeColor(reference).brighter()));
        tFieldG.setFont(new Font("Dialog", Font.BOLD, 12));
        tFieldG.addActionListener(new inputActionListener());
        add(tFieldG);

        add(sliderG);
        tFieldG.setText(sliderG.getValue() + "");
        //////////////////
        labelB = new JLabel();
        labelB.setPreferredSize(new Dimension(15, 50));
        labelB.setForeground(controller.getFieldColor("text"));
        labelB.setFont(new Font("Dialog", Font.BOLD, 12));
        labelB.setVerticalAlignment(JLabel.CENTER);
        labelB.setHorizontalAlignment(JLabel.CENTER);
        labelB.setText("B");
        add(labelB);

        tFieldB = new JTextField();
        tFieldB.setPreferredSize(new Dimension(30, 20));
        tFieldB.setBackground(controller.getAnimeColor(reference).brighter());
        tFieldB.setForeground(controller.getFieldColor("text"));
        tFieldB.setCaretColor(controller.getFieldColor("text"));
        tFieldB.setBorder(BorderFactory.createLineBorder(controller.getAnimeColor(reference).brighter()));
        tFieldB.setFont(new Font("Dialog", Font.BOLD, 12));
        tFieldB.addActionListener(new inputActionListener());
        add(tFieldB);
        //////////////////
        add(sliderB);
        tFieldB.setText(sliderB.getValue() + "");

        showLabel = new JLabel();
        showLabel.setOpaque(true);
        showLabel.setPreferredSize(new Dimension(270,34));
        showLabel.setBackground(getPickedColor());
        add(showLabel);

        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 25));
        cancelButton.addActionListener(new cancelButtonActionListener());
        cancelButton.setBackground(controller.getFieldColor("buttons"));
        cancelButton.setForeground(controller.getFieldColor("text"));
        cancelButton.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder")));
        cancelButton.setFocusPainted(false);
        add(cancelButton);

        commitButton = new JButton("Commit");
        commitButton.setPreferredSize(new Dimension(100, 25));
        commitButton.addActionListener(new commitButtonActionListener());
        commitButton.setBackground(controller.getFieldColor("buttons"));
        commitButton.setForeground(controller.getFieldColor("text"));
        commitButton.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder")));
        commitButton.setFocusPainted(false);
        add(commitButton);
    }

    public void toggleEnables(boolean bool) {
        sliderR.setEnabled(bool);
        sliderG.setEnabled(bool);
        sliderB.setEnabled(bool);
        tFieldR.setEnabled(bool);
        tFieldG.setEnabled(bool);
        tFieldB.setEnabled(bool);
        cancelButton.setEnabled(bool);
        commitButton.setEnabled(bool);
    }

    public JSlider generateSlider(int min, int max, int initializer, int tickSpacing, int tockSpacing) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, initializer);
        slider.setUI(new CustomSliderUI(slider, controller.getAnimeColor(reference).brighter()));
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(tickSpacing);
        slider.setMinorTickSpacing(tockSpacing);
        slider.setPaintLabels(true);
        slider.addChangeListener(new sliderListener());
        slider.setPreferredSize(new Dimension(230, 44));
        slider.setBackground(controller.getAnimeColor(reference));
        slider.setForeground(controller.getFieldColor("text"));
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
        }
        repaint(); // repaint and revalidate are here to prevent duplications of objects from
                   // showing up upon moving sliders
        revalidate();
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
            } else {
                setValueForColor(value, 'B');
            } 
            showLabel.setBackground(getPickedColor());
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
                    //refreshShowPanel();
                }
                else System.out.println("invalid input to textField");
            }
            catch (Exception E){
                System.out.println("invalid input to textField");
            }
        }
    }

    private class cancelButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            abovePanel.generateRightBottom();
        }
    }

    private class commitButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.setAnimeColor(reference, getPickedColor());
            controller.set(reference, "customColor", "false");
            abovePanel.refreshPage(textAreaText);
        }
    }
}
