import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;

import java.awt.event.MouseAdapter;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NotesPanel extends JPanel{
    private static final long serialVersionUID = 1L;
    Dimension fullDim = new Dimension(978,704);
    Controller controller;
    MainGUI mainGUI;

    JPanel leftPanel, rightPanel;
    JButton saveButton;
    JTextArea leftArea, rightArea;
    JScrollPane rightScrollPane;
    JScrollBar rightScrollBar;

    public NotesPanel(Controller controller, MainGUI mainGUI){
        this.controller = controller;
        this.mainGUI = mainGUI;

        setLayout(new BorderLayout());
        generateTop();
        generateLeft();
        generateRight();
    }

    public void generateTop() {
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(978,29));
        topPanel.setBackground(controller.getFieldColor("background1"));
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,2));
        add(topPanel,BorderLayout.NORTH);

        saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(60,25));
        saveButton.setBackground(controller.getFieldColor("buttons"));
        saveButton.setForeground(controller.getFieldColor("text"));
        saveButton.setBorder(BorderFactory.createLineBorder(controller.getFieldColor("buttonBorder")));
        saveButton.addActionListener(new saveButtonActionListener());
        saveButton.addMouseListener(new buttonHover());
        saveButton.setFocusPainted(false);
        topPanel.add(saveButton);
    }

    public void generateLeft() {
        leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(748,675));
        leftPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,0,0));
        leftPanel.setBackground(controller.getFieldColor("background1"));
        add(leftPanel, BorderLayout.WEST);

        int n = 9000;

        leftArea = new JTextArea();
        leftArea.setPreferredSize(new Dimension(748,n));
        leftArea.setBackground(controller.getFieldColor("background2").brighter());
        leftArea.setForeground(controller.getFieldColor("text"));
        leftArea.setLineWrap(true);
        leftArea.setWrapStyleWord(true);
        leftArea.setCaretColor(controller.getFieldColor("text"));
        leftArea.setFont(new Font("Dialog", Font.BOLD, 14));
        leftArea.setText(controller.getFieldText("notepadLeft"));
        leftArea.setSelectionStart(0); // these two make sure the panel starts at the top
        leftArea.setSelectionEnd(0);
        
        JScrollPane scrollPane = new JScrollPane(leftArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
        scrollPane.setOpaque(false);
        scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, controller.getFieldColor("background2"), 
                                                                 controller.getFieldColor("background2").darker(), 
                                                                 controller.getFieldColor("background2"), 
                                                                 controller.getFieldColor("background2").brighter()));
        scrollPane.setPreferredSize(new Dimension (733,670));
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
        scrollPane.remove(scrollBar); 
        scrollBar.setPreferredSize(new Dimension(10,670));
        scrollBar.setUI(new CustomScrollBarUI(controller,controller.getFieldColor("buttons"),controller.getFieldColor("background2").brighter()));
        scrollBar.setBorder(new BevelBorder(BevelBorder.LOWERED, controller.getFieldColor("background2"), 
                                                                 controller.getFieldColor("background2").darker(), 
                                                                 controller.getFieldColor("background2"), 
                                                                 controller.getFieldColor("background2").brighter()));
        leftPanel.add(scrollPane);
        leftPanel.add(scrollBar);
    }

    public void generateRight(){
        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(230,675));
        rightPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        rightPanel.setBackground(controller.getFieldColor("background1"));
        add(rightPanel, BorderLayout.EAST);

        int n = 9000;

        rightArea = new JTextArea();
        rightArea.setPreferredSize(new Dimension(230,n));
        rightArea.setBackground(controller.getFieldColor("background2").brighter());
        rightArea.setForeground(controller.getFieldColor("text"));
        rightArea.setLineWrap(true);
        rightArea.setWrapStyleWord(true);
        rightArea.setCaretColor(controller.getFieldColor("text"));
        rightArea.setFont(new Font("Dialog", Font.BOLD, 14));
        rightArea.setText(controller.getFieldText("notepadRight"));
        rightArea.setSelectionStart(0); // these two make sure the panel starts at the top
        rightArea.setSelectionEnd(0);
        
        rightScrollPane = new JScrollPane(rightArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
        rightScrollPane.setOpaque(false);
        rightScrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, controller.getFieldColor("background2"), 
                                                                 controller.getFieldColor("background2").darker(), 
                                                                 controller.getFieldColor("background2"), 
                                                                 controller.getFieldColor("background2").brighter()));
        rightScrollPane.setPreferredSize(new Dimension (215,670));
        rightScrollPane.getVerticalScrollBar().setUnitIncrement(20);

        rightScrollBar = rightScrollPane.getVerticalScrollBar();
        rightScrollPane.remove(rightScrollBar); 
        rightScrollBar.setPreferredSize(new Dimension(10,670));
        rightScrollBar.setUI(new CustomScrollBarUI(controller,controller.getFieldColor("buttons"),controller.getFieldColor("background2").brighter()));
        rightScrollBar.setBorder(new BevelBorder(BevelBorder.LOWERED, controller.getFieldColor("background2"), 
                                                                 controller.getFieldColor("background2").darker(), 
                                                                 controller.getFieldColor("background2"), 
                                                                 controller.getFieldColor("background2").brighter()));
        rightPanel.add(rightScrollPane,BorderLayout.WEST);
        rightPanel.add(rightScrollBar,BorderLayout.EAST);
    }

    public void toggleEnables(boolean bool){ 
        if (rightArea != null) rightArea.setEnabled(bool);
        if (rightScrollPane != null) {
            rightScrollPane.getVerticalScrollBar().setEnabled(bool);
            rightScrollPane.setWheelScrollingEnabled(bool);
            if (bool){
                rightPanel.add(rightScrollBar,BorderLayout.EAST);
            }
            else {
                rightPanel.remove(rightScrollBar);
            }
        }
        
    }

    private class saveButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent V){
            controller.setFieldText("notepadLeft",leftArea.getText());
            controller.setFieldText("notepadRight",rightArea.getText());
        }
    }

    private class buttonHover extends MouseAdapter {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent V) {
            JButton button = (JButton) V.getSource();
            if (button.isEnabled()){
                button.setBackground(controller.getFieldColor("buttons").brighter());
            }
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent V) {
            JButton button = (JButton) V.getSource();
            button.setBackground(controller.getFieldColor("buttons"));
        }
    }
}
