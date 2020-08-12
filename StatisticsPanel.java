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

    JPanel loadingPanel;
    JProgressBar progressBar;
    JLabel label;

    public StatisticsPanel(Controller controller, MainGUI mainGUI){
        this.controller = controller;
        this.mainGUI = mainGUI;

        setLayout(new BorderLayout());
        setOpaque(false);

        load(); // creates a loading screen and loads everything needed for the page to be painted
    }

    @SuppressWarnings("rawtypes") 
    public void load(){
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
                setProgressN(25);
                //
                setProgressN(50);
                //
                setProgressN(75);
                //
                setProgressN(100);
                return true;
            }
        };
        worker1.execute();
    }

    public void generate(){
        repaint();
        revalidate();

        JPanel structurePanel = new JPanel();
        structurePanel.setLayout(new BorderLayout());
        structurePanel.setBackground(controller.getFieldColor("background1"));
        structurePanel.setPreferredSize(fullDim);
        add(structurePanel,BorderLayout.CENTER);
    }

    public void setProgressN(int value){ // method that the threads use to change the loading bar value
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
}