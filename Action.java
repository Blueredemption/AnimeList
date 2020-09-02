import java.io.File;

import javax.swing.SwingUtilities;

public class Action
{
  public static void main(String[] args) // runs the program
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      @Override
      public void run()
      {
        checkDirectories();
        new MainGUI().setVisible(true);
        
      }
    });     
  }

  public static void checkDirectories(){ // checks for necessary directories and creates them if they don't exist
    String[] directories = {"Anime Objects", "UI Values and Text", "Images", "Images/UI", "Images/Anime"};
    for (String dir : directories) {
      File file = new File(dir);
      if (!file.isDirectory()) file.mkdir();
    }
  }
}
