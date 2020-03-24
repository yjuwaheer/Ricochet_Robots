import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Write a description of class Stats here.
 *
 * @author (Yudish)
 * @version (0.1)
 */
public class Stats extends JFrame
{
    private JMenuBar frameMenu;
    
    public Stats()
    {
        super("Ricochet Robots | Stats");
        //Setting the icon of the frame
        Image rrIcon =  Toolkit.getDefaultToolkit().getImage("Resources/rr.jpg");
        setIconImage(rrIcon);
        //
        menu();
        setJMenuBar(frameMenu);
        setPreferredSize(new Dimension(500, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    //Creating the menu of the stats frame
    private void menu()
    {
        //Creating the menu bar
        frameMenu = new JMenuBar();
        JMenu file = new JMenu("File");
        frameMenu.add(file);
        
        JMenuItem menu = new JMenuItem("Menu");
        JMenuItem exit = new JMenuItem("Exit");
        file.add(menu);
        file.add(exit);
        
        //Going to menu frame
        menu.addActionListener((ActionEvent ev) -> {dispose();new MainFrame();});
        //Exiting the frame
        exit.addActionListener((ActionEvent ev) -> {System.exit(0);});
    }
}
