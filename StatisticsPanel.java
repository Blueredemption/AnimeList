import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
//import javax.swing.border.BevelBorder;
import javax.swing.plaf.basic.BasicProgressBarUI;

//import java.awt.event.*;
import java.awt.Dimension;
import java.io.File;
//import java.util.ArrayList;
import java.io.IOException;

public class StatisticsPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    Dimension fullDim = new Dimension(978, 700);
    
    Controller controller;
    MainGUI mainGUI;

    // loading screen related
    JPanel loadingPanel;
    JProgressBar progressBar;
    JLabel label;

    // pregenerated panels
    JPanel structurePanel, topPanel, leftPanel, rightPanel, rightLeftPanel, rightRightPanel, rightBottomPanel;

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
        // JComboBoxes hight is 25px
    }

    public void generateShellPanels() {
        //  JPanel structurePanel, topPanel, leftPanel, rightPanel, rightLeftPanel, rightRightPanel, rightBottomPanel;
        // Dimension fullDim = new Dimension(978, 700);
        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(978,29));
        topPanel.setLayout(new BorderLayout());
        //topPanel.setOpaque(false);
        topPanel.setBackground(Color.RED);

        leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(489,671));
        leftPanel.setLayout(new FlowLayout());
        //leftPanel.setOpaque(false);
        leftPanel.setBackground(Color.GREEN);

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
        
    }

    public void generateLeftPanel() {

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
        dropdown.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder")));
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
        dropdown.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder")));
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
        dropdown.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder")));
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
        dropdown.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder")));
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
        dropdown.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder")));
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
        dropdown.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder")));
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
        dropdown.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder")));
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
        dropdown.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder")));
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
        dropdown.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder")));
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
}
