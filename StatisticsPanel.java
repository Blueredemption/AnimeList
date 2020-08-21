import java.awt.*;
import javax.swing.*;
//import javax.swing.border.BevelBorder;
import javax.swing.plaf.basic.BasicProgressBarUI;

//import java.awt.event.*;
import java.awt.Dimension;
//import java.util.ArrayList;

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
        leftPanel.setBackground(Color.BLUE);

        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(489,671));
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setOpaque(false);

        rightLeftPanel = new JPanel();
        rightLeftPanel.setPreferredSize(new Dimension(200,481));
        rightLeftPanel.setLayout(new FlowLayout());
        //rightLeftPanel.setOpaque(false);
        rightLeftPanel.setBackground(Color.WHITE);
        rightPanel.add(rightLeftPanel,BorderLayout.WEST);

        rightRightPanel = new JPanel();
        rightRightPanel.setPreferredSize(new Dimension(289,481));
        rightRightPanel.setLayout(new FlowLayout());
        //rightRightPanel.setOpaque(false);
        rightRightPanel.setBackground(Color.ORANGE);
        rightPanel.add(rightRightPanel,BorderLayout.EAST);

        rightBottomPanel = new JPanel();
        rightBottomPanel.setPreferredSize(new Dimension(489,190));
        rightBottomPanel.setLayout(new FlowLayout());
        //rightBottomPanel.setOpaque(false);
        rightBottomPanel.setBackground(Color.YELLOW);
        rightPanel.add(rightBottomPanel,BorderLayout.SOUTH);
    }

    public void generateTopPanel() {
        
    }

    public void generateLeftPanel() {

    }

    public void generateRightUpperPanels() {

    }

    public void generateRightBottomPanel() {

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
}
