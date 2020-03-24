import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;
import java.util.*;

/**
 * Implementation of the class ComplexMap.
 *
 * @author (All)
 * @version (0.1)
 */
public class ComplexMap extends JFrame implements ActionListener, MouseListener
{
    private JMenuBar frameMenu;
    
    private JPanel panel;
    private static BoardSquare [][] boardSquares;
    private int NUM_OF_SQUARES = 16;
    
    
    //Robots to be placed on board
    private static Robot redRobot = new Robot();
    private static Robot blueRobot = new Robot();
    private static Robot yellowRobot = new Robot();
    private static Robot greenRobot = new Robot();
    
    
    //Declaring the icons
    private Icon basicSquareIcon;
    private static Icon rightSideBarrier;
    private static Icon leftSideBarrier;
    private static Icon topSideBarrier;
    private static Icon bottomSideBarrier;

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
    
    public ComplexMap()
    {
        super("Ricochet Robots | Simple Map");
        //Setting the icon of the frame
        Image rrIcon =  Toolkit.getDefaultToolkit().getImage("Resources/rr.jpg");
        setIconImage(rrIcon);
        
        /*
        //Sets the number of robots
        this.num_of_robots = num_of_robots;
        */
        
        menu();
        setJMenuBar(frameMenu);
        
        //Method to distinguish between basic or vision assist ressources
        colorAssistance();
        
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
        
        setRobot();
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.SOUTH);
        
        ///setPreferredSize(new Dimension(800, 800));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
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
        boardSquares[7][11].setTargetSpace("redDiagonal");
        
        //Red Diagonal at location (13,1)
        boardSquares[13][1].setIcon(redDiagonal);
        boardSquares[13][1].setDiagonal(true);
        boardSquares[13][1].setTargetSpace("redDiagonal");
        
        //Blue Diagonal at location (2,14)
        boardSquares[2][14].setIcon(blueDiagonal);
        boardSquares[2][14].setDiagonal(true);
        boardSquares[2][14].setTargetSpace("blueDiagonal");
        
        //Blue Diagonal at location (14,11)
        boardSquares[14][11].setIcon(blueDiagonal);
        boardSquares[14][11].setDiagonal(true);
        boardSquares[14][11].setTargetSpace("blueDiagonal");
        
        //Yellow Diagonal at location (7,5)
        boardSquares[7][5].setIcon(yellowDiagonal);
        boardSquares[7][5].setDiagonal(true);
        boardSquares[7][5].setTargetSpace("yellowDiagonal");
        
        //Yellow Diagonal at location (12,9)
        boardSquares[12][9].setIcon(yellowDiagonal);
        boardSquares[12][9].setDiagonal(true);
        boardSquares[12][9].setTargetSpace("yellowDiagonal");
        
        //Green Diagonal at location (1,4)
        boardSquares[1][4].setIcon(greenDiagonal);
        boardSquares[1][4].setDiagonal(true);
        boardSquares[1][4].setTargetSpace("greenDiagonal");
        
        //Green Diagonal at location (12,6)
        boardSquares[12][6].setIcon(greenDiagonal);
        boardSquares[12][6].setDiagonal(true);
        boardSquares[12][6].setTargetSpace("greenDiagonal");
        
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
    
    //Method for actionPerformed()
    public void actionPerformed(ActionEvent aevt)
    {
        
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
            }
            
            else if (x == blueRobot.getrLocx() && y == blueRobot.getrLocy())
            {
                blueRobot.changeState();
            }
            
            else if (x == yellowRobot.getrLocx() && y == yellowRobot.getrLocy())
            {
                yellowRobot.changeState();
            }
            
            else if (x == greenRobot.getrLocx() && y == greenRobot.getrLocy())
            {
                greenRobot.changeState();
            }
            
            else if (redRobot.isSelected() && ((x == redRobot.getrLocx() && 
            y != redRobot.getrLocy()) || (x != redRobot.getrLocx() && 
            y == redRobot.getrLocy())))
            {
                redRobot.move(x,y);
            }
            
            else if (blueRobot.isSelected() && ((x == blueRobot.getrLocx() && 
            y != blueRobot.getrLocy()) || (x != blueRobot.getrLocx() && 
            y == blueRobot.getrLocy())))
            {
                blueRobot.move(x,y);
            }
            
            else if (yellowRobot.isSelected() && ((x == yellowRobot.getrLocx() && 
            y != yellowRobot.getrLocy()) || (x != yellowRobot.getrLocx() && 
            y == yellowRobot.getrLocy())))
            {
                yellowRobot.move(x,y);
            }
            
            else if (greenRobot.isSelected() && ((x == greenRobot.getrLocx() && 
            y != greenRobot.getrLocy()) || (x != greenRobot.getrLocx() && 
            y == greenRobot.getrLocy())))
            {
                greenRobot.move(x,y);
            }
        }
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
    
    public void mouseEntered(MouseEvent arg0){}
    public void mouseExited(MouseEvent arg0){}
    public void mousePressed(MouseEvent arg0){}
    public void mouseReleased(MouseEvent arg0){}
}