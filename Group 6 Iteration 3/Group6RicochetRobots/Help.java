import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Write a description of class Help here.
 *
 * @author (Yudish)
 * @version (0.1)
 */
public class Help extends JFrame
{
    private JMenuBar frameMenu;
    
    private JLabel info, info2;
    GridBagConstraints gbc = new GridBagConstraints();
    
    public Help()
    {
        super("Ricochet Robots | Help");
        //Setting the icon of the frame
        Image rrIcon =  Toolkit.getDefaultToolkit().getImage("Resources/rr.jpg");
        setIconImage(rrIcon);
        //
        menu();
        setJMenuBar(frameMenu);
        setPreferredSize(new Dimension(500, 500));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
        helpDetails();
        //
        // JPanel panel = new JPanel();
        // getContentPane().setLayout(new BorderLayout());
        // getContentPane().add(panel, BorderLayout.CENTER);
        
        // JLabel label1 = new JLabel("How to Move a Robot: ");
        // JLabel label2 = new JLabel("1. Click on the robot you want to move to select it.");
        // JLabel label3 = new JLabel("2. Click on the square right next to the square that the robot is currently on in the direction you want to move.");
        // JLabel label4 = new JLabel("For example, if you want to move right, click on the square to the right of the robot.");
        // JLabel label5 = new JLabel("3. Click on the robot again to deselect it when you are done moving it.");
        // JLabel label6 = new JLabel("NOTE: Wait for 1-2 seconds between moves to allow it to finish the previous move.");
        // panel.add(label1);
        // panel.add(label2);
        // panel.add(label3);
        // panel.add(label4);
        // panel.add(label5);
        // panel.add(label6);
        //
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    //Creating the menu of the help frame
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
    
    private void helpDetails()
    {
        setLayout(new GridBagLayout());
        

        gbc.gridx = 10;
        gbc.gridy = 10;
        
        
        //How to move a robot instructions
        info = new JLabel("<html><p> <b>How to Move a Robot: </b></p>" +
               "<p> 1. Click on the robot you want to move to select it. </p>" + 
               "<p> 2. Click on the square right next to the square that the robot is currently on in the direction you want to move. For example, if you want to move right, click on the square to the right of the robot. </p>" +
               "<p> 3. Click on the robot again to deselect it when you are done moving it. </p>" +
               "<p> <b>NOTE:</b> Wait for 1-2 seconds between moves to allow it to finish the previous move.</p></html>", JLabel.CENTER);
               
        info2 = new JLabel("<html><p> <b>How to Demonstrate a Solution: </b></p>" +
                "<p> 1. Click on one of the player buttons to select which player you are. </p>" +
                "<p> 2. Enter your bid in the text box and press the Bid button to enter it. </p>" +
                "<p> 3. Wait for the timer to run out. </p>" +
                "<p> 4. Move the robots in order to get the robot whose colour matches the colour of the chosen target space to the chosen target space. </p>" +
                "<p> <b>NOTE:</b> In order for the solution to be successful, the number of moves you make must be less than or equal to the bid entered and the robot must ricochet at least once. </p></html>", JLabel.CENTER);
               
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 10;
        gbc.gridy = 10;
        gbc.insets = new Insets(10,10,10,10);
        gbc.weightx = 10;
        add(info, gbc);
        
        gbc.gridy = 12;
        add(info2, gbc);
    }
}
