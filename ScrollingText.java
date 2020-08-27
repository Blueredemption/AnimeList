import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScrollingText extends JPanel implements ActionListener{
    private static final long serialVersionUID = 1L;
    int RATE = 15;
    Timer timer = new Timer(500 / RATE, this);
    JLabel label = new JLabel();
    String string;
    int stringLength;
    int index;
    
    public ScrollingText(String string, int stringLength, Controller controller){ // constructor
        setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        setPreferredSize(new Dimension(489,41));
        setBackground(controller.getFieldColor("background1"));
        
        StringBuilder sb = new StringBuilder(stringLength);
        for(int i = 0; i < stringLength; i++){
            sb.append('\u2002');
        }
        this.string = sb +string +sb;
        this.stringLength = stringLength;
        
        label.setPreferredSize(new Dimension(550,41));
        label.setForeground(controller.getFieldColor("text"));
        label.setFont(new Font("Dialog", Font.BOLD, 15));
        label.setVerticalAlignment(JLabel.BOTTOM);
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setText(sb.toString());
        this.add(label);

        timer.start();
  }

  @Override
    public void actionPerformed(ActionEvent e){
        index++;
        if (index > string.length() - stringLength) index = 0;
        label.setText(string.substring(index, index + stringLength));
    }
}