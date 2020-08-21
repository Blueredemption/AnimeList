import java.awt.*; // change these later
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class SettingsPanel extends JPanel{
    private static final long serialVersionUID = 1L;
    Controller controller;
    JFrame superior;  // access to the object that called it is only here to tell the program to refresh colors on change, and to disable navs when using colorPickerPanel.
    ColorPickerPanel colorPicker;

    JPanel panelA1, panelA2, panelA3;
    JLabel  fillLabel, miniMainImage, warningLabel;
    JTextField pathNameField;

    public SettingsPanel(JFrame superior, Controller controller){
        this.controller = controller;
        this.superior = superior;

        setLayout(new BorderLayout());
        setOpaque(false);

        generateLeft();
        generateRight();
    }

    public void generateLeft(){
        if (panelA1 != null) remove(panelA1);
        repaint();
        revalidate();

        panelA1 = new JPanel();
        panelA1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        panelA1.setPreferredSize(new Dimension(348, 700));
        panelA1.setBackground(controller.getFieldColor("background1"));
        add(panelA1, BorderLayout.WEST);

        JLabel choosePaletteLabel = new JLabel();
        choosePaletteLabel.setPreferredSize(new Dimension(340, 50));
        choosePaletteLabel.setForeground(controller.getFieldColor("text"));
        choosePaletteLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        choosePaletteLabel.setVerticalAlignment(JLabel.CENTER);
        choosePaletteLabel.setHorizontalAlignment(JLabel.CENTER);
        choosePaletteLabel.setText("Choose Palette to Edit:");
        panelA1.add(choosePaletteLabel);

        int bound = 8;
        JLabel[] descriptions = new JLabel[bound];
        JButton[] editButtons = new JButton[bound];
        String[] labelText = { "Navigation Tab", "Buttons", "Borders", "Background", "Fields", "Dropdowns",
                "Default List", "Text"};

        for (int i = 0; i < bound; i++) {
            descriptions[i] = new JLabel();
            descriptions[i].setPreferredSize(new Dimension(220, 30));
            descriptions[i].setForeground(controller.getFieldColor("text"));
            descriptions[i].setFont(new Font("Dialog", Font.BOLD, 15));
            descriptions[i].setVerticalAlignment(JLabel.CENTER);
            descriptions[i].setHorizontalAlignment(JLabel.LEFT);
            descriptions[i].setText(labelText[i]);
            panelA1.add(descriptions[i]);

            editButtons[i] = new JButton();
            editButtons[i].setPreferredSize(new Dimension(80, 25));
            editButtons[i].addActionListener(new changeColorActionListener());
            editButtons[i].setText("/");
            editButtons[i].setName(labelText[i]);
            setButtonDefaults(editButtons[i]);
            panelA1.add(editButtons[i]);
        }

        JLabel chooseDefaultLabel = new JLabel();
        chooseDefaultLabel.setPreferredSize(new Dimension(340, 50));
        chooseDefaultLabel.setForeground(controller.getFieldColor("text"));
        chooseDefaultLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        chooseDefaultLabel.setVerticalAlignment(JLabel.CENTER);
        chooseDefaultLabel.setHorizontalAlignment(JLabel.CENTER);
        chooseDefaultLabel.setText("Presets:");
        panelA1.add(chooseDefaultLabel);

        JButton lightPreset = new JButton();
        lightPreset.setPreferredSize(new Dimension(130, 30));
        // lightPreset.addActionListener(new );
        lightPreset.setText("Default Light");
        setButtonDefaults(lightPreset);
        lightPreset.addActionListener(new defaultLightActionListener());
        panelA1.add(lightPreset);

        fillLabel = new JLabel(); // spacer
        fillLabel.setPreferredSize(new Dimension(20, 10));
        panelA1.add(fillLabel);

        JButton darkPreset = new JButton();
        darkPreset.setPreferredSize(new Dimension(130, 30));
        // darkPreset.addActionListener(new );
        darkPreset.setText("Default Dark");
        setButtonDefaults(darkPreset);
        darkPreset.addActionListener(new defaultDarkActionListener());
        panelA1.add(darkPreset);

        fillLabel = new JLabel(); // spacer
        fillLabel.setPreferredSize(new Dimension(300, 30));
        panelA1.add(fillLabel);

        JLabel chooseMainImageLabel = new JLabel();
        chooseMainImageLabel.setPreferredSize(new Dimension(340, 60));
        chooseMainImageLabel.setForeground(controller.getFieldColor("text"));
        chooseMainImageLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        chooseMainImageLabel.setVerticalAlignment(JLabel.CENTER);
        chooseMainImageLabel.setHorizontalAlignment(JLabel.CENTER);
        chooseMainImageLabel.setText("Choose Homescreen Image:");
        panelA1.add(chooseMainImageLabel);

        JButton chooseMainImage = new JButton();
        chooseMainImage.setPreferredSize(new Dimension(80, 25));
        chooseMainImage.addActionListener(new mainImageListener());
        chooseMainImage.setText("Choose File");
        setButtonDefaults(chooseMainImage);
        panelA1.add(chooseMainImage);

        pathNameField = new JTextField();
        pathNameField.setPreferredSize(new Dimension(240, 25));
        pathNameField.setBackground(controller.getFieldColor("background2")); // background2 is for fields etc
        pathNameField.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("background2")));
        pathNameField.setForeground(controller.getFieldColor("text"));
        pathNameField.setEnabled(false);
        pathNameField.setText(" " + controller.getFieldText("mainScreenImage"));
        panelA1.add(pathNameField);

        warningLabel = new JLabel();
        warningLabel.setPreferredSize(new Dimension(340, 60));
        warningLabel.setForeground(controller.getFieldColor("text"));
        warningLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        warningLabel.setVerticalAlignment(JLabel.TOP);
        warningLabel.setHorizontalAlignment(JLabel.CENTER);
        warningLabel.setText("Invalid File Type");
        warningLabel.setVisible(false);
        panelA1.add(warningLabel);
    }

    public void generateRight(){
        if (panelA2 != null) remove(panelA2);
        repaint();
        revalidate();

        panelA2 = new JPanel();
        panelA2.setLayout(new BorderLayout());
        panelA2.setPreferredSize(new Dimension(630, 700));
        panelA2.setBackground(new Color(0, 0, 0, 0));
        add(panelA2, BorderLayout.EAST);

        panelA3 = new JPanel();
        panelA3.setLayout(new BorderLayout());
        panelA3.setPreferredSize(new Dimension(630, 700));
        panelA3.setBackground(controller.getFieldColor("background1"));
        panelA2.add(panelA3, BorderLayout.NORTH);

        colorPicker = setColorPicker("default");
        
        panelA3.add(colorPicker, BorderLayout.CENTER);

        JPanel panelA4 = new JPanel();
        panelA4.setPreferredSize(new Dimension(620,255));
        panelA4.setLayout(new FlowLayout(FlowLayout.CENTER,30,5));
        panelA4.setBackground(new Color(0,0,0,0)); 
        panelA3.add(panelA4, BorderLayout.SOUTH);

        JButton cancel = new JButton("Cancel");
        cancel.setPreferredSize(new Dimension(120,30));
        cancel.addActionListener(new cancelButtonActionListener());
        setButtonDefaults(cancel);
        panelA4.add(cancel);

        JButton ok = new JButton("Commit");
        ok.setPreferredSize(new Dimension(120, 30));
        ok.addActionListener(new applyColorActionListener());
        setButtonDefaults(ok);
        panelA4.add(ok);

        JLabel spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(600,7));
        panelA4.add(spacer);

        Image image = getImage(controller.getFieldText("mainScreenImage"), new Dimension(274,195));
        miniMainImage = new JLabel();
        if (image != null) miniMainImage.setIcon(new ImageIcon(image));
        miniMainImage.setPreferredSize(new Dimension(274, 195));
        miniMainImage.setBorder(new BevelBorder(BevelBorder.LOWERED, controller.getFieldColor("background1"), 
                                                                     controller.getFieldColor("background1").darker(), 
                                                                     controller.getFieldColor("background1"), 
                                                                     controller.getFieldColor("background1").brighter()));
        panelA4.add(miniMainImage);
    }
    

    // length cutting methods
    public void setButtonDefaults(JButton button) {
        button.setBackground(controller.getFieldColor("buttons"));
        button.setForeground(controller.getFieldColor("text"));
        button.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder")));
        button.setFocusPainted(false);
    }

    public ColorPickerPanel setColorPicker(String identifierString){ // clears the previous colorPicker off the panel
        if (colorPicker != null) {
            panelA3.remove(colorPicker);
            panelA3.repaint();
            panelA3.revalidate();
        }

        return new ColorPickerPanel(identifierString, controller);
    }

    public Image getImage(String location, Dimension dim) {
        try {
            Image thisImage = ImageIO.read(new File(location));
            thisImage = thisImage.getScaledInstance((int) dim.getWidth(), (int) dim.getHeight(), Image.SCALE_DEFAULT);
            return thisImage;
        }
        catch (IOException e) { // if image read fails return null
            Image thisImage = null;
            return thisImage;
        }
    } 

    // ACTION LISTENERS
    private class changeColorActionListener implements ActionListener { // activates the color picker to change the right field
        @Override
        public void actionPerformed(ActionEvent V){
            JButton button = (JButton) V.getSource();
            String text = button.getName();

            switch (text){
                case "Navigation Tab": colorPicker = setColorPicker("navigation"); break;
                case "Buttons": colorPicker = setColorPicker("buttons"); break;
                case "Borders": colorPicker = setColorPicker("buttonBorder"); break;
                case "Background": colorPicker = setColorPicker("background1"); break;
                case "Fields": colorPicker = setColorPicker("background2"); break;
                case "Dropdowns": colorPicker = setColorPicker("background3"); break;
                case "Default List": colorPicker = setColorPicker("list"); break;
                case "Text": colorPicker = setColorPicker("text"); break; 
                default: System.out.println("changeColorActionListener text is invalid");
            }
            panelA3.add(colorPicker,BorderLayout.CENTER);
            ((MainGUI)superior).generateNavigationPageSmall(); 
            ((MainGUI)superior).setNavsDisabled();
        }
    }

    private class applyColorActionListener implements ActionListener { // activates the color picker to change the right field
        @Override
        public void actionPerformed(ActionEvent V){
            Color newColor = colorPicker.getPickedColor();
            String changed = colorPicker.getField();

            switch (changed) {
                case "buttons": controller.setFieldColor("buttons",newColor); break;
                case "background1": controller.setFieldColor("background1",newColor); break;
                case "background2": controller.setFieldColor("background2",newColor); break;
                case "background3": controller.setFieldColor("background3",newColor); break;
                case "list": controller.setFieldColor("list",newColor); break;
                case "navigation": controller.setFieldColor("navigation",newColor); break;
                case "text": controller.setFieldColor("text",newColor); break;
                case "buttonBorder": controller.setFieldColor("buttonBorder",newColor); break;
                default: System.out.println("applyColorActionListener field is invalid");
            }
            ((MainGUI)superior).generateSettingsPage();
        }
    }

    private class defaultLightActionListener implements ActionListener { 
        @Override
        public void actionPerformed(ActionEvent V){
            controller.loadLightPreset();
            ((MainGUI)superior).generateSettingsPage();
        }
    }

    private class defaultDarkActionListener implements ActionListener { 
        @Override
        public void actionPerformed(ActionEvent V){
            controller.loadDarkPreset();
            ((MainGUI)superior).generateSettingsPage();
        }
    }

    private class cancelButtonActionListener implements ActionListener { // refreshes the page, thus making the colorpicker default
        @Override
        public void actionPerformed(ActionEvent V){
            ((MainGUI)superior).generateSettingsPage();
        }
    }

    private class mainImageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File ("Images/Anime"));
            fc.showOpenDialog(null);
            File file = fc.getSelectedFile();
            try{
                Image image = ImageIO.read(new File(file.getAbsolutePath()));
                image = image.getScaledInstance(274,195,Image.SCALE_DEFAULT);
                ImageIcon icon = new ImageIcon(image);
                controller.setFieldText("mainScreenImage", file.getAbsolutePath());
                pathNameField.setText(file.getAbsolutePath());
                miniMainImage.setIcon(icon);
                warningLabel.setVisible(false);
            }
            catch(Exception e){ // if an exception occurs it will happen at the first line of the statement, thus:
                warningLabel.setVisible(true);
            }
            
        }
    }
}
