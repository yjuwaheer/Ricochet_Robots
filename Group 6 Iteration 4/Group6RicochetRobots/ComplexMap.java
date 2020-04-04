import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;
import java.util.*;
import javax.swing.border.Border;
import javax.swing.border.*;
import javax.swing.Timer;
import java.io.*;

/**
 * Implementation of the class ComplexMap.
 *
 * @author (Yudish)
 * @version (0.4)
 */
public class ComplexMap extends JFrame implements ActionListener, MouseListener
{
    private JMenuBar frameMenu;
    
    private JPanel panel;
    private static BoardSquare [][] boardSquares;
    private int NUM_OF_SQUARES = 16;
    
    //Robots to be placed on board
    private int num_of_robots; //Number of robots
    private static ComplexRobot redRobot = new ComplexRobot();
    private static ComplexRobot blueRobot = new ComplexRobot();
    private static ComplexRobot yellowRobot = new ComplexRobot();
    private static ComplexRobot greenRobot = new ComplexRobot();
    
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
    
    private static Icon redDiagonal;
    private static Icon blueDiagonal;
    private static Icon yellowDiagonal;
    private static Icon greenDiagonal;
    
    //Maps the type of target square to the icon
    public static HashMap<String, Icon> targetMap = new HashMap<>();
    
    ///For main panel to East of Board
    private static JPanel eastPanel;
    private static GridBagConstraints gbc = new GridBagConstraints();
    private static JPanel mainEast;
    private static int xPoint, yPoint;
    //For current target
    private static Icon currentTarget;
    private static String currentTargetString;
    //For the players to act
    private static JButton[] playerButtons = new JButton[4];
    private static JButton previous = null; //For player correct colour
    //private static int currentPlayer; //To keep track of current player
    private static JButton previousSelectedPlayer; //When re-bidding moves
    ///For player scores
    public static JLabel[] playerScores = new JLabel[4];
    public static int[] scores = new int[]{0,0,0,0};
    //For bidding
    private static JTextField bidField,bid;
    private JButton bidButton;
    private static int bidValue = 0;
    //For time
    private static JLabel time;
    private static int start = 60;  ////To change to 60 when finished testing
    private static Timer timer;
    private static String timeExh = ""; //To know when time has been exhausted
    //For current turn and solution
    private static ComplexTurn currentTurn;
    private static JButton targetIcon;
    private static Boolean stillCurrentTarget = false;
    //For current robot selected
    private static ComplexRobot currentRobot;
    //To return to original positon if demonstration fails
    private static int redOriginalX, blueOriginalX, yellowOriginalX, greenOriginalX;
    private static int redOriginalY, blueOriginalY, yellowOriginalY, greenOriginalY;
    private static Boolean currentSolved = false;
    private static ComplexRobot redORobot, blueORobot, yellowORobot, greenORobot;
    private static Boolean redToken = true, blueToken = true, yellowToken = true, greenToken = true;
    ////To end the game
    public static JButton endGame;
    ///
    
    //Needed for load/save
    private static boolean loadingOrNot;
    private static boolean colourAssist;
    private static boolean p1Select;
    private static boolean p2Select;
    private static boolean p3Select;
    private static boolean p4Select;
    private String p1T;
    private String p2T;
    private String p3T;
    private String p4T;
    private String c1T;
    private String c2T;
    private String c3T;
    private String c4T;
    
    public ComplexMap()
    {
        super("Ricochet Robots | Complex Map");
        
        mainComplexMapSetup();
    }
    
    public ComplexMap(boolean colourAssistSelected, boolean p1Selected,
                     boolean p2Selected, boolean p3Selected, boolean p4Selected,
                     String p1Type, String p2Type, String p3Type, String p4Type,
                     String c1Type, String c2Type, String c3Type, String c4Type)
    {
        super("Ricochet Robots | Complex Map");
        
        loadingOrNot = true;
        //Storing variables from file
        colourAssist = colourAssistSelected;
        p1Select = p1Selected;
        p2Select = p2Selected;
        p3Select = p3Selected;
        p4Select = p4Selected;
        p1T = p1Type;
        p2T = p2Type;
        p3T = p3Type;
        p4T = p4Type;
        c1T = c1Type;
        c2T = c2Type;
        c3T = c3Type;
        c4T = c4Type;
        
        mainComplexMapSetup();
    }
    
