import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.basic.BasicProgressBarUI;

import java.awt.event.*;
import java.awt.Dimension;
import java.io.File;
//import java.util.ArrayList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class StatisticsPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    Dimension fullDim = new Dimension(978, 700);
    
    Controller controller;
    MainGUI mainGUI;
    ArrayList<String> references;

    // loading screen related
    JPanel loadingPanel;
    JProgressBar progressBar;
    JLabel label;

    // pregenerated panels
    JPanel structurePanel, topPanel, leftPanel, rightPanel, rightLeftPanel, rightRightPanel, rightBottomPanel;

    // navigation break related
    JComboBox<String> navBreakBox; 
    JButton hiddenBox;

    // statistics arrays

    public StatisticsPanel(Controller controller, MainGUI mainGUI) { // constructor
        this.controller = controller;
        this.mainGUI = mainGUI;

        setLayout(new BorderLayout());
        setOpaque(false);

        load(); 
    }

    @SuppressWarnings("rawtypes") 
    public void load(){ // creates a loading screen and loads everything needed for the page to be painted
        mainGUI.setNavs(false);
        loadingPanel = new JPanel();
        loadingPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
        loadingPanel.setBackground(controller.getFieldColor("background1"));
        loadingPanel.setPreferredSize(fullDim);
        add(loadingPanel,BorderLayout.CENTER);
        
        JLabel spacer = new JLabel(); 
        spacer.setPreferredSize(new Dimension(500,250));
        loadingPanel.add(spacer);

        label = new JLabel("Generating Statistics...");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(800,30));
        label.setFont(new Font("Dialog", Font.BOLD, 18));
        label.setForeground(controller.getFieldColor("text"));
        loadingPanel.add(label);

        progressBar = new JProgressBar();
        progressBar.setPreferredSize(new Dimension (276,15));
        progressBar.setValue(0);
        progressBar.setBackground(controller.getFieldColor("background2").brighter().brighter()); 
        progressBar.setForeground(controller.getFieldColor("background2").brighter());
        progressBar.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder").brighter()));
        progressBar.setStringPainted(true);
        progressBar.setUI(new BasicProgressBarUI() {
            protected Color getSelectionBackground() { return controller.getFieldColor("text");}
            protected Color getSelectionForeground() { return controller.getFieldColor("text");} });
        loadingPanel.add(progressBar);
        
        SwingWorker worker1 = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() throws Exception{
                // tasks
                generateGeneralStatistics();
                setProgressN(11);
                //
                generateImagesRelated();
                setProgressN(22);
                //
                generateFilteredStatistics();
                setProgressN(33);
                //
                generateShellPanels();
                setProgressN(44);
                //
                generateTopPanel(); // filter related panel
                setProgressN(55);
                //
                generateLeftPanel();
                setProgressN(66);
                //
                generateRightUpperPanels();
                setProgressN(77);
                //
                generateRightBottomPanel(); // image panel
                setProgressN(88);
                //
                addComponentsToStructurePanel();
                setProgressN(100);
                //
                return true;
            }
        };
        worker1.execute();
    }

    public void setProgressN(int value) { // method that the threads use to change the loading bar value
        @SuppressWarnings("rawtypes")
        SwingWorker continuous = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() throws Exception{
                while (progressBar.getValue() < value) {
                    progressBar.setValue(progressBar.getValue() + 1);
                    Thread.sleep(5);
                }
                if (value == 100) label.setText("Done!");
                Thread.sleep(100);
                return true;
            }
        
            @Override
            protected void done() {
                if (value == 100){
                    remove(loadingPanel);
                    generate();
                    mainGUI.setNavs(true);
                }
            }
        };
        continuous.execute();
    }

    public void generateGeneralStatistics() {

    }

    public void generateImagesRelated() {

    }
    
    public void generateFilteredStatistics() {

    }

    public void generateShellPanels() {
        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(978,29));
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT,3,2));
        topPanel.setOpaque(false);

        leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(489,671));
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
        leftPanel.setOpaque(false);

        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(489,671));
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setOpaque(false);

        rightLeftPanel = new JPanel();
        rightLeftPanel.setPreferredSize(new Dimension(200,481));
        rightLeftPanel.setLayout(new FlowLayout());
        rightLeftPanel.setOpaque(false);
        rightPanel.add(rightLeftPanel,BorderLayout.WEST);

        rightRightPanel = new JPanel();
        rightRightPanel.setPreferredSize(new Dimension(289,481));
        rightRightPanel.setLayout(new FlowLayout());
        rightRightPanel.setOpaque(false);
        rightPanel.add(rightRightPanel,BorderLayout.EAST);

        rightBottomPanel = new JPanel();
        rightBottomPanel.setPreferredSize(new Dimension(489,190));
        rightBottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER,33,5));
        rightBottomPanel.setOpaque(false);
        rightPanel.add(rightBottomPanel,BorderLayout.SOUTH);
    }

    public void generateTopPanel() {
        topPanel.removeAll(); // if this is refreshing after a change of a filter selection (better than refreshing the whole page)

        JButton resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(68,25));
        resetButton.addActionListener(new resetButtonActionListener());
        resetButton.setBackground(controller.getFieldColor("buttons"));
        resetButton.setForeground(controller.getFieldColor("text"));
        resetButton.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder")));
        resetButton.setFocusPainted(false);
        topPanel.add(resetButton);

        JButton applyButton = new JButton("Apply");
        applyButton.setPreferredSize(new Dimension(68,25));
        applyButton.addActionListener(new applyButtonActionListener());
        applyButton.setBackground(controller.getFieldColor("buttons"));
        applyButton.setForeground(controller.getFieldColor("text"));
        applyButton.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder")));
        applyButton.setFocusPainted(false);
        applyButton.setEnabled(controller.checkForFilterChange());
        topPanel.add(applyButton);

        JLabel spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(22,0));
        topPanel.add(spacer);
        
        JLabel titleLabel = new JLabel("Filters: ");
        titleLabel.setForeground(controller.getFieldColor("text"));
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        titleLabel.setVerticalAlignment(JLabel.CENTER);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(titleLabel);

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

        filterBox[iter].setName("Season");
        filterBox[iter].setPreferredSize(new Dimension(80,25));
        topPanel.add(filterBox[iter++]);

        filterBox[iter].setName("Year");
        filterBox[iter].setPreferredSize(new Dimension(64,25));
        topPanel.add(filterBox[iter++]);

        filterBox[iter].setName("Started");
        filterBox[iter].setPreferredSize(new Dimension(80,25));
        topPanel.add(filterBox[iter++]);

        filterBox[iter].setName("Language");
        topPanel.add(filterBox[iter++]);

        filterBox[iter].setName("Rating");
        filterBox[iter].setPreferredSize(new Dimension(72,25));
        topPanel.add(filterBox[iter++]);

        filterBox[iter].setName("Genre");
        topPanel.add(filterBox[iter++]);

        filterBox[iter].setName("Studio");
        navBreakBox = filterBox[iter]; // for enable purposes on navigation open / close
        topPanel.add(filterBox[iter++]);
        
        spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(22,0));
        topPanel.add(spacer);

        JLabel hiddenLabel = new JLabel("Show Hidden? ");
        hiddenLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        hiddenLabel.setForeground(controller.getFieldColor("text"));
        topPanel.add(hiddenLabel); 

        hiddenBox = new JButton();
        if (controller.getHidden()) hiddenBox.setText("\u2714");
        hiddenBox.setPreferredSize(new Dimension(15,15));
        hiddenBox.setBackground(controller.getFieldColor("buttons"));
        hiddenBox.setForeground(controller.getFieldColor("text"));
        hiddenBox.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder")));
        hiddenBox.setFocusPainted(false);
        hiddenBox.addActionListener(new hiddenButtonActionListener());
        topPanel.add(hiddenBox);
    }

    public void generateLeftPanel() {
        /// 1a
        JPanel graphPanel = new JPanel();
        graphPanel.setPreferredSize(new Dimension(237,170));
        graphPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        graphPanel.setOpaque(false);
        leftPanel.add(graphPanel);

        /// 1b

        JPanel seasonPanel = new JPanel();
        seasonPanel.setPreferredSize(new Dimension(237,170));
        seasonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        seasonPanel.setOpaque(false);
        leftPanel.add(seasonPanel);

        JLabel titleLabel = new JLabel("By Season");
        titleLabel.setPreferredSize(new Dimension(227,30));
        titleLabel.setForeground(controller.getFieldColor("text"));
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        titleLabel.setVerticalAlignment(JLabel.CENTER);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        seasonPanel.add(titleLabel);

        /// 1

        JPanel outsidePanel = new JPanel();
        outsidePanel.setPreferredSize(new Dimension(237,150));
        outsidePanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        outsidePanel.setOpaque(false);

        JPanel insidePanel = new JPanel();
        insidePanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        insidePanel.setBackground(controller.getFieldColor("background1"));
        addComponents(insidePanel, "By Release Year");

        titleLabel = new JLabel("By Release Year");
        titleLabel.setPreferredSize(new Dimension(227,30));
        titleLabel.setForeground(controller.getFieldColor("text"));
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        titleLabel.setVerticalAlignment(JLabel.CENTER);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JScrollPane scrollPane = new JScrollPane(insidePanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
        scrollPane.setOpaque(false);
        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        scrollPane.setPreferredSize(new Dimension (205,120));
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
        scrollPane.remove(scrollBar); 
        scrollBar.setPreferredSize(new Dimension(10,120));
        scrollBar.setUI(new CustomScrollBarUI(controller,controller.getFieldColor("buttons"),controller.getFieldColor("background1").brighter()));
        scrollBar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        outsidePanel.add(titleLabel);
        outsidePanel.add(scrollPane);
        outsidePanel.add(scrollBar);
        leftPanel.add(outsidePanel);
        
        /// 2

        outsidePanel = new JPanel();
        outsidePanel.setPreferredSize(new Dimension(237,150));
        outsidePanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        outsidePanel.setOpaque(false);

        insidePanel = new JPanel();
        insidePanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        insidePanel.setBackground(controller.getFieldColor("background1"));
        addComponents(insidePanel, "By Start Year");

        titleLabel = new JLabel("By Start Year");
        titleLabel.setPreferredSize(new Dimension(227,30));
        titleLabel.setForeground(controller.getFieldColor("text"));
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        titleLabel.setVerticalAlignment(JLabel.CENTER);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        scrollPane = new JScrollPane(insidePanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
        scrollPane.setOpaque(false);
        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        scrollPane.setPreferredSize(new Dimension (205,120));
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        scrollBar = scrollPane.getVerticalScrollBar();
        scrollPane.remove(scrollBar); 
        scrollBar.setPreferredSize(new Dimension(10,120));
        scrollBar.setUI(new CustomScrollBarUI(controller,controller.getFieldColor("buttons"),controller.getFieldColor("background1").brighter()));
        scrollBar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        outsidePanel.add(titleLabel);
        outsidePanel.add(scrollPane);
        outsidePanel.add(scrollBar);
        leftPanel.add(outsidePanel);
        
        /// 3

        outsidePanel = new JPanel();
        outsidePanel.setPreferredSize(new Dimension(237,150));
        outsidePanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        outsidePanel.setOpaque(false);

        insidePanel = new JPanel();
        insidePanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        insidePanel.setBackground(controller.getFieldColor("background1"));
        addComponents(insidePanel, "By Main Genre");

        titleLabel = new JLabel("By Main Genre");
        titleLabel.setPreferredSize(new Dimension(227,30));
        titleLabel.setForeground(controller.getFieldColor("text"));
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        titleLabel.setVerticalAlignment(JLabel.CENTER);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        scrollPane = new JScrollPane(insidePanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
        scrollPane.setOpaque(false);
        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        scrollPane.setPreferredSize(new Dimension (205,120));
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        scrollBar = scrollPane.getVerticalScrollBar();
        scrollPane.remove(scrollBar); 
        scrollBar.setPreferredSize(new Dimension(10,120));
        scrollBar.setUI(new CustomScrollBarUI(controller,controller.getFieldColor("buttons"),controller.getFieldColor("background1").brighter()));
        scrollBar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        outsidePanel.add(titleLabel);
        outsidePanel.add(scrollPane);
        outsidePanel.add(scrollBar);
        leftPanel.add(outsidePanel);
        
        /// 4

        outsidePanel = new JPanel();
        outsidePanel.setPreferredSize(new Dimension(237,150));
        outsidePanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        outsidePanel.setOpaque(false);

        insidePanel = new JPanel();
        insidePanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        insidePanel.setBackground(controller.getFieldColor("background1"));
        addComponents(insidePanel, "By Sub Genre");

        titleLabel = new JLabel("By Sub Genre");
        titleLabel.setPreferredSize(new Dimension(227,30));
        titleLabel.setForeground(controller.getFieldColor("text"));
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        titleLabel.setVerticalAlignment(JLabel.CENTER);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        scrollPane = new JScrollPane(insidePanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
        scrollPane.setOpaque(false);
        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        scrollPane.setPreferredSize(new Dimension (205,120));
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        scrollBar = scrollPane.getVerticalScrollBar();
        scrollPane.remove(scrollBar); 
        scrollBar.setPreferredSize(new Dimension(10,120));
        scrollBar.setUI(new CustomScrollBarUI(controller,controller.getFieldColor("buttons"),controller.getFieldColor("background1").brighter()));
        scrollBar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        outsidePanel.add(titleLabel);
        outsidePanel.add(scrollPane);
        outsidePanel.add(scrollBar);
        leftPanel.add(outsidePanel);
        
        /// 5

        outsidePanel = new JPanel();
        outsidePanel.setPreferredSize(new Dimension(237,150));
        outsidePanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        outsidePanel.setOpaque(false);

        insidePanel = new JPanel();
        insidePanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        insidePanel.setBackground(controller.getFieldColor("background1"));
        addComponents(insidePanel, "By Studio");

        titleLabel = new JLabel("By Studio");
        titleLabel.setPreferredSize(new Dimension(227,30));
        titleLabel.setForeground(controller.getFieldColor("text"));
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        titleLabel.setVerticalAlignment(JLabel.CENTER);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        scrollPane = new JScrollPane(insidePanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
        scrollPane.setOpaque(false);
        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        scrollPane.setPreferredSize(new Dimension (205,120));
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        scrollBar = scrollPane.getVerticalScrollBar();
        scrollPane.remove(scrollBar); 
        scrollBar.setPreferredSize(new Dimension(10,120));
        scrollBar.setUI(new CustomScrollBarUI(controller,controller.getFieldColor("buttons"),controller.getFieldColor("background1").brighter()));
        scrollBar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        outsidePanel.add(titleLabel);
        outsidePanel.add(scrollPane);
        outsidePanel.add(scrollBar);
        leftPanel.add(outsidePanel);
        
        /// 6

        outsidePanel = new JPanel();
        outsidePanel.setPreferredSize(new Dimension(237,150));
        outsidePanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        outsidePanel.setOpaque(false);

        insidePanel = new JPanel();
        insidePanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        insidePanel.setBackground(controller.getFieldColor("background1"));
        addComponents(insidePanel, "By Content Rating");

        titleLabel = new JLabel("By Content Rating");
        titleLabel.setPreferredSize(new Dimension(227,30));
        titleLabel.setForeground(controller.getFieldColor("text"));
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        titleLabel.setVerticalAlignment(JLabel.CENTER);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        scrollPane = new JScrollPane(insidePanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
        scrollPane.setOpaque(false);
        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        scrollPane.setPreferredSize(new Dimension (205,120));
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        scrollBar = scrollPane.getVerticalScrollBar();
        scrollPane.remove(scrollBar); 
        scrollBar.setPreferredSize(new Dimension(10,120));
        scrollBar.setUI(new CustomScrollBarUI(controller,controller.getFieldColor("buttons"),controller.getFieldColor("background1").brighter()));
        scrollBar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        outsidePanel.add(titleLabel);
        outsidePanel.add(scrollPane);
        outsidePanel.add(scrollBar);
        leftPanel.add(outsidePanel);

        /// Scrolling JLabel

        JLabel scrollLabel = new JLabel("Scrolling Text Goes Here");
        scrollLabel.setPreferredSize(new Dimension(489,30));
        scrollLabel.setForeground(controller.getFieldColor("text"));
        scrollLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        scrollLabel.setVerticalAlignment(JLabel.CENTER);
        scrollLabel.setHorizontalAlignment(JLabel.LEFT);
        leftPanel.add(scrollLabel);
    }

    public void generateRightUpperPanels() {
        // rightRightPanel
        JLabel sLabel = new JLabel();
        sLabel.setText("####"); // total # anime watched
        sLabel.setPreferredSize(new Dimension(289,90));
        sLabel.setHorizontalAlignment(JLabel.CENTER);
        sLabel.setVerticalAlignment(JLabel.CENTER);
        sLabel.setFont(new Font("Dialog", Font.BOLD, 105));
        sLabel.setForeground(controller.getFieldColor("text"));
        rightRightPanel.add(sLabel);

        sLabel = new JLabel();
        sLabel.setText("Anime Watched"); 
        sLabel.setPreferredSize(new Dimension(289,20));
        sLabel.setHorizontalAlignment(JLabel.CENTER);
        sLabel.setVerticalAlignment(JLabel.CENTER);
        sLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        sLabel.setForeground(controller.getFieldColor("text"));
        rightRightPanel.add(sLabel);

        sLabel = new JLabel();
        sLabel.setText("####"); // total # episodes watched
        sLabel.setPreferredSize(new Dimension(320,90));
        sLabel.setHorizontalAlignment(JLabel.CENTER);
        sLabel.setVerticalAlignment(JLabel.CENTER);
        sLabel.setFont(new Font("Dialog", Font.BOLD, 105));
        sLabel.setForeground(controller.getFieldColor("text"));
        rightRightPanel.add(sLabel);

        sLabel = new JLabel();
        sLabel.setText("Episodes Watched"); 
        sLabel.setPreferredSize(new Dimension(289,20));
        sLabel.setHorizontalAlignment(JLabel.CENTER);
        sLabel.setVerticalAlignment(JLabel.CENTER);
        sLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        sLabel.setForeground(controller.getFieldColor("text"));
        rightRightPanel.add(sLabel);

        sLabel = new JLabel(); // spacer
        sLabel.setPreferredSize(new Dimension(289,15));
        rightRightPanel.add(sLabel);

        sLabel = new JLabel();
        sLabel.setText("Overall Time Spent"); 
        sLabel.setPreferredSize(new Dimension(289,20));
        sLabel.setHorizontalAlignment(JLabel.CENTER);
        sLabel.setVerticalAlignment(JLabel.CENTER);
        sLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        sLabel.setForeground(controller.getFieldColor("text"));
        rightRightPanel.add(sLabel);

        sLabel = new JLabel("####");
        sLabel.setPreferredSize(new Dimension(289,35));
        sLabel.setHorizontalAlignment(JLabel.CENTER);
        sLabel.setFont(new Font("Dialog", Font.BOLD, 45));
        sLabel.setForeground(controller.getFieldColor("text"));
        rightRightPanel.add(sLabel);

        sLabel = new JLabel("Days");
        sLabel.setPreferredSize(new Dimension(289,15));
        sLabel.setHorizontalAlignment(JLabel.CENTER);
        sLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        sLabel.setForeground(controller.getFieldColor("text"));
        rightRightPanel.add(sLabel);
        
        sLabel = new JLabel("##");
        sLabel.setPreferredSize(new Dimension(289,35));
        sLabel.setHorizontalAlignment(JLabel.CENTER);
        sLabel.setFont(new Font("Dialog", Font.BOLD, 45));
        sLabel.setForeground(controller.getFieldColor("text"));
        rightRightPanel.add(sLabel);
        
        sLabel = new JLabel("Hours");
        sLabel.setPreferredSize(new Dimension(289,15));
        sLabel.setHorizontalAlignment(JLabel.CENTER);
        sLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        sLabel.setForeground(controller.getFieldColor("text"));
        rightRightPanel.add(sLabel);
        
        sLabel = new JLabel("##"); 
        sLabel.setPreferredSize(new Dimension(289,35));
        sLabel.setHorizontalAlignment(JLabel.CENTER);
        sLabel.setFont(new Font("Dialog", Font.BOLD, 45));
        sLabel.setForeground(controller.getFieldColor("text"));
        rightRightPanel.add(sLabel);
        
        sLabel = new JLabel("Minutes");
        sLabel.setPreferredSize(new Dimension(289,15));
        sLabel.setHorizontalAlignment(JLabel.CENTER);
        sLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        sLabel.setForeground(controller.getFieldColor("text"));
        rightRightPanel.add(sLabel);

        //rightLeftPanel

        sLabel = new JLabel(); // spacer
        sLabel.setPreferredSize(new Dimension(200,3));
        rightLeftPanel.add(sLabel);

        JComboBox<String> dropdown = new JComboBox<String>(/*list of anime subbed*/);
        dropdown.setRenderer(new CustomComboBoxRenderer(controller));
        dropdown.setEditor(new CustomComboBoxEditor(controller));
        dropdown.setUI(new CustomComboBoxUI(controller));
        dropdown.setPreferredSize(new Dimension(200,25));
        dropdown.setFont(new Font("Dialog", Font.BOLD, 12));
        dropdown.setForeground(controller.getFieldColor("text"));
        dropdown.setBackground(controller.getFieldColor("background3"));
        dropdown.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("background2")));
        dropdown.setEditable(true);
        dropdown.setSelectedItem("Subbed:  ## Anime"); // # of subbed anime watched
        dropdown.setEditable(false);
        dropdown.setFocusable(false);
        //dropdown.addActionListener(); 
        rightLeftPanel.add(dropdown);
        
        dropdown = new JComboBox<String>(/*list of anime dubbed*/);
        dropdown.setRenderer(new CustomComboBoxRenderer(controller));
        dropdown.setEditor(new CustomComboBoxEditor(controller));
        dropdown.setUI(new CustomComboBoxUI(controller));
        dropdown.setPreferredSize(new Dimension(200,25));
        dropdown.setFont(new Font("Dialog", Font.BOLD, 12));
        dropdown.setForeground(controller.getFieldColor("text"));
        dropdown.setBackground(controller.getFieldColor("background3"));
        dropdown.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("background2")));
        dropdown.setEditable(true);
        dropdown.setSelectedItem("Dubbed:  ## Anime"); // # of dubbed anime watched
        dropdown.setEditable(false);
        dropdown.setFocusable(false);
        //dropdown.addActionListener(); 
        rightLeftPanel.add(dropdown);

        dropdown = new JComboBox<String>(/*list of anime other*/);
        dropdown.setRenderer(new CustomComboBoxRenderer(controller));
        dropdown.setEditor(new CustomComboBoxEditor(controller));
        dropdown.setUI(new CustomComboBoxUI(controller));
        dropdown.setPreferredSize(new Dimension(200,25));
        dropdown.setFont(new Font("Dialog", Font.BOLD, 12));
        dropdown.setForeground(controller.getFieldColor("text"));
        dropdown.setBackground(controller.getFieldColor("background3"));
        dropdown.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("background2")));
        dropdown.setEditable(true);
        dropdown.setSelectedItem("Other:  ## Anime"); // # of other anime watched
        dropdown.setEditable(false);
        dropdown.setFocusable(false);
        //dropdown.addActionListener(); 
        rightLeftPanel.add(dropdown);

        sLabel = new JLabel(); // spacer
        sLabel.setPreferredSize(new Dimension(200,25));
        rightLeftPanel.add(sLabel);

        dropdown = new JComboBox<String>(/*list of epsiodes subbed*/); // make sure these 3 dropdowns are sorted by episodes not by whatever the sortstate is?
        dropdown.setRenderer(new CustomComboBoxRenderer(controller));
        dropdown.setEditor(new CustomComboBoxEditor(controller));
        dropdown.setUI(new CustomComboBoxUI(controller));
        dropdown.setPreferredSize(new Dimension(200,25));
        dropdown.setFont(new Font("Dialog", Font.BOLD, 12));
        dropdown.setForeground(controller.getFieldColor("text"));
        dropdown.setBackground(controller.getFieldColor("background3"));
        dropdown.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("background2")));
        dropdown.setEditable(true);
        dropdown.setSelectedItem("Subbed:  ##### Episodes"); // # of subbed anime watched
        dropdown.setEditable(false);
        dropdown.setFocusable(false);
        //dropdown.addActionListener(); 
        rightLeftPanel.add(dropdown);
        
        dropdown = new JComboBox<String>(/*list of episodes dubbed*/);
        dropdown.setRenderer(new CustomComboBoxRenderer(controller));
        dropdown.setEditor(new CustomComboBoxEditor(controller));
        dropdown.setUI(new CustomComboBoxUI(controller));
        dropdown.setPreferredSize(new Dimension(200,25));
        dropdown.setFont(new Font("Dialog", Font.BOLD, 12));
        dropdown.setForeground(controller.getFieldColor("text"));
        dropdown.setBackground(controller.getFieldColor("background3"));
        dropdown.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("background2")));
        dropdown.setEditable(true);
        dropdown.setSelectedItem("Dubbed:  ##### Epsiodes"); // # of dubbed episodes watched
        dropdown.setEditable(false);
        dropdown.setFocusable(false);
        //dropdown.addActionListener(); 
        rightLeftPanel.add(dropdown);

        dropdown = new JComboBox<String>(/*list of episodes other*/);
        dropdown.setRenderer(new CustomComboBoxRenderer(controller));
        dropdown.setEditor(new CustomComboBoxEditor(controller));
        dropdown.setUI(new CustomComboBoxUI(controller));
        dropdown.setPreferredSize(new Dimension(200,25));
        dropdown.setFont(new Font("Dialog", Font.BOLD, 12));
        dropdown.setForeground(controller.getFieldColor("text"));
        dropdown.setBackground(controller.getFieldColor("background3"));
        dropdown.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("background2")));
        dropdown.setEditable(true);
        dropdown.setSelectedItem("Other:  ##### Episodes"); // # of other anime watched
        dropdown.setEditable(false);
        dropdown.setFocusable(false);
        //dropdown.addActionListener(); 
        rightLeftPanel.add(dropdown);

        sLabel = new JLabel(); // spacer
        sLabel.setPreferredSize(new Dimension(200,35));
        rightLeftPanel.add(sLabel);

        sLabel = new JLabel("Favorite Anime 1");
        sLabel.setPreferredSize(new Dimension(200,20));
        sLabel.setHorizontalAlignment(JLabel.CENTER);
        sLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        sLabel.setForeground(controller.getFieldColor("text"));
        rightLeftPanel.add(sLabel);

        dropdown = new JComboBox<String>(/*selector for favorite 1*/);
        dropdown.setRenderer(new CustomComboBoxRenderer(controller));
        dropdown.setEditor(new CustomComboBoxEditor(controller));
        dropdown.setUI(new CustomComboBoxUI(controller));
        dropdown.setPreferredSize(new Dimension(200,25));
        dropdown.setFont(new Font("Dialog", Font.BOLD, 12));
        dropdown.setForeground(controller.getFieldColor("text"));
        dropdown.setBackground(controller.getFieldColor("background3"));
        dropdown.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("background2")));
        dropdown.setEditable(true);
        dropdown.setSelectedItem("Select One:"/*index of selected item OR "Select One:" */); // this will take some conditional statements
        dropdown.setEditable(false);
        dropdown.setFocusable(false);
        //dropdown.addActionListener(); 
        rightLeftPanel.add(dropdown);

        sLabel = new JLabel(); // spacer
        sLabel.setPreferredSize(new Dimension(200,20));
        rightLeftPanel.add(sLabel);

        sLabel = new JLabel("Favorite Anime 2");
        sLabel.setPreferredSize(new Dimension(200,20));
        sLabel.setHorizontalAlignment(JLabel.CENTER);
        sLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        sLabel.setForeground(controller.getFieldColor("text"));
        rightLeftPanel.add(sLabel);

        dropdown = new JComboBox<String>(/*selector for favorite 1*/);
        dropdown.setRenderer(new CustomComboBoxRenderer(controller));
        dropdown.setEditor(new CustomComboBoxEditor(controller));
        dropdown.setUI(new CustomComboBoxUI(controller));
        dropdown.setPreferredSize(new Dimension(200,25));
        dropdown.setFont(new Font("Dialog", Font.BOLD, 12));
        dropdown.setForeground(controller.getFieldColor("text"));
        dropdown.setBackground(controller.getFieldColor("background3"));
        dropdown.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("background2")));
        dropdown.setEditable(true);
        dropdown.setSelectedItem("Select One:"/*index of selected item OR "Select One:" */); // this will take some conditional statements
        dropdown.setEditable(false);
        dropdown.setFocusable(false);
        //dropdown.addActionListener(); 
        rightLeftPanel.add(dropdown);

        sLabel = new JLabel(); // spacer
        sLabel.setPreferredSize(new Dimension(200,20));
        rightLeftPanel.add(sLabel);

        sLabel = new JLabel("Favorite Anime 3");
        sLabel.setPreferredSize(new Dimension(200,20));
        sLabel.setHorizontalAlignment(JLabel.CENTER);
        sLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        sLabel.setForeground(controller.getFieldColor("text"));
        rightLeftPanel.add(sLabel);

        dropdown = new JComboBox<String>(/*selector for favorite 1*/);
        dropdown.setRenderer(new CustomComboBoxRenderer(controller));
        dropdown.setEditor(new CustomComboBoxEditor(controller));
        dropdown.setUI(new CustomComboBoxUI(controller));
        dropdown.setPreferredSize(new Dimension(200,25));
        dropdown.setFont(new Font("Dialog", Font.BOLD, 12));
        dropdown.setForeground(controller.getFieldColor("text"));
        dropdown.setBackground(controller.getFieldColor("background3"));
        dropdown.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("background2")));
        dropdown.setEditable(true);
        dropdown.setSelectedItem("Select One:"/*index of selected item OR "Select One:" */); // this will take some conditional statements
        dropdown.setEditable(false);
        dropdown.setFocusable(false);
        //dropdown.addActionListener(); 
        rightLeftPanel.add(dropdown);
    }

    public void generateRightBottomPanel() {
        rightBottomPanel.removeAll(); // if this is refreshing after a change of favorite image the components need to be generated (better than refreshing the whole page)

        Image imageOne = getImage(controller.getFieldText("favorite1"), new Dimension(129,180));
        JLabel favoriteImageOne = new JLabel();
        if (imageOne != null) favoriteImageOne.setIcon(new ImageIcon(imageOne));
        favoriteImageOne.setPreferredSize(new Dimension(125,180));
        favoriteImageOne.setBorder(new BevelBorder(BevelBorder.LOWERED, controller.getFieldColor("background1"), 
                                                                        controller.getFieldColor("background1").darker(), 
                                                                        controller.getFieldColor("background1"), 
                                                                        controller.getFieldColor("background1").brighter()));
        rightBottomPanel.add(favoriteImageOne);

        Image imageTwo = getImage(controller.getFieldText("favorite2"), new Dimension(129,180));
        JLabel favoriteImageTwo = new JLabel();
        if (imageTwo != null) favoriteImageTwo.setIcon(new ImageIcon(imageTwo));
        favoriteImageTwo.setPreferredSize(new Dimension(125,180));
        favoriteImageTwo.setBorder(new BevelBorder(BevelBorder.LOWERED, controller.getFieldColor("background1"), 
                                                                        controller.getFieldColor("background1").darker(), 
                                                                        controller.getFieldColor("background1"), 
                                                                        controller.getFieldColor("background1").brighter()));
        rightBottomPanel.add(favoriteImageTwo);

        Image imageThree = getImage(controller.getFieldText("favorite3"), new Dimension(129,180));
        JLabel favoriteImageThree = new JLabel();
        if (imageThree != null) favoriteImageThree.setIcon(new ImageIcon(imageThree));
        favoriteImageThree.setPreferredSize(new Dimension(125,180));
        favoriteImageThree.setBorder(new BevelBorder(BevelBorder.LOWERED, controller.getFieldColor("background1"), 
                                                                          controller.getFieldColor("background1").darker(), 
                                                                          controller.getFieldColor("background1"), 
                                                                          controller.getFieldColor("background1").brighter()));
        rightBottomPanel.add(favoriteImageThree);
    }

    public void addComponentsToStructurePanel() {
        structurePanel = new JPanel();
        structurePanel.setLayout(new BorderLayout());
        structurePanel.setBackground(controller.getFieldColor("background1"));
        structurePanel.setPreferredSize(fullDim);

        structurePanel.add(topPanel,BorderLayout.NORTH);
        structurePanel.add(leftPanel,BorderLayout.WEST);
        structurePanel.add(rightPanel,BorderLayout.EAST);
    }

    public void addComponents(JPanel componentPanel, String caseString){ // this method is what adds the JComboBoxes to the scrollpanels panel (and sets its size)
        componentPanel.setPreferredSize(new Dimension(205,1000));
    }

    public void generate() {
        repaint();
        revalidate();

        add(structurePanel,BorderLayout.CENTER);
    }

    // length cutting methods
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

    // Action Listeners
    private class filterBoxActionListener implements ActionListener { 
        @Override
        public void actionPerformed(ActionEvent V){
            mainGUI.generateNavigationPageSmall();
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
                case "Studio": controller.setPreApplyFilterField(6,string); break;
            } 
            references = controller.getFilteredReferenceList();
            generateTopPanel();
        }
    }

    private class resetButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent V){
            controller.resetFilters();
            mainGUI.generateStatisticsPage();
        }
    }

    private class applyButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent V){
            controller.applyFilters();
            mainGUI.generateStatisticsPage();
        }
    }

    private class hiddenButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            controller.setHidden(!controller.getHidden());
            mainGUI.generateStatisticsPage();
        }
    }
}
