import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.*;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * StartFrame() displays the player configuration frame before a game.
 *
 * @author (Yudish)
 * @version (0.1)
 */
public class StartFrame extends JFrame implements ActionListener
{
    ///Declaring variables to be used
    private JMenuBar frameMenu;
    
    private JButton start;
    public static JRadioButton easy, difficult;
    
    private JPanel mainP1, mainP2, mainP3, mainP4; //layout for each player
    
    private static JCheckBox colorAssist; //Variale for colour assistance options
    
    private static JCheckBox p1, p2, p3, p4; //players
    private static JComboBox p1Type, p2Type, p3Type, p4Type; //player's type
    
    private static JComboBox c1Type, c2Type, c3Type, c4Type; //computer's difficulty type
    
    private JFrame info; //frame for message alert box
    
    GridBagConstraints gbc = new GridBagConstraints();
    static String[] playerType = { "Human", "Computer"};
    static String[] compType = { "Easy", "Hard"};
    
    //
    private static ArrayList playerData = new ArrayList<>();
    //
    
    public StartFrame()
    {
        super("Ricochet Robots | Configure");
        createMenu();
        setJMenuBar(frameMenu);
        addDetails();
        addDetailsToPlayers();
        playerType();
        computerDif();
        defaultSelect();
        setPreferredSize(new Dimension(500, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    //Creating the menu bar with options
    private void createMenu()
    {
        //Creating the menu bar
        frameMenu = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu about = new JMenu("About");
        frameMenu.add(file);
        frameMenu.add(about);
        
        JMenuItem menu = new JMenuItem("Menu");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem game = new JMenuItem("Game");
        file.add(menu);
        file.add(load);
        file.add(exit);
        about.add(game);
        
        //Going to the menu frame
        menu.addActionListener((ActionEvent ev) -> {dispose();new MainFrame();});
        //Going to the load frame
        load.addActionListener((ActionEvent ev) -> {dispose();new Load();});
        //Exiting the frame
        exit.addActionListener((ActionEvent ev) -> {System.exit(0);});
        //About the game
        game.addActionListener((ActionEvent ev) -> {dispose();new About();});
    }
    
    private void addDetails()
    {
        //Setting the frame icon
        Image rrIcon =  Toolkit.getDefaultToolkit().getImage("Resources/rr.jpg");
        setIconImage(rrIcon);
        
        //frame layout
        setLayout(new BorderLayout());
        
        //Adding panel to center of frame layout
        JPanel main = new JPanel(new GridLayout(2,2,5,5));
        main.setBorder(BorderFactory.createTitledBorder("NEW GAME"));
        add(main, BorderLayout.CENTER);
        
        //Adding panel to bottom of frame layout
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //sub bottom panel
        JPanel startP = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel difficultyP = new JPanel(new BorderLayout(5,5));
        bottom.add(startP, BorderLayout.LINE_START);
        bottom.add(difficultyP, BorderLayout.LINE_END);
        add(bottom, BorderLayout.PAGE_END);
        
        //Creating selection for the players
        mainP1 = new JPanel(new GridBagLayout());
        mainP1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel labelP1 = new JLabel("PLAYER 1", JLabel.CENTER);
        labelP1.setFont(new Font("Verdana", Font.BOLD, 14));
        mainP1.add(labelP1);
        main.add(mainP1);
        
        mainP2 = new JPanel(new GridBagLayout());
        mainP2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel labelP2 = new JLabel("PLAYER 2", JLabel.CENTER);
        labelP2.setFont(new Font("Verdana", Font.BOLD, 14));
        mainP2.add(labelP2);
        main.add(mainP2);
        
        mainP3 = new JPanel(new GridBagLayout());
        mainP3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel labelP3 = new JLabel("PLAYER 3", JLabel.CENTER);
        labelP3.setFont(new Font("Verdana", Font.BOLD, 14));
        mainP3.add(labelP3);
        main.add(mainP3);
        
        mainP4 = new JPanel(new GridBagLayout());
        mainP4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel labelP4 = new JLabel("PLAYER 4", JLabel.CENTER);
        labelP4.setFont(new Font("Verdana", Font.BOLD, 14));
        mainP4.add(labelP4);
        main.add(mainP4);
        //
        
        //Adding element to bottom of frame
        start = new JButton("START");
        start.setPreferredSize(new Dimension(230,58));
        startP.add(start);
        start.addActionListener(this);
        
        //Map difficulty selection
        JLabel diffTitle = new JLabel("MAP TYPE:");
        diffTitle.setHorizontalAlignment(JLabel.CENTER);
        diffTitle.setVerticalAlignment(JLabel.CENTER);
        difficultyP.add(diffTitle, BorderLayout.NORTH);
        easy = new JRadioButton("SIMPLE");
        difficult = new JRadioButton("COMPLEX");
        
        //Grouping the radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(easy);
        group.add(difficult);
        
        //Adding radiobuttons to difficultyP panel
        difficultyP.add(easy, BorderLayout.WEST);
        difficultyP.add(difficult, BorderLayout.EAST);
        
        //Registering a listener
        easy.addActionListener(this);
        difficult.addActionListener(this);
        
        //Colour vision assistance
        colorAssist = new JCheckBox("ENABLE  VISION  ASSIST ");
        colorAssist.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        colorAssist.setBorderPainted(true);
        colorAssist.addActionListener(this);
        colorAssist.setSelected(false);
        difficultyP.add(colorAssist, BorderLayout.SOUTH);
    }
    
    private void addDetailsToPlayers()
    {
        //for player 1
        p1 = new JCheckBox("SELECT  PLAYER  1");
        p1.setSelected(false);
        p1.addActionListener(this);
        p1.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainP1.add(p1, gbc);
        
        //for player 2
        p2 = new JCheckBox("SELECT  PLAYER  2");
        p2.setSelected(false);
        p2.addActionListener(this);
        p2.setFocusPainted(false);
        mainP2.add(p2, gbc);
        
        //for player 1
        p3 = new JCheckBox("SELECT  PLAYER  3");
        p3.setSelected(false);
        p3.addActionListener(this);
        p3.setFocusPainted(false);
        mainP3.add(p3, gbc);
        
        //for player 1
        p4 = new JCheckBox("SELECT  PLAYER  4");
        p4.setSelected(false);
        p4.addActionListener(this);
        p4.setFocusPainted(false);
        mainP4.add(p4, gbc);
    }
    
    private void playerType()
    {
        //ComboBox for player 1
        p1Type = new JComboBox(playerType);
        p1Type.setSelectedIndex(0);
        p1Type.addActionListener(this);
        p1Type.setEnabled(false);
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainP1.add(p1Type, gbc);
        
        //ComboBox for player 2
        p2Type = new JComboBox(playerType);
        p2Type.setSelectedIndex(0);
        p2Type.addActionListener(this);
        p2Type.setEnabled(false);
        mainP2.add(p2Type, gbc);
        
        //ComboBox for player 3
        p3Type = new JComboBox(playerType);
        p3Type.setSelectedIndex(1);
        p3Type.addActionListener(this);
        p3Type.setEnabled(false);
        mainP3.add(p3Type, gbc);
        
        //ComboBox for player 4
        p4Type = new JComboBox(playerType);
        p4Type.setSelectedIndex(1);
        p4Type.addActionListener(this);
        p4Type.setEnabled(false);
        mainP4.add(p4Type, gbc);
    }
    
    //To create computer difficulty box
    private void computerDif()
    {
        //ComboBox for computer 1
        c1Type = new JComboBox(compType);
        c1Type.setSelectedIndex(0);
        c1Type.addActionListener(this);
        c1Type.setVisible(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainP1.add(c1Type, gbc);
        
        //ComboBox for computer 2
        c2Type = new JComboBox(compType);
        c2Type.setSelectedIndex(0);
        c2Type.addActionListener(this);
        c2Type.setVisible(false);
        mainP2.add(c2Type, gbc);
        
        //ComboBox for computer 3
        c3Type = new JComboBox(compType);
        c3Type.setSelectedIndex(0);
        c3Type.addActionListener(this);
        c3Type.setVisible(false);
        mainP3.add(c3Type, gbc);
        
        //ComboBox for computer 4
        c4Type = new JComboBox(compType);
        c4Type.setSelectedIndex(0);
        c4Type.addActionListener(this);
        c4Type.setVisible(false);
        mainP4.add(c4Type, gbc);
    }
    
    private void defaultSelect()
    {
        p1.setSelected(true);
        p1Type.setEnabled(true);
        
        p2.setSelected(true);
        p2Type.setEnabled(true);
    }
    
    //Listeners action
    public void actionPerformed(ActionEvent ev)
    {
        //Catering for color assist
        if (colorAssist.isSelected())
        {
            colorAssist.setSelected(true);
        } else {
            colorAssist.setSelected(false);
        }
        
        //For ComboBox to appear only if a player is selected
        if(p1.isSelected()){           
            p1Type.setEnabled(true);
        } else {
            p1Type.setEnabled(false);
        }
        if(p2.isSelected()){
            p2Type.setEnabled(true);
        } else {
            p2Type.setEnabled(false);
        }
        if(p3.isSelected()){
            p3Type.setEnabled(true);
        } else {
            p3Type.setEnabled(false);
        }
        if(p4.isSelected()){
            p4Type.setEnabled(true);
        } else {
            p4Type.setEnabled(false);
        }
        
        //For ComboBox to appear only if a computer is selected
        //To choose computer difficulty
        if(p1.isSelected() && p1Type.getSelectedItem() == "Computer"){
            c1Type.setVisible(true);
        } else {
            c1Type.setVisible(false);
        }
        if(p2.isSelected() && p2Type.getSelectedItem() == "Computer"){
            c2Type.setVisible(true);
        } else {
            c2Type.setVisible(false);
        }
        if(p3.isSelected() && p3Type.getSelectedItem() == "Computer"){
            c3Type.setVisible(true);
        } else {
            c3Type.setVisible(false);
        }
        if(p4.isSelected() && p4Type.getSelectedItem() == "Computer"){
            c4Type.setVisible(true);
        } else {
            c4Type.setVisible(false);
        }
        
        //Game start logic //Making sure selections are valid
        if (ev.getSource() == start)
        {
            boolean playerTest = true; //Variable to test player selection
            //The following snippet of code ensure that proper number of players is selected
            if (p1.isSelected() == true && p2.isSelected() == false && p3.isSelected() == false && p4.isSelected() == false)
            {
                playerTest = false;
            }
            if (p1.isSelected() == false && p2.isSelected() == true && p3.isSelected() == false && p4.isSelected() == false)
            {
                playerTest = false;
            }
            if (p1.isSelected() == false && p2.isSelected() == false && p3.isSelected() == true && p4.isSelected() == false)
            {
                playerTest = false;
            }
            if (p1.isSelected() == false && p2.isSelected() == false && p3.isSelected() == false && p4.isSelected() == true)
            {
                playerTest = false;
            }
            if (p1.isSelected() == false && p2.isSelected() == false && p3.isSelected() == false && p4.isSelected() == false)
            {
                playerTest = false;
            }          
            
            
            if (playerTest == false)
            {
                info = new JFrame("INFO");
                JOptionPane.showMessageDialog(info,"Select at least 2 players.");
            } else {
                if (easy.isSelected())
                {
                    //The following code snippet ensures that at least one player is human
                    if (p1Type.getSelectedItem()=="Human" || p2Type.getSelectedItem()=="Human" || p3Type.getSelectedItem()=="Human" || p4Type.getSelectedItem()=="Human")
                    {
                        dispose();
                        new SimpleMap();
                    } else {
                        info = new JFrame("INFO");
                        JOptionPane.showMessageDialog(info,"Select at least 1 Human Player.");
                    }
                } else {
                    if (difficult.isSelected())
                    {
                        /*
                        //TO DO
                        info = new JFrame("NOTE");
                        JOptionPane.showMessageDialog(info,"Complex Map to be implemented later." +
                                                      " Not planned for this iteration.");
                        */
                        /*
                        dispose();
                        new ComplexMap();
                        */
                        if (p1Type.getSelectedItem()=="Human" || p2Type.getSelectedItem()=="Human" || p3Type.getSelectedItem()=="Human" || p4Type.getSelectedItem()=="Human")
                        {
                            dispose();
                            new ComplexMap();
                        } else {
                            info = new JFrame("INFO");
                            JOptionPane.showMessageDialog(info,"Select at least 1 Human Player.");
                        }
                    }
                    if (easy.isSelected() == false && difficult.isSelected() == false)
                    {
                        info = new JFrame("INFO");
                        JOptionPane.showMessageDialog(info,"Select a map type at bottom right.");
                    }
                }
            }
        }
    }
    
    //get the value for colour assistance
    public static boolean getColourAssist()
    {
        return colorAssist.isSelected();
    }
    
    //Adding additional methods for map class
    public static ArrayList getPlayerData()
    {
        playerData.clear();
        playerData.add(p1.isSelected());
        playerData.add(p1Type.getSelectedItem());
        playerData.add(c1Type.getSelectedItem());
        
        playerData.add(p2.isSelected());
        playerData.add(p2Type.getSelectedItem());
        playerData.add(c2Type.getSelectedItem());
        
        playerData.add(p3.isSelected());
        playerData.add(p3Type.getSelectedItem());
        playerData.add(c3Type.getSelectedItem());
        
        playerData.add(p4.isSelected());
        playerData.add(p4Type.getSelectedItem());
        playerData.add(c4Type.getSelectedItem());
        
        return playerData;
    }
    
    public static void saveData()
    {
        //Which players are selected
        boolean p1Selected = p1.isSelected();
        boolean p2Selected = p2.isSelected();
        boolean p3Selected = p3.isSelected();
        boolean p4Selected = p4.isSelected();
        
        //Player types
        int ptypeInt1 = p1Type.getSelectedIndex();
        String player1Type = playerType[ptypeInt1];
        int ptypeInt2 = p2Type.getSelectedIndex();
        String player2Type = playerType[ptypeInt2];
        int ptypeInt3 = p3Type.getSelectedIndex();
        String player3Type = playerType[ptypeInt3];
        int ptypeInt4 = p4Type.getSelectedIndex();
        String player4Type = playerType[ptypeInt4];
        
        //Computer types
        int ctypeInt1 = c1Type.getSelectedIndex();
        String comp1Type = compType[ctypeInt1];
        int ctypeInt2 = c2Type.getSelectedIndex();
        String comp2Type = compType[ctypeInt2];
        int ctypeInt3 = c3Type.getSelectedIndex();
        String comp3Type = compType[ctypeInt3];
        int ctypeInt4 = c4Type.getSelectedIndex();
        String comp4Type = compType[ctypeInt4];
        
        //Colour assist selected
        boolean colourAssistSelected = colorAssist.isSelected();
        
        //Simple map or complex map selected
        boolean simpleMapSelected = easy.isSelected();
        boolean complexMapSelected = difficult.isSelected();
        
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("saveFile.txt"));
            
            bw.write(""+simpleMapSelected);
            bw.newLine();
            
            bw.write(""+colourAssistSelected);
            bw.newLine();
            
            bw.write(""+p1Selected);
            bw.newLine();
            bw.write(""+p2Selected);
            bw.newLine();
            bw.write(""+p3Selected);
            bw.newLine();
            bw.write(""+p4Selected);
            bw.newLine();
            
            bw.write(player1Type);
            bw.newLine();
            bw.write(player2Type);
            bw.newLine();
            bw.write(player3Type);
            bw.newLine();
            bw.write(player4Type);
            bw.newLine();
            
            bw.write(comp1Type);
            bw.newLine();
            bw.write(comp2Type);
            bw.newLine();
            bw.write(comp3Type);
            bw.newLine();
            bw.write(comp4Type);
            bw.newLine();
            
            // bw.write(""+complexMapSelected);
            // bw.newLine();
            
            bw.close();
        }
        catch(Exception e){
            
        }
    }
}
