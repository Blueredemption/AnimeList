//*****************************************//
// Created by Cooper Eisnor                //
//*****************************************//

import java.awt.*; // change these later
import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;

import java.awt.event.*;
import java.util.ArrayList;

public class ListPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    Dimension fullDim = new Dimension(978,700);
    Dimension leftDim = new Dimension(184,700);
    Dimension rightDim = new Dimension(794,700);

    Controller controller;
    ArrayList<String> references;
    JFrame superior; // access to the object that called it is only here to tell it when to self destruct (move to the anime window)

    JPanel leftPanel;
    JPanel rightPanel; 
    JScrollPane scrollPane;
    JScrollBar scrollBar;
    JButton[] listButtons;


    public ListPanel(JFrame superior, Controller controller){ // constructor
        this.controller = controller;
        this.superior = superior;

        references = controller.getReferenceList();
        
        setLayout(new BorderLayout());
        setOpaque(false);

        generateLeft();
        generateRight();
    }
    
    public void generateLeft(){
        if (leftPanel != null) remove(leftPanel);
        repaint();
        revalidate();
        leftPanel = new JPanel();
        leftPanel.setPreferredSize(leftDim);
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setBackground(controller.getFieldColor("background1"));
        add(leftPanel,BorderLayout.WEST);

        JLabel[] bufferLabel = new JLabel[20];
        for (int i = 0; i < 20; i++) {
            bufferLabel[i] = new JLabel();
            bufferLabel[i].setPreferredSize(new Dimension(12,30));
        }
        JLabel[] actualLabel = new JLabel[10];
        for (int i = 0; i < 10; i++) {
            actualLabel[i] = new JLabel();
            actualLabel[i].setPreferredSize(new Dimension(70,30));
            actualLabel[i].setForeground(controller.getFieldColor("text"));
            actualLabel[i].setFont(new Font("Dialog", Font.BOLD, 15));
            actualLabel[i].setVerticalAlignment(JLabel.CENTER);
            actualLabel[i].setHorizontalAlignment(JLabel.LEFT);
        }
        int c1 = 0;
        int c2 = 0;

        JLabel searchLabel = new JLabel("Quick Search:");
        searchLabel.setPreferredSize(new Dimension(184,30));
        searchLabel.setForeground(controller.getFieldColor("text"));
        searchLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        searchLabel.setVerticalAlignment(JLabel.CENTER);
        searchLabel.setHorizontalAlignment(JLabel.CENTER);
        leftPanel.add(searchLabel);

        bufferLabel[c1].setPreferredSize(new Dimension(12,8));
        leftPanel.add(bufferLabel[c1++]);

        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(160, 20));
        searchField.setBackground(controller.getFieldColor("background2"));
        searchField.setForeground(controller.getFieldColor("text"));
        searchField.setCaretColor(controller.getFieldColor("text"));
        searchField.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("background2")));
        searchField.setFont(new Font("Dialog", Font.BOLD, 12));
        searchField.addActionListener(new quickSearchListener());
        leftPanel.add(searchField);

        bufferLabel[c1].setPreferredSize(new Dimension(20,150));
        leftPanel.add(bufferLabel[c1++]);

        JLabel sortLabel = new JLabel("Sort List By:");
        sortLabel.setPreferredSize(new Dimension(184,30));
        sortLabel.setForeground(controller.getFieldColor("text"));
        sortLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        sortLabel.setVerticalAlignment(JLabel.CENTER);
        sortLabel.setHorizontalAlignment(JLabel.CENTER);
        leftPanel.add(sortLabel);

        leftPanel.add(bufferLabel[c1++]);

        actualLabel[c2].setText("Order:");
        leftPanel.add(actualLabel[c2++]);

        leftPanel.add(bufferLabel[c1++]);

        JButton upButton = new JButton();
        upButton.setPreferredSize(new Dimension(37, 25));
        upButton.addActionListener(new orderListener());
        upButton.setText(String.valueOf("\u25B2"));
        upButton.setName("Up");
        upButton.setFont(new Font("Dialog", Font.BOLD, 10));
        setButtonDefaults(upButton);
        if (controller.getOrder()==0) upButton.setEnabled(false);
        leftPanel.add(upButton);

        bufferLabel[c1].setPreferredSize(new Dimension(6,30));
        leftPanel.add(bufferLabel[c1++]);

        JButton downButton = new JButton();
        downButton.setPreferredSize(new Dimension(37, 25));
        downButton.addActionListener(new orderListener());
        downButton.setText(String.valueOf("\u25BC"));
        downButton.setName("Down");
        downButton.setFont(new Font("Dialog", Font.BOLD, 10));
        setButtonDefaults(downButton);
        if (controller.getOrder()==1) downButton.setEnabled(false);
        leftPanel.add(downButton);

        leftPanel.add(bufferLabel[c1++]);

        actualLabel[c2].setText("Added:");
        leftPanel.add(actualLabel[c2++]);

        leftPanel.add(bufferLabel[c1++]);
        
        JButton addedButton = new JButton();
        addedButton.setPreferredSize(new Dimension(80, 25));
        addedButton.addActionListener(new sortListener());
        addedButton.setText("Select");
        addedButton.setName("Added");
        setButtonDefaults(addedButton);
        if (controller.getSort()==0) addedButton.setEnabled(false);
        leftPanel.add(addedButton);

        leftPanel.add(bufferLabel[c1++]);

        actualLabel[c2].setText("Name:");
        leftPanel.add(actualLabel[c2++]);

        leftPanel.add(bufferLabel[c1++]);
        
        JButton nameButton = new JButton();
        nameButton.setPreferredSize(new Dimension(80, 25));
        nameButton.addActionListener(new sortListener());
        nameButton.setText("Select");
        nameButton.setName("Name");
        setButtonDefaults(nameButton);
        if (controller.getSort()==1) nameButton.setEnabled(false);
        leftPanel.add(nameButton);

        leftPanel.add(bufferLabel[c1++]);

        actualLabel[c2].setText("Episodes:");
        leftPanel.add(actualLabel[c2++]);

        leftPanel.add(bufferLabel[c1++]);
        
        JButton episodesButton = new JButton();
        episodesButton.setPreferredSize(new Dimension(80, 25));
        episodesButton.addActionListener(new sortListener());
        episodesButton.setText("Select");
        episodesButton.setName("Episodes");
        setButtonDefaults(episodesButton);
        if (controller.getSort()==2) episodesButton.setEnabled(false);
        leftPanel.add(episodesButton);

        leftPanel.add(bufferLabel[c1++]);

        actualLabel[c2].setText("Season:");
        leftPanel.add(actualLabel[c2++]);

        leftPanel.add(bufferLabel[c1++]);
        
        JButton seasonButton = new JButton();
        seasonButton.setPreferredSize(new Dimension(80, 25));
        seasonButton.addActionListener(new sortListener());
        seasonButton.setText("Select");
        seasonButton.setName("Season");
        setButtonDefaults(seasonButton);
        if (controller.getSort()==3) seasonButton.setEnabled(false);
        leftPanel.add(seasonButton);

        leftPanel.add(bufferLabel[c1++]);

        actualLabel[c2].setText("Year:");
        leftPanel.add(actualLabel[c2++]);

        leftPanel.add(bufferLabel[c1++]);
        
        JButton yearButton = new JButton();
        yearButton.setPreferredSize(new Dimension(80, 25));
        yearButton.addActionListener(new sortListener());
        yearButton.setText("Select");
        yearButton.setName("Year");
        setButtonDefaults(yearButton);
        if (controller.getSort()==4) yearButton.setEnabled(false);
        leftPanel.add(yearButton);

        leftPanel.add(bufferLabel[c1++]);

        actualLabel[c2].setText("Progress:");
        leftPanel.add(actualLabel[c2++]);

        leftPanel.add(bufferLabel[c1++]);
        
        JButton progressButton = new JButton();
        progressButton.setPreferredSize(new Dimension(80, 25));
        progressButton.addActionListener(new sortListener());
        progressButton.setText("Select");
        progressButton.setName("Progress");
        setButtonDefaults(progressButton);
        if (controller.getSort()==5) progressButton.setEnabled(false);
        leftPanel.add(progressButton);

        leftPanel.add(bufferLabel[c1++]);

        actualLabel[c2].setText("Color:");
        leftPanel.add(actualLabel[c2++]);

        leftPanel.add(bufferLabel[c1++]);
        
        JButton colorButton = new JButton();
        colorButton.setPreferredSize(new Dimension(80, 25));
        colorButton.addActionListener(new sortListener());
        colorButton.setText("Select");
        colorButton.setName("Color");
        setButtonDefaults(colorButton);
        if (controller.getSort()==6) colorButton.setEnabled(false);
        leftPanel.add(colorButton);
    }

    public void generateRight(){
        if (rightPanel != null) remove(rightPanel);
        repaint();
        revalidate();
        rightPanel = new JPanel();
        rightPanel.setPreferredSize(rightDim);
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setOpaque(false);
        add(rightPanel,BorderLayout.EAST);

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(794,30));
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        topPanel.setBackground(controller.getFieldColor("background1"));
        rightPanel.add(topPanel,BorderLayout.NORTH);

        JLabel[] descLabels = new JLabel[8];
        for (int i = 0; i < 8; i++) {
            descLabels[i] = new JLabel();
            descLabels[i].setPreferredSize(new Dimension(80,30));
            descLabels[i].setForeground(controller.getFieldColor("text"));
            descLabels[i].setFont(new Font("Dialog", Font.BOLD, 15));
            descLabels[i].setVerticalAlignment(JLabel.CENTER);
            descLabels[i].setHorizontalAlignment(JLabel.LEFT);
        }
        int c1 = 0;

        descLabels[c1].setPreferredSize(new Dimension(2,30));
        topPanel.add(descLabels[c1++]);

        descLabels[c1].setText("#:");
        descLabels[c1].setPreferredSize(new Dimension(36,30));
        topPanel.add(descLabels[c1++]);

        descLabels[c1].setText("Name:");
        descLabels[c1].setPreferredSize(new Dimension(350,30));
        topPanel.add(descLabels[c1++]);

        descLabels[c1].setText("Episodes:");
        descLabels[c1].setPreferredSize(new Dimension(90,30));
        topPanel.add(descLabels[c1++]);

        descLabels[c1].setText("Season:");
        topPanel.add(descLabels[c1++]);

        descLabels[c1].setText("Year:");
        descLabels[c1].setPreferredSize(new Dimension(60,30));
        topPanel.add(descLabels[c1++]);

        descLabels[c1].setText("Progress:");
        descLabels[c1].setPreferredSize(new Dimension(93,30));
        topPanel.add(descLabels[c1++]);

        descLabels[c1].setText("Details:");
        descLabels[c1].setPreferredSize(new Dimension(80,30));
        topPanel.add(descLabels[c1++]);

        int n = references.size();

        JPanel scrollPanel = new JPanel();
        scrollPanel.setPreferredSize(new Dimension(760,n*31));
        scrollPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,1));
        scrollPanel.setBackground(controller.getFieldColor("background1").brighter());
        
        scrollPane = new JScrollPane(scrollPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // global for disable purposes
        scrollPane.setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("background1").brighter()));
        scrollPane.setPreferredSize(new Dimension (794,674));
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        scrollBar = scrollPane.getVerticalScrollBar(); // global for disable purposes
        scrollPane.remove(scrollBar); 
        scrollBar.setPreferredSize(new Dimension(10,674));
        scrollBar.setUI(new CustomScrollBarUI(controller));
        rightPanel.add(scrollBar,BorderLayout.EAST);
        rightPanel.add(scrollPane,BorderLayout.CENTER);

        JPanel[] listPanel = new JPanel[n];
        listButtons = new JButton[n]; // global for disable purposes
        JLabel[][] listLabels = new JLabel[n][6];
        JProgressBar[] progressBars = new JProgressBar[n];

        for (int i = 0; i < n; i++){
            listPanel[i] = new JPanel();
            listPanel[i].setPreferredSize(new Dimension (782,30));
            listPanel[i].setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
            listPanel[i].setBackground(controller.getAnimeColor(references.get(i)));

            listLabels[i][1] = new JLabel(i+1 +":");
            listLabels[i][1].setPreferredSize(new Dimension(38,30));
            listLabels[i][1].setForeground(controller.getFieldColor("text"));
            listLabels[i][1].setFont(new Font("Dialog", Font.BOLD, 13));
            listLabels[i][1].setVerticalAlignment(JLabel.CENTER);
            listLabels[i][1].setHorizontalAlignment(JLabel.LEFT);
            listPanel[i].add(listLabels[i][1]);

            listLabels[i][2] = new JLabel(controller.get(references.get(i), "animeName"));
            listLabels[i][2].setPreferredSize(new Dimension(350,30));
            listLabels[i][2].setForeground(controller.getFieldColor("text"));
            listLabels[i][2].setFont(new Font("Dialog", Font.BOLD, 13));
            listLabels[i][2].setVerticalAlignment(JLabel.CENTER);
            listLabels[i][2].setHorizontalAlignment(JLabel.LEFT);
            listPanel[i].add(listLabels[i][2]);

            listLabels[i][3] = new JLabel(controller.get(references.get(i), "numberOfEpisodesTotal"));
            listLabels[i][3].setPreferredSize(new Dimension(90,30));
            listLabels[i][3].setForeground(controller.getFieldColor("text"));
            listLabels[i][3].setFont(new Font("Dialog", Font.BOLD, 13));
            listLabels[i][3].setVerticalAlignment(JLabel.CENTER);
            listLabels[i][3].setHorizontalAlignment(JLabel.LEFT);
            listPanel[i].add(listLabels[i][3]);

            listLabels[i][4] = new JLabel(controller.get(references.get(i), "seasonReleased"));
            listLabels[i][4].setPreferredSize(new Dimension(80,30));
            listLabels[i][4].setForeground(controller.getFieldColor("text"));
            listLabels[i][4].setFont(new Font("Dialog", Font.BOLD, 13));
            listLabels[i][4].setVerticalAlignment(JLabel.CENTER);
            listLabels[i][4].setHorizontalAlignment(JLabel.LEFT);
            listPanel[i].add(listLabels[i][4]);

            listLabels[i][5] = new JLabel(controller.get(references.get(i), "yearReleased"));
            listLabels[i][5].setPreferredSize(new Dimension(60,30));
            listLabels[i][5].setForeground(controller.getFieldColor("text"));
            listLabels[i][5].setFont(new Font("Dialog", Font.BOLD, 13));
            listLabels[i][5].setVerticalAlignment(JLabel.CENTER);
            listLabels[i][5].setHorizontalAlignment(JLabel.LEFT);
            listPanel[i].add(listLabels[i][5]);
            
            progressBars[i] = new JProgressBar();
            progressBars[i].setPreferredSize(new Dimension (88,25));
            progressBars[i].setValue((int)(Double.parseDouble(controller.get(references.get(i), "progress"))));
            progressBars[i].setBackground(controller.getFieldColor("list").brighter()); 
            progressBars[i].setForeground(controller.getFieldColor("list"));
            progressBars[i].setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder").brighter()));
            progressBars[i].setStringPainted(true);
            progressBars[i].setUI(new BasicProgressBarUI() {
                protected Color getSelectionBackground() { return controller.getFieldColor("text");}
                protected Color getSelectionForeground() { return controller.getFieldColor("text");} });
            listPanel[i].add(progressBars[i]);

            listLabels[i][0] = new JLabel();
            listLabels[i][0].setPreferredSize(new Dimension(3,30));
            listPanel[i].add(listLabels[i][0]);

            listButtons[i] = new JButton();
            listButtons[i].setPreferredSize(new Dimension(71, 25));
            listButtons[i].addActionListener(new changeToAnimeListener());
            listButtons[i].setText("View");
            listButtons[i].setName(references.get(i)); 
            setButtonDefaults(listButtons[i]);
            listPanel[i].add(listButtons[i]);

            scrollPanel.add(listPanel[i]);
        }
    }

    public void setButtonDefaults(JButton button) {
        button.setBackground(controller.getFieldColor("buttons"));
        button.setForeground(controller.getFieldColor("text"));
        button.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder")));
        button.setFocusPainted(false);
    }

    public void disableScrollBar(){
        scrollPane.getVerticalScrollBar().setEnabled(false);
        scrollPane.setWheelScrollingEnabled(false);
        rightPanel.remove(scrollBar);
    }

    public void enableScrollBar(){
        scrollPane.getVerticalScrollBar().setEnabled(true);
        scrollPane.setWheelScrollingEnabled(true);
        rightPanel.add(scrollBar,BorderLayout.EAST);
    }

    public void disableButtons(){
        for (JButton jButton : listButtons) {
            jButton.setEnabled(false);
        }
    }

    public void enableButtons(){
        for (JButton jButton : listButtons) {
            jButton.setEnabled(true);
        }
    }

    // Action Listeners
    private class orderListener implements ActionListener { 
        @Override
        public void actionPerformed(ActionEvent V){
            JButton source = (JButton)(V.getSource());
            switch(source.getName()){
                case "Up": controller.setOrder(0); break;
                case "Down": controller.setOrder(1); break;
            }
            references = controller.getReferenceList();
            generateLeft();
            generateRight(); 
        }
    }
    private class sortListener implements ActionListener { 
        @Override
        public void actionPerformed(ActionEvent V){
            JButton source = (JButton)(V.getSource());
            switch(source.getName()){
                case "Added": controller.setSort(0); break;
                case "Name": controller.setSort(1); break;
                case "Episodes": controller.setSort(2); break;
                case "Season": controller.setSort(3); break;
                case "Year": controller.setSort(4); break;
                case "Progress": controller.setSort(5); break;
                case "Color": controller.setSort(6); break;
            } 
            references = controller.getReferenceList();
            generateLeft();
            generateRight();
        }
    }

    private class quickSearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            references = controller.getSearchedReferenceList(((JTextField)V.getSource()).getText());
            generateLeft();
            generateRight();
        }
    }

    private class changeToAnimeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            ((MainGUI)superior).generateAnimePage(((JButton)V.getSource()).getName());
        }
    }
}