    public void mainComplexMapSetup()
    {
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
        
        setUpDiagonalBars();
        
        setEdgeSquaresAsBarriers();
        
        setUpMiddleSquares();
        
        if(loadingOrNot == false)
        {
            setRobot();
        }
        
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
        
        if(loadingOrNot == false)
        {
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
        }
        else
        {
            if(p1Select == true)
            {
                playerButtons[0] = new JButton("PLAYER1");
                playerButtons[0].addActionListener(this);
                gbc.gridx = xPoint;
                gbc.gridy = yPoint;
                gbc.insets = new Insets(10,0,0,0);
                yPoint++;
                mainEast.add(playerButtons[0],gbc);
            }
            if(p2Select == true)
            {
                playerButtons[1] = new JButton("PLAYER2");
                playerButtons[1].addActionListener(this);
                gbc.gridx = xPoint;
                gbc.gridy = yPoint;
                gbc.insets = new Insets(10,0,0,0);
                yPoint++;
                mainEast.add(playerButtons[1],gbc);
            }
            if(p3Select == true)
            {
                playerButtons[2] = new JButton("PLAYER3");
                playerButtons[2].addActionListener(this);
                gbc.gridx = xPoint;
                gbc.gridy = yPoint;
                gbc.insets = new Insets(10,0,0,0);
                yPoint++;
                mainEast.add(playerButtons[2],gbc);
            }
            if(p4Select == true)
            {
                playerButtons[3] = new JButton("PLAYER4");
                playerButtons[3].addActionListener(this);
                gbc.gridx = xPoint;
                gbc.gridy = yPoint;
                gbc.insets = new Insets(10,0,0,0);
                yPoint++;
                mainEast.add(playerButtons[3],gbc);
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
        bidButton.setPreferredSize(new Dimension(59,30));
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
        if(loadingOrNot == false)
        {
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
        }
        else
        {
            if(p1Select == true)
            {
                playerScores[0] = new JLabel("Player 1: " + scores[0]);
                playerScores[0].setFont(new Font("Harrington", Font.BOLD, 17));
                gbc.gridx = xPoint;
                gbc.gridy = yPoint;
                gbc.insets = new Insets(10,0,0,0);
                yPoint++;
                mainEast.add(playerScores[0],gbc);
            }
            if(p2Select == true)
            {
                playerScores[1] = new JLabel("Player 2: " + scores[1]);
                playerScores[1].setFont(new Font("Harrington", Font.BOLD, 17));
                gbc.gridx = xPoint;
                gbc.gridy = yPoint;
                gbc.insets = new Insets(10,0,0,0);
                yPoint++;
                mainEast.add(playerScores[1],gbc);
            }
            if(p3Select == true)
            {
                playerScores[2] = new JLabel("Player 3: " + scores[0]);
                playerScores[2].setFont(new Font("Harrington", Font.BOLD, 17));
                gbc.gridx = xPoint;
                gbc.gridy = yPoint;
                gbc.insets = new Insets(10,0,0,0);
                yPoint++;
                mainEast.add(playerScores[2],gbc);
            }
            if(p4Select == true)
            {
                playerScores[3] = new JLabel("Player 4: " + scores[0]);
                playerScores[3].setFont(new Font("Harrington", Font.BOLD, 17));
                gbc.gridx = xPoint;
                gbc.gridy = yPoint;
                gbc.insets = new Insets(10,0,0,0);
                yPoint++;
                mainEast.add(playerScores[3],gbc);
            }
        }
        
        ////Exit/GiveUp button
        endGame = new JButton();
        endGame.setBorder(new LineBorder(Color.BLACK));
        Icon endIcon = new ImageIcon(((new ImageIcon("Resources/end.png")).getImage()).getScaledInstance(90,35,java.awt.Image.SCALE_SMOOTH));
        endGame.setIcon(endIcon);
        endGame.addActionListener(this);
        gbc.insets = new Insets(20,0,0,0);
        gbc.gridx = xPoint;
        gbc.gridy = yPoint++;
        mainEast.add(endGame, gbc);
        
        eastPanel.add(mainEast);
    }
    
    
    //Method to reset east layout
    public static void resetEastLayout()
    {
        //System.out.println(previous.getText());
        //This snippet of code caters to reset players and bid after demonstration
        if (getTimeExh().equals("exhausted"))
        {
            if(loadingOrNot == false)
            {
                int num = 0;
                for (int i = 0; i < 10; i = i + 3)
                {
                    if ((Boolean)StartFrame.getPlayerData().get(i))
                    {
                        playerButtons[num].setBackground(null);
                    }
                }
            }
            else
            {
                if(p1Select == true)
                {
                    playerButtons[0].setBackground(null);
                }
                if(p2Select == true)
                {
                    playerButtons[1].setBackground(null);
                }
                if(p3Select == true)
                {
                    playerButtons[2].setBackground(null);
                }
                if(p4Select == true)
                {
                    playerButtons[3].setBackground(null);
                }
            }
            
            bid.setText("");
            
            ////Resetting the currently selected player button colour
            previous.setBackground(null);
           
            if (stillCurrentTarget == true)
            {
                //Resetting the time
                time.setText("Time Left: 60");
                timer.stop();
                start = 60;  ////To change value to 60 after testing
            
                //Resetting the bid value
                bidValue = 0;
            }
        }
        
        ////Resetting the time exhaustion string
        timeExh = "";
        
        //For resetting the bid value
        ////resetBidding();
        
        if(currentSolved == true)
        {
            setNewOriginalPositions();
            
            //Resetting the time
            time.setText("Time Left: 60");
            timer.stop();
            start = 60;  ////To change value to 60 after testing
            
            //Resetting the bid value
            bidValue = 0;
            
            currentSolved = false;
            
            timeExh = "";
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
                boardSquares[redORobot.getrLocx()][redORobot.getrLocy()].setIcon(ComplexRobot.findIcon(redORobot.getrLocx(),redORobot.getrLocy()));
                boardSquares[redORobot.getrLocx()][redORobot.getrLocy()].changeRobotOnSquare();
            
                ComplexRobot.setrLocx(redORobot, redOriginalX);
                ComplexRobot.setrLocy(redORobot, redOriginalY);
            
                boardSquares[redOriginalX][redOriginalY].setIcon(redORobot.getIcon());
                boardSquares[redOriginalX][redOriginalY].changeRobotOnSquare();
            }
            
            ///For blue robot
            if (blueORobot != null)
            {
                boardSquares[blueORobot.getrLocx()][blueORobot.getrLocy()].setIcon(ComplexRobot.findIcon(blueORobot.getrLocx(),blueORobot.getrLocy()));
                boardSquares[blueORobot.getrLocx()][blueORobot.getrLocy()].changeRobotOnSquare();
            
                ComplexRobot.setrLocx(blueORobot, blueOriginalX);
                ComplexRobot.setrLocy(blueORobot, blueOriginalY);
            
                boardSquares[blueOriginalX][blueOriginalY].setIcon(blueORobot.getIcon());
                boardSquares[blueOriginalX][blueOriginalY].changeRobotOnSquare();
            }
        
            ///For yellow robot
            if (yellowORobot != null)
            {
                boardSquares[yellowORobot.getrLocx()][yellowORobot.getrLocy()].setIcon(ComplexRobot.findIcon(yellowORobot.getrLocx(),yellowORobot.getrLocy()));
                boardSquares[yellowORobot.getrLocx()][yellowORobot.getrLocy()].changeRobotOnSquare();
            
                ComplexRobot.setrLocx(yellowORobot, yellowOriginalX);
                ComplexRobot.setrLocy(yellowORobot, yellowOriginalY);
            
                boardSquares[yellowOriginalX][yellowOriginalY].setIcon(yellowORobot.getIcon());
                boardSquares[yellowOriginalX][yellowOriginalY].changeRobotOnSquare();
            }
        
            ///For green robot
            if (greenORobot != null)
            {
                boardSquares[greenORobot.getrLocx()][greenORobot.getrLocy()].setIcon(ComplexRobot.findIcon(greenORobot.getrLocx(),greenORobot.getrLocy()));
                boardSquares[greenORobot.getrLocx()][greenORobot.getrLocy()].changeRobotOnSquare();
            
                ComplexRobot.setrLocx(greenORobot, greenOriginalX);
                ComplexRobot.setrLocy(greenORobot, greenOriginalY);
            
                boardSquares[greenOriginalX][greenOriginalY].setIcon(greenORobot.getIcon());
                boardSquares[greenOriginalX][greenOriginalY].changeRobotOnSquare();
            }
        }
    }
    
    ////To review this piece of code(may or may not be needed) ??
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
        currentTurn = new ComplexTurn();
        //
        currentTargetString = currentTurn.getChosenTargetSpace();
        currentTarget = currentTurn.getRandomTarget(); //Selecting a random target icon
        targetIcon = new JButton();
        targetIcon.setIcon(currentTarget);
        targetIcon.setPreferredSize(new Dimension(45,45));
        gbc.gridy = yPoint++;
        mainEast.add(targetIcon, gbc);       
    }
    
