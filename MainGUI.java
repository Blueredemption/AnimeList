//*****************************************//
// Created by Cooper Eisnor                //
//*****************************************//

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainGUI extends JFrame {

    private static final long serialVersionUID = 1L; // this is here to fix a warning over MainGUI. Unsure what it is
                                                     // for, reseach later.

    Controller controller;
    ColorPickerPanel colorPicker;
    JLayeredPane pane;
    JPanel panelA, panelB, panelBA, panelBB, navPanel, subNavPanel1, subNavPanel2, panelA1, panelA2, panelA3, panelA4,
            panelA5;
    JLabel fillLabel, navLabel, miniMainImage, warningLabel;
    JButton nav1, nav2, home, list, stats, notes, newAnime, settings, ok;
    JTextField pathNameField;


    Dimension standardDim = new Dimension(1024, 768);
    Dimension leftDim = new Dimension(978, 768);
    Dimension rightDimSmall = new Dimension(30, 768);
    Dimension rightDimLarge = new Dimension(200, 768);
    Dimension rightDimLargeTop = new Dimension(170, 120);
    Dimension rightDimLargeBottom = new Dimension(170, 354);

    public MainGUI() { // constructor
        controller = new Controller();
        LaunchSettings();
    }

    public void LaunchSettings() { // generates the basic window for the program
        setIconImage(new ImageIcon("Images/UI/Icon.png").getImage());
        setTitle("AnimeList v3");
        setSize(standardDim);
        setLocationRelativeTo(null);
        setResizable(false);
        // setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        generatePane();
        generatePanelB();
        generatePanelA();

    }

    public void generatePane() { // generates layered pane that holds the two main pains
        pane = new JLayeredPane();
        pane.setPreferredSize(standardDim);
        pane.setLayout(new BorderLayout());
        pane.setBackground(controller.getFieldColor("background1"));
        pane.setOpaque(true);
        add(pane, BorderLayout.CENTER);
    }

    public void generatePanelA() { // initially generates the left (content) pane
        panelA = new JPanel();
        pane.add(panelA, BorderLayout.WEST);
        generateHomePage();
    }

    public void generateNavPanel(String label) { // generates the nav panel on the left (content) pane
        navPanel = new JPanel();
        navPanel.setLayout(new BorderLayout());
        navPanel.setPreferredSize(new Dimension(978, 25));
        navPanel.setOpaque(false);
        panelA.add(navPanel, BorderLayout.NORTH);

        subNavPanel1 = new JPanel();
        subNavPanel1.setPreferredSize(new Dimension(808, 25));
        subNavPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        subNavPanel1.setBackground(controller.getFieldColor("navigation"));
        navPanel.add(subNavPanel1, BorderLayout.WEST);

        subNavPanel2 = new JPanel();
        subNavPanel2.setPreferredSize(new Dimension(170, 25));
        subNavPanel2.setBackground(controller.getFieldColor("navigation"));
        navPanel.add(subNavPanel2, BorderLayout.EAST);

        fillLabel = new JLabel(); // this label moves the navLabel over ever so slightly because of flowLayout. It
                                  // will be used elswhere to shift objects around.
        fillLabel.setPreferredSize(new Dimension(180, 25));
        subNavPanel1.add(fillLabel);

        navLabel = new JLabel(label);
        navLabel.setForeground(controller.getFieldColor("text"));
        navLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        navLabel.setVerticalAlignment(JLabel.TOP);
        subNavPanel1.add(navLabel);
    }

    public void generatePanelB() { // initially generates the right (navigation) pane
        panelB = new JPanel();
        pane.add(panelB, BorderLayout.EAST);

        generateNavigationPageSmall();
    }

    public void generateNavigationPageSmall() { // clears whatever nav is there (if needed) and generates the small pane
        pane.remove(panelB);
        pane.repaint();
        pane.revalidate();
        panelB = new JPanel();
        panelB.setLayout(new FlowLayout());
        panelB.setPreferredSize(rightDimSmall);
        panelB.setBackground(controller.getFieldColor("navigation"));
        pane.add(panelB, BorderLayout.EAST);
        pane.moveToFront(panelB);

        nav1 = new JButton();
        nav1.setPreferredSize(new Dimension(20, 120));
        nav1.addActionListener(new flipNavigation());
        nav1.setText("|||");
        setButtonDefaults(nav1);
        panelB.add(nav1);

        // need to add action listeners for these

        home = new JButton();
        home.setPreferredSize(new Dimension(20, 35));
        home.addActionListener(new homeButtonListener());
        home.setText("H");
        setButtonDefaults(home);
        panelB.add(home);

        list = new JButton();
        list.setPreferredSize(new Dimension(20, 35));
        list.addActionListener(new listButtonListener());
        list.setText("L");
        setButtonDefaults(list);
        panelB.add(list);

        stats = new JButton();
        stats.setPreferredSize(new Dimension(20, 35));
        stats.addActionListener(new statisticsButtonListener());
        stats.setText("S");
        setButtonDefaults(stats);
        panelB.add(stats);

        notes = new JButton();
        notes.setPreferredSize(new Dimension(20, 35));
        notes.addActionListener(new notesButtonListener());
        notes.setText("N");
        setButtonDefaults(notes);
        panelB.add(notes);

        newAnime = new JButton();
        newAnime.setPreferredSize(new Dimension(20, 35));
        newAnime.addActionListener(new animeButtonListener());
        newAnime.setText("A");
        setButtonDefaults(newAnime);
        panelB.add(newAnime);

        settings = new JButton();
        settings.setPreferredSize(new Dimension(20, 35));
        settings.addActionListener(new settingsButtonListener());
        settings.setText("S");
        setButtonDefaults(settings);
        panelB.add(settings);

        nav2 = new JButton();
        nav2.setPreferredSize(new Dimension(20, 354));
        nav2.addActionListener(new flipNavigation());
        nav2.setText("|||");
        setButtonDefaults(nav2);
        panelB.add(nav2);
    }

    public void generateNavigationPageLarge() {
        pane.remove(panelB);
        pane.repaint();
        pane.revalidate();

        panelB = new JPanel();
        panelB.setLayout(new FlowLayout());
        panelB.setPreferredSize(rightDimLarge);
        panelB.setBackground(controller.getFieldColor("navigation"));
        pane.add(panelB, BorderLayout.EAST);
        pane.moveToFront(panelB);

        nav1 = new JButton();
        nav1.setPreferredSize(new Dimension(20, 120));
        nav1.addActionListener(new flipNavigation());
        nav1.setText("|||");
        setButtonDefaults(nav1);
        panelB.add(nav1);

        panelBA = new JPanel();
        panelBA.setLayout(new FlowLayout());
        panelBA.setPreferredSize(rightDimLargeTop);
        panelBA.setBackground(new Color(0, 0, 0, 0)); // panelBA/BB are to be clear, hence hard code.
        panelB.add(panelBA);

        home = new JButton();
        home.setPreferredSize(new Dimension(194, 35));
        home.addActionListener(new homeButtonListener());
        home.setText("Home");
        setButtonDefaults(home);
        panelB.add(home);

        list = new JButton();
        list.setPreferredSize(new Dimension(194, 35));
        list.addActionListener(new listButtonListener());
        list.setText("Anime List");
        setButtonDefaults(list);
        panelB.add(list);

        stats = new JButton();
        stats.setPreferredSize(new Dimension(194, 35));
        stats.addActionListener(new statisticsButtonListener());
        stats.setText("Statistics");
        setButtonDefaults(stats);
        panelB.add(stats);

        notes = new JButton();
        notes.setPreferredSize(new Dimension(194, 35));
        notes.addActionListener(new notesButtonListener());
        notes.setText("Notepad");
        setButtonDefaults(notes);
        panelB.add(notes);

        newAnime = new JButton();
        newAnime.setPreferredSize(new Dimension(194, 35));
        newAnime.addActionListener(new animeButtonListener());
        newAnime.setText("New Anime");
        setButtonDefaults(newAnime);
        panelB.add(newAnime);

        settings = new JButton();
        settings.setPreferredSize(new Dimension(194, 35));
        settings.addActionListener(new settingsButtonListener());
        settings.setText("Settings");
        setButtonDefaults(settings);
        panelB.add(settings);

        nav2 = new JButton();
        nav2.setPreferredSize(new Dimension(20, 354));
        nav2.addActionListener(new flipNavigation());
        nav2.setText("|||");
        setButtonDefaults(nav2);
        panelB.add(nav2);

        panelBB = new JPanel();
        panelBB.setLayout(new FlowLayout());
        panelBB.setPreferredSize(rightDimLargeBottom);
        panelBB.setBackground(new Color(0, 0, 0, 0));
        panelB.add(panelBB);
    }

    public void generateHomePage() {
        controller.setState(0);
        generateNavigationPageSmall();
        panelA.removeAll();
        panelA.repaint();
        panelA.revalidate();
        panelA.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 0));
        panelA.setPreferredSize(leftDim);
        panelA.setBackground(controller.getFieldColor("background1"));

        generateNavPanel("Welcome to Your AnimeList!");
        
        JLabel spacer = new JLabel(); // creating some gapping without flowlayout hgap.
        spacer.setPreferredSize(new Dimension(1,3));
        panelA.add(spacer);

        Image image = getImage(controller.getFieldText("mainScreenImage"), new Dimension((int)leftDim.getWidth()-6,(int)leftDim.getHeight()-72));
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension((int)leftDim.getWidth()-6,(int)leftDim.getHeight()-72));
        if (image != null) imageLabel.setIcon(new ImageIcon(image));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panelA.add(imageLabel, BorderLayout.CENTER);
    }

    public void generateListPage() {
        controller.setState(1);
        generateNavigationPageSmall();
        panelA.removeAll();
        panelA.repaint();
        panelA.revalidate();
        panelA.setLayout(new BorderLayout());
        panelA.setPreferredSize(leftDim);
        panelA.setBackground(controller.getFieldColor("background1"));

        generateNavPanel("Viewing: Anime List");
    }

    public void generateStatisticsPage() {
        controller.setState(2);
        generateNavigationPageSmall();
        panelA.removeAll();
        panelA.repaint();
        panelA.revalidate();
        panelA.setLayout(new BorderLayout());
        panelA.setPreferredSize(leftDim);
        panelA.setBackground(controller.getFieldColor("background1"));

        generateNavPanel("Viewing: Statistics");
    }

    public void generateNotesPage() {
        controller.setState(3);
        generateNavigationPageSmall();
        panelA.removeAll();
        panelA.repaint();
        panelA.revalidate();
        panelA.setLayout(new BorderLayout());
        panelA.setPreferredSize(leftDim);
        panelA.setBackground(controller.getFieldColor("background1"));

        generateNavPanel("Viewing: Notes");
    }

    public void generateAnimePage() {
        controller.setState(4);
        generateNavigationPageSmall();
        panelA.removeAll();
        panelA.repaint();
        panelA.revalidate();
        panelA.setLayout(new BorderLayout());
        panelA.setPreferredSize(leftDim);
        panelA.setBackground(controller.getFieldColor("background1"));
        
        generateNavPanel("Viewing: Anime"); // change this later to specific anime
    }

    public void generateSettingsPage() {
        controller.setState(5);
        generateNavigationPageSmall();
        panelA.removeAll();
        panelA.repaint();
        panelA.revalidate();
        panelA.setLayout(new BorderLayout());
        panelA.setPreferredSize(leftDim);
        panelA.setBackground(controller.getFieldColor("background1"));

        generateNavPanel("Viewing: Settings");

        // ************************************** //
        // ************** Left Side ************* //
        // ************************************** //
        panelA1 = new JPanel();
        panelA1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        panelA1.setPreferredSize(new Dimension(348, 700));
        panelA1.setBackground(controller.getFieldColor("background1"));
        panelA.add(panelA1, BorderLayout.WEST);

        JLabel choosePaletteLabel = new JLabel();
        choosePaletteLabel.setPreferredSize(new Dimension(340, 50));
        choosePaletteLabel.setForeground(controller.getFieldColor("text"));
        choosePaletteLabel.setFont(new Font("Dialog", Font.BOLD, 25));
        choosePaletteLabel.setVerticalAlignment(JLabel.CENTER);
        choosePaletteLabel.setHorizontalAlignment(JLabel.CENTER);
        choosePaletteLabel.setText("Choose Palette to Edit:");
        panelA1.add(choosePaletteLabel);

        int bound = 8;
        JLabel[] descriptions = new JLabel[bound];
        JButton[] editButtons = new JButton[bound];
        String[] labelText = { "Navigation Tab", "Buttons", "Button Borders", "Background", "Input Fields", "Dropdowns",
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
            editButtons[i].setText("Edit");
            editButtons[i].setName(labelText[i]);
            setButtonDefaults(editButtons[i]);
            panelA1.add(editButtons[i]);
        }

        JLabel chooseDefaultLabel = new JLabel();
        chooseDefaultLabel.setPreferredSize(new Dimension(340, 50));
        chooseDefaultLabel.setForeground(controller.getFieldColor("text"));
        chooseDefaultLabel.setFont(new Font("Dialog", Font.BOLD, 25));
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
        fillLabel.setPreferredSize(new Dimension(300, 40));
        panelA1.add(fillLabel);

        JLabel chooseMainImageLabel = new JLabel();
        chooseMainImageLabel.setPreferredSize(new Dimension(340, 60));
        chooseMainImageLabel.setForeground(controller.getFieldColor("text"));
        chooseMainImageLabel.setFont(new Font("Dialog", Font.BOLD, 25));
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

        // ************************************** //
        // ************** Right Side ************ //
        // ************************************** //

        panelA2 = new JPanel();
        panelA2.setLayout(new BorderLayout());
        panelA2.setPreferredSize(new Dimension(630, 700));
        panelA2.setBackground(new Color(0, 0, 0, 0));
        panelA.add(panelA2, BorderLayout.EAST);

        panelA3 = new JPanel(); // this will be the color picker
        panelA3.setLayout(new BorderLayout());
        panelA3.setPreferredSize(new Dimension(630, 486));
        panelA3.setBackground(controller.getFieldColor("background1"));
        panelA2.add(panelA3, BorderLayout.NORTH);

        colorPicker = setColorPicker("default");
        
        panelA3.add(colorPicker, BorderLayout.CENTER);

        JPanel fillerPanel = new JPanel();
        fillerPanel.setPreferredSize(new Dimension(620,40));
        fillerPanel.setBackground(new Color(0,0,0,0));
        panelA3.add(fillerPanel, BorderLayout.SOUTH);

        JButton cancel = new JButton("Cancel");
        cancel.setPreferredSize(new Dimension(120,25));
        cancel.addActionListener(new cancelButtonActionListener());
        setButtonDefaults(cancel);
        fillerPanel.add(cancel);

        ok = new JButton("Apply Changes");
        ok.setPreferredSize(new Dimension(120, 25));
        ok.addActionListener(new applyColorActionListener());
        setButtonDefaults(ok);
        fillerPanel.add(ok);

        panelA4 = new JPanel();
        panelA4.setLayout(new FlowLayout());
        panelA4.setPreferredSize(new Dimension(630, 204));
        panelA4.setBackground(controller.getFieldColor("background1"));
        panelA2.add(panelA4, BorderLayout.SOUTH);

        Image image = getImage(controller.getFieldText("mainScreenImage"), new Dimension(274,195));
        miniMainImage = new JLabel();
        if (image != null) miniMainImage.setIcon(new ImageIcon(image));
        miniMainImage.setPreferredSize(new Dimension(274, 195));
        miniMainImage.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panelA4.add(miniMainImage);
    }

    // length cutting methods
    public ColorPickerPanel setColorPicker(String identifierString){ // clears the previous colorPicker off the panel
        if (colorPicker != null) {
            panelA3.remove(colorPicker);
            panelA3.repaint();
            panelA3.revalidate();
        }

        return new ColorPickerPanel(identifierString, controller.getFieldColor("navigation"),controller.getFieldColor("buttons"),controller.getFieldColor("buttonBorder"),
                    controller.getFieldColor("background1"), controller.getFieldColor("background2"),controller.getFieldColor("background3"),controller.getFieldColor("list"),
                    controller.getFieldColor("text"));
    }

    public void setButtonDefaults(JButton button) {
        button.setBackground(controller.getFieldColor("buttons"));
        button.setForeground(controller.getFieldColor("text"));
        button.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder")));
        button.setFocusPainted(false);
    }

    public void swapNavPanelFocus(boolean big) {
        if (!big) {
            subNavPanel2.setBackground(new Color(0, 0, 0, 0));
        } 
        else {
            subNavPanel2.setBackground(controller.getFieldColor("navigation"));
        }
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

    private class flipNavigation implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){   
            if (panelB.getPreferredSize().equals(rightDimSmall)){
                swapNavPanelFocus(false);
                generateNavigationPageLarge();
            }
            else{
                swapNavPanelFocus(true);
                generateNavigationPageSmall();
            } 
        }
    }

    private class homeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            if(controller.getState() != 0) generateHomePage();
        }
    }

    private class listButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            if(controller.getState() != 1) generateListPage();
        }
    }

    private class statisticsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            if(controller.getState() != 2) generateStatisticsPage();
        }
    }

    private class notesButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            if(controller.getState() != 3) generateNotesPage();
        }
    }

    private class animeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            // if(controller.getState() != 4) Unessesary because every time this is clicked the code is meant to generate a new anime
            generateAnimePage();
        }
    }

    private class settingsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            if(controller.getState() != 5) generateSettingsPage();
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

    private class changeColorActionListener implements ActionListener { // activates the color picker to change the right field
        @Override
        public void actionPerformed(ActionEvent V){
            JButton button = (JButton) V.getSource();
            String text = button.getName();

            switch (text){
                case "Navigation Tab": colorPicker = setColorPicker("navigation"); break;
                case "Buttons": colorPicker = setColorPicker("buttons"); break;
                case "Button Borders": colorPicker = setColorPicker("buttonBorder"); break;
                case "Background": colorPicker = setColorPicker("background1"); break;
                case "Input Fields": colorPicker = setColorPicker("background2"); break;
                case "Dropdowns": colorPicker = setColorPicker("background3"); break;
                case "Default List": colorPicker = setColorPicker("list"); break;
                case "Text": colorPicker = setColorPicker("text"); break; 
                default: System.out.println("changeColorActionListener text is invalid");
            }
            panelA3.add(colorPicker,BorderLayout.CENTER);
            generateNavigationPageSmall(); 
            nav1.setEnabled(false); // to prevent overlay issues with color editor
            nav2.setEnabled(false);
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
            pane.setBackground(controller.getFieldColor("background1"));
            generateSettingsPage();
        }
    }

    private class defaultLightActionListener implements ActionListener { 
        @Override
        public void actionPerformed(ActionEvent V){
            controller.loadLightPreset();
            pane.setBackground(controller.getFieldColor("background1"));
            generateSettingsPage();
        }
    }

    private class defaultDarkActionListener implements ActionListener { 
        @Override
        public void actionPerformed(ActionEvent V){
            controller.loadDarkPreset();
            pane.setBackground(controller.getFieldColor("background1"));
            generateSettingsPage();
        }
    }

    private class cancelButtonActionListener implements ActionListener { // refreshes the page, thus making the colorpicker default
        @Override
        public void actionPerformed(ActionEvent V){
            generateSettingsPage();
        }
    }
}