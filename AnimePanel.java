//*****************************************//
// Created by Cooper Eisnor                //
//*****************************************//

import java.awt.*; // change these later
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.basic.BasicProgressBarUI;

import java.util.Calendar;
import java.awt.event.*;
import java.util.ArrayList;

import com.github.lgooddatepicker.*;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;

public class AnimePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    Dimension fullDim = new Dimension(978,700);

    Controller controller;
    String reference;
    ArrayList<String> references;

    JPanel topPanel, bottomPanel, leftTopPanel, leftBottomPanel, rightTopPanel, rightBottomPanel;
    JTextArea textArea;
    JButton leftButton, rightButton;

    public AnimePanel(Controller controller, String reference){
        this.controller = controller;
        this.reference = reference;

        setLayout(new BorderLayout());
        setOpaque(false);


        generateTopBottom();
        generateLeftTop();
        generateLeftBottom();
        generateRightTop();
        generateRightBottom();
    }

    public void generateTopBottom(){
        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(978,440));
        topPanel.setLayout(new BorderLayout());
        topPanel.setOpaque(false);
        add(topPanel, BorderLayout.NORTH);

        bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(978,265));
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setOpaque(false);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void generateLeftTop(){
        if (leftTopPanel != null) topPanel.remove(leftTopPanel);
        repaint();
        revalidate();

        leftTopPanel = new JPanel();
        leftTopPanel.setPreferredSize(new Dimension(662,438));
        leftTopPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,2));
        leftTopPanel.setBackground(controller.getAnimeColor(reference));
        topPanel.add(leftTopPanel,BorderLayout.WEST);

        int buttonBound = 50;
        int labelBound = 100;
        JButton[] buttons = new JButton[buttonBound];
        JLabel[] labels = new JLabel[labelBound];
        for(int i = 0; i < labelBound; i++){
            labels[i] = new JLabel();
            labels[i].setPreferredSize(new Dimension(100,30));
            labels[i].setForeground(controller.getFieldColor("text"));
            labels[i].setFont(new Font("Dialog", Font.BOLD, 15));
            labels[i].setVerticalAlignment(JLabel.CENTER);
            labels[i].setHorizontalAlignment(JLabel.LEFT);
            if (i < buttonBound){
                buttons[i] = new JButton();
                buttons[i].setPreferredSize(new Dimension(80, 25));
                buttons[i].setText("/"); // defualt text
                setButtonDefaults(buttons[i]);
            }
        }

        int cb = 0;
        int c1 = 0;

        buttons[cb].setName("animeName");
        buttons[cb].addActionListener(new makeEditActionListener());
        leftTopPanel.add(buttons[cb++]);

        labels[c1].setFont(new Font("Dialog", Font.BOLD, 20));
        labels[c1].setPreferredSize(new Dimension(481,25));
        labels[c1].setText(controller.get(reference,"animeName"));
        labels[c1].setHorizontalAlignment(JLabel.CENTER);
        leftTopPanel.add(labels[c1++]);

        buttons[cb].addActionListener(new changeImageActionListener());
        buttons[cb].setText("Image");
        leftTopPanel.add(buttons[cb++]);

        labels[c1].setPreferredSize(new Dimension(600,10)); // spacer
        leftTopPanel.add(labels[c1++]);

        //
        buttons[cb].setName("numberOfEpisodesWatched");
        buttons[cb].addActionListener(new makeEditActionListener());
        leftTopPanel.add(buttons[cb++]);

        labels[c1].setPreferredSize(new Dimension(40,30)); // spacer
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(300,25));
        labels[c1].setText("Episodes Watched:");
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(220,25));
        labels[c1].setText(controller.get(reference, "numberOfEpisodesWatched") +" Eps."); 
        leftTopPanel.add(labels[c1++]);

        //
        buttons[cb].setName("numberOfEpisodesTotal");
        buttons[cb].addActionListener(new makeEditActionListener());
        leftTopPanel.add(buttons[cb++]);
        
        labels[c1].setPreferredSize(new Dimension(40,30)); // spacer
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(300,25));
        labels[c1].setText("Out of:");
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(220,25));
        labels[c1].setText(controller.get(reference, "numberOfEpisodesTotal") +" Eps."); 
        leftTopPanel.add(labels[c1++]);

        //
        buttons[cb].setName("averageEpisodeLength");
        buttons[cb].addActionListener(new makeEditActionListener());
        leftTopPanel.add(buttons[cb++]);
        
        labels[c1].setPreferredSize(new Dimension(40,30)); // spacer
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(300,25));
        labels[c1].setText("Average Episode Length:");
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(220,25));
        labels[c1].setText(controller.get(reference, "averageEpisodeLength") +" min"); 
        leftTopPanel.add(labels[c1++]);
        
        //
        buttons[cb].setName("seasonReleased");
        buttons[cb].addActionListener(new makeEditActionListener());
        leftTopPanel.add(buttons[cb++]);
        
        labels[c1].setPreferredSize(new Dimension(40,30)); // spacer
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(300,25));
        labels[c1].setText("Season Released:");
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(220,25));
        labels[c1].setText(controller.get(reference, "seasonReleased")); 
        leftTopPanel.add(labels[c1++]);
        
        //
        buttons[cb].setName("yearReleased");
        buttons[cb].addActionListener(new makeEditActionListener());
        leftTopPanel.add(buttons[cb++]);
        
        labels[c1].setPreferredSize(new Dimension(40,30)); // spacer
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(300,25));
        labels[c1].setText("Year Released:");
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(220,25));
        labels[c1].setText(controller.get(reference, "yearReleased")); 
        leftTopPanel.add(labels[c1++]);

        //
        buttons[cb].setName("watchingStartDate");
        buttons[cb].addActionListener(new makeEditActionListener());
        leftTopPanel.add(buttons[cb++]);
        
        labels[c1].setPreferredSize(new Dimension(40,30)); // spacer
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(300,25));
        labels[c1].setText("Started Watching:");
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(220,25));
        labels[c1].setText(controller.get(reference, "watchingStartDate")); 
        leftTopPanel.add(labels[c1++]);

        //
        buttons[cb].setName("watchingEndDate");
        buttons[cb].addActionListener(new makeEditActionListener());
        leftTopPanel.add(buttons[cb++]);
        
        labels[c1].setPreferredSize(new Dimension(40,30)); // spacer
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(300,25));
        labels[c1].setText("Finished Watching:");
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(220,25));
        labels[c1].setText(controller.get(reference, "watchingEndDate")); 
        leftTopPanel.add(labels[c1++]);

        //
        buttons[cb].setName("languageWatchedIn");
        buttons[cb].addActionListener(new makeEditActionListener());
        leftTopPanel.add(buttons[cb++]);
        
        labels[c1].setPreferredSize(new Dimension(40,30)); // spacer
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(300,25));
        labels[c1].setText("Language:");
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(220,25));
        labels[c1].setText(controller.get(reference, "languageWatchedIn")); 
        leftTopPanel.add(labels[c1++]);

        //
        buttons[cb].setName("ageRating");
        buttons[cb].addActionListener(new makeEditActionListener());
        leftTopPanel.add(buttons[cb++]);
        
        labels[c1].setPreferredSize(new Dimension(40,30)); // spacer
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(300,25));
        labels[c1].setText("Content Rating:");
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(220,25));
        labels[c1].setText(controller.get(reference, "ageRating")); 
        leftTopPanel.add(labels[c1++]);

        //
        buttons[cb].setName("mainGenre");
        buttons[cb].addActionListener(new makeEditActionListener());
        leftTopPanel.add(buttons[cb++]);
        
        labels[c1].setPreferredSize(new Dimension(40,30)); // spacer
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(300,25));
        labels[c1].setText("Main Genre:");
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(220,25));
        labels[c1].setText(controller.get(reference, "mainGenre")); 
        leftTopPanel.add(labels[c1++]);

        //
        buttons[cb].setName("animationStudio");
        buttons[cb].addActionListener(new makeEditActionListener());
        leftTopPanel.add(buttons[cb++]);
        
        labels[c1].setPreferredSize(new Dimension(40,30)); // spacer
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(300,25));
        labels[c1].setText("Studio:");
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(220,25));
        labels[c1].setText(controller.get(reference, "animationStudio")); 
        leftTopPanel.add(labels[c1++]);

        //
        labels[c1].setPreferredSize(new Dimension(600,15)); // spacer
        leftTopPanel.add(labels[c1++]);

        buttons[cb].setName("finish");
        buttons[cb].setText("MAX");
        buttons[cb].setFont(new Font("Dialog", Font.BOLD, 10));
        buttons[cb].addActionListener(new makeEditActionListener());
        leftTopPanel.add(buttons[cb++]);

        labels[c1].setPreferredSize(new Dimension(40,30)); // spacer
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(110,25));
        labels[c1].setText("Progress:");
        leftTopPanel.add(labels[c1++]);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setPreferredSize(new Dimension (276,25));
        progressBar.setValue((int)(Double.parseDouble(controller.get(reference, "progress"))));
        progressBar.setBackground(controller.getAnimeColor(reference).brighter().brighter()); 
        progressBar.setForeground(controller.getAnimeColor(reference).brighter());
        progressBar.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder").brighter()));
        progressBar.setStringPainted(true);
        progressBar.setUI(new BasicProgressBarUI() {
            protected Color getSelectionBackground() { return controller.getFieldColor("text");}
            protected Color getSelectionForeground() { return controller.getFieldColor("text");} });
        leftTopPanel.add(progressBar);

        labels[c1].setPreferredSize(new Dimension(40,30)); // spacer
        leftTopPanel.add(labels[c1++]);

        buttons[cb].setName("color");
        buttons[cb].setText("Color");
        buttons[cb].addActionListener(new makeEditActionListener());
        leftTopPanel.add(buttons[cb++]);
    }

    public void generateLeftBottom(){
        if (leftBottomPanel != null) bottomPanel.remove(leftBottomPanel);
        repaint();
        revalidate();

        leftBottomPanel = new JPanel();
        leftBottomPanel.setPreferredSize(new Dimension(662,267));
        leftBottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,6));
        leftBottomPanel.setBackground(controller.getAnimeColor(reference));
        bottomPanel.add(leftBottomPanel,BorderLayout.WEST);

        int n = 1000;

        JLabel spacer = new JLabel(); // spacer
        spacer.setPreferredSize(new Dimension(4,10));
        leftBottomPanel.add(spacer);

        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(600,n));
        textArea.setBackground(controller.getAnimeColor(reference).brighter());
        textArea.setForeground(controller.getFieldColor("text"));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setCaretColor(controller.getFieldColor("text"));
        textArea.setFont(new Font("Dialog", Font.PLAIN, 14));
        textArea.setText(controller.get(reference,"notepadText"));
        
        JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
        scrollPane.setOpaque(false);
        scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, controller.getAnimeColor(reference), 
                                                                 controller.getAnimeColor(reference).darker(), 
                                                                 controller.getAnimeColor(reference), 
                                                                 controller.getAnimeColor(reference).brighter()));
        scrollPane.setPreferredSize(new Dimension (648,223));
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
        scrollPane.remove(scrollBar); 
        scrollBar.setPreferredSize(new Dimension(10,223));
        scrollBar.setUI(new CustomScrollBarUI(controller,controller.getFieldColor("buttons"),controller.getAnimeColor(reference).brighter()));
        scrollBar.setBorder(new BevelBorder(BevelBorder.LOWERED, controller.getAnimeColor(reference), 
                                                                 controller.getAnimeColor(reference).darker(), 
                                                                 controller.getAnimeColor(reference), 
                                                                 controller.getAnimeColor(reference).brighter()));
        leftBottomPanel.add(scrollPane);
        leftBottomPanel.add(scrollBar);

        spacer = new JLabel(); // spacer
        spacer.setPreferredSize(new Dimension(5,10));
        leftBottomPanel.add(spacer);

        JButton removeAnimeButton = new JButton("Remove");
        removeAnimeButton.setName("remove");
        removeAnimeButton.setPreferredSize(new Dimension(80, 25));
        removeAnimeButton.addActionListener(new makeEditActionListener());
        setButtonDefaults(removeAnimeButton);
        leftBottomPanel.add(removeAnimeButton);

        spacer = new JLabel(); // spacer
        spacer.setPreferredSize(new Dimension(471,10));
        leftBottomPanel.add(spacer);

        JButton saveButton = new JButton("Save Notepad");
        saveButton.setPreferredSize(new Dimension(100, 25));
        saveButton.addActionListener(new saveButtonActionListener());
        setButtonDefaults(saveButton);
        leftBottomPanel.add(saveButton);
    }

    public void generateRightTop(){ 
        if (rightTopPanel != null) topPanel.remove(rightTopPanel);
        repaint();
        revalidate();

        rightTopPanel = new JPanel();
        rightTopPanel.setPreferredSize(new Dimension(316,438));
        rightTopPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        rightTopPanel.setBackground(controller.getAnimeColor(reference));
        topPanel.add(rightTopPanel,BorderLayout.EAST);

        Image image = getImage(controller.get(reference,"imageLocation"), new Dimension(316,440));
        JLabel imageLabel = new JLabel();
        if (image != null) imageLabel.setIcon(new ImageIcon(image));
        imageLabel.setPreferredSize(new Dimension(316, 440));
        imageLabel.setBorder(new BevelBorder(BevelBorder.LOWERED, controller.getAnimeColor(reference), 
                                                                  controller.getAnimeColor(reference).darker(), 
                                                                  controller.getAnimeColor(reference), 
                                                                  controller.getAnimeColor(reference).brighter()));
        rightTopPanel.add(imageLabel);
    }

    public void generateRightBottom(){
        if (rightBottomPanel != null) bottomPanel.remove(rightBottomPanel);
        repaint();
        revalidate();

        rightBottomPanel = new JPanel();
        rightBottomPanel.setPreferredSize(new Dimension(316,267));
        rightBottomPanel.setLayout(new BorderLayout());
        rightBottomPanel.setBackground(controller.getAnimeColor(reference));
        bottomPanel.add(rightBottomPanel,BorderLayout.EAST);

        setLeftRightButtons();

        JPanel actionPanel = new JPanel();
        actionPanel.setPreferredSize(new Dimension(316,241));
        actionPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,0,0));
        actionPanel.setOpaque(false);
        rightBottomPanel.add(actionPanel, BorderLayout.CENTER);

        JLabel[] labels = new JLabel[13];
        for(int i = 0; i < 13; i++){
            labels[i] = new JLabel();
            labels[i].setPreferredSize(new Dimension(150,30));
            labels[i].setForeground(controller.getFieldColor("text"));
            labels[i].setFont(new Font("Dialog", Font.BOLD, 15));
            labels[i].setVerticalAlignment(JLabel.CENTER);
            labels[i].setHorizontalAlignment(JLabel.LEFT);
        }

        int c1 = 0;

        // controller calls to get this information
        labels[c1].setText("General Statistics:");
        labels[c1].setHorizontalAlignment(JLabel.CENTER);
        labels[c1].setVerticalAlignment(JLabel.BOTTOM);
        labels[c1].setPreferredSize(new Dimension(300,34));
        labels[c1].setFont(new Font("Dialog", Font.BOLD, 18));
        actionPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(300,9));
        actionPanel.add(labels[c1++]);

        labels[c1].setText("Time Spent Watching:");
        labels[c1].setPreferredSize(new Dimension(185,30));
        actionPanel.add(labels[c1++]);

        labels[c1].setText(controller.getDays(reference) +" Days");
        labels[c1].setPreferredSize(new Dimension(120,30));
        actionPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(185,30));
        actionPanel.add(labels[c1++]); // filler

        labels[c1].setText(controller.getHours(reference) +" Hours");
        labels[c1].setPreferredSize(new Dimension(120,30));
        actionPanel.add(labels[c1++]); 

        labels[c1].setPreferredSize(new Dimension(185,30));
        actionPanel.add(labels[c1++]); // filler
        
        labels[c1].setText(controller.getMinutes(reference) +" Minutes");
        labels[c1].setPreferredSize(new Dimension(120,30));
        actionPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(300,30));
        actionPanel.add(labels[c1++]);

        labels[c1].setText("Over a Span of:");
        labels[c1].setPreferredSize(new Dimension(185,30));
        actionPanel.add(labels[c1++]);

        labels[c1].setText("Unknown Days");
        labels[c1].setPreferredSize(new Dimension(120,30)); // get after dates overhaul
        actionPanel.add(labels[c1++]);

        labels[c1].setText("% of Total Watch Time:");
        labels[c1].setPreferredSize(new Dimension(185,30));
        actionPanel.add(labels[c1++]);

        labels[c1].setText(controller.getPercentWhole(reference) +"%");
        labels[c1].setPreferredSize(new Dimension(120,30));
        actionPanel.add(labels[c1]);

    }
        

    public void generateRightBottom(String sourceString){ // for inputField usage.
        if (rightBottomPanel != null) bottomPanel.remove(rightBottomPanel);
        repaint();
        revalidate();

        rightBottomPanel = new JPanel();
        rightBottomPanel.setPreferredSize(new Dimension(316,267));
        rightBottomPanel.setLayout(new BorderLayout());
        rightBottomPanel.setBackground(controller.getAnimeColor(reference));
        bottomPanel.add(rightBottomPanel,BorderLayout.EAST);

        setLeftRightButtons();

        JPanel actionPanel = new JPanel();
        actionPanel.setPreferredSize(new Dimension(316,241));
        actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        actionPanel.setOpaque(false);
        rightBottomPanel.add(actionPanel, BorderLayout.CENTER);

        // switch statement starts here
        switch(sourceString){
            case "animeName": loadTextRetrieval(sourceString, actionPanel); break; //
            case "numberOfEpisodesWatched": loadButtonInput(sourceString, actionPanel); break; //
            case "numberOfEpisodesTotal": loadButtonInput(sourceString, actionPanel); break; //
            case "averageEpisodeLength": loadDropDown(sourceString, actionPanel); break; //
            case "seasonReleased": loadDropDown(sourceString, actionPanel); break; //
            case "yearReleased": loadDropDown(sourceString, actionPanel); break; //
            case "watchingStartDate": loadDatePicker(sourceString, actionPanel); break;
            case "watchingEndDate": loadDatePicker(sourceString, actionPanel); break;
            case "languageWatchedIn": loadDropDown(sourceString, actionPanel); break; //
            case "ageRating": loadDropDown(sourceString, actionPanel); break; //
            case "mainGenre": loadDropDown(sourceString, actionPanel); break; //
            case "animationStudio": loadTextRetrieval(sourceString, actionPanel); break; //
            case "finish": loadTextRetrieval(sourceString, actionPanel); break; //
            case "color": loadMiniColorPicker(actionPanel); break; //
            case "remove": loadTextRetrieval(sourceString, actionPanel); break; //
            default: System.out.println("Invalid source: " +sourceString);
        } 
    }
    
    //length cutting methods
    public void loadTextRetrieval(String source, JPanel actionPanel){
        switch(source){
            case "animeName": break;
            case "animationStudio": break;
            case "finish": break;
            case "remove": break;
        }
    }

    public void loadButtonInput(String source, JPanel actionPanel){
        switch(source){
            case "numberOfEpisodesWatched": break; 
            case "numberOfEpsiodesTotal": break;
        }
    }

    public void loadDropDown(String source, JPanel actionPanel){
        switch(source){
            case "averageEpisodeLength": break; 
            case "seasonReleased": break; 
            case "yearReleased": break; 
            case "languageWatchedIn": break; 
            case "contentRating": break; 
            case "mainGenre": break;
        }
    }

    public void loadDatePicker(String source, JPanel actionPanel){
        JLabel sectionLabel = new JLabel();
        sectionLabel.setHorizontalAlignment(JLabel.CENTER);
        sectionLabel.setVerticalAlignment(JLabel.BOTTOM);
        sectionLabel.setPreferredSize(new Dimension(300,34));
        sectionLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        sectionLabel.setForeground(controller.getFieldColor("text"));
        actionPanel.add(sectionLabel);

        JLabel spacer = new JLabel(); // spacer
        spacer.setPreferredSize(new Dimension(300,10));
        actionPanel.add(spacer);

        DatePickerSettings settings = new DatePickerSettings(); // edit what needs to be edited in the settings
        settings.setColor(DateArea.TextFieldBackgroundValidDate, controller.getFieldColor("background2"));
        settings.setColor(DateArea.DatePickerTextValidDate, controller.getFieldColor("text"));
        settings.setFontValidDate(new Font("Dialog", Font.BOLD, 13));
        settings.setAllowEmptyDates(false);
        
        DatePicker datePicker = new DatePicker();
        datePicker.setSettings(settings);
        datePicker.setDateToToday();
        datePicker.setOpaque(false);
        
        JButton toggleButton = datePicker.getComponentToggleCalendarButton(); // pull out these to edit them directly, some of these I think I need to do this
                                                                              // or something similar to get the effect I want.
        toggleButton.setText("\u2B9F");
        setButtonDefaults(toggleButton);

        JTextField dateField = datePicker.getComponentDateTextField();
        dateField.setHorizontalAlignment(JTextField.CENTER);
        dateField.setBackground(controller.getFieldColor("background2"));
        dateField.setForeground(controller.getFieldColor("text"));
        dateField.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder")));
        dateField.setPreferredSize(new Dimension(206,25));
        dateField.setEditable(false);

        actionPanel.add(datePicker);

        switch(source){
            case "watchingStartDate": 
                sectionLabel.setText("Set Start Date:");
                break;
            case "watchingEndDate": 
                sectionLabel.setText("Set End Date:");
                break;
        }
    }

    public void loadMiniColorPicker(JPanel actionPanel){

    }

    public void setButtonDefaults(JButton button) {
        button.setBackground(controller.getFieldColor("buttons"));
        button.setForeground(controller.getFieldColor("text"));
        button.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder")));
        button.setFocusPainted(false);
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

    public void setLeftRightButtons(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(315,26));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,1));
        buttonPanel.setOpaque(false);
        rightBottomPanel.add(buttonPanel,BorderLayout.NORTH);

        leftButton = new JButton("Previous");
        leftButton.setName("left");
        leftButton.setPreferredSize(new Dimension(156, 25));
        leftButton.addActionListener(new switchButtonActionListener());
        setButtonDefaults(leftButton);
        buttonPanel.add(leftButton);

        JLabel spacer = new JLabel(); // spacer
        spacer.setPreferredSize(new Dimension(3,10));
        buttonPanel.add(spacer);

        rightButton = new JButton("Next");
        rightButton.setName("right");
        rightButton.setPreferredSize(new Dimension(156, 25));
        rightButton.addActionListener(new switchButtonActionListener());
        setButtonDefaults(rightButton);
        buttonPanel.add(rightButton);
    }

    //ACTION LISTENERS
    private class makeEditActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            JButton source = (JButton)(V.getSource());
            String sourceString = source.getName();
            generateRightBottom(sourceString);
        }
    }

    private class changeImageActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File ("Images/Anime"));
            fc.showOpenDialog(null);
            File file = fc.getSelectedFile();
            try{
                Image image = ImageIO.read(new File(file.getAbsolutePath()));
                image = image.getScaledInstance(274,195,Image.SCALE_DEFAULT);
                controller.set(reference,"imageLocation", file.getAbsolutePath());
                generateRightTop();
            }
            catch(Exception e){ // if an exception occurs it will happen at the first line of the statement, thus:
                System.out.println("Invalid file type, change ignored");
            }
        }
    }

    private class saveButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            controller.set(reference,"notepadText",textArea.getText());
        }
    }

    private class switchButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            // do something
        }
    }









    private class DateLabelFormatter extends AbstractFormatter {
        private static final long serialVersionUID = 1L;

        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
    
        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }
    
        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
    
            return "";
        }
    }
    
    
}