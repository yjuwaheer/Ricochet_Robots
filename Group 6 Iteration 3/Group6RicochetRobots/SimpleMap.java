import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;
import java.util.*;
import javax.swing.border.Border;
import javax.swing.border.*;
import javax.swing.Timer;

/**
 * Sets up the simple board complete with barriers and target spaces.
 *
 * @author (Yudish; Brianna, Faisal)
 * @version (0.1)
 */
public class SimpleMap extends JFrame implements ActionListener, MouseListener
{
    private JMenuBar frameMenu;
    
    private JPanel panel;
    private static BoardSquare[][] boardSquares;
    private int NUM_OF_SQUARES = 16;
    
    //Robots to be placed on boards
    private int num_of_robots; //Number of robots
    private static Robot redRobot = new Robot();
    private static Robot blueRobot = new Robot();
    private static Robot yellowRobot = new Robot();
    private static Robot greenRobot = new Robot();
    
    //Declaring the icons
    public static Icon basicSquareIcon;
    public static Icon rightSideBarrier;
    public static Icon leftSideBarrier;
    public static Icon topSideBarrier;
    public static Icon bottomSideBarrier;

    private static Icon redCircle;
    private static Icon redSquare;
    private static Icon redTriangle;
    private static Icon redStar;
    private static Icon blueCircle;
    private static Icon blueSquare;
    private static Icon blueTriangle;
    private static Icon blueStar;
    private static Icon yellowCircle;
    private static Icon yellowSquare;
    private static Icon yellowTriangle;
    private static Icon yellowStar;
    private static Icon greenCircle;
    private static Icon greenSquare;
    private static Icon greenTriangle;
    private static Icon greenStar;
    private static Icon multivortex;
        
    //Maps the type of target square to the icon
    public static HashMap<String, Icon> targetMap = new HashMap<>();
    
    ///For iteration 3
    private static JPanel eastPanel;
    private static GridBagConstraints gbc = new GridBagConstraints();
    private static JPanel mainEast;
    private static int xPoint, yPoint;
    //For current target
    private static Icon currentTarget;
    //For the players to act
    private static JButton[] playerButtons = new JButton[4];
    private static JButton previous = null; //For player correct colour
    //private static int currentPlayer; //To keep track of current player
    private static JButton previousSelectedPlayer; //When re-bidding moves
    ///For player scores
    private static JLabel[] playerScores = new JLabel[4];
    public static int[] scores = new int[]{0,0,0,0};
    //For bidding
    private static JTextField bidField,bid;
    private JButton bidButton;
    private static int bidValue = 0;
    //For time
    private static JLabel time;
    private static int start = 60;
    private static Timer timer;
    private static String timeExh = ""; //To know when time has been exhausted
    //For current turn and solution
    private static Turn currentTurn;
    private static JButton targetIcon;
    private static Boolean stillCurrentTarget = false;
    //For current robot selected
    private static Robot currentRobot;
    //To return to original positon if demonstration fails
    private static int redOriginalX, blueOriginalX, yellowOriginalX, greenOriginalX;
    private static int redOriginalY, blueOriginalY, yellowOriginalY, greenOriginalY;
    private static Boolean currentSolved = false;
    private static Robot redORobot, blueORobot, yellowORobot, greenORobot;
    private static Boolean redToken = true, blueToken = true, yellowToken = true, greenToken = true;
    ///
    