    public static String getCurrentTargetString()
    {
        return currentTargetString;
    }
    
    ///Update the current target to the next
    public static void updateCurrentTarget()
    {
        currentTurn = new ComplexTurn();
        
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
    public static ComplexTurn getCurrentTurn()
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
    
    public static void setBidValue(int value)
    {
        bidValue = value;
    }
    
    public static void setRedOriginalX(int x)
    {
        redOriginalX = x;
    }
    
    public static void setRedOriginalY(int y)
    {
        redOriginalY = y;
    }
    
    public static void setBlueOriginalX(int x)
    {
        blueOriginalX = x;
    }
    
    public static void setBlueOriginalY(int y)
    {
        blueOriginalY = y;
    }
    
    public static void setYellowOriginalX(int x)
    {
        yellowOriginalX = x;
    }
    
    public static void setYellowOriginalY(int y)
    {
        yellowOriginalY = y;
    }
    
    public static void setGreenOriginalX(int x)
    {
        greenOriginalX = x;
    }
    
    public static void setGreenOriginalY(int y)
    {
        greenOriginalY = y;
    }
    
    public static void setCurrentTargetString(String target)
    {
        currentTargetString = target;
    }
    
    public static void setCurrentTarget(Icon target)
    {
        currentTarget = target;
    }
    
    public static JButton getTargetIcon()
    {
        return targetIcon;
    }
    
    public static void setTargetIcon(Icon icon)
    {
        targetIcon.setIcon(icon);
    }
    
    private void colorAssistance()
    {
        if(loadingOrNot == false)
        {
            if (StartFrame.getColourAssist()){
                basicSquareIcon = new ImageIcon(((new ImageIcon("Resources/Assist/basicSquareAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                rightSideBarrier = new ImageIcon(((new ImageIcon("Resources/Assist/rightSideBarrierAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                leftSideBarrier = new ImageIcon(((new ImageIcon("Resources/Assist/leftSideBarrierAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                topSideBarrier = new ImageIcon(((new ImageIcon("Resources/Assist/topSideBarrierAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                bottomSideBarrier = new ImageIcon(((new ImageIcon("Resources/Assist/bottomSideBarrierAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                redCircle = new ImageIcon(((new ImageIcon("Resources/Assist/redCircleAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                blueCircle = new ImageIcon(((new ImageIcon("Resources/Assist/complexBlueCircleAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                yellowCircle = new ImageIcon(((new ImageIcon("Resources/Assist/yellowCircleAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                greenCircle = new ImageIcon(((new ImageIcon("Resources/Assist/greenCircleAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                redSquare = new ImageIcon(((new ImageIcon("Resources/Assist/redSquareAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                blueSquare = new ImageIcon(((new ImageIcon("Resources/Assist/blueSquareAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                yellowSquare = new ImageIcon(((new ImageIcon("Resources/Assist/yellowSquareAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                greenSquare = new ImageIcon(((new ImageIcon("Resources/Assist/complexGreenSquareAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                redTriangle = new ImageIcon(((new ImageIcon("Resources/Assist/redTriangleAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                blueTriangle = new ImageIcon(((new ImageIcon("Resources/Assist/blueTriangleAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                yellowTriangle = new ImageIcon(((new ImageIcon("Resources/Assist/complexYellowTriangleAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                greenTriangle = new ImageIcon(((new ImageIcon("Resources/Assist/greenTriangleAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                redStar = new ImageIcon(((new ImageIcon("Resources/Assist/redStarAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                blueStar = new ImageIcon(((new ImageIcon("Resources/Assist/blueStarAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                yellowStar = new ImageIcon(((new ImageIcon("Resources/Assist/yellowStarAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                greenStar = new ImageIcon(((new ImageIcon("Resources/Assist/greenStarAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                multivortex = new ImageIcon(((new ImageIcon("Resources/Assist/multivortexAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                //Colored Assist Diagonal Bars
                redDiagonal = new ImageIcon(((new ImageIcon("Resources/Assist/redDiagonalAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                blueDiagonal = new ImageIcon(((new ImageIcon("Resources/Assist/blueDiagonalAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                yellowDiagonal = new ImageIcon(((new ImageIcon("Resources/Assist/yellowDiagonalAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                greenDiagonal = new ImageIcon(((new ImageIcon("Resources/Assist/greenDiagonalAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH)); 
           
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
                blueCircle = new ImageIcon(((new ImageIcon("Resources/blueCircleComplex.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                yellowCircle = new ImageIcon(((new ImageIcon("Resources/yellowCircle.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                greenCircle = new ImageIcon(((new ImageIcon("Resources/greenCircle.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                redSquare = new ImageIcon(((new ImageIcon("Resources/redSquare.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                blueSquare = new ImageIcon(((new ImageIcon("Resources/blueSquare.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                yellowSquare = new ImageIcon(((new ImageIcon("Resources/yellowSquare.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                greenSquare = new ImageIcon(((new ImageIcon("Resources/greenSquareComplex.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                redTriangle = new ImageIcon(((new ImageIcon("Resources/redTriangle.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                blueTriangle = new ImageIcon(((new ImageIcon("Resources/blueTriangle.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                yellowTriangle = new ImageIcon(((new ImageIcon("Resources/yellowTriangleComplex.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                greenTriangle = new ImageIcon(((new ImageIcon("Resources/greenTriangle.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                redStar = new ImageIcon(((new ImageIcon("Resources/redStar.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                blueStar = new ImageIcon(((new ImageIcon("Resources/blueStar.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                yellowStar = new ImageIcon(((new ImageIcon("Resources/yellowStar.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                greenStar = new ImageIcon(((new ImageIcon("Resources/greenStar.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                multivortex = new ImageIcon(((new ImageIcon("Resources/multivortex.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                //Colored Diagonal bars
                redDiagonal = new ImageIcon(((new ImageIcon("Resources/redDiagonal.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                blueDiagonal = new ImageIcon(((new ImageIcon("Resources/blueDiagonal.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                yellowDiagonal = new ImageIcon(((new ImageIcon("Resources/yellowDiagonal.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                greenDiagonal = new ImageIcon(((new ImageIcon("Resources/greenDiagonal.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH)); 
                
                //Robots
                redRobot.setIcon(new ImageIcon("Resources/redRobot.png"));
                blueRobot.setIcon(new ImageIcon("Resources/blueRobot.png"));
                yellowRobot.setIcon(new ImageIcon("Resources/yellowRobot.png"));
                greenRobot.setIcon(new ImageIcon("Resources/greenRobot.png"));
            }
        }
        else{
            if(colourAssist == true)
            {
                basicSquareIcon = new ImageIcon(((new ImageIcon("Resources/Assist/basicSquareAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                rightSideBarrier = new ImageIcon(((new ImageIcon("Resources/Assist/rightSideBarrierAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                leftSideBarrier = new ImageIcon(((new ImageIcon("Resources/Assist/leftSideBarrierAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                topSideBarrier = new ImageIcon(((new ImageIcon("Resources/Assist/topSideBarrierAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                bottomSideBarrier = new ImageIcon(((new ImageIcon("Resources/Assist/bottomSideBarrierAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                redCircle = new ImageIcon(((new ImageIcon("Resources/Assist/redCircleAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                blueCircle = new ImageIcon(((new ImageIcon("Resources/Assist/complexBlueCircleAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                yellowCircle = new ImageIcon(((new ImageIcon("Resources/Assist/yellowCircleAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                greenCircle = new ImageIcon(((new ImageIcon("Resources/Assist/greenCircleAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                redSquare = new ImageIcon(((new ImageIcon("Resources/Assist/redSquareAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                blueSquare = new ImageIcon(((new ImageIcon("Resources/Assist/blueSquareAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                yellowSquare = new ImageIcon(((new ImageIcon("Resources/Assist/yellowSquareAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                greenSquare = new ImageIcon(((new ImageIcon("Resources/Assist/complexGreenSquareAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                redTriangle = new ImageIcon(((new ImageIcon("Resources/Assist/redTriangleAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                blueTriangle = new ImageIcon(((new ImageIcon("Resources/Assist/blueTriangleAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                yellowTriangle = new ImageIcon(((new ImageIcon("Resources/Assist/complexYellowTriangleAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                greenTriangle = new ImageIcon(((new ImageIcon("Resources/Assist/greenTriangleAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                redStar = new ImageIcon(((new ImageIcon("Resources/Assist/redStarAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                blueStar = new ImageIcon(((new ImageIcon("Resources/Assist/blueStarAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                yellowStar = new ImageIcon(((new ImageIcon("Resources/Assist/yellowStarAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                greenStar = new ImageIcon(((new ImageIcon("Resources/Assist/greenStarAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                multivortex = new ImageIcon(((new ImageIcon("Resources/Assist/multivortexAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                //Colored Assist Diagonal Bars
                redDiagonal = new ImageIcon(((new ImageIcon("Resources/Assist/redDiagonalAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                blueDiagonal = new ImageIcon(((new ImageIcon("Resources/Assist/blueDiagonalAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                yellowDiagonal = new ImageIcon(((new ImageIcon("Resources/Assist/yellowDiagonalAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                greenDiagonal = new ImageIcon(((new ImageIcon("Resources/Assist/greenDiagonalAssist.jpg")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH)); 
           
                //Robots
                redRobot.setIcon(new ImageIcon("Resources/Assist/redRobotAssist.jpg"));
                blueRobot.setIcon(new ImageIcon("Resources/Assist/blueRobotAssist.jpg"));
                yellowRobot.setIcon(new ImageIcon("Resources/Assist/yellowRobotAssist.jpg"));
                greenRobot.setIcon(new ImageIcon("Resources/Assist/greenRobotAssist.jpg"));
            }
            else{
                basicSquareIcon = new ImageIcon(((new ImageIcon("Resources/basicSquare.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                rightSideBarrier = new ImageIcon(((new ImageIcon("Resources/rightSideBarrier.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                leftSideBarrier = new ImageIcon(((new ImageIcon("Resources/leftSideBarrier.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                topSideBarrier = new ImageIcon(((new ImageIcon("Resources/topSideBarrier.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                bottomSideBarrier = new ImageIcon(((new ImageIcon("Resources/bottomSideBarrier.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                redCircle = new ImageIcon(((new ImageIcon("Resources/redCircle.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                blueCircle = new ImageIcon(((new ImageIcon("Resources/blueCircleComplex.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                yellowCircle = new ImageIcon(((new ImageIcon("Resources/yellowCircle.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                greenCircle = new ImageIcon(((new ImageIcon("Resources/greenCircle.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                redSquare = new ImageIcon(((new ImageIcon("Resources/redSquare.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                blueSquare = new ImageIcon(((new ImageIcon("Resources/blueSquare.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                yellowSquare = new ImageIcon(((new ImageIcon("Resources/yellowSquare.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                greenSquare = new ImageIcon(((new ImageIcon("Resources/greenSquareComplex.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                redTriangle = new ImageIcon(((new ImageIcon("Resources/redTriangle.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                blueTriangle = new ImageIcon(((new ImageIcon("Resources/blueTriangle.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                yellowTriangle = new ImageIcon(((new ImageIcon("Resources/yellowTriangleComplex.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                greenTriangle = new ImageIcon(((new ImageIcon("Resources/greenTriangle.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                redStar = new ImageIcon(((new ImageIcon("Resources/redStar.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                blueStar = new ImageIcon(((new ImageIcon("Resources/blueStar.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                yellowStar = new ImageIcon(((new ImageIcon("Resources/yellowStar.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                greenStar = new ImageIcon(((new ImageIcon("Resources/greenStar.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                multivortex = new ImageIcon(((new ImageIcon("Resources/multivortex.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                //Colored Diagonal bars
                redDiagonal = new ImageIcon(((new ImageIcon("Resources/redDiagonal.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                blueDiagonal = new ImageIcon(((new ImageIcon("Resources/blueDiagonal.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                yellowDiagonal = new ImageIcon(((new ImageIcon("Resources/yellowDiagonal.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
                greenDiagonal = new ImageIcon(((new ImageIcon("Resources/greenDiagonal.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH)); 
                
                //Robots
                redRobot.setIcon(new ImageIcon("Resources/redRobot.png"));
                blueRobot.setIcon(new ImageIcon("Resources/blueRobot.png"));
                yellowRobot.setIcon(new ImageIcon("Resources/yellowRobot.png"));
                greenRobot.setIcon(new ImageIcon("Resources/greenRobot.png"));
            }
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
        JMenuItem save = new JMenuItem("Save Game");
        JMenuItem howto = new JMenuItem("How to?");
        file.add(menu);
        file.add(newG);
        file.add(exit);
        file.add(save);
        help.add(howto);
        
        //Going to menu frame
        menu.addActionListener((ActionEvent ev) -> {dispose();new MainFrame();});
        //Going to the start a game frame
        newG.addActionListener((ActionEvent ev) -> {dispose();new StartFrame();});
        //Exiting the frame
        exit.addActionListener((ActionEvent ev) -> {System.exit(0);});
        //Saving the game
        save.addActionListener((ActionEvent ev) -> {saveData();StartFrame.saveData();});
        
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
        boardSquares[15][10].setIcon(rightSideBarrier);
        boardSquares[15][10].setRightEdge(true);
        boardSquares[15][11].setIcon(leftSideBarrier);
        boardSquares[15][11].setLeftEdge(true);
        boardSquares[13][15].setIcon(topSideBarrier);
        boardSquares[13][15].setTopEdge(true);
        boardSquares[12][15].setIcon(bottomSideBarrier);
        boardSquares[12][15].setBottomEdge(true);
        boardSquares[2][15].setIcon(bottomSideBarrier);
        boardSquares[2][15].setBottomEdge(true);
        boardSquares[3][15].setIcon(topSideBarrier);
        boardSquares[3][15].setTopEdge(true);
        boardSquares[0][5].setIcon(rightSideBarrier);
        boardSquares[0][5].setRightEdge(true);
        boardSquares[0][6].setIcon(leftSideBarrier);
        boardSquares[0][6].setLeftEdge(true);
        boardSquares[0][8].setIcon(rightSideBarrier);
        boardSquares[0][8].setRightEdge(true);
        boardSquares[0][9].setIcon(leftSideBarrier);
        boardSquares[0][9].setLeftEdge(true);
        boardSquares[6][0].setIcon(topSideBarrier);
        boardSquares[6][0].setTopEdge(true);
        boardSquares[5][0].setIcon(bottomSideBarrier);
        boardSquares[5][0].setBottomEdge(true);
        boardSquares[11][0].setIcon(topSideBarrier);
        boardSquares[11][0].setTopEdge(true);
        boardSquares[10][0].setIcon(bottomSideBarrier);
        boardSquares[10][0].setBottomEdge(true);
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
        boardSquares[3][1].setIcon(redCircle);
        boardSquares[3][1].setTopEdge(true);
        boardSquares[3][1].setLeftEdge(true);
        boardSquares[2][1].setBottomEdge(true);
        boardSquares[2][1].setIcon(bottomSideBarrier);
        boardSquares[3][0].setRightEdge(true);
        boardSquares[3][0].setIcon(rightSideBarrier);
        boardSquares[3][1].setTargetSpace("redCircle");
        boardSquares[3][1].sendIcon(redCircle);
        ///
        boardSquares[3][1].setSquareColour("red");
        ///
        targetMap.put("redCircle", redCircle);
        
        //Red Square
        boardSquares[1][10].setIcon(redSquare);
        boardSquares[1][10].setTopEdge(true);
        boardSquares[1][10].setLeftEdge(true);
        boardSquares[0][10].setBottomEdge(true);
        boardSquares[0][10].setIcon(bottomSideBarrier);
        boardSquares[1][9].setRightEdge(true);
        boardSquares[1][9].setIcon(rightSideBarrier);
        boardSquares[1][10].setTargetSpace("redSquare");
        boardSquares[1][10].sendIcon(redSquare);
        ///
        boardSquares[1][10].setSquareColour("red");
        ///
        targetMap.put("redSquare", redCircle);
        
        //Green Triangle
        boardSquares[6][2].setIcon(greenTriangle);
        boardSquares[6][2].setTopEdge(true);
        boardSquares[6][2].setRightEdge(true);
        boardSquares[5][2].setBottomEdge(true);
        boardSquares[5][2].setIcon(bottomSideBarrier);
        boardSquares[6][3].setLeftEdge(true);
        ///boardSquares[6][3].setIcon(leftSideBarrier);
        boardSquares[6][2].setTargetSpace("greenTriangle");
        boardSquares[6][2].sendIcon(greenTriangle);
        ///
        boardSquares[6][2].setSquareColour("green");
        ///
        targetMap.put("greenTriangle", greenTriangle);
        
        //Blue Triangle
        boardSquares[4][8].setIcon(blueTriangle);
        boardSquares[4][8].setBottomEdge(true);
        boardSquares[4][8].setRightEdge(true);
        boardSquares[5][8].setTopEdge(true);
        boardSquares[5][8].setIcon(topSideBarrier);
        boardSquares[4][9].setLeftEdge(true);
        boardSquares[4][9].setIcon(leftSideBarrier);
        boardSquares[4][8].setTargetSpace("blueTriangle");
        boardSquares[4][8].sendIcon(blueTriangle);
        ///
        boardSquares[4][8].setSquareColour("blue");
        ///
        targetMap.put("blueTriangle", blueTriangle);
        
        //Yellow Star
        boardSquares[4][6].setIcon(yellowStar);
        boardSquares[4][6].setBottomEdge(true);
        boardSquares[4][6].setRightEdge(true);
        boardSquares[5][6].setTopEdge(true);
        boardSquares[5][6].setIcon(topSideBarrier);
        boardSquares[4][7].setLeftEdge(true);
        boardSquares[4][7].setIcon(leftSideBarrier);
        boardSquares[4][6].setTargetSpace("yellowStar");
        boardSquares[4][6].sendIcon(yellowStar);
        ///
        boardSquares[4][6].setSquareColour("yellow");
        ///
        targetMap.put("yellowStar", yellowStar);
        
        //Green Star
        boardSquares[5][13].setIcon(greenStar);
        boardSquares[5][13].setBottomEdge(true);
        boardSquares[5][13].setLeftEdge(true);
        boardSquares[6][13].setTopEdge(true);
        ///boardSquares[6][13].setIcon(topSideBarrier);
        boardSquares[5][12].setRightEdge(true);
        boardSquares[5][12].setIcon(rightSideBarrier);
        boardSquares[5][13].setTargetSpace("greenStar");
        boardSquares[5][13].sendIcon(greenStar);
        ///
        boardSquares[5][13].setSquareColour("green");
        ///
        targetMap.put("greenStar", greenStar);
        
        //Blue Square
        boardSquares[6][3].setIcon(blueSquare);
        boardSquares[6][3].setBottomEdge(true);
        boardSquares[6][3].setLeftEdge(true);
        boardSquares[7][3].setTopEdge(true);
        boardSquares[7][3].setIcon(topSideBarrier);
        boardSquares[6][2].setRightEdge(true);
        ///boardSquares[6][2].setIcon(rightSideBarrier);
        boardSquares[6][3].setTargetSpace("blueSquare");
        boardSquares[6][3].sendIcon(blueSquare);
        ///
        boardSquares[6][3].setSquareColour("blue");
        ///
        targetMap.put("blueSquare", blueSquare);
        
        //Yellow Circle
        boardSquares[6][13].setIcon(yellowCircle);
        boardSquares[6][13].setTopEdge(true);
        boardSquares[6][13].setRightEdge(true);
        boardSquares[5][13].setBottomEdge(true);
        ///boardSquares[5][13].setIcon(bottomSideBarrier);
        boardSquares[6][14].setLeftEdge(true);
        boardSquares[6][14].setIcon(leftSideBarrier);
        boardSquares[6][13].setTargetSpace("yellowCircle");
        boardSquares[6][13].sendIcon(yellowCircle);
        ///
        boardSquares[6][13].setSquareColour("yellow");
        ///
        targetMap.put("yellowCircle", yellowCircle);
        
        //Multivortex
        boardSquares[10][7].setIcon(multivortex);
        boardSquares[10][7].setTopEdge(true);
        boardSquares[10][7].setRightEdge(true);
        boardSquares[9][7].setBottomEdge(true);
        boardSquares[9][7].setIcon(bottomSideBarrier);
        boardSquares[10][8].setLeftEdge(true);
        boardSquares[10][8].setIcon(leftSideBarrier);
        boardSquares[10][7].setTargetSpace("multivortex");
        boardSquares[10][7].sendIcon(multivortex);
        ///
        boardSquares[10][7].setSquareColour("multi");
        ///
        targetMap.put("multivortex", multivortex);
        
        //Blue Circle
        boardSquares[13][3].setIcon(blueCircle);
        boardSquares[13][3].setTopEdge(true);
        boardSquares[13][3].setLeftEdge(true);
        boardSquares[12][3].setBottomEdge(true);
        boardSquares[12][3].setIcon(bottomSideBarrier);
        boardSquares[13][2].setRightEdge(true);
        boardSquares[13][2].setIcon(rightSideBarrier);
        boardSquares[13][3].setTargetSpace("blueCircle");
        boardSquares[13][3].sendIcon(blueCircle);
        ///
        boardSquares[13][3].setSquareColour("blue");
        ///
        targetMap.put("blueCircle", blueCircle);
        
        //Yellow Square
        boardSquares[9][10].setIcon(yellowSquare);
        boardSquares[9][10].setBottomEdge(true);
        boardSquares[9][10].setRightEdge(true);
        boardSquares[10][10].setTopEdge(true);
        boardSquares[10][10].setIcon(topSideBarrier);
        boardSquares[9][11].setLeftEdge(true);
        boardSquares[9][11].setIcon(leftSideBarrier);
        boardSquares[9][10].setTargetSpace("yellowSquare");
        boardSquares[9][10].sendIcon(yellowSquare);
        ///
        boardSquares[9][10].setSquareColour("yellow");
        ///
        targetMap.put("yellowSquare", yellowSquare);
        
        //Green Square
        boardSquares[12][3].setIcon(greenSquare);
        boardSquares[12][3].setBottomEdge(true);
        boardSquares[12][3].setLeftEdge(true);
        boardSquares[13][3].setTopEdge(true);
        ///boardSquares[13][3].setIcon(topSideBarrier);
        boardSquares[12][4].setLeftEdge(true);
        boardSquares[12][4].setIcon(leftSideBarrier);
        boardSquares[12][3].setTargetSpace("greenSquare");
        boardSquares[12][3].sendIcon(greenSquare);
        ///
        boardSquares[12][3].setSquareColour("green");
        ///
        targetMap.put("greenSquare", greenSquare);
        
        //Red Triangle
        boardSquares[11][12].setIcon(redTriangle);
        boardSquares[11][12].setTopEdge(true);
        boardSquares[11][12].setRightEdge(true);
        boardSquares[10][12].setBottomEdge(true);
        boardSquares[10][12].setIcon(bottomSideBarrier);
        boardSquares[11][13].setLeftEdge(true);
        ///boardSquares[11][13].setIcon(leftSideBarrier);
        boardSquares[11][12].setTargetSpace("redTriangle");
        boardSquares[11][12].sendIcon(redTriangle);
        ///
        boardSquares[11][12].setSquareColour("red");
        ///
        targetMap.put("redTriangle", redTriangle);
        
        //Green Circle
        boardSquares[11][13].setIcon(greenCircle);
        boardSquares[11][13].setBottomEdge(true);
        boardSquares[11][13].setLeftEdge(true);
        boardSquares[12][13].setTopEdge(true);
        boardSquares[12][13].setIcon(topSideBarrier);
        boardSquares[11][12].setRightEdge(true);
        ///boardSquares[11][12].setIcon(rightSideBarrier);
        boardSquares[11][13].setTargetSpace("greenCircle");
        boardSquares[11][13].sendIcon(greenCircle);
        ///
        boardSquares[11][13].setSquareColour("green");
        ///
        targetMap.put("greenCircle", greenCircle);
        
        //Red Star
        boardSquares[14][5].setIcon(redStar);
        boardSquares[14][5].setTopEdge(true);
        boardSquares[14][5].setRightEdge(true);
        boardSquares[13][5].setBottomEdge(true);
        boardSquares[13][5].setIcon(bottomSideBarrier);
        boardSquares[14][6].setLeftEdge(true);
        boardSquares[14][6].setIcon(leftSideBarrier);
        boardSquares[14][5].setTargetSpace("redStar");
        boardSquares[14][5].sendIcon(redStar);
        ///
        boardSquares[14][5].setSquareColour("red");
        ///
        targetMap.put("redStar", redStar);
        
        //Blue Star
        boardSquares[13][9].setIcon(blueStar);
        boardSquares[13][9].setTopEdge(true);
        boardSquares[13][9].setLeftEdge(true);
        boardSquares[12][9].setBottomEdge(true);
        ///boardSquares[12][9].setIcon(bottomSideBarrier);
        boardSquares[13][8].setRightEdge(true);
        boardSquares[13][8].setIcon(rightSideBarrier);
        boardSquares[13][9].setTargetSpace("blueStar");
        boardSquares[13][9].sendIcon(blueStar);
        ///
        boardSquares[13][9].setSquareColour("blue");
        ///
        targetMap.put("blueStar", blueStar);
        
        //Yellow Triangle
        boardSquares[9][2].setIcon(yellowTriangle);
        boardSquares[9][2].setBottomEdge(true);
        boardSquares[9][2].setLeftEdge(true);
        boardSquares[10][2].setTopEdge(true);
        boardSquares[10][2].setIcon(topSideBarrier);
        boardSquares[9][1].setRightEdge(true);
        boardSquares[9][1].setIcon(rightSideBarrier);
        boardSquares[9][2].setTargetSpace("yellowTriangle");
        boardSquares[9][2].sendIcon(yellowTriangle);
        ///
        boardSquares[9][2].setSquareColour("yellow");
        ///
        targetMap.put("yellowTriangle", yellowTriangle);
    }
    
    /**
     * For the complex map, set up sepcific squares of the board with
     * appropriate coloured diagonal bars
     */
    private void setUpDiagonalBars()
    {
        //Red Diagonal at location (7,11)
        boardSquares[7][11].setIcon(redDiagonal);
        boardSquares[7][11].setDiagonal(true);
        ///
        boardSquares[7][11].setSquareColour("red");
        ///
        
        //Red Diagonal at location (13,1)
        boardSquares[13][1].setIcon(redDiagonal);
        boardSquares[13][1].setDiagonal(true);
        ///
        boardSquares[13][1].setSquareColour("red");
        ///
        
        //Blue Diagonal at location (2,14)
        boardSquares[2][14].setIcon(blueDiagonal);
        boardSquares[2][14].setDiagonal(true);
        ///
        boardSquares[2][14].setSquareColour("blue");
        ///
        
        //Blue Diagonal at location (14,11)
        boardSquares[14][11].setIcon(blueDiagonal);
        boardSquares[14][11].setDiagonal(true);
        ///
        boardSquares[14][11].setSquareColour("blue");
        ///
        
        //Yellow Diagonal at location (7,5)
        boardSquares[7][5].setIcon(yellowDiagonal);
        boardSquares[7][5].setDiagonal(true);
        ///
        boardSquares[7][5].setSquareColour("yellow");
        ///
        
        //Yellow Diagonal at location (12,9)
        boardSquares[12][9].setIcon(yellowDiagonal);
        boardSquares[12][9].setDiagonal(true);
        ///
        boardSquares[12][9].setSquareColour("yellow");
        ///
        
        //Green Diagonal at location (1,4)
        boardSquares[1][4].setIcon(greenDiagonal);
        boardSquares[1][4].setDiagonal(true);
        ///
        boardSquares[1][4].setSquareColour("green");
        ///
        
        //Green Diagonal at location (12,6)
        boardSquares[12][6].setIcon(greenDiagonal);
        boardSquares[12][6].setDiagonal(true);
        ///
        boardSquares[12][6].setSquareColour("green");
        ///
        
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
        
        redRobot.setColour("red");
        Icon icon = redRobot.getIcon();
        boardSquares[x][y].setIcon(icon);
        boardSquares[x][y].changeRobotOnSquare();

        x = blueRobot.getrLocx();
        y = blueRobot.getrLocy();
           
        blueRobot.setColour("blue");
        Icon icon1 = blueRobot.getIcon();
        boardSquares[x][y].setIcon(icon1);
        boardSquares[x][y].changeRobotOnSquare();
           
        x = yellowRobot.getrLocx();
        y = yellowRobot.getrLocy();           
           
        yellowRobot.setColour("yellow");
        Icon icon2 = yellowRobot.getIcon();
        boardSquares[x][y].setIcon(icon2);
        boardSquares[x][y].changeRobotOnSquare();
           
        x = greenRobot.getrLocx();
        y = greenRobot.getrLocy();          
           
        greenRobot.setColour("green");
        Icon icon3 = greenRobot.getIcon();
        boardSquares[x][y].setIcon(icon3);
        boardSquares[x][y].changeRobotOnSquare();
    }
    
    /*
     * Alternate setRobot method to be used when loading data
     */
    public static void setRobot(int redX, int redY, int blueX, int blueY,
                                int yellowX, int yellowY, int greenX, int greenY)
    {
       int x = redX;
       int y = redY;
         
       ImageIcon redRobotIcon;
       if(colourAssist == false)
       {
           redRobotIcon = new ImageIcon(((new ImageIcon("Resources/redRobot.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
       }
       else
       {
           redRobotIcon = new ImageIcon(((new ImageIcon("Resources/Assist/redRobot.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
       }
       //redRobot = new Robot(redX, redY, redRobotIcon);
       //Icon icon = redRobot.getIcon();
       boardSquares[x][y].setIcon(redRobotIcon);
       boardSquares[x][y].changeRobotOnSquare();
       ComplexRobot.setrLocx(redRobot, redX);
       ComplexRobot.setrLocy(redRobot, redY);

       x = blueX;
       y = blueY;
       
       ImageIcon blueRobotIcon;
       if(colourAssist == false)
       {
           blueRobotIcon = new ImageIcon(((new ImageIcon("Resources/blueRobot.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
       }
       else
       {
           blueRobotIcon = new ImageIcon(((new ImageIcon("Resources/Assist/blueRobot.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
       }
       //blueRobot = new Robot(blueX, blueY, blueRobotIcon);
       //Icon icon1 = blueRobot.getIcon();
       boardSquares[x][y].setIcon(blueRobotIcon);
       boardSquares[x][y].changeRobotOnSquare();
       ComplexRobot.setrLocx(blueRobot, blueX);
       ComplexRobot.setrLocy(blueRobot, blueY);
       
       x = yellowX;
       y = yellowY;           
       
       ImageIcon yellowRobotIcon;
       if(colourAssist == false)
       {
           yellowRobotIcon = new ImageIcon(((new ImageIcon("Resources/yellowRobot.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
       }
       else
       {
           yellowRobotIcon = new ImageIcon(((new ImageIcon("Resources/Assist/yellowRobot.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
       }
       //yellowRobot = new Robot(yellowX, yellowY, yellowRobotIcon);
       //Icon icon2 = yellowRobot.getIcon();
       boardSquares[x][y].setIcon(yellowRobotIcon);
       boardSquares[x][y].changeRobotOnSquare();
       ComplexRobot.setrLocx(yellowRobot, yellowX);
       ComplexRobot.setrLocy(yellowRobot, yellowY);
       
       x = greenX;
       y = greenY;         
       
       ImageIcon greenRobotIcon;
       if(colourAssist == false)
       {
           greenRobotIcon = new ImageIcon(((new ImageIcon("Resources/greenRobot.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
       }
       else
       {
           greenRobotIcon = new ImageIcon(((new ImageIcon("Resources/Assist/greenRobot.png")).getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
       }
       //greenRobot = new Robot(greenX, greenY, greenRobotIcon);
       //Icon icon3 = greenRobot.getIcon();
       boardSquares[x][y].setIcon(greenRobotIcon);
       boardSquares[x][y].changeRobotOnSquare();
       ComplexRobot.setrLocx(greenRobot, greenX);
       ComplexRobot.setrLocy(greenRobot, greenY);
    }
    
    public void saveData()
    {
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("boardSaveFile.txt"));
            
            //Player scores
            bw.write(""+scores[0]);
            bw.newLine();
            bw.write(""+scores[1]);
            bw.newLine();
            bw.write(""+scores[2]);
            bw.newLine();
            bw.write(""+scores[3]);
            bw.newLine();
            
            //Robot current positions
            bw.write(""+redRobot.getrLocx());
            bw.newLine();
            bw.write(""+redRobot.getrLocy());
            bw.newLine();
            bw.write(""+blueRobot.getrLocx());
            bw.newLine();
            bw.write(""+blueRobot.getrLocy());
            bw.newLine();
            bw.write(""+yellowRobot.getrLocx());
            bw.newLine();
            bw.write(""+yellowRobot.getrLocy());
            bw.newLine();
            bw.write(""+greenRobot.getrLocx());
            bw.newLine();
            bw.write(""+greenRobot.getrLocy());
            bw.newLine();
            
            //Current target
            currentTargetString = currentTurn.getChosenTargetSpace();
            bw.write(currentTargetString);
            bw.newLine();
            
            //Robot original/reset positions
            bw.write(""+redOriginalX);
            bw.newLine();
            bw.write(""+redOriginalY);
            bw.newLine();
            bw.write(""+blueOriginalX);
            bw.newLine();
            bw.write(""+blueOriginalY);
            bw.newLine();
            bw.write(""+yellowOriginalX);
            bw.newLine();
            bw.write(""+yellowOriginalY);
            bw.newLine();
            bw.write(""+greenOriginalX);
            bw.newLine();
            bw.write(""+greenOriginalY);
            bw.newLine();
            
            //Saving current robot
            if(currentRobot == redRobot)
            {
                bw.write("redRobot");
            }
            else if(currentRobot == blueRobot)
            {
                bw.write("blueRobot");
            }
            else if(currentRobot == yellowRobot)
            {
                bw.write("yellowRobot");
            }
            else if(currentRobot == greenRobot)
            {
                bw.write("greenRobot");
            }
            bw.newLine();
            
            bw.close();
        }
        catch(Exception e){
            
        }
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
    public static ComplexRobot getCurrentRobot()
    {
        return currentRobot;
    }
    
    public static void setCurrentRobot(String robot)
    {
        if(robot.equals("redRobot"))
        {
            currentRobot = redRobot;
        }
        else if(robot.equals("blueRobot"))
        {
            currentRobot = blueRobot;
        }
        else if(robot.equals("yellowRobot"))
        {
            currentRobot = yellowRobot;
        }
        else if(robot.equals("greenRobot"))
        {
            currentRobot = greenRobot;
        }
    }
    
    //Return the currently setup boardSquare()
    public static BoardSquare[][] getBoardSquare()
    {
        return boardSquares;
    }
    
    public ComplexRobot getRedRobot()
    {
        return redRobot;
    }
    
    public ComplexRobot getGreenRobot()
    {
        return greenRobot;
    }
    
    public ComplexRobot getYellowRobot()
    {
        return yellowRobot;
    }
    
    public ComplexRobot getBlueRobot()
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
                        ////To review code since it was uncommented
                        time.setText("Time Left: 60");
                        timer.stop();
                        //timer.restart();
                        start = 60;
                        startTheTimer();
                    }
                } catch(NumberFormatException e) {
                    previous.setBackground(Color.GREEN);
                }
            }
        }
        
        //End game and winner announcement
        if (aevt.getSource() == endGame)
        {
            int winner = -1;
            int winnerNum = 0;
            int draw = 0;
            int drawNum = 0;
            int count = 0;
            int num = 0;
            int numI = 0;
            
            if(loadingOrNot == false)
            {
                for (int i = 0; i < 10; i = i + 3)
                {
                    if ((Boolean)StartFrame.getPlayerData().get(i))
                    {
                        if (scores[num] > winner)
                        {
                            winner = scores[num];
                            winnerNum = num + 1;
                            
                            draw = scores[num];
                            drawNum = num;
                            for (int j = 0; j < 10; j = j + 3)
                            {
                                if ((Boolean)StartFrame.getPlayerData().get(j))
                                {
                                    if ((drawNum != numI) && (scores[numI] == draw))
                                    {
                                       count = 1;
                                    }
                                    numI++;
                                }
                            }
                            numI = 0;
                        }
                        num++;
                    }
                }
            }
            else
            {
                int numOfPlayers = 0;
                if(p1Select == true)
                {
                    numOfPlayers++;
                }
                if(p2Select == true)
                {
                    numOfPlayers++;
                }
                if(p3Select == true)
                {
                    numOfPlayers++;
                }
                if(p4Select == true)
                {
                    numOfPlayers++;
                }
                
                for (int i = 0; i < numOfPlayers; i = i + 1)
                {
                    if (scores[num] > winner)
                    {
                            winner = scores[num];
                            winnerNum = num + 1;
                            
                            draw = scores[num];
                            drawNum = num;
                            for (int j = 0; j < numOfPlayers; j = j + 1)
                            {
                                if ((drawNum != numI) && (scores[numI] == draw))
                                {
                                   count = 1;
                                }
                                numI++;
                            }
                            numI = 0;
                    }
                    num++;
                }
            }
            
            JFrame stats = new JFrame("STATS");
            if (count == 0)
            {
                JOptionPane.showMessageDialog(stats, ("The winner of this game is PLAYER " + winnerNum + "."));
            } else {
                JOptionPane.showMessageDialog(stats, ("The match is a draw."));
            }
            
            //Resetting all the player scores
            if(loadingOrNot == false)
            {
                num = 0;
                for (int k = 0; k < 10; k = k + 3)
                {
                    if ((Boolean)StartFrame.getPlayerData().get(k))
                    {
                        scores[num] = 0;
                        num++;
                    }
                }
            }
            else
            {
                if(p1Select == true)
                {
                    scores[0] = 0;
                }
                if(p2Select == true)
                {
                    scores[1] = 0;
                }
                if(p3Select == true)
                {
                    scores[2] = 0;
                }
                if(p4Select == true)
                {
                    scores[3] = 0;
                }
            }
            
            dispose();
            new MainFrame();
        }
    }
    
    
    public void mouseEntered(MouseEvent arg0){}
    public void mouseExited(MouseEvent arg0){}
    public void mousePressed(MouseEvent arg0){}
    public void mouseReleased(MouseEvent arg0){}
}