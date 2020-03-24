import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Write a description of class About here.
 *
 * @author (Yudish)
 * @version (0.1)
 */
public class About extends JFrame
{
    private JMenuBar frameMenu;
    
    private JLabel info;
    GridBagConstraints gbc = new GridBagConstraints();
    
    public About()
    {
        super("Ricochet Robots | About");
        //Setting the icon of the frame
        Image rrIcon =  Toolkit.getDefaultToolkit().getImage("Resources/rr.jpg");
        setIconImage(rrIcon);
        //
        menu();
        setJMenuBar(frameMenu);
        aboutDetails();
        setPreferredSize(new Dimension(500, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    //Creating the menu of the about frame
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
    
    private void aboutDetails()
    {
        setLayout(new GridBagLayout());
        

        gbc.gridx = 10;
        gbc.gridy = 10;
        
        JLabel aboutGame = new JLabel("About Game:", JLabel.CENTER);
        aboutGame.setFont(new Font("Forte", Font.BOLD, 30));
        add(aboutGame, gbc);
        
        //About the game
        info = new JLabel("<html><p>Ricochet Robot is a board game for 2 or more people in which a player must move a robot to its target in as few moves as posssible. " +
               "The game was first published in Germany in 1999 as <b>Rasende Roboter</b>. " + 
               "The English version was published by Rio Grande Games.</p></html>", JLabel.CENTER);
        /*  
        JEditorPane text = new JEditorPane();
        text.setContentType("text/html");
        text.setText(info);
        text.setEditable(false);
        text.setOpaque(false);
        */
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 10;
        gbc.gridy = 11;
        gbc.insets = new Insets(10,10,10,10);
        gbc.weightx = 10;
        add(info, gbc);
    }
}