    public SimpleMap()
    {
        super("Ricochet Robots | Simple Map");
        //Setting the icon of the frame
        Image rrIcon =  Toolkit.getDefaultToolkit().getImage("Resources/rr.jpg");
        setIconImage(rrIcon);
        
        //Sets the number of robots
        this.num_of_robots = num_of_robots;
        
        menu();
        setJMenuBar(frameMenu);
        
        //Method to distinguish between basic or vision assist ressources
        colorAssistance();
        
        ///Setting robots colour
        redRobot.setColour("red");
        blueRobot.setColour("blue");
        yellowRobot.setColour("yellow");
        greenRobot.setColour("green");
        ///
        
        //Setting up the main squares on the board
        panel = new JPanel();
        panel.setLayout(new GridLayout(NUM_OF_SQUARES, NUM_OF_SQUARES));
        
        boardSquares = new BoardSquare[NUM_OF_SQUARES][NUM_OF_SQUARES];
        for(int column = 0; column < NUM_OF_SQUARES; column++)
        {
            for(int row = 0; row < NUM_OF_SQUARES; row++)
            {
                boardSquares[column][row] = new BoardSquare();
                boardSquares[column][row].setXCoord(column);
                boardSquares[column][row].setYCoord(row);
                boardSquares[column][row].setIcon(basicSquareIcon);
                boardSquares[column][row].setSize(45, 45);
                boardSquares[column][row].setOpaque(true);             
                boardSquares[column][row].setBorderPainted(false);
                boardSquares[column][row].setBorder(BorderFactory.createEmptyBorder());
                boardSquares[column][row].setBackground(Color.BLACK); 
                boardSquares[column][row].setTargetSpace(null);
                ///
                boardSquares[column][row].setSquareColour("");
                ///
                boardSquares[column][row].addActionListener(this);
                
                boardSquares[column][row].addMouseListener(this);
                boardSquares[column][row].sendIcon(basicSquareIcon);
                
                panel.add(boardSquares[column][row]);
            }
        }
        
        setUpBarrierSquares();
        
        setUpTargetSpaces();
        
        setEdgeSquaresAsBarriers();
        
        setUpMiddleSquares();
        
        setRobot();
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        
        ///Creating the east panel
        eastPanel = new JPanel();
        setUpEastLayout();
        getContentPane().add(eastPanel, BorderLayout.EAST);
        ///
        
        
        ///setPreferredSize(new Dimension(800, 800));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
    public void setUpEastLayout()
    {
        gbc = new GridBagConstraints();
        mainEast = new JPanel(new GridBagLayout());
        
        xPoint = 0;
        yPoint = 0;
        //Create current target
        if (stillCurrentTarget)
        {
            //Do nothing
        }else {
            getCurrentTarget();
        }
        //Layering the selected players
        int num = 0;
        int numPlayer = 1;
        JLabel players = new JLabel("SELECT PLAYER:", JLabel.CENTER);
        players.setFont(new Font("Verdana", Font.BOLD, 14));
        gbc.insets = new Insets(20,0,0,0);
        ///
        gbc.gridx = xPoint;
        ///
        gbc.gridy = yPoint++;
        mainEast.add(players, gbc);
        yPoint++;
        for (int i = 0; i < 10; i = i + 3)
        {
            if ((Boolean)StartFrame.getPlayerData().get(i))
            {
                playerButtons[num] = new JButton("PLAYER"+ numPlayer);
                playerButtons[num].addActionListener(this);
                gbc.gridx = xPoint;
                gbc.gridy = yPoint;
                gbc.insets = new Insets(10,0,0,0);
                yPoint++;
                mainEast.add(playerButtons[num],gbc);
                num++;
                numPlayer++;
            }
        }
        
        //Bidding options
        JLabel bidField = new JLabel("PLACE BID:",JLabel.CENTER);
        bidField.setFont(new Font("Verdana", Font.BOLD, 14));
        gbc.gridy = yPoint++;
        gbc.insets = new Insets(25,0,0,0);
        mainEast.add(bidField, gbc);
        bid =  new JTextField(10);
        bid.addActionListener(this);
        bid.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = yPoint;
        gbc.insets = new Insets(5,0,0,0);
        mainEast.add(bid, gbc);
        //Button
        bidButton = new JButton("Bid");
        bidButton.setPreferredSize(new Dimension(55,30));
        bidButton.addActionListener(this);
        bidButton.setEnabled(false);
        gbc.gridx = 1;
        gbc.gridy = yPoint++;
        gbc.insets = new Insets(10,10,0,0);
        mainEast.add(bidButton, gbc);
        
        
        //Timer options
        JLabel timer = new JLabel("TIMER:", JLabel.CENTER);
        timer.setFont(new Font("Verdana", Font.BOLD, 14));
        gbc.insets = new Insets(10,0,0,0);
        gbc.gridx = 0;
        gbc.gridy = yPoint++;
        mainEast.add(timer, gbc);
        //Displaying the time
        time = new JLabel("Time Left: 60");
        time.setFont(new Font("Verdana", Font.BOLD, 14));
        time.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //gbc.insets = new Insets(0,0,0,0);
        gbc.gridx = 0;
        gbc.gridy = yPoint++;
        mainEast.add(time, gbc);
        
        ///Scores for the current player
        num = 0;
        numPlayer = 1;
        JLabel playerSco = new JLabel("PLAYER SCORES:", JLabel.CENTER);
        playerSco.setFont(new Font("Verdana", Font.BOLD, 14));
        gbc.insets = new Insets(20,0,0,0);
        gbc.gridx = xPoint;
        gbc.gridy = yPoint++;
        mainEast.add(playerSco, gbc);
        yPoint++;
        ///Initialising the labels
        for (int i = 0; i < 10; i = i + 3)
        {
            if ((Boolean)StartFrame.getPlayerData().get(i))
            {
                playerScores[num] = new JLabel("Player " + numPlayer + ": " + scores[num]);
                playerScores[num].setFont(new Font("Harrington", Font.BOLD, 17));
                gbc.gridx = xPoint;
                gbc.gridy = yPoint;
                gbc.insets = new Insets(10,0,0,0);
                yPoint++;
                mainEast.add(playerScores[num],gbc);
                num++;
                numPlayer++;
            }
        }
        
        eastPanel.add(mainEast);
    }
    
    
    //Method to reset east layout
    public static void resetEastLayout()
    {
        if (getTimeExh().equals("exhausted"))
        {
            int num = 0;
            for (int i = 0; i < 10; i = i + 3)
            {
                if ((Boolean)StartFrame.getPlayerData().get(i))
                {
                    playerButtons[num].setBackground(null);
                }
            }
            bid.setText("");
        }
        
        //For resetting the bid value
        resetBidding();
        
        if(currentSolved == true)
        {
            setNewOriginalPositions();
        }
        //Resetting the robots location
        else
        {
            //System.out.println(redORobot.getColour());
            //System.out.println(redORobot.getrLocx());
            //System.out.println(redORobot.getrLocy());
            //System.out.println(redOriginalX);
            //System.out.println(redOriginalY);
            
            ///For red robot
            if (redORobot != null)
            {
                boardSquares[redORobot.getrLocx()][redORobot.getrLocy()].setIcon(Robot.findIcon(redORobot.getrLocx(),redORobot.getrLocy()));
                boardSquares[redORobot.getrLocx()][redORobot.getrLocy()].changeRobotOnSquare();
            
                Robot.setrLocx(redORobot, redOriginalX);
                Robot.setrLocy(redORobot, redOriginalY);
            
                boardSquares[redOriginalX][redOriginalY].setIcon(redORobot.getIcon());
                boardSquares[redOriginalX][redOriginalY].changeRobotOnSquare();
            }
            
            ///For blue robot
            if (blueORobot != null)
            {
                boardSquares[blueORobot.getrLocx()][blueORobot.getrLocy()].setIcon(Robot.findIcon(blueORobot.getrLocx(),blueORobot.getrLocy()));
                boardSquares[blueORobot.getrLocx()][blueORobot.getrLocy()].changeRobotOnSquare();
            
                Robot.setrLocx(blueORobot, blueOriginalX);
                Robot.setrLocy(blueORobot, blueOriginalY);
            
                boardSquares[blueOriginalX][blueOriginalY].setIcon(blueORobot.getIcon());
                boardSquares[blueOriginalX][blueOriginalY].changeRobotOnSquare();
            }
        
            ///For yellow robot
            if (yellowORobot != null)
            {
                boardSquares[yellowORobot.getrLocx()][yellowORobot.getrLocy()].setIcon(Robot.findIcon(yellowORobot.getrLocx(),yellowORobot.getrLocy()));
                boardSquares[yellowORobot.getrLocx()][yellowORobot.getrLocy()].changeRobotOnSquare();
            
                Robot.setrLocx(yellowORobot, yellowOriginalX);
                Robot.setrLocy(yellowORobot, yellowOriginalY);
            
                boardSquares[yellowOriginalX][yellowOriginalY].setIcon(yellowORobot.getIcon());
                boardSquares[yellowOriginalX][yellowOriginalY].changeRobotOnSquare();
            }
        
            ///For green robot
            if (greenORobot != null)
            {
                boardSquares[greenORobot.getrLocx()][greenORobot.getrLocy()].setIcon(Robot.findIcon(greenORobot.getrLocx(),greenORobot.getrLocy()));
                boardSquares[greenORobot.getrLocx()][greenORobot.getrLocy()].changeRobotOnSquare();
            
                Robot.setrLocx(greenORobot, greenOriginalX);
                Robot.setrLocy(greenORobot, greenOriginalY);
            
                boardSquares[greenOriginalX][greenOriginalY].setIcon(greenORobot.getIcon());
                boardSquares[greenOriginalX][greenOriginalY].changeRobotOnSquare();
            }
        }
    }
    
    /**
     * Resets the bidValue after a solution has been demonstrated
     * (either successfully or unsuccessfully) and allows the players
     * to enter new bids
     */
    public static void resetBidding()
    {
        bidValue = 0;
        bid.setText("");
        bid.setEditable(true);
        start = 60;
    }
    
    public static void setNewOriginalPositions()
    {
        redOriginalX = redRobot.getrLocx();
        redOriginalY = redRobot.getrLocy();
        
        greenOriginalX = greenRobot.getrLocx();
        greenOriginalY = greenRobot.getrLocy();
        
        yellowOriginalX = yellowRobot.getrLocx();
        yellowOriginalY = yellowRobot.getrLocy();
        
        blueOriginalX = redRobot.getrLocx();
        blueOriginalY = redRobot.getrLocy();
    }
    
    //Method to get the current target
    public static void getCurrentTarget()
    {        
        JLabel target = new JLabel("TARGET:");
        target.setFont(new Font("Verdana", Font.BOLD, 14));
        gbc.insets = new Insets(5,0,0,0);
        gbc.gridx = xPoint;
        gbc.gridy = yPoint++;
        mainEast.add(target, gbc);
        
        //
        currentTurn = new Turn();
        //
        currentTarget = currentTurn.getRandomTarget(); //Selecting a random target icon
        targetIcon = new JButton();
        targetIcon.setIcon(currentTarget);
        targetIcon.setPreferredSize(new Dimension(45,45));
        gbc.gridy = yPoint++;
        mainEast.add(targetIcon, gbc);       
    }
    
    ///Update the current target to the next
    public static void updateCurrentTarget()
    {
        currentTurn = new Turn();
        
        currentTarget = currentTurn.getRandomTarget();
        
        targetIcon.setIcon(currentTarget);
    }
    
    //Method to start the timer
    private void startTheTimer()
    {
        timer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
              start--;
              if(start>=1)
              {   
                  time.setText("Time Left: "+ start); //changing the label of button as the timer decrases
              } else {
                  time.setText("Timeout...");
                  timer.stop();
                  //System.out.println(previous.getText());
                  timeExh = "exhausted";
                  currentTurn.startNewSolution(previous.getText());
              }
        }
       });
       timer.start();
    }
    
    ///Method to get the player scores labes so that they can be updated
    public static JLabel[] getPlayerScoresLabel()
    {
        return playerScores;
    }
    
    ///Method to get the player scores value so that they can be updated
    public static int[] getPlayerScores()
    {
        return scores;
    }
    
    ///To get response if time has been exhausted or not
    public static String getTimeExh()
    {
        return timeExh;
    }
    
    //To get the current turn
    public static Turn getCurrentTurn()
    {
        return currentTurn;
    }
    
    ///To get the most recent bid value
    public static int getBidValue()
    {
        return bidValue;
    }
    
    ///To set if current target still being considered or not
    public static void setStillCurrentTarget(Boolean stillCurrent)
    {
        stillCurrentTarget = stillCurrent;
    }
    
    ///To set the value of the variable currentSolved
    public static void setCurrentSolved(Boolean currently)
    {
        currentSolved = currently;
    }
    
    private void colorAssistance()
    {
        
        if (StartFrame.getColourAssist()){
            basicSquareIcon = new ImageIcon(((new ImageIcon("Resources/Assist/basicSquareAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            rightSideBarrier = new ImageIcon(((new ImageIcon("Resources/Assist/rightSideBarrierAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            leftSideBarrier = new ImageIcon(((new ImageIcon("Resources/Assist/leftSideBarrierAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            topSideBarrier = new ImageIcon(((new ImageIcon("Resources/Assist/topSideBarrierAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            bottomSideBarrier = new ImageIcon(((new ImageIcon("Resources/Assist/bottomSideBarrierAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            redCircle = new ImageIcon(((new ImageIcon("Resources/Assist/redCircleAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            blueCircle = new ImageIcon(((new ImageIcon("Resources/Assist/blueCircleAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            yellowCircle = new ImageIcon(((new ImageIcon("Resources/Assist/yellowCircleAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            greenCircle = new ImageIcon(((new ImageIcon("Resources/Assist/greenCircleAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            redSquare = new ImageIcon(((new ImageIcon("Resources/Assist/redSquareAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            blueSquare = new ImageIcon(((new ImageIcon("Resources/Assist/blueSquareAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            yellowSquare = new ImageIcon(((new ImageIcon("Resources/Assist/yellowSquareAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            greenSquare = new ImageIcon(((new ImageIcon("Resources/Assist/greenSquareAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            redTriangle = new ImageIcon(((new ImageIcon("Resources/Assist/redTriangleAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            blueTriangle = new ImageIcon(((new ImageIcon("Resources/Assist/blueTriangleAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            yellowTriangle = new ImageIcon(((new ImageIcon("Resources/Assist/yellowTriangleAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            greenTriangle = new ImageIcon(((new ImageIcon("Resources/Assist/greenTriangleAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            redStar = new ImageIcon(((new ImageIcon("Resources/Assist/redStarAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            blueStar = new ImageIcon(((new ImageIcon("Resources/Assist/blueStarAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            yellowStar = new ImageIcon(((new ImageIcon("Resources/Assist/yellowStarAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            greenStar = new ImageIcon(((new ImageIcon("Resources/Assist/greenStarAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            multivortex = new ImageIcon(((new ImageIcon("Resources/Assist/multivortexAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            //Robots
            redRobot.setIcon(new ImageIcon("Resources/Assist/redRobotAssist.jpg"));
            blueRobot.setIcon(new ImageIcon("Resources/Assist/blueRobotAssist.jpg"));
            yellowRobot.setIcon(new ImageIcon("Resources/Assist/yellowRobotAssist.jpg"));
            greenRobot.setIcon(new ImageIcon("Resources/Assist/greenRobotAssist.jpg"));
        } else {
            basicSquareIcon = new ImageIcon(((new ImageIcon("Resources/basicSquare.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            rightSideBarrier = new ImageIcon(((new ImageIcon("Resources/rightSideBarrier.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            leftSideBarrier = new ImageIcon(((new ImageIcon("Resources/leftSideBarrier.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            topSideBarrier = new ImageIcon(((new ImageIcon("Resources/topSideBarrier.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            bottomSideBarrier = new ImageIcon(((new ImageIcon("Resources/bottomSideBarrier.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            redCircle = new ImageIcon(((new ImageIcon("Resources/redCircle.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            blueCircle = new ImageIcon(((new ImageIcon("Resources/blueCircle.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            yellowCircle = new ImageIcon(((new ImageIcon("Resources/yellowCircle.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            greenCircle = new ImageIcon(((new ImageIcon("Resources/greenCircle.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            redSquare = new ImageIcon(((new ImageIcon("Resources/redSquare.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            blueSquare = new ImageIcon(((new ImageIcon("Resources/blueSquare.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            yellowSquare = new ImageIcon(((new ImageIcon("Resources/yellowSquare.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            greenSquare = new ImageIcon(((new ImageIcon("Resources/greenSquare.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            redTriangle = new ImageIcon(((new ImageIcon("Resources/redTriangle.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            blueTriangle = new ImageIcon(((new ImageIcon("Resources/blueTriangle.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            yellowTriangle = new ImageIcon(((new ImageIcon("Resources/yellowTriangle.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            greenTriangle = new ImageIcon(((new ImageIcon("Resources/greenTriangle.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            redStar = new ImageIcon(((new ImageIcon("Resources/redStar.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            blueStar = new ImageIcon(((new ImageIcon("Resources/blueStar.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            yellowStar = new ImageIcon(((new ImageIcon("Resources/yellowStar.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            greenStar = new ImageIcon(((new ImageIcon("Resources/greenStar.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            multivortex = new ImageIcon(((new ImageIcon("Resources/multivortex.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
            //Robots
            redRobot.setIcon(new ImageIcon("Resources/redRobot.png"));
            blueRobot.setIcon(new ImageIcon("Resources/blueRobot.png"));
            yellowRobot.setIcon(new ImageIcon("Resources/yellowRobot.png"));
            greenRobot.setIcon(new ImageIcon("Resources/greenRobot.png"));
        }
    }
    
    //Creating the menu of the game frame
    private void menu()
    {
        //Creating the menu bar
        frameMenu = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu help = new JMenu("Help");
        frameMenu.add(file);
        frameMenu.add(help);
        
        JMenuItem menu = new JMenuItem("Menu");
        JMenuItem newG = new JMenuItem("New Game");
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem howto = new JMenuItem("How to?");
        file.add(menu);
        file.add(newG);
        file.add(exit);
        help.add(howto);
        
        //Going to menu frame
        menu.addActionListener((ActionEvent ev) -> {dispose();new MainFrame();});
        //Going to the start a game frame
        newG.addActionListener((ActionEvent ev) -> {dispose();new StartFrame();});
        //Exiting the frame
        exit.addActionListener((ActionEvent ev) -> {System.exit(0);});
        
        //Opening the help window
        howto.addActionListener((ActionEvent ev) -> {new Help();});
    }
    
    /**
     * Setting up the squares with barriers but no target spaces.
     * Sets each square with a barrier to the correct icon and
     * updates the appropriate edge of each square to account 
     * for the barrier.
     */
    private void setUpBarrierSquares()
    {
        boardSquares[15][6].setIcon(rightSideBarrier);
        boardSquares[15][6].setRightEdge(true);
        boardSquares[15][7].setIcon(leftSideBarrier);
        boardSquares[15][7].setLeftEdge(true);
        boardSquares[15][11].setIcon(rightSideBarrier);
        boardSquares[15][11].setRightEdge(true);
        boardSquares[15][12].setIcon(leftSideBarrier);
        boardSquares[15][12].setLeftEdge(true);
        boardSquares[11][15].setIcon(topSideBarrier);
        boardSquares[11][15].setTopEdge(true);
        boardSquares[10][15].setIcon(bottomSideBarrier);
        boardSquares[10][15].setBottomEdge(true);
        boardSquares[3][15].setIcon(bottomSideBarrier);
        boardSquares[3][15].setBottomEdge(true);
        boardSquares[4][15].setIcon(topSideBarrier);
        boardSquares[4][15].setTopEdge(true);
        boardSquares[0][1].setIcon(rightSideBarrier);
        boardSquares[0][1].setRightEdge(true);
        boardSquares[0][2].setIcon(leftSideBarrier);
        boardSquares[0][2].setLeftEdge(true);
        boardSquares[0][11].setIcon(rightSideBarrier);
        boardSquares[0][11].setRightEdge(true);
        boardSquares[0][12].setIcon(leftSideBarrier);
        boardSquares[0][12].setLeftEdge(true);
        boardSquares[6][0].setIcon(topSideBarrier);
        boardSquares[6][0].setTopEdge(true);
        boardSquares[5][0].setIcon(bottomSideBarrier);
        boardSquares[5][0].setBottomEdge(true);
        boardSquares[12][0].setIcon(topSideBarrier);
        boardSquares[12][0].setTopEdge(true);
        boardSquares[11][0].setIcon(bottomSideBarrier);
        boardSquares[11][0].setBottomEdge(true);
    }
    
    /**
     * Setting up the target spaces. Sets each target space to
     * have the correct icon updates the edges to account for
     * the barriers. Also updates the edges of the surrounding
     * squares to account for the barriers.
     */
    private void setUpTargetSpaces()
    {
        //Red Circle
        boardSquares[1][4].setIcon(redCircle);
        boardSquares[1][4].setTopEdge(true);
        boardSquares[1][4].setLeftEdge(true);
        boardSquares[0][4].setBottomEdge(true);
        boardSquares[0][4].setIcon(bottomSideBarrier);
        boardSquares[1][3].setRightEdge(true);
        boardSquares[1][3].setIcon(rightSideBarrier);
        boardSquares[1][4].setTargetSpace("redCircle");
        boardSquares[1][4].sendIcon(redCircle);
        ///
        boardSquares[1][4].setSquareColour("red");
        ///
        targetMap.put("redCircle", redCircle);
        
        //Red Square
        boardSquares[1][13].setIcon(redSquare);
        boardSquares[1][13].setTopEdge(true);
        boardSquares[1][13].setLeftEdge(true);
        boardSquares[0][13].setBottomEdge(true);
        boardSquares[0][13].setIcon(bottomSideBarrier);
        boardSquares[1][12].setRightEdge(true);
        boardSquares[1][12].setIcon(rightSideBarrier);
        boardSquares[1][13].setTargetSpace("redSquare");
        boardSquares[1][13].sendIcon(redSquare);
        ///
        boardSquares[1][13].setSquareColour("red");
        ///
        targetMap.put("redSquare", redSquare);
        
        //Green Triangle
        boardSquares[2][1].setIcon(greenTriangle);
        boardSquares[2][1].setTopEdge(true);
        boardSquares[2][1].setRightEdge(true);
        boardSquares[1][1].setBottomEdge(true);
        boardSquares[1][1].setIcon(bottomSideBarrier);
        boardSquares[2][2].setLeftEdge(true);
        boardSquares[2][2].setIcon(leftSideBarrier);
        boardSquares[2][1].setTargetSpace("greenTriangle");
        boardSquares[2][1].sendIcon(greenTriangle);
        ///
        boardSquares[2][1].setSquareColour("green");
        ///
        targetMap.put("greenTriangle", greenTriangle);
        
        //Blue Triangle
        boardSquares[2][9].setIcon(blueTriangle);
        boardSquares[2][9].setBottomEdge(true);
        boardSquares[2][9].setRightEdge(true);
        boardSquares[3][9].setTopEdge(true);
        boardSquares[3][9].setIcon(topSideBarrier);
        boardSquares[2][10].setLeftEdge(true);
        boardSquares[2][10].setIcon(leftSideBarrier);
        boardSquares[2][9].setTargetSpace("blueTriangle");
        boardSquares[2][9].sendIcon(blueTriangle);
        ///
        boardSquares[2][9].setSquareColour("blue");
        ///
        targetMap.put("blueTriangle", blueTriangle);
        
        //Yellow Star
        boardSquares[3][6].setIcon(yellowStar);
        boardSquares[3][6].setBottomEdge(true);
        boardSquares[3][6].setRightEdge(true);
        boardSquares[4][6].setTopEdge(true);
        boardSquares[4][6].setIcon(topSideBarrier);
        boardSquares[3][7].setLeftEdge(true);
        boardSquares[3][7].setIcon(leftSideBarrier);
        boardSquares[3][6].setTargetSpace("yellowStar");
        boardSquares[3][6].sendIcon(yellowStar);
        ///
        boardSquares[3][6].setSquareColour("yellow");
        ///
        targetMap.put("yellowStar", yellowStar);
        
        //Green Star
        boardSquares[5][14].setIcon(greenStar);
        boardSquares[5][14].setBottomEdge(true);
        boardSquares[5][14].setLeftEdge(true);
        boardSquares[6][14].setTopEdge(true);
        boardSquares[6][14].setIcon(topSideBarrier);
        boardSquares[5][13].setRightEdge(true);
        boardSquares[5][13].setIcon(rightSideBarrier);
        boardSquares[5][14].setTargetSpace("greenStar");
        boardSquares[5][14].sendIcon(greenStar);
        ///
        boardSquares[5][14].setSquareColour("green");
        ///
        targetMap.put("greenStar", greenStar);
        
        //Blue Square
        boardSquares[6][3].setIcon(blueSquare);
        boardSquares[6][3].setBottomEdge(true);
        boardSquares[6][3].setLeftEdge(true);
        boardSquares[7][3].setTopEdge(true);
        boardSquares[7][3].setIcon(topSideBarrier);
        boardSquares[6][2].setRightEdge(true);
        boardSquares[6][2].setIcon(rightSideBarrier);
        boardSquares[6][3].setTargetSpace("blueSquare");
        boardSquares[6][3].sendIcon(blueSquare);
        ///
        boardSquares[6][3].setSquareColour("blue");
        ///
        targetMap.put("blueSquare", blueSquare);
        
        //Yellow Circle
        boardSquares[6][11].setIcon(yellowCircle);
        boardSquares[6][11].setTopEdge(true);
        boardSquares[6][11].setRightEdge(true);
        boardSquares[5][11].setBottomEdge(true);
        boardSquares[5][11].setIcon(bottomSideBarrier);
        boardSquares[6][12].setLeftEdge(true);
        boardSquares[6][12].setIcon(leftSideBarrier);
        boardSquares[6][11].setTargetSpace("yellowCircle");
        boardSquares[6][11].sendIcon(yellowCircle);
        ///
        boardSquares[6][11].setSquareColour("yellow");
        ///
        targetMap.put("yellowCircle", yellowCircle);
        
        //Multivortex
        boardSquares[8][5].setIcon(multivortex);
        boardSquares[8][5].setTopEdge(true);
        boardSquares[8][5].setRightEdge(true);
        boardSquares[7][5].setBottomEdge(true);
        boardSquares[7][5].setIcon(bottomSideBarrier);
        boardSquares[8][6].setLeftEdge(true);
        boardSquares[8][6].setIcon(leftSideBarrier);
        boardSquares[8][5].setTargetSpace("multivortex");
        boardSquares[8][5].sendIcon(multivortex);
        ///
        boardSquares[8][5].setSquareColour("multi");
        ///
        targetMap.put("multivortex", multivortex);
        
        //Blue Circle
        boardSquares[9][1].setIcon(blueCircle);
        boardSquares[9][1].setBottomEdge(true);
        boardSquares[9][1].setRightEdge(true);
        boardSquares[10][1].setTopEdge(true);
        boardSquares[10][1].setIcon(topSideBarrier);
        boardSquares[9][2].setLeftEdge(true);
        boardSquares[9][2].setIcon(leftSideBarrier);
        boardSquares[9][1].setTargetSpace("blueCircle");
        boardSquares[9][1].sendIcon(blueCircle);
        ///
        boardSquares[9][1].setSquareColour("blue");
        ///
        targetMap.put("blueCircle", blueCircle);
        
        //Yellow Square
        boardSquares[9][14].setIcon(yellowSquare);
        boardSquares[9][14].setBottomEdge(true);
        boardSquares[9][14].setRightEdge(true);
        boardSquares[10][14].setTopEdge(true);
        boardSquares[10][14].setIcon(topSideBarrier);
        boardSquares[9][15].setLeftEdge(true);
        boardSquares[9][15].setIcon(leftSideBarrier);
        boardSquares[9][14].setTargetSpace("yellowSquare");
        boardSquares[9][14].sendIcon(yellowSquare);
        ///
        boardSquares[9][14].setSquareColour("yellow");
        ///
        targetMap.put("yellowSquare", yellowSquare);
        
        //Green Square
        boardSquares[10][4].setIcon(greenSquare);
        boardSquares[10][4].setBottomEdge(true);
        boardSquares[10][4].setLeftEdge(true);
        boardSquares[11][4].setTopEdge(true);
        boardSquares[11][4].setIcon(topSideBarrier);
        boardSquares[10][3].setRightEdge(true);
        boardSquares[10][3].setIcon(rightSideBarrier);
        boardSquares[10][4].setTargetSpace("greenSquare");
        boardSquares[10][4].sendIcon(greenSquare);
        ///
        boardSquares[10][4].setSquareColour("green");
        ///
        targetMap.put("greenSquare", greenSquare);
        
        //Red Triangle
        boardSquares[10][8].setIcon(redTriangle);
        boardSquares[10][8].setTopEdge(true);
        boardSquares[10][8].setRightEdge(true);
        boardSquares[9][8].setBottomEdge(true);
        boardSquares[9][8].setIcon(bottomSideBarrier);
        boardSquares[10][9].setLeftEdge(true);
        boardSquares[10][9].setIcon(leftSideBarrier);
        boardSquares[10][8].setTargetSpace("redTriangle");
        boardSquares[10][8].sendIcon(redTriangle);
        ///
        boardSquares[10][8].setSquareColour("red");
        ///
        targetMap.put("redTriangle", redTriangle);
        
        //Green Circle
        boardSquares[11][13].setIcon(greenCircle);
        boardSquares[11][13].setBottomEdge(true);
        boardSquares[11][13].setLeftEdge(true);
        boardSquares[12][13].setTopEdge(true);
        boardSquares[12][13].setIcon(topSideBarrier);
        boardSquares[11][12].setRightEdge(true);
        boardSquares[11][12].setIcon(rightSideBarrier);
        boardSquares[11][13].setTargetSpace("greenCircle");
        boardSquares[11][13].sendIcon(greenCircle);
        ///
        boardSquares[11][13].setSquareColour("green");
        ///
        targetMap.put("greenCircle", greenCircle);
        
        //Red Star
        boardSquares[13][5].setIcon(redStar);
        boardSquares[13][5].setTopEdge(true);
        boardSquares[13][5].setRightEdge(true);
        boardSquares[12][5].setBottomEdge(true);
        boardSquares[12][5].setIcon(bottomSideBarrier);
        boardSquares[13][6].setLeftEdge(true);
        boardSquares[13][6].setIcon(leftSideBarrier);
        boardSquares[13][5].setTargetSpace("redStar");
        boardSquares[13][5].sendIcon(redStar);
        ///
        boardSquares[13][5].setSquareColour("red");
        ///
        targetMap.put("redStar", redStar);
        
        //Blue Star
        boardSquares[13][10].setIcon(blueStar);
        boardSquares[13][10].setTopEdge(true);
        boardSquares[13][10].setLeftEdge(true);
        boardSquares[12][10].setBottomEdge(true);
        boardSquares[12][10].setIcon(bottomSideBarrier);
        boardSquares[13][9].setRightEdge(true);
        boardSquares[13][9].setIcon(rightSideBarrier);
        boardSquares[13][10].setTargetSpace("blueStar");
        boardSquares[13][10].sendIcon(blueStar);
        ///
        boardSquares[13][10].setSquareColour("blue");
        ///
        targetMap.put("blueStar", blueStar);
        
        //Yellow Triangle
        boardSquares[14][3].setIcon(yellowTriangle);
        boardSquares[14][3].setTopEdge(true);
        boardSquares[14][3].setLeftEdge(true);
        boardSquares[13][3].setBottomEdge(true);
        boardSquares[13][3].setIcon(bottomSideBarrier);
        boardSquares[14][2].setRightEdge(true);
        boardSquares[14][2].setIcon(rightSideBarrier);
        boardSquares[14][3].setTargetSpace("yellowTriangle");
        boardSquares[14][3].sendIcon(yellowTriangle);
        ///
        boardSquares[14][3].setSquareColour("yellow");
        ///
        targetMap.put("yellowTriangle", yellowTriangle);
    }
    
    /**
     * For all the squares around the edges of the board, updates the 
     * appropriate edges of the squares to count as barriers
     */
    private void setEdgeSquaresAsBarriers()
    {
        for(int column = 0; column < NUM_OF_SQUARES; column++)
        {
            boardSquares[column][0].setLeftEdge(true);
        }
        
        for(int column = 0; column < NUM_OF_SQUARES; column++)
        {
            boardSquares[column][15].setRightEdge(true);
        }
        
        for(int row = 0; row < NUM_OF_SQUARES; row++)
        {
            boardSquares[0][row].setTopEdge(true);
        }
        
        for(int row = 0; row < NUM_OF_SQUARES; row++)
        {
            boardSquares[15][row].setBottomEdge(true);
        }
    }
    
    /** 
     * Sets up the four squares in the middle of the board by removing
     * the icons and updating all the edges as barriers. Also updates the
     * edges of the surrounding squares to account for the barriers.
     */
    private void setUpMiddleSquares()
    {
        boardSquares[7][7].setIcon(null);
        boardSquares[7][7].setTopEdge(true);
        boardSquares[7][7].setLeftEdge(true);
        boardSquares[7][7].setBottomEdge(true);
        boardSquares[7][7].setRightEdge(true);
        boardSquares[6][7].setBottomEdge(true);
        boardSquares[7][6].setRightEdge(true);
        
        boardSquares[7][8].setIcon(null);
        boardSquares[7][8].setTopEdge(true);
        boardSquares[7][8].setLeftEdge(true);
        boardSquares[7][8].setBottomEdge(true);
        boardSquares[7][8].setRightEdge(true);
        boardSquares[6][8].setBottomEdge(true);
        boardSquares[7][9].setLeftEdge(true);
        
        boardSquares[8][7].setIcon(null);
        boardSquares[8][7].setTopEdge(true);
        boardSquares[8][7].setLeftEdge(true);
        boardSquares[8][7].setBottomEdge(true);
        boardSquares[8][7].setRightEdge(true);
        boardSquares[8][6].setRightEdge(true);
        boardSquares[9][7].setTopEdge(true);
        
        boardSquares[8][8].setIcon(null);
        boardSquares[8][8].setTopEdge(true);
        boardSquares[8][8].setLeftEdge(true);
        boardSquares[8][8].setBottomEdge(true);
        boardSquares[8][8].setRightEdge(true);
        boardSquares[9][8].setTopEdge(true);
        boardSquares[8][9].setLeftEdge(true);
    }
    
    public void setRobot()
    {           
           int x = redRobot.getrLocx();
           int y = redRobot.getrLocy();
           
           Icon icon = redRobot.getIcon();
           boardSquares[x][y].setIcon(icon);
           boardSquares[x][y].changeRobotOnSquare();

           x = blueRobot.getrLocx();
           y = blueRobot.getrLocy();
           
           Icon icon1 = blueRobot.getIcon();
           boardSquares[x][y].setIcon(icon1);
           boardSquares[x][y].changeRobotOnSquare();
           
           x = yellowRobot.getrLocx();
           y = yellowRobot.getrLocy();           
           
           Icon icon2 = yellowRobot.getIcon();
           boardSquares[x][y].setIcon(icon2);
           boardSquares[x][y].changeRobotOnSquare();
           
           x = greenRobot.getrLocx();
           y = greenRobot.getrLocy();          
           
           Icon icon3 = greenRobot.getIcon();
           boardSquares[x][y].setIcon(icon3);
           boardSquares[x][y].changeRobotOnSquare();
    }
    
    public static void replaceIcon(int x, int y, Icon icon)
    {
        boardSquares[x][y].setIcon(icon);
    }
    
    public void mouseClicked(MouseEvent mevt)
    {
        Object selected = mevt.getSource();
        if(selected instanceof BoardSquare)
        {
            BoardSquare square = ((BoardSquare) selected);
            int x = square.getXCoord();
            int y = square.getYCoord();
            
            
            if (x == redRobot.getrLocx() && y == redRobot.getrLocy())
            {
                redRobot.changeState();
                ///To keep track of the coordinates of original red robot position in case
                ///the current target has not been solved during demonstration.
                if (redToken)
                {
                    if (currentSolved == false)
                    {
                        redORobot = redRobot;
                        redOriginalX = x;
                        redOriginalY = y;
                    }
                    redToken = false;
                }
            }
            
            else if (x == blueRobot.getrLocx() && y == blueRobot.getrLocy())
            {
                blueRobot.changeState();
                ///To keep track of the coordinates of original red robot position in case
                ///the current target has not been solved during demonstration.
                if (blueToken)
                {
                    if (currentSolved == false)
                    {
                        blueORobot = blueRobot;
                        blueOriginalX = x;
                        blueOriginalY = y;
                    }
                    blueToken = false;
                }
            }
            
            else if (x == yellowRobot.getrLocx() && y == yellowRobot.getrLocy())
            {
                yellowRobot.changeState();
                ///To keep track of the coordinates of original red robot position in case
                ///the current target has not been solved during demonstration.
                if (yellowToken)
                {
                    if (currentSolved == false)
                    {
                        yellowORobot = yellowRobot;
                        yellowOriginalX = x;
                        yellowOriginalY = y;
                    }
                    yellowToken = false;
                }
            }
            
            else if (x == greenRobot.getrLocx() && y == greenRobot.getrLocy())
            {
                greenRobot.changeState();
                ///To keep track of the coordinates of original red robot position in case
                ///the current target has not been solved during demonstration.
                if (greenToken)
                {
                    if (currentSolved == false)
                    {
                        greenORobot = greenRobot;
                        greenOriginalX = x;
                        greenOriginalY = y;
                    }
                    greenToken = false;
                }
            }
            
            else if (redRobot.isSelected() && ((x == redRobot.getrLocx() && 
            y != redRobot.getrLocy()) || (x != redRobot.getrLocx() && 
            y == redRobot.getrLocy())))
            {
                currentRobot = redRobot;
                
                redRobot.move(x,y);
                
                //currentRobot = redRobot;
            }
            
            else if (blueRobot.isSelected() && ((x == blueRobot.getrLocx() && 
            y != blueRobot.getrLocy()) || (x != blueRobot.getrLocx() && 
            y == blueRobot.getrLocy())))
            {
                currentRobot = blueRobot;
                
                blueRobot.move(x,y);
                
                //currentRobot = blueRobot;
            }
            
            else if (yellowRobot.isSelected() && ((x == yellowRobot.getrLocx() && 
            y != yellowRobot.getrLocy()) || (x != yellowRobot.getrLocx() && 
            y == yellowRobot.getrLocy())))
            {
                currentRobot = yellowRobot;
                
                yellowRobot.move(x,y);
                
                //currentRobot = yellowRobot;
            }
            
            else if (greenRobot.isSelected() && ((x == greenRobot.getrLocx() && 
            y != greenRobot.getrLocy()) || (x != greenRobot.getrLocx() && 
            y == greenRobot.getrLocy())))
            {
                currentRobot = greenRobot;
                
                greenRobot.move(x,y);
                
                //currentRobot = greenRobot;
            }
            
            ///To keep track of the original robot position in case
            ///the current target has not been solved during demonstration.
            ///Primarily which robot
            /*
            if (currentSolved == false && currentRobot != null)
            {
                redORobot = currentRobot;
                currentSolved = true;
            }
            */
        }
    }
    
    //Return the current selected robot once move countdown has started
    public static Robot getCurrentRobot()
    {
        return currentRobot;
    }
    
    //Return the currently setup boardSquare()
    public static BoardSquare[][] getBoardSquare()
    {
        return boardSquares;
    }
    
    public Robot getRedRobot()
    {
        return redRobot;
    }
    
    public Robot getGreenRobot()
    {
        return greenRobot;
    }
    
    public Robot getYellowRobot()
    {
        return yellowRobot;
    }
    
    public Robot getBlueRobot()
    {
        return blueRobot;
    }
    
    public static BoardSquare getBoardSquare(int x, int y)
    {
        return boardSquares[x][y];
    }
    
    public static HashMap getTargetSpaceMap()
    {
        return targetMap;
    }
    
    public void actionPerformed(ActionEvent aevt)
    {
        //Catering for the current player actions
        if (aevt.getSource() == playerButtons[0])
        {
            bid.setEditable(true);
            bidButton.setEnabled(true);
            if (previous != null)
            {
                previous.setBackground(null);
            }
            playerButtons[0].setBackground(Color.GREEN);
            previousSelectedPlayer = previous;
            previous = playerButtons[0];
        }
        if (aevt.getSource() == playerButtons[1])
        {
            bid.setEditable(true);
            bidButton.setEnabled(true);
            if (previous != null)
            {
                previous.setBackground(null);
            }
            playerButtons[1].setBackground(Color.GREEN);
            previousSelectedPlayer = previous;
            previous = playerButtons[1];
        }
        if (aevt.getSource() == playerButtons[2])
        {
            bid.setEditable(true);
            bidButton.setEnabled(true);
            if (previous != null)
            {
                previous.setBackground(null);
            }
            playerButtons[2].setBackground(Color.GREEN);
            previousSelectedPlayer = previous;
            previous = playerButtons[2];
        }
        if (aevt.getSource() == playerButtons[3])
        {
            bid.setEditable(true);
            bidButton.setEnabled(true);
            if (previous != null)
            {
                previous.setBackground(null);
            }
            playerButtons[3].setBackground(Color.GREEN);
            previousSelectedPlayer = previous;
            previous = playerButtons[3];
        }
        
        
        //Bid field verification
        if (aevt.getSource() == bidButton)
        {
            if (bidValue == 0){
                try{
                    bidValue = Integer.parseInt(bid.getText());
                    if (bidValue < 2 || bidValue > 50)
                    {
                        JFrame info = new JFrame("INFO");
                        JOptionPane.showMessageDialog(info, "Please enter an integer between 2 and 50 inclusive");
                        bidValue = 0;
                        bid.setText("");
                    } else {
                        startTheTimer();
                        bid.setEditable(false);
                        bidButton.setEnabled(false);
                    }
                } catch(NumberFormatException e) {
                    JFrame info = new JFrame("INFO");
                    JOptionPane.showMessageDialog(info, "Please enter an integer between 2 and 50 inclusive");
                    bidValue = 0;
                    bid.setText("");
                }
            } else {
                try{
                    int newBid = Integer.parseInt(bid.getText());
                    if (newBid >= bidValue)
                    {
                        JButton current = previous; // Refers to the currently selected player button
                        current.setBackground(null);
                        previousSelectedPlayer.setBackground(Color.GREEN);
                        previous = previousSelectedPlayer; //Refers back to the previous player once appropriate color have been set
                        bid.setText(Integer.toString(bidValue));
                        bid.setEditable(false);
                        bidButton.setEnabled(false);
                    } else {
                        bidValue = newBid; //Reassigning the new valid bid
                        bid.setEditable(false);
                        bidButton.setEnabled(false);
                        //time.setText("Time Left: 60");
                        //timer.stop();
                        //timer.restart();
                        //start = 15;
                        //startTheTimer();
                    }
                    
                } catch(NumberFormatException e) {
                    previous.setBackground(Color.GREEN);
                }
            }
        }
    }
    
    
    public void mouseEntered(MouseEvent arg0){}
    public void mouseExited(MouseEvent arg0){}
    public void mousePressed(MouseEvent arg0){}
    public void mouseReleased(MouseEvent arg0){}
}