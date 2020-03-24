import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Write a description of class Load here.
 *
 * @author (Yudish)
 * @version (0.1)
 */
public class Load extends JFrame
{
    private JPanel main;
    
    private JMenuBar frameMenu;
    
    public Load()
    {
        super("Ricochet Robots | Load");
        //Setting the icon of the frame
        Image rrIcon =  Toolkit.getDefaultToolkit().getImage("Resources/rr.jpg");
        setIconImage(rrIcon);
        //
        menu();
        setJMenuBar(frameMenu);
        settingPanel();
        add(main); //Adding main panel
        setPreferredSize(new Dimension(500, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    //Creating load panel
    private void settingPanel()
    {
        //Main panel of the frame
        main = new JPanel(new BorderLayout());
        
        JPanel upPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel loadGame = new JLabel("LOAD GAME");
        upPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Putting Load Game Label on Top Panel
        loadGame.setFont(new Font("Forte", Font.BOLD, 30));
        //loadGame.setAlignmentX(Font.CENTER_BASELINE);
        upPanel.add(loadGame);

        //Adding the Top panel to the fullPanel
        main.add(upPanel, BorderLayout.NORTH);
        
        //Adding scroll pane for saved games
        JPanel centerPanel=new JPanel(new GridLayout(10,1));
    
        //Adding scrollbar to bottom panel
        JScrollPane scroll=new JScrollPane(centerPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //Adding scroll to fullPanel
        main.add(scroll,BorderLayout.CENTER);
    }
    
    //Creating the menu of the load frame
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
