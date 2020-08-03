//*****************************************//
// Created by Cooper Eisnor                //
//*****************************************//

import javax.swing.*;

public class Action
{
  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      @Override
      public void run()
      {
        new MainGUI().setVisible(true);
      }
    });     
  }
}