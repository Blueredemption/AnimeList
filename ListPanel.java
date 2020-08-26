import java.awt.*; // change these later
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.basic.BasicProgressBarUI;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;

public class ListPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    Dimension fullDim = new Dimension(978,700);
    Dimension leftDim = new Dimension(184,700);
    Dimension rightDim = new Dimension(794,700);

    Controller controller;
    ArrayList<String> references;
    JFrame superior; // access to the object that called it is only here to tell it when to self destruct (move to the anime window)

    JTextField searchField;
    JPanel leftPanel;
    JPanel rightPanel; 
    JScrollPane scrollPane;
    JScrollBar scrollBar;
    JButton[] listButtons;

    public ListPanel(JFrame superior, Controller controller){ // constructor
        this.controller = controller;
        this.superior = superior;

        references = controller.getFilteredReferenceList();
        
        setLayout(new BorderLayout());
        setOpaque(false);

        generateLeft();
        generateRight();
        searchField.requestFocusInWindow();
        searchField.requestFocus();
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

        JLabel[] bufferLabel = new JLabel[18];
        for (int i = 0; i < 18; i++) {
            bufferLabel[i] = new JLabel();
            bufferLabel[i].setPreferredSize(new Dimension(5,30));
        }
        JLabel[] actualLabel = new JLabel[11];
        for (int i = 0; i < 11; i++) {
            actualLabel[i] = new JLabel();
            actualLabel[i].setPreferredSize(new Dimension(81,28));
            actualLabel[i].setForeground(controller.getFieldColor("text"));
            actualLabel[i].setFont(new Font("Dialog", Font.BOLD, 15));
            actualLabel[i].setVerticalAlignment(JLabel.CENTER);
            actualLabel[i].setHorizontalAlignment(JLabel.LEFT);
        }
        int c1 = 0;
        int c2 = 0;

        // search related components
        JLabel searchLabel = new JLabel("Quick Search:");
        searchLabel.setPreferredSize(new Dimension(184,30));
        searchLabel.setForeground(controller.getFieldColor("text"));
        searchLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        searchLabel.setVerticalAlignment(JLabel.CENTER);
        searchLabel.setHorizontalAlignment(JLabel.CENTER);
        leftPanel.add(searchLabel);

        bufferLabel[c1].setPreferredSize(new Dimension(5,8));
        leftPanel.add(bufferLabel[c1++]);

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(172, 20));
        searchField.setBackground(controller.getFieldColor("background2"));
        searchField.setForeground(controller.getFieldColor("text"));
        searchField.setCaretColor(controller.getFieldColor("text"));
        searchField.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("background2")));
        searchField.setFont(new Font("Dialog", Font.BOLD, 12));
        searchField.addActionListener(new quickSearchListener());
        leftPanel.add(searchField);

        bufferLabel[c1].setPreferredSize(new Dimension(20,85));
        leftPanel.add(bufferLabel[c1++]);

        // sort related components
        JLabel sortLabel = new JLabel("Sort List By:");
        sortLabel.setPreferredSize(new Dimension(184,30));
        sortLabel.setForeground(controller.getFieldColor("text"));
        sortLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        sortLabel.setVerticalAlignment(JLabel.CENTER);
        sortLabel.setHorizontalAlignment(JLabel.CENTER);
        leftPanel.add(sortLabel);

        bufferLabel[c1].setPreferredSize(new Dimension(5,28));
        leftPanel.add(bufferLabel[c1++]);

        actualLabel[c2].setText("Order:");
        leftPanel.add(actualLabel[c2++]);

        JButton upButton = new JButton();
        upButton.setPreferredSize(new Dimension(45, 24));
        upButton.addActionListener(new orderListener());
        upButton.setText(String.valueOf("\u25B2"));
        upButton.setName("Up");
        upButton.setFont(new Font("Dialog", Font.BOLD, 10));
        setButtonDefaults(upButton);
        if (controller.getOrder()==0) upButton.setEnabled(false);
        leftPanel.add(upButton);

        bufferLabel[c1].setPreferredSize(new Dimension(4,30));
        leftPanel.add(bufferLabel[c1++]);

        JButton downButton = new JButton();
        downButton.setPreferredSize(new Dimension(45, 24));
        downButton.addActionListener(new orderListener());
        downButton.setText(String.valueOf("\u25BC"));
        downButton.setName("Down");
        downButton.setFont(new Font("Dialog", Font.BOLD, 10));
        setButtonDefaults(downButton);
        if (controller.getOrder()==1) downButton.setEnabled(false);
        leftPanel.add(downButton);

        bufferLabel[c1].setPreferredSize(new Dimension(5,28));
        leftPanel.add(bufferLabel[c1++]);

        actualLabel[c2].setText("Attribute:");
        leftPanel.add(actualLabel[c2++]);

        String[] sortTypes = {"Started","Name","Episodes","Season","Year","Progress","Color"};
        JComboBox<String> sortBox = new JComboBox<String>(sortTypes); 
        sortBox.setRenderer(new CustomComboBoxRenderer(controller));
        sortBox.setEditor(new CustomComboBoxEditor(controller));
        sortBox.setUI(new CustomComboBoxUI(controller));
        sortBox.setPreferredSize(new Dimension(94,25));
        sortBox.setFont(new Font("Dialog", Font.BOLD, 12));
        sortBox.setForeground(controller.getFieldColor("text"));
        sortBox.setBackground(controller.getFieldColor("background3"));
        sortBox.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder")));
        sortBox.setEditable(true);
        sortBox.setSelectedIndex(controller.getSort());
        sortBox.setEditable(false);
        sortBox.setFocusable(false);
        sortBox.addActionListener(new sortBoxActionListener()); 
        leftPanel.add(sortBox);
        
        bufferLabel[c1].setPreferredSize(new Dimension(20,20));
        leftPanel.add(bufferLabel[c1++]);

        // filter related components
        actualLabel[c2] = new JLabel("Filter List By:");
        actualLabel[c2].setPreferredSize(new Dimension(184,30));
        actualLabel[c2].setForeground(controller.getFieldColor("text"));
        actualLabel[c2].setFont(new Font("Dialog", Font.BOLD, 15));
        actualLabel[c2].setVerticalAlignment(JLabel.CENTER);
        actualLabel[c2].setHorizontalAlignment(JLabel.CENTER);
        leftPanel.add(actualLabel[c2++]);

        ArrayList<String> stringList = new ArrayList<String>(); // for the list years
        for(int i = Calendar.getInstance().get(Calendar.YEAR); i > 1900; i--){
            stringList.add((i) +"");
        }

        Object[] lists = {controller.getListOfDescriptors("seasons"), // these are the items in the combobox dropdowns
                          stringList,
                          stringList,
                          controller.getListOfDescriptors("languages"),
                          controller.getListOfDescriptors("contentRatings"),
                          controller.getListOfDescriptors("genres"),
                          controller.getListOfStudios()};
        
        @SuppressWarnings("unchecked") // will always be a string array
        ArrayList<String> checking = ((ArrayList<String>)lists[6]);            
        if (checking.size() == 0) checking.add("");

        @SuppressWarnings("unchecked") // I can't put <String> after the JComboBox when initializing the array :/
        JComboBox<String>[] filterBox = new JComboBox[7];
        String[] checkers = {"Season?","Year?","Started?","Language?","Rating?","Genre?","Studio?"};
        for (int i = 0; i < 7; i++){
            @SuppressWarnings("unchecked") // this will always be an ArrayList<String>
            String[] listString = ((ArrayList<String>)lists[i]).toArray(new String[0]);
            filterBox[i] = new JComboBox<String>(listString);
            filterBox[i].setRenderer(new CustomComboBoxRenderer(controller));
            filterBox[i].setEditor(new CustomComboBoxEditor(controller));
            filterBox[i].setUI(new CustomComboBoxUI(controller));
            filterBox[i].setPreferredSize(new Dimension(94,25));
            filterBox[i].setFont(new Font("Dialog", Font.BOLD, 12));
            filterBox[i].setForeground(controller.getFieldColor("text"));
            filterBox[i].setBackground(controller.getFieldColor("background3"));
            filterBox[i].setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder")));
            filterBox[i].setEditable(true);
            String string = controller.getPreApplyFilterField(i);
            if (string.equals(checkers[i])) filterBox[i].setSelectedItem(checkers[i]);
            else{
                @SuppressWarnings("unchecked") // this will always be an ArrayList<String>
                int index = ((ArrayList<String>)lists[i]).indexOf(string);
                filterBox[i].setSelectedIndex(index);
            }
            filterBox[i].setEditable(false);
            filterBox[i].setFocusable(false);
            filterBox[i].addActionListener(new filterBoxActionListener()); 
        }

        int iter = 0;

        leftPanel.add(bufferLabel[c1++]);
        actualLabel[c2].setText("Season:");
        leftPanel.add(actualLabel[c2++]);
        filterBox[iter].setName("Season");
        leftPanel.add(filterBox[iter++]);

        leftPanel.add(bufferLabel[c1++]);
        actualLabel[c2].setText("Year:");
        leftPanel.add(actualLabel[c2++]);
        filterBox[iter].setName("Year");
        leftPanel.add(filterBox[iter++]);

        leftPanel.add(bufferLabel[c1++]);
        actualLabel[c2].setText("Started:");
        leftPanel.add(actualLabel[c2++]);
        filterBox[iter].setName("Started");
        leftPanel.add(filterBox[iter++]);
        
        leftPanel.add(bufferLabel[c1++]);
        actualLabel[c2].setText("Language:");
        leftPanel.add(actualLabel[c2++]);
        filterBox[iter].setName("Language");
        leftPanel.add(filterBox[iter++]);

        leftPanel.add(bufferLabel[c1++]);
        actualLabel[c2].setText("Rating:");
        leftPanel.add(actualLabel[c2++]);
        filterBox[iter].setName("Rating");
        leftPanel.add(filterBox[iter++]);

        leftPanel.add(bufferLabel[c1++]);
        actualLabel[c2].setText("Genre:");
        leftPanel.add(actualLabel[c2++]);
        filterBox[iter].setName("Genre");
        leftPanel.add(filterBox[iter++]);

        leftPanel.add(bufferLabel[c1++]);
        actualLabel[c2].setText("Studio:");
        leftPanel.add(actualLabel[c2++]);
        filterBox[iter].setName("Studio");
        leftPanel.add(filterBox[iter++]);

        bufferLabel[c1].setPreferredSize(new Dimension(4,28));
        leftPanel.add(bufferLabel[c1++]);

        bufferLabel[c1].setPreferredSize(new Dimension(4,28));
        leftPanel.add(bufferLabel[c1++]);

        JButton resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(86,24));
        resetButton.addActionListener(new resetButtonActionListener());
        setButtonDefaults(resetButton);
        leftPanel.add(resetButton);
        
        bufferLabel[c1].setPreferredSize(new Dimension(4,28));
        leftPanel.add(bufferLabel[c1++]);

        JButton applyButton = new JButton("Apply");
        applyButton.setPreferredSize(new Dimension(86,24));
        applyButton.addActionListener(new applyButtonActionListener());
        setButtonDefaults(applyButton);
        applyButton.setEnabled(controller.checkForFilterChange());
        leftPanel.add(applyButton);

        bufferLabel[c1].setPreferredSize(new Dimension(184,161));
        leftPanel.add(bufferLabel[c1++]);

        bufferLabel[c1].setPreferredSize(new Dimension(25,39));
        leftPanel.add(bufferLabel[c1++]);
        
        actualLabel[c2] = new JLabel("Show Hidden?");
        actualLabel[c2].setPreferredSize(new Dimension(110,30));
        actualLabel[c2].setFont(new Font("Dialog", Font.BOLD, 15));
        actualLabel[c2].setForeground(controller.getFieldColor("text"));
        actualLabel[c2].setVerticalAlignment(JLabel.CENTER);
        leftPanel.add(actualLabel[c2++]); 

        JButton hiddenBox = new JButton();
        if (controller.getHidden()) hiddenBox.setText("\u2714");
        hiddenBox.setPreferredSize(new Dimension(15,15));
        setButtonDefaults(hiddenBox);
        hiddenBox.addActionListener(new hiddenButtonActionListener());
        leftPanel.add(hiddenBox);
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
        scrollPanel.setPreferredSize(new Dimension(760,n*31 -1));
        scrollPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
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
        scrollBar.setBorder(new BevelBorder(BevelBorder.RAISED, controller.getFieldColor("list"), 
                                                                controller.getFieldColor("list").darker(), 
                                                                controller.getFieldColor("list"), 
                                                                controller.getFieldColor("list").brighter()));
        rightPanel.add(scrollBar,BorderLayout.EAST);
        rightPanel.add(scrollPane,BorderLayout.CENTER);

        JPanel[] listPanel = new JPanel[n];
        listButtons = new JButton[n]; // global for disable purposes
        JLabel[][] listLabels = new JLabel[n][6];
        JProgressBar[] progressBars = new JProgressBar[n];

        for (int i = 0; i < n; i++){
            if (i != 0) {
                JLabel spacers = new JLabel();
                spacers.setPreferredSize(new Dimension(100,1));
                scrollPanel.add(spacers);
            }
            listPanel[i] = new JPanel();
            listPanel[i].setPreferredSize(new Dimension (782,30));
            listPanel[i].setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
            listPanel[i].setBackground(controller.getAnimeColor(references.get(i)));

            listLabels[i][1] = new JLabel(" " +(i+1) +":");
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
            progressBars[i].setBackground(controller.getAnimeColor(references.get(i)).brighter().brighter()); 
            progressBars[i].setForeground(controller.getAnimeColor(references.get(i)).brighter());
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

    public void setSearchFocus(){
        searchField.requestFocus();
    }

    // Action Listeners
    private class orderListener implements ActionListener { 
        @Override
        public void actionPerformed(ActionEvent V){
            ((MainGUI)superior).generateNavigationPageSmall();
            JButton source = (JButton)(V.getSource());
            switch(source.getName()){
                case "Up": controller.setOrder(0); break;
                case "Down": controller.setOrder(1); break;
            }
            references = controller.getFilteredReferenceList();
            generateLeft();
            generateRight(); 
            searchField.requestFocus();
        }
    }
    private class sortBoxActionListener implements ActionListener { 
        @Override
        public void actionPerformed(ActionEvent V){
            ((MainGUI)superior).generateNavigationPageSmall();
            @SuppressWarnings("unchecked")
            String source = ((String)((JComboBox<String>)V.getSource()).getSelectedItem());
            switch(source){
                case "Started": controller.setSort(0); break;
                case "Name": controller.setSort(1); break;
                case "Episodes": controller.setSort(2); break;
                case "Season": controller.setSort(3); break;
                case "Year": controller.setSort(4); break;
                case "Progress": controller.setSort(5); break;
                case "Color": controller.setSort(6); break;
            } 
            references = controller.getFilteredReferenceList();
            generateLeft();
            generateRight();
            searchField.requestFocus();
        }
    }

    private class filterBoxActionListener implements ActionListener { 
        @Override
        public void actionPerformed(ActionEvent V){
            ((MainGUI)superior).generateNavigationPageSmall();
            @SuppressWarnings("unchecked")
            String string = ((String)((JComboBox<String>)V.getSource()).getSelectedItem());
            @SuppressWarnings("unchecked")
            String name = ((String)((JComboBox<String>)V.getSource()).getName());
            switch(name){
                case "Season": controller.setPreApplyFilterField(0,string); break;
                case "Year": controller.setPreApplyFilterField(1,string); break;
                case "Started": controller.setPreApplyFilterField(2,string); break;
                case "Language": controller.setPreApplyFilterField(3,string); break;
                case "Rating": controller.setPreApplyFilterField(4,string); break;
                case "Genre": controller.setPreApplyFilterField(5,string); break;
                case "Studio": 
                    if (controller.getListOfStudios().size() == 0){
                        // do nothing (don't apply the change)
                    }
                    else controller.setPreApplyFilterField(6,string); 
                    break;
            } 
            references = controller.getFilteredReferenceList();
            generateLeft();
            generateRight();
            searchField.requestFocus();
        }
    }

    private class hiddenButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            ((MainGUI)superior).generateNavigationPageSmall();
            controller.setHidden(!controller.getHidden());
            references = controller.getFilteredReferenceList();
            generateLeft();
            generateRight();
            searchField.requestFocus();
        }
    }

    private class applyButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent V){
            ((MainGUI)superior).generateNavigationPageSmall();
            controller.applyFilters();
            references = controller.getFilteredReferenceList();
            generateLeft();
            generateRight();
            searchField.requestFocus();
        }
    }

    private class resetButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent V){
            ((MainGUI)superior).generateNavigationPageSmall();
            controller.resetFilters();
            references = controller.getFilteredReferenceList();
            generateLeft();
            generateRight();
            searchField.requestFocus();
        }
    }

    private class quickSearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            ((MainGUI)superior).generateNavigationPageSmall();
            references = controller.getSearchedReferenceList(((JTextField)V.getSource()).getText());
            generateLeft();
            generateRight();
            searchField.requestFocus();
        }
    }

    private class changeToAnimeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            ((MainGUI)superior).generateAnimePage(((JButton)V.getSource()).getName());
        }
    }
}
