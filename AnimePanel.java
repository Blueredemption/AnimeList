//*****************************************//
// Created by Cooper Eisnor                //
//*****************************************//

import java.awt.*; // change these later
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.basic.BasicProgressBarUI;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;

public class AnimePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    Dimension fullDim = new Dimension(978,700);
    boolean commitState = true;
    boolean hiddenOverride = true; // if rightBottomPanel needs to be reloaded.

    Controller controller;
    MainGUI mainGUI;
    String reference;
    ArrayList<String> references;
    DatePicker datePicker;
    JComboBox<String> dropBox;
    MiniColorPickerPanel colorPanel;

    JButton nButton1, nButton2, nButton3, nButton4, nButton5, nButton6;
    JPanel topPanel, bottomPanel, leftTopPanel, leftBottomPanel, rightTopPanel, rightBottomPanel;
    JTextArea textArea;
    JTextField textInput;
    JButton leftButton, rightButton, cancelButton, commitButton;
    JLabel numberLabel, toggleSpacer;

    public AnimePanel(Controller controller, MainGUI mainGUI, String reference){  // constructor
        this.controller = controller;
        this.mainGUI = mainGUI;
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
            labels[i].setPreferredSize(new Dimension(100,29));
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

        labels[c1].setPreferredSize(new Dimension(40,28)); // spacer
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
        
        labels[c1].setPreferredSize(new Dimension(40,28)); // spacer
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
        
        labels[c1].setPreferredSize(new Dimension(40,28)); // spacer
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(300,25));
        labels[c1].setText("Episode Length:");
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(220,25));
        labels[c1].setText(controller.get(reference, "averageEpisodeLength") +" min"); 
        leftTopPanel.add(labels[c1++]);
        
        //
        buttons[cb].setName("seasonReleased");
        buttons[cb].addActionListener(new makeEditActionListener());
        leftTopPanel.add(buttons[cb++]);
        
        labels[c1].setPreferredSize(new Dimension(40,28)); // spacer
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
        
        labels[c1].setPreferredSize(new Dimension(40,28)); // spacer
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
        
        labels[c1].setPreferredSize(new Dimension(40,28)); // spacer
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
        
        labels[c1].setPreferredSize(new Dimension(40,28)); // spacer
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
        
        labels[c1].setPreferredSize(new Dimension(40,28)); // spacer
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
        
        labels[c1].setPreferredSize(new Dimension(40,28)); // spacer
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
        
        labels[c1].setPreferredSize(new Dimension(40,28)); // spacer
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(300,25));
        labels[c1].setText("Main Genre:");
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(220,25));
        labels[c1].setText(controller.get(reference, "mainGenre")); 
        leftTopPanel.add(labels[c1++]);

        //
        buttons[cb].setName("subGenre");
        buttons[cb].addActionListener(new makeEditActionListener());
        leftTopPanel.add(buttons[cb++]);
        
        labels[c1].setPreferredSize(new Dimension(40,28)); // spacer
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(300,25));
        labels[c1].setText("Secondary Genre:");
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(220,25));
        labels[c1].setText(controller.get(reference, "subGenre")); 
        leftTopPanel.add(labels[c1++]);

        //
        buttons[cb].setName("animationStudio");
        buttons[cb].addActionListener(new makeEditActionListener());
        leftTopPanel.add(buttons[cb++]);
        
        labels[c1].setPreferredSize(new Dimension(40,28)); // spacer
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(300,25));
        labels[c1].setText("Animation Studio:");
        leftTopPanel.add(labels[c1++]);

        labels[c1].setPreferredSize(new Dimension(220,25));
        labels[c1].setText(controller.get(reference, "animationStudio")); 
        leftTopPanel.add(labels[c1++]);
        
        //
        labels[c1].setPreferredSize(new Dimension(600,7)); // spacer
        leftTopPanel.add(labels[c1++]);

        buttons[cb].setName("finish");
        buttons[cb].setText("MAX");
        buttons[cb].setFont(new Font("Dialog", Font.BOLD, 10));
        buttons[cb].addActionListener(new makeEditActionListener());
        leftTopPanel.add(buttons[cb++]);

        labels[c1].setPreferredSize(new Dimension(40,28)); // spacer
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
        textArea.setFont(new Font("Dialog", Font.BOLD, 14));
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
        spacer.setPreferredSize(new Dimension(200,25));
        leftBottomPanel.add(spacer);

        JLabel hiddenLabel = new JLabel("Hidden?");
        hiddenLabel.setPreferredSize(new Dimension(70,25));
        hiddenLabel.setForeground(controller.getFieldColor("text"));
        hiddenLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        hiddenLabel.setVerticalAlignment(JLabel.CENTER);
        hiddenLabel.setHorizontalAlignment(JLabel.LEFT);
        leftBottomPanel.add(hiddenLabel);
        
        JButton hiddenBox = new JButton();
        if (controller.get(reference,"hidden").equals("true")) hiddenBox.setText("\u2714");
        hiddenBox.setPreferredSize(new Dimension(15,15));
        setButtonDefaults(hiddenBox);
        hiddenLabel.setBackground(controller.getFieldColor("background2"));
        hiddenBox.addActionListener(new hiddenActionListener());
        leftBottomPanel.add(hiddenBox);

        spacer = new JLabel(); // spacer
        spacer.setPreferredSize(new Dimension(186,10));
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

        hiddenOverride = true;

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

        if (controller.get(reference,"hidden").equals("false")){ // if true then it can't be part of the whole
            labels[c1].setText("% of Total Watch Time:");
            labels[c1].setPreferredSize(new Dimension(185,30));
            actionPanel.add(labels[c1++]);

            labels[c1].setText(controller.getPercentWhole(reference) +"%");
            labels[c1].setPreferredSize(new Dimension(120,30));
            actionPanel.add(labels[c1]);
        }

    }

    public void generateRightBottom(String sourceString){ // for inputField usage.
        if (rightBottomPanel != null) bottomPanel.remove(rightBottomPanel);
        repaint();
        revalidate();

        commitState = true;
        hiddenOverride = false;

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
            case "numberOfEpisodesWatched": loadButtonInput(sourceString, actionPanel); break; 
            case "numberOfEpisodesTotal": loadButtonInput(sourceString, actionPanel); break; 
            case "averageEpisodeLength": loadDropDown(sourceString, actionPanel); break; 
            case "seasonReleased": loadDropDown(sourceString, actionPanel); break; 
            case "yearReleased": loadDropDown(sourceString, actionPanel); break; 
            case "watchingStartDate": loadDatePicker(sourceString, actionPanel); break;
            case "watchingEndDate": loadDatePicker(sourceString, actionPanel); break;
            case "languageWatchedIn": loadDropDown(sourceString, actionPanel); break; 
            case "ageRating": loadDropDown(sourceString, actionPanel); break; 
            case "mainGenre": loadDropDown(sourceString, actionPanel); break; 
            case "subGenre": loadDropDown(sourceString, actionPanel); break;
            case "animationStudio": loadTextRetrieval(sourceString, actionPanel); break; //
            case "finish": loadTextRetrieval(sourceString, actionPanel); break; //
            case "color": loadMiniColorPicker(actionPanel); break; //
            case "remove": loadTextRetrieval(sourceString, actionPanel); break; //
            default: System.out.println("Invalid source: " +sourceString);
        } 
    }
    
    //length cutting methods
    public void loadTextRetrieval(String source, JPanel actionPanel){
        JLabel sectionLabel = new JLabel();
        sectionLabel.setHorizontalAlignment(JLabel.CENTER);
        sectionLabel.setVerticalAlignment(JLabel.BOTTOM);
        sectionLabel.setPreferredSize(new Dimension(300,34));
        sectionLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        sectionLabel.setForeground(controller.getFieldColor("text"));
        actionPanel.add(sectionLabel);

        JLabel spacer = new JLabel(); 
        spacer.setPreferredSize(new Dimension(300,15));
        actionPanel.add(spacer);
        
        textInput = new JTextField();
        textInput.setPreferredSize(new Dimension(235, 20));
        textInput.setBackground(controller.getAnimeColor(reference).brighter());
        textInput.setForeground(controller.getFieldColor("text"));
        textInput.setCaretColor(controller.getFieldColor("text"));
        textInput.setBorder(BorderFactory.createLineBorder(controller.getAnimeColor(reference).brighter()));
        textInput.setFont(new Font("Dialog", Font.BOLD, 12));
        textInput.setName(source);
        textInput.addActionListener(new commitButtonActionListener());
        actionPanel.add(textInput);
        
        switch(source){
            case "animeName": 
                sectionLabel.setText("Set Anime Name:");
                break;
            case "animationStudio": 
                sectionLabel.setText("Set Animation Studio:");
                break;
            case "finish":
                sectionLabel.setText("Finish Anime: (Type Yes)");
                break;
            case "remove":
                sectionLabel.setText("Remove Anime: (Type Yes)"); 
                break;
        }

        spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(300,140));
        actionPanel.add(spacer);

        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 25));
        cancelButton.addActionListener(new cancelButtonActionListener());
        setButtonDefaults(cancelButton);
        actionPanel.add(cancelButton);

        spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(3,0));
        actionPanel.add(spacer);

        commitButton = new JButton("Commit");
        commitButton.setName(source);
        commitButton.setPreferredSize(new Dimension(100, 25));
        commitButton.addActionListener(new commitButtonActionListener());
        setButtonDefaults(commitButton);
        actionPanel.add(commitButton);

        textInput.requestFocus();
    }

    public void loadButtonInput(String source, JPanel actionPanel){
        JLabel sectionLabel = new JLabel();
        sectionLabel.setHorizontalAlignment(JLabel.CENTER);
        sectionLabel.setVerticalAlignment(JLabel.BOTTOM);
        sectionLabel.setPreferredSize(new Dimension(300,34));
        sectionLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        sectionLabel.setForeground(controller.getFieldColor("text"));
        actionPanel.add(sectionLabel);

        JLabel spacer = new JLabel(); 
        spacer.setPreferredSize(new Dimension(300,10));
        actionPanel.add(spacer);

        nButton1 = new JButton("+1");
        nButton1.setName("1");
        nButton1.setPreferredSize(new Dimension(77,25));
        nButton1.addActionListener(new numberButtonActionListener());
        setButtonDefaults(nButton1);
        actionPanel.add(nButton1);
        
        spacer = new JLabel(); 
        spacer.setPreferredSize(new Dimension(2,0));
        actionPanel.add(spacer);

        nButton2 = new JButton("+10");
        nButton2.setName("10");
        nButton2.setPreferredSize(new Dimension(77,25));
        nButton2.addActionListener(new numberButtonActionListener());
        setButtonDefaults(nButton2);
        actionPanel.add(nButton2);

        spacer = new JLabel(); 
        spacer.setPreferredSize(new Dimension(2,0));
        actionPanel.add(spacer);

        nButton3 = new JButton("+100");
        nButton3.setName("100");
        nButton3.setPreferredSize(new Dimension(77,25));
        nButton3.addActionListener(new numberButtonActionListener());
        setButtonDefaults(nButton3);
        actionPanel.add(nButton3);

        spacer = new JLabel(); 
        spacer.setPreferredSize(new Dimension(300,2));
        actionPanel.add(spacer);

        nButton4 = new JButton("-1");
        nButton4.setName("-1");
        nButton4.setPreferredSize(new Dimension(77,25));
        nButton4.addActionListener(new numberButtonActionListener());
        setButtonDefaults(nButton4);
        actionPanel.add(nButton4);

        spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(2,0));
        actionPanel.add(spacer);

        nButton5 = new JButton("-10");
        nButton5.setName("-10");
        nButton5.setPreferredSize(new Dimension(77,25));
        nButton5.addActionListener(new numberButtonActionListener());
        setButtonDefaults(nButton5);
        actionPanel.add(nButton5);

        spacer = new JLabel(); 
        spacer.setPreferredSize(new Dimension(2,0));
        actionPanel.add(spacer);

        nButton6 = new JButton("-100");
        nButton6.setName("-100");
        nButton6.setPreferredSize(new Dimension(77,25));
        nButton6.addActionListener(new numberButtonActionListener());
        setButtonDefaults(nButton6);
        actionPanel.add(nButton6);

        spacer = new JLabel(); 
        spacer.setPreferredSize(new Dimension(300,44));
        actionPanel.add(spacer);

        numberLabel = new JLabel();
        numberLabel.setHorizontalAlignment(JLabel.CENTER);
        numberLabel.setVerticalAlignment(JLabel.BOTTOM);
        numberLabel.setPreferredSize(new Dimension(300,25));
        numberLabel.setFont(new Font("Dialog", Font.BOLD, 25));
        numberLabel.setForeground(controller.getFieldColor("text"));
        actionPanel.add(numberLabel);

        switch(source){
            case "numberOfEpisodesWatched": 
                sectionLabel.setText("Set Episodes Watched:");
                numberLabel.setText(controller.get(reference,"numberOfEpisodesWatched"));
                break; 
            case "numberOfEpisodesTotal": 
                sectionLabel.setText("Set Total Episodes:");
                numberLabel.setText(controller.get(reference,"numberOfEpisodesTotal"));
                break;
        }

        spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(300,44));
        actionPanel.add(spacer);

        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 25));
        cancelButton.addActionListener(new cancelButtonActionListener());
        setButtonDefaults(cancelButton);
        actionPanel.add(cancelButton);

        spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(3,0));
        actionPanel.add(spacer);

        commitButton = new JButton("Commit");
        commitButton.setName(source);
        commitButton.setPreferredSize(new Dimension(100, 25));
        commitButton.addActionListener(new commitButtonActionListener());
        setButtonDefaults(commitButton);
        actionPanel.add(commitButton);

    }

    public void loadDropDown(String source, JPanel actionPanel){
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

        ArrayList<String> stringList;
        
        switch(source){
            case "averageEpisodeLength":
                stringList = new ArrayList<String>();
                for(int i = 0; i < 100; i++){
                    stringList.add((i+1) +"");
                }
                sectionLabel.setText("Set Episode Length:");
                break; 
            case "seasonReleased": 
                stringList = controller.getListOfDescriptors("seasons");
                sectionLabel.setText("Set Season Released:");
                break; 
            case "yearReleased": 
                stringList = new ArrayList<String>();
                for(int i = Calendar.getInstance().get(Calendar.YEAR); i > 1900; i--){
                    stringList.add((i) +"");
                }
                sectionLabel.setText("Set Year Released:");
                break; 
            case "languageWatchedIn": 
                stringList = controller.getListOfDescriptors("languages");
                sectionLabel.setText("Set Language:");
                break; 
            case "ageRating": 
                stringList = controller.getListOfDescriptors("contentRatings");
                sectionLabel.setText("Set Content Rating:");
                break; 
            case "mainGenre": 
                stringList = controller.getListOfDescriptors("genres"); 
                sectionLabel.setText("Set Main Genre:");
                break;
            case "subGenre": 
                stringList = controller.getListOfDescriptors("genres"); 
                sectionLabel.setText("Set Secondary Genre:");
                break;
            default: 
                stringList = new ArrayList<String>();
        }

        toggleSpacer = new JLabel(); // appears to fill gap when datepicker his hidden when nav is opened.
        toggleSpacer.setPreferredSize(new Dimension(100,25));
        toggleSpacer.setVisible(false);
        actionPanel.add(toggleSpacer);

        dropBox = new JComboBox<String>(stringList.toArray(new String[0]));
        dropBox.setRenderer(new CustomComboBoxRenderer(controller));
        dropBox.setEditor(new CustomComboBoxEditor(controller));
        dropBox.setUI(new CustomComboBoxUI(controller));
        dropBox.setPreferredSize(new Dimension(235,25));
        dropBox.setFont(new Font("Dialog", Font.BOLD, 12));
        dropBox.setForeground(controller.getFieldColor("text"));
        dropBox.setBackground(controller.getFieldColor("background3"));
        dropBox.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder")));
        dropBox.setEditable(true);
        dropBox.setSelectedItem("Select One:");
        dropBox.setEditable(false);
        dropBox.setFocusable(false);
        dropBox.addActionListener(new toggleCommitActionListener()); // must be after editing of selected item or the listener throws an exception
        actionPanel.add(dropBox);

        spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(300,140));
        actionPanel.add(spacer);

        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 25));
        cancelButton.addActionListener(new cancelButtonActionListener());
        setButtonDefaults(cancelButton);
        actionPanel.add(cancelButton);

        spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(3,0));
        actionPanel.add(spacer);

        commitButton = new JButton("Commit");
        commitButton.setName(source);
        commitButton.setPreferredSize(new Dimension(100, 25));
        commitButton.addActionListener(new commitButtonActionListener());
        commitButton.setEnabled(false);
        setButtonDefaults(commitButton);
        actionPanel.add(commitButton);

        commitState = false;
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
        
        datePicker = new DatePicker();
        datePicker.setSettings(settings);
        datePicker.setDateToToday();
        datePicker.setOpaque(false);
        
        JButton toggleButton = datePicker.getComponentToggleCalendarButton(); // pull out these to edit them directly, some of these I think I need to do this
                                                                              // or something similar to get the effect I want.
        toggleButton.setText("\u2B9F");
        setButtonDefaults(toggleButton);
        toggleButton.setFont(new Font("Dialog", Font.BOLD, 10));

        toggleSpacer = new JLabel(); // appears to fill gap when datepicker his hidden when nav is opened.
        toggleSpacer.setPreferredSize(new Dimension(100,25));
        toggleSpacer.setVisible(false);
        actionPanel.add(toggleSpacer);

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
        
        spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(300,140));
        actionPanel.add(spacer);

        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 25));
        cancelButton.addActionListener(new cancelButtonActionListener());
        setButtonDefaults(cancelButton);
        actionPanel.add(cancelButton);

        spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(3,0));
        actionPanel.add(spacer);

        commitButton = new JButton("Commit");
        commitButton.setName(source);
        commitButton.setPreferredSize(new Dimension(100, 25));
        commitButton.addActionListener(new commitButtonActionListener());
        setButtonDefaults(commitButton);
        actionPanel.add(commitButton);

    }

    public void loadMiniColorPicker(JPanel actionPanel){
        colorPanel = new MiniColorPickerPanel(reference,controller, this);
        actionPanel.add(colorPanel);

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
        spacer.setPreferredSize(new Dimension(3,0));
        buttonPanel.add(spacer);

        rightButton = new JButton("Next");
        rightButton.setName("right");
        rightButton.setPreferredSize(new Dimension(156, 25));
        rightButton.addActionListener(new switchButtonActionListener());
        setButtonDefaults(rightButton);
        buttonPanel.add(rightButton);
    }
    
    public void refreshPage(){
        mainGUI.generateAnimePage(reference);
    }

    public void toggleEnables(boolean bool){ // I would use looping if it was feasible.
        if (cancelButton != null) cancelButton.setEnabled(bool);
        if (commitButton != null) commitButton.setEnabled(bool);
        if (leftButton != null) leftButton.setEnabled(bool);
        if (rightButton != null) rightButton.setEnabled(bool);
        if (datePicker != null) datePicker.setVisible(bool);
        if (dropBox != null) dropBox.setVisible(bool);
        if (textInput != null) textInput.setEnabled(bool);
        if (nButton1 != null) nButton1.setEnabled(bool);
        if (nButton2 != null) nButton2.setEnabled(bool); 
        if (nButton3 != null) nButton3.setEnabled(bool);
        if (nButton4 != null) nButton4.setEnabled(bool); 
        if (nButton5 != null) nButton5.setEnabled(bool); 
        if (nButton6 != null) nButton6.setEnabled(bool);
        if (colorPanel != null) colorPanel.toggleEnables(bool);
        if (toggleSpacer != null) toggleSpacer.setVisible(!bool);
    }

    //ACTION LISTENERS
    private class makeEditActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            mainGUI.generateNavigationPageSmall();
            JButton source = (JButton)(V.getSource());
            String sourceString = source.getName();
            generateRightBottom(sourceString);
        }
    }

    private class changeImageActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            mainGUI.generateNavigationPageSmall();
            toggleEnables(true);
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

    private class cancelButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            generateRightBottom();
        }
    }

    private class commitButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            JComponent source = (JComponent)V.getSource();
            String sourceString = source.getName();

            switch(sourceString){
                case "watchingStartDate": // step down
                case "watchingEndDate": 
                    controller.set(reference,sourceString,datePicker.getText());
                    generateRightBottom();
                    generateLeftTop();
                    break;
                case "averageEpisodeLength": // step down
                case "seasonReleased": // step down
                case "yearReleased": // step down
                case "languageWatchedIn": // step down
                case "ageRating": // step down
                case "mainGenre": // step down
                case "subGenre": 
                    controller.set(reference,sourceString,(String)dropBox.getSelectedItem());
                    generateRightBottom();
                    generateLeftTop();
                    break;
                case "numberOfEpisodesWatched": // step down
                case "numberOfEpisodesTotal": 
                    int num = Integer.parseInt(numberLabel.getText());
                    int epW = Integer.parseInt(controller.get(reference,"numberOfEpisodesWatched"));
                    int epT = Integer.parseInt(controller.get(reference,"numberOfEpisodesTotal"));

                    if (sourceString.equals("numberOfEpisodesWatched")&&(num > epT)){
                        controller.set(reference,sourceString,num +"");
                        controller.set(reference,"numberOfEpisodesTotal",num +"");
                    }
                    else if (sourceString.equals("numberOfEpisodesTotal")&&(num < epW)){
                        controller.set(reference,sourceString,num +"");
                        controller.set(reference,"numberOfEpisodesWatched",num +"");
                    }
                    else {
                        controller.set(reference,sourceString,num +"");
                    }
                    generateRightBottom();
                    generateLeftTop();
                    break;   
                case "animeName":
                    controller.set(reference,sourceString,textInput.getText()); 
                    mainGUI.setNavigationText(controller.get(reference,sourceString));
                    generateRightBottom();
                    generateLeftTop();
                    break;
                case "animationStudio":
                    controller.set(reference,sourceString,textInput.getText());
                    generateRightBottom();
                    generateLeftTop();
                    break;
                case "finish": 
                    if (textInput.getText().trim().toUpperCase().equals("YES")){
                        controller.set(reference,"numberOfEpisodesWatched",controller.get(reference,"numberOfEpisodesTotal"));
                        datePicker = new DatePicker(); // somewhat lazy way of getting the date in the format from the datepicker
                        datePicker.setDateToToday();
                        controller.set(reference,"watchingEndDate",datePicker.getText());
                        generateRightBottom();
                        generateLeftTop();
                    }
                    break;
                case "remove": 
                    if (textInput.getText().trim().toUpperCase().equals("YES")){
                        controller.deleteAnime(reference);
                        mainGUI.generateListPage();
                    }       
            }
        }
    }

    private class numberButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            mainGUI.generateNavigationPageSmall();
            JButton source = (JButton)(V.getSource());

            int num = Integer.parseInt(numberLabel.getText());
            int sourceNum = Integer.parseInt(source.getName());

            if ((num + sourceNum) <0) numberLabel.setText("0");
            else numberLabel.setText((num + sourceNum) +"");


        }
    }

    private class toggleCommitActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            commitButton.setEnabled(true);
            commitState = true;
        }
    }

    private class hiddenActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            mainGUI.generateNavigationPageSmall();
            toggleEnables(true);
            if (controller.get(reference,"hidden").equals("true")) controller.set(reference,"hidden","false");
            else controller.set(reference,"hidden","true");    
            generateLeftBottom();
            if (hiddenOverride) generateRightBottom();
        }
    }
}