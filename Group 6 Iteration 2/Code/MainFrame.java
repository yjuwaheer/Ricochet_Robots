import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * MainFrame() which displays the first window frame
 *
 * @author (Yudish)
 * @version (0.1)
 */
public class MainFrame extends JFrame
{
    private JButton loadGame;
    private JButton newGame;
    private JButton stats;
    private JButton help;
    
    private JMenuBar frameMenu;
    
    public MainFrame(){
        super("Ricochet Robots");
        menu();
        setJMenuBar(frameMenu);
        createButtons();
        formatting();
        buttonPressed();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
    //Creating the menu of the start frame
    private void menu()
    {
        //Creating the menu bar
        frameMenu = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu about = new JMenu("About");
        frameMenu.add(file);
        frameMenu.add(about);
        
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem game = new JMenuItem("Game");
        file.add(exit);
        about.add(game);
        
        //Exiting the frame
        exit.addActionListener((ActionEvent ev) -> {System.exit(0);});
        //About the game
        game.addActionListener((ActionEvent ev) -> {dispose();new About();});
    }
    
    //Method to create buttons and assign their icon image
    private void createButtons()
    {
        //Retrieving and formatting button icons
        Icon newIcon = new ImageIcon(((new ImageIcon("Resources/start.png")).getImage()).getScaledInstance(150,80,java.awt.Image.SCALE_SMOOTH));
        Icon loadIcon = new ImageIcon(((new ImageIcon("Resources/load.png")).getImage()).getScaledInstance(150,80,java.awt.Image.SCALE_SMOOTH));
        Icon statIcon = new ImageIcon(((new ImageIcon("Resources/stats.png")).getImage()).getScaledInstance(150,80,java.awt.Image.SCALE_SMOOTH));  
        Icon helpIcon = new ImageIcon(((new ImageIcon("Resources/help.png")).getImage()).getScaledInstance(150,80,java.awt.Image.SCALE_SMOOTH));
        
        //Creating the required buttons
        newGame = new JButton(newIcon);
        newGame.setBorder(new LineBorder(Color.BLACK));
        loadGame = new JButton(loadIcon);
        loadGame.setBorder(new LineBorder(Color.BLACK));
        stats = new JButton(statIcon);
        stats.setBorder(new LineBorder(Color.BLACK));
        help = new JButton(helpIcon);
        help.setBorder(new LineBorder(Color.BLACK));
        
        //Adjusting the button size
        newGame.setPreferredSize(new Dimension(150,150));
        loadGame.setPreferredSize(new Dimension(150,150));
        stats.setPreferredSize(new Dimension(150,150));
        help.setPreferredSize(new Dimension(150,150));
    }
    
    //Partitioning the frame into different layouts and adjusting each layout
    //to accomodate created buttons
    private void formatting()
    {        
        //Icons and formating
        Image rrIcon =  Toolkit.getDefaultToolkit().getImage("Resources/rr.jpg");
        
        //Setting the icon of the frame
        setIconImage(rrIcon);
        
        //Basic placement layout
        setLayout(new BorderLayout());
        
        //Creating title of the game
        JLabel title = new JLabel("RICOCHET ROBOTS", JLabel.CENTER);
        title.setPreferredSize(new Dimension(0,100));
        title.setFont(new Font("Forte", Font.BOLD, 40));
        
        //Creating Jpanel for each section Border Layout
        JPanel centerLayout = new JPanel(new GridLayout(2,2));
        JPanel belowLayout = new JPanel(new GridLayout(0,2));
        JPanel leftLayout = new JPanel(new GridLayout(0,2));
        JPanel rightLayout = new JPanel(new GridLayout(0,2));
        
        //Adding the created buttons to the center layout
        centerLayout.add(newGame);
        centerLayout.add(loadGame);
        centerLayout.add(stats);
        centerLayout.add(help);
        
        //Setting the border for bottom, left and right layout
        belowLayout.setBorder(BorderFactory.createEmptyBorder(100,100,50,50));
        leftLayout.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        rightLayout.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        
        //Button tips at bottom of frame
        JLabel buttonTips = new JLabel("<html>|START - to start a new game<br/>|LOAD - brings up saved game" +
                                        "<br/>|STATS - to view highscores<br/>|HELP - bring up rules " +
                                        "and interface directions</html>", JLabel.CENTER);
        buttonTips.setPreferredSize(new Dimension(0,100));
        buttonTips.setFont(new Font("Serif", Font.BOLD, 14));
        
        add(buttonTips, BorderLayout.PAGE_END);
        
        add(title, BorderLayout.PAGE_START); //Adding title to frame border layout
        add(centerLayout, BorderLayout.CENTER); //Adding created layouts to basic layout
        add(leftLayout, BorderLayout.LINE_START);
        add(rightLayout, BorderLayout.LINE_END);
    }
    
    //Actions to be taken when a button is pressed
    private void buttonPressed()
    {
        newGame.addActionListener((ActionEvent ev) ->
            {
                dispose();
                new StartFrame();
            }
        );
        
        loadGame.addActionListener((ActionEvent ev)->
            {
                dispose();
                new Load();
            }
        );
        
        stats.addActionListener((ActionEvent ev)->
            {
                dispose();
                new Stats();
            }
        );
        
        help.addActionListener((ActionEvent ev)->
            {
                dispose();
                new Help();
            }
        );
    }
}
