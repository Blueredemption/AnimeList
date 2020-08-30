import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame {
    private static final long serialVersionUID = 1L; 

    Controller controller;
    JLayeredPane pane;
    JPanel panelA, panelB, panelBA, panelBB, navPanel, subNavPanel1, subNavPanel2;
    JLabel fillLabel, navLabel;
    JButton nav1, nav2, home, list, stats, notes, newAnime, settings;
    JTextField pathNameField;

    ListPanel listPanel; // these are global to toggle the active components off when the nav tab is open
    AnimePanel animePanel;
    StatisticsPanel statisticsPanel;
    NotesPanel notesPanel;

    Dimension standardDim = new Dimension(1024, 768);
    Dimension leftDim = new Dimension(978, 728);
    Dimension rightDimSmall = new Dimension(30, 700);
    Dimension rightDimLarge = new Dimension(200, 700);
    Dimension rightDimLargeTop = new Dimension(170, 120);
    Dimension rightDimLargeBottom = new Dimension(170, 120);

    public MainGUI() { // constructor
        controller = new Controller();
        LaunchSettings();
    }

    public void LaunchSettings() { // generates the basic window for the program
        setIconImage(new ImageIcon("Images/UI/Icon.png").getImage());
        setTitle("AnimeList v3");
        setMaximumSize(standardDim);
        setResizable(false);
        // setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        generatePane();
        generatePanelB();
        generatePanelA();
        
        pack();
        setLocationRelativeTo(null);
    }

    public void generatePane() { // generates layered pane that holds the two main pains
        pane = new JLayeredPane();
        pane.setLayout(new BorderLayout());
        pane.setBackground(controller.getFieldColor("background1"));
        pane.setOpaque(true);
        add(pane, BorderLayout.CENTER);
    }

    public void generatePanelA() { // initially generates the left (content) pane
        panelA = new JPanel();
        pane.add(panelA, BorderLayout.WEST);
        generateHomePage();
        swapNavPanelFocus(true);
    }

    public void generateNavPanel(String label) { // generates the nav panel on the left (content) pane
        navPanel = new JPanel();
        navPanel.setLayout(new BorderLayout());
        navPanel.setPreferredSize(new Dimension(978, 25));
        navPanel.setOpaque(false);
        panelA.add(navPanel, BorderLayout.NORTH);

        subNavPanel1 = new JPanel() {
            private static final long serialVersionUID = 1L;
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(controller.getFieldColor("buttonBorder"));
                g.drawLine(3,12, 808, 12);
            }
        };
        subNavPanel1.setPreferredSize(new Dimension(808, 25));
        subNavPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        subNavPanel1.setBackground(controller.getFieldColor("navigation"));
        navPanel.add(subNavPanel1, BorderLayout.WEST);

        subNavPanel2 = new JPanel() {
            private static final long serialVersionUID = 1L;
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(controller.getFieldColor("buttonBorder"));
                g.drawLine(0,12, 167, 12);
            }
        };
        subNavPanel2.setPreferredSize(new Dimension(170, 25));
        subNavPanel2.setBackground(controller.getFieldColor("navigation"));
        navPanel.add(subNavPanel2, BorderLayout.EAST);

        fillLabel = new JLabel(); // this label moves the navLabel over ever so slightly because of flowLayout.
        fillLabel.setPreferredSize(new Dimension(180, 25));
        subNavPanel1.add(fillLabel);

        navLabel = new JLabel(" " +label +" ");
        navLabel.setForeground(controller.getFieldColor("text"));
        navLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        navLabel.setVerticalAlignment(JLabel.TOP);
        navLabel.setBackground(getCombinedColor(controller.getFieldColor("navigation"),controller.getFieldColor("background1")));
        navLabel.setOpaque(true);
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

        home = new JButton();
        home.setPreferredSize(new Dimension(20, 35));
        home.addActionListener(new homeButtonListener());
        home.setText(String.valueOf("\u2302"));
        setButtonDefaults(home);
        panelB.add(home);

        list = new JButton();
        list.setPreferredSize(new Dimension(20, 35));
        list.addActionListener(new listButtonListener());
        list.setText(String.valueOf("\u2261"));
        list.setFont(new Font("Dialog", Font.BOLD, 17));
        setButtonDefaults(list);
        panelB.add(list);

        stats = new JButton();
        stats.setPreferredSize(new Dimension(20, 35));
        stats.addActionListener(new statisticsButtonListener());
        stats.setText(String.valueOf("\u03C3"));
        stats.setFont(new Font("Dialog", Font.PLAIN, 17));
        setButtonDefaults(stats);
        panelB.add(stats);

        notes = new JButton();
        notes.setPreferredSize(new Dimension(20, 35));
        notes.addActionListener(new notesButtonListener());
        notes.setText(String.valueOf("\u270E"));
        notes.setFont(new Font("Dialog", Font.PLAIN, 14));
        setButtonDefaults(notes);
        panelB.add(notes);

        newAnime = new JButton();
        newAnime.setPreferredSize(new Dimension(20, 35));
        newAnime.addActionListener(new animeButtonListener());
        newAnime.setFont(new Font("Dialog", Font.PLAIN, 15));
        newAnime.setText(String.valueOf("\uFF0B"));
        setButtonDefaults(newAnime);
        panelB.add(newAnime);

        settings = new JButton();
        settings.setPreferredSize(new Dimension(20, 35));
        settings.addActionListener(new settingsButtonListener());
        settings.setText(String.valueOf("\u26ED"));
        setButtonDefaults(settings);
        panelB.add(settings);

        nav2 = new JButton();
        nav2.setPreferredSize(new Dimension(20, 354));
        nav2.addActionListener(new flipNavigation());
        nav2.setText("|||");
        setButtonDefaults(nav2);
        panelB.add(nav2);
        
        if (subNavPanel2 != null) swapNavPanelFocus(true);
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
        panelBA.setBackground(new Color(0, 0, 0, 0));
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
        nullifyPanels();
        generateNavigationPageSmall();
        panelA.removeAll();
        panelA.repaint();
        panelA.revalidate();
        panelA.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 0));
        panelA.setPreferredSize(leftDim);
        panelA.setBackground(controller.getFieldColor("background1"));

        generateNavPanel("Welcome to AnimeList v3 1.0.2");
        
        JLabel spacer = new JLabel(); // creating some gapping without flowlayout hgap.
        spacer.setPreferredSize(new Dimension(1,3));
        panelA.add(spacer);

        Image image = getImage(controller.getFieldText("mainScreenImage"), new Dimension((int)leftDim.getWidth()-6,(int)leftDim.getHeight()-31));
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension((int)leftDim.getWidth()-6,(int)leftDim.getHeight()-31));
        if (image != null) imageLabel.setIcon(new ImageIcon(image));
        imageLabel.setBorder(new BevelBorder(BevelBorder.LOWERED, controller.getFieldColor("background1"), 
                                                                  controller.getFieldColor("background1").darker(), 
                                                                  controller.getFieldColor("background1"), 
                                                                  controller.getFieldColor("background1").brighter()));
        panelA.add(imageLabel, BorderLayout.CENTER);
    }

    public void generateListPage() {
        controller.setState(1);
        nullifyPanels();
        generateNavigationPageSmall();
        panelA.removeAll();
        panelA.repaint();
        panelA.revalidate();
        panelA.setLayout(new BorderLayout());
        panelA.setPreferredSize(leftDim);
        panelA.setBackground(controller.getFieldColor("background1"));

        generateNavPanel("Viewing: Anime List");
        listPanel = new ListPanel(this, controller);
        panelA.add(listPanel,BorderLayout.WEST);
    }

    public void generateStatisticsPage() {
        controller.setState(2);
        nullifyPanels();
        generateNavigationPageSmall();
        panelA.removeAll();
        panelA.repaint();
        panelA.revalidate();
        panelA.setLayout(new BorderLayout());
        panelA.setPreferredSize(leftDim);
        panelA.setBackground(controller.getFieldColor("background1"));

        generateNavPanel("Viewing: Statistics");
        statisticsPanel = new StatisticsPanel(controller, this);
        panelA.add(statisticsPanel,BorderLayout.WEST);
    }

    public void generateNotesPage() {
        controller.setState(3);
        nullifyPanels();
        generateNavigationPageSmall();
        panelA.removeAll();
        panelA.repaint();
        panelA.revalidate();
        panelA.setLayout(new BorderLayout());
        panelA.setPreferredSize(leftDim);
        panelA.setBackground(controller.getFieldColor("background1"));

        generateNavPanel("Viewing: Notes");
        notesPanel = new NotesPanel(controller, this);
        panelA.add(notesPanel,BorderLayout.WEST);
    }

    public void generateAnimePage(String reference) {
        controller.setState(4);
        nullifyPanels();
        generateNavigationPageSmall();
        panelA.removeAll();
        panelA.repaint();
        panelA.revalidate();
        panelA.setLayout(new BorderLayout());
        panelA.setPreferredSize(leftDim);
        panelA.setBackground(controller.getFieldColor("background1"));

        if (reference.equals("New Anime")) reference = controller.createAnime();
        generateNavPanel("Viewing: " +controller.get(reference, "animeName"));
        animePanel = new AnimePanel(controller,this,reference);
        panelA.add(animePanel,BorderLayout.WEST);
    }

    public void generateAnimePage(String reference, String textAreaText) { // if refreshing
        controller.setState(4);
        nullifyPanels();
        generateNavigationPageSmall();
        panelA.removeAll();
        panelA.repaint();
        panelA.revalidate();
        panelA.setLayout(new BorderLayout());
        panelA.setPreferredSize(leftDim);
        panelA.setBackground(controller.getFieldColor("background1"));

        if (reference.equals("New Anime")) reference = controller.createAnime();
        generateNavPanel("Viewing: " +controller.get(reference, "animeName"));
        animePanel = new AnimePanel(controller,this,reference,textAreaText);
        panelA.add(animePanel,BorderLayout.WEST);
    }

    public void generateSettingsPage() {
        controller.setState(5);
        nullifyPanels();
        generateNavigationPageSmall();
        panelA.removeAll();
        panelA.repaint();
        panelA.revalidate();
        panelA.setLayout(new BorderLayout());
        panelA.setPreferredSize(leftDim);
        panelA.setBackground(controller.getFieldColor("background1"));

        pane.setBackground(controller.getFieldColor("background1"));

        generateNavPanel("Viewing: Settings");

        SettingsPanel settingsPanel = new SettingsPanel(this, controller);
        panelA.add(settingsPanel,BorderLayout.WEST);

    }

    // length cutting methods
    public Color getCombinedColor(Color front, Color back){ // adds two colors together component wise
        float r = ((float)front.getRed()/255)*((float)front.getAlpha()/255) + ((float)back.getRed()/255)*((float)back.getAlpha()/255)*(1-((float)front.getAlpha()/255));
        float g = ((float)front.getGreen()/255)*((float)front.getAlpha()/255) + ((float)back.getGreen()/255)*((float)back.getAlpha()/255)*(1-((float)front.getAlpha()/255));
        float b = ((float)front.getBlue()/255)*((float)front.getAlpha()/255) + ((float)back.getBlue()/255)*((float)back.getAlpha()/255)*(1-((float)front.getAlpha()/255));
        return new Color(r,g,b);
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
    
    public void nullifyPanels() { // to save memory when these pages are not in use this method is called. 
        listPanel = null; 
        animePanel = null;
        statisticsPanel = null;
        notesPanel =  null;
    }

    public void setNavsDisabled(){  // to prevent overlay issues with color editor in SettingsPanel
        nav1.setEnabled(false);
        nav2.setEnabled(false);
    }

    public void setNavs(boolean bool){ // to prevent people from navigating off settings page while threads are still compiling data.
        nav1.setEnabled(bool);
        nav2.setEnabled(bool);
        home.setEnabled(bool);
        list.setEnabled(bool);
        stats.setEnabled(bool);
        notes.setEnabled(bool);
        newAnime.setEnabled(bool);
        settings.setEnabled(bool);
    }

    public void setNavigationText(String text){ // for animePanel to set the text after a name change
        navLabel.setText(" " +text +" ");
        navPanel.repaint();
        navPanel.revalidate();
    }

    // ACTION LISTENERS
    private class flipNavigation implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){   
            if (panelB.getPreferredSize().equals(rightDimSmall)){
                if (listPanel != null){
                    listPanel.disableScrollBar();
                    listPanel.disableButtons();
                } 
                else if (animePanel != null){
                    animePanel.toggleEnables(false);
                } 
                else if (notesPanel != null){
                    notesPanel.toggleEnables(false);
                }
                swapNavPanelFocus(false);
                generateNavigationPageLarge();
            }
            else{
                if (listPanel != null){
                    listPanel.enableScrollBar();
                    listPanel.enableButtons();
                } 
                else if (animePanel != null){
                    animePanel.toggleEnables(true);
                } 
                else if (notesPanel != null){
                    notesPanel.toggleEnables(true);
                }
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
            if(controller.getState() != 1){
                generateListPage();
                listPanel.setSearchFocus();
            } 
            else listPanel.setSearchFocus();
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
            generateAnimePage("New Anime");
        }
    }

    private class settingsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            if(controller.getState() != 5) generateSettingsPage();
        }
    }
}
