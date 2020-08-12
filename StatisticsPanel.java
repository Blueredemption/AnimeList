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

    int progress = 0;

    public StatisticsPanel(Controller controller, MainGUI mainGUI){
        this.controller = controller;
        this.mainGUI = mainGUI;

        setLayout(new BorderLayout());
        setOpaque(false);

        load(); // creates a loading screen. and loads everything needed for the page to be painted.
    }

    public void load(){
        mainGUI.setNavs(false);
        JPanel loadingPanel = new JPanel();
        loadingPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
        loadingPanel.setBackground(Color.RED);
        loadingPanel.setPreferredSize(fullDim);
        add(loadingPanel,BorderLayout.CENTER);
        
        JLabel spacer = new JLabel(); 
        spacer.setPreferredSize(new Dimension(500,250));
        loadingPanel.add(spacer);

        JLabel label = new JLabel("Loading...");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(800,30));
        label.setFont(new Font("Dialog", Font.BOLD, 18));
        label.setForeground(controller.getFieldColor("text"));
        loadingPanel.add(label);

        JProgressBar progressBar = new JProgressBar();
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
        
        @SuppressWarnings("rawtypes") 
        SwingWorker worker1 = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() throws Exception{
                Thread.sleep(4000);
                return true;
            }
        
            @Override
            protected void done() {
                progressBar.setValue(progress + 25);
                setProgressN(progress + 25);
            }
        };
        worker1.execute();

        @SuppressWarnings("rawtypes") 
        SwingWorker worker2 = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() throws Exception{
                Thread.sleep(3000);
                return true;
            }
        
            @Override
            protected void done() {
                progressBar.setValue(progress + 25);
                setProgressN(progress + 25);
            }
        };
        worker2.execute();

        @SuppressWarnings("rawtypes") 
        SwingWorker worker3 = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() throws Exception{
                Thread.sleep(2000);
                return true;
            }
        
            @Override
            protected void done() {
                progressBar.setValue(progress + 25);
                setProgressN(progress + 25);
            }
        };
        worker3.execute();

        @SuppressWarnings("rawtypes") 
        SwingWorker worker4 = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() throws Exception{
                Thread.sleep(1000);
                return true;
            }
        
            @Override
            protected void done() {
                progressBar.setValue(progress + 25);
                setProgressN(progress + 25);
            }
        };
        worker4.execute();

        @SuppressWarnings("rawtypes") 
        SwingWorker wait = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() throws Exception{
                while (progress != 100){
                    Thread.sleep(10); // check every 100th of a second.
                }
                Thread.sleep(300); // gives the bar a little time at 100%
                return true;
            }
        
            @Override
            protected void done() {
                remove(loadingPanel);
                generate();
                mainGUI.setNavs(true);
            }
        };
        wait.execute();
    }

    public void generate(){
        repaint();
        revalidate();

        JPanel structurePanel = new JPanel();
        structurePanel.setLayout(new BorderLayout());
        structurePanel.setBackground(Color.BLUE);
        structurePanel.setPreferredSize(fullDim);
        add(structurePanel,BorderLayout.CENTER);
    }

    public void setProgressN(int value){ // method that the threads use to communicate to the loading bar
        System.out.println("Hi!");
        progress = value;
    }
}