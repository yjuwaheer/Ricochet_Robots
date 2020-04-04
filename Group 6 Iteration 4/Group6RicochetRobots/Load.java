import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.util.*;

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
    
    //From StartFrame class
    private boolean simpleMapSelected;
    private boolean colourAssistSelected;
    
    private boolean p1Selected, p2Selected, p3Selected, p4Selected;
    
    private String p1Type, p2Type, p3Type, p4Type;
    
    private String c1Type, c2Type, c3Type, c4Type;
    //
    
    //From SimpleMap class
    private int p1Score, p2Score, p3Score, p4Score;
    
    private int redX, redY, blueX, blueY, yellowX, yellowY, greenX, greenY;
    
    private String currentTargetString;
    public Icon currentTarget;
    
    private int redOX, redOY, blueOX, blueOY, yellowOX, yellowOY, greenOX, greenOY;
    
    private String currentRobot;
    //
    
    public Load()
    {
        // super("Ricochet Robots | Load");
        // //Setting the icon of the frame
        // Image rrIcon =  Toolkit.getDefaultToolkit().getImage("Resources/rr.jpg");
        // setIconImage(rrIcon);
        // //
        // menu();
        // setJMenuBar(frameMenu);
        // settingPanel();
        // add(main); //Adding main panel
        // setPreferredSize(new Dimension(500, 500));
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // pack();
        // setLocationRelativeTo(null);
        // setVisible(true);
        
        loadData();
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
    
    public void loadData()
    {
        try{
            //Loading data from the StartFrame class
            BufferedReader br = new BufferedReader(new FileReader("saveFile.txt"));
            
            simpleMapSelected = Boolean.parseBoolean(br.readLine());
            
            colourAssistSelected = Boolean.parseBoolean(br.readLine());
            
            p1Selected = Boolean.parseBoolean(br.readLine());
            p2Selected = Boolean.parseBoolean(br.readLine());
            p3Selected = Boolean.parseBoolean(br.readLine());
            p4Selected = Boolean.parseBoolean(br.readLine());
            
            p1Type = br.readLine();
            p2Type = br.readLine();
            p3Type = br.readLine();
            p4Type = br.readLine();
            
            c1Type = br.readLine();
            c2Type = br.readLine();
            c3Type = br.readLine();
            c4Type = br.readLine();
            
            br.close();
            //
            
            //Loading data from the SimpleMap or ComplexMap class
            BufferedReader br2 = new BufferedReader(new FileReader("boardSaveFile.txt"));
            
            p1Score = Integer.parseInt(br2.readLine());
            p2Score = Integer.parseInt(br2.readLine());
            p3Score = Integer.parseInt(br2.readLine());
            p4Score = Integer.parseInt(br2.readLine());
            
            redX = Integer.parseInt(br2.readLine());
            redY = Integer.parseInt(br2.readLine());
            blueX = Integer.parseInt(br2.readLine());
            blueY = Integer.parseInt(br2.readLine());
            yellowX = Integer.parseInt(br2.readLine());
            yellowY = Integer.parseInt(br2.readLine());
            greenX = Integer.parseInt(br2.readLine());
            greenY = Integer.parseInt(br2.readLine());
            
            currentTargetString = br2.readLine();
            
            redOX = Integer.parseInt(br2.readLine());
            redOY = Integer.parseInt(br2.readLine());
            blueOX = Integer.parseInt(br2.readLine());
            blueOY = Integer.parseInt(br2.readLine());
            yellowOX = Integer.parseInt(br2.readLine());
            yellowOY = Integer.parseInt(br2.readLine());
            greenOX = Integer.parseInt(br2.readLine());
            greenOY = Integer.parseInt(br2.readLine());
            
            currentRobot = br2.readLine();
            
            br2.close();
            //
            
            if(simpleMapSelected == true)
            {
                new SimpleMap(colourAssistSelected,p1Selected, p2Selected, 
                              p3Selected, p4Selected, p1Type, p2Type, p3Type,
                              p4Type, c1Type, c2Type, c3Type, c4Type);
                
                //Passing player scores & robot positions to SimpleMap
                if(p1Selected == true)
                {
                    SimpleMap.scores[0] = p1Score;
                    SimpleMap.playerScores[0].setText("Player 1: " + p1Score);
                }
                if(p2Selected == true)
                {
                    SimpleMap.scores[1] = p2Score;
                    SimpleMap.playerScores[1].setText("Player 2: " + p2Score);
                }
                if(p3Selected == true)
                {
                    SimpleMap.scores[2] = p3Score;
                    SimpleMap.playerScores[2].setText("Player 3: " + p3Score);
                }
                if(p4Selected == true)
                {
                    SimpleMap.scores[3] = p4Score;
                    SimpleMap.playerScores[3].setText("Player 4: " + p4Score);
                }
                
                SimpleMap.setRobot(redX, redY, blueX, blueY, yellowX, yellowY,
                                   greenX, greenY);
                                   
                //Loading the current target space   
                SimpleMap.setCurrentTargetString(currentTargetString);
                HashMap<String, Icon> targetSpaceMap = SimpleMap.getTargetSpaceMap();
                currentTarget = targetSpaceMap.get(currentTargetString);
                SimpleMap.setCurrentTarget(currentTarget);
                SimpleMap.setTargetIcon(currentTarget);
                
                //Loading the robot's original positions
                SimpleMap.setRedOriginalX(redOX);
                SimpleMap.setRedOriginalY(redOY);
                SimpleMap.setBlueOriginalX(blueOX);
                SimpleMap.setBlueOriginalY(blueOY);
                SimpleMap.setYellowOriginalX(yellowOX);
                SimpleMap.setYellowOriginalY(yellowOY);
                SimpleMap.setGreenOriginalX(greenOX);
                SimpleMap.setGreenOriginalX(greenOY);
                
                //Loading the current robot
                // SimpleMap.setCurrentRobot(currentRobot);
            }
            else
            {
                new ComplexMap(colourAssistSelected,p1Selected, p2Selected, 
                              p3Selected, p4Selected, p1Type, p2Type, p3Type,
                              p4Type, c1Type, c2Type, c3Type, c4Type);
                
                //Passing player scores & robot positions to SimpleMap
                if(p1Selected == true)
                {
                    ComplexMap.scores[0] = p1Score;
                    ComplexMap.playerScores[0].setText("Player 1: " + p1Score);
                }
                if(p2Selected == true)
                {
                    ComplexMap.scores[1] = p2Score;
                    ComplexMap.playerScores[1].setText("Player 2: " + p2Score);
                }
                if(p3Selected == true)
                {
                    ComplexMap.scores[2] = p3Score;
                    ComplexMap.playerScores[2].setText("Player 3: " + p3Score);
                }
                if(p4Selected == true)
                {
                    ComplexMap.scores[3] = p4Score;
                    ComplexMap.playerScores[3].setText("Player 4: " + p4Score);
                }
                
                ComplexMap.setRobot(redX, redY, blueX, blueY, yellowX, yellowY,
                                   greenX, greenY);
                                   
                //Loading the current target space   
                ComplexMap.setCurrentTargetString(currentTargetString);
                HashMap<String, Icon> targetSpaceMap = ComplexMap.getTargetSpaceMap();
                currentTarget = targetSpaceMap.get(currentTargetString);
                ComplexMap.setCurrentTarget(currentTarget);
                ComplexMap.setTargetIcon(currentTarget);
                
                //Loading the robot's original positions
                ComplexMap.setRedOriginalX(redOX);
                ComplexMap.setRedOriginalY(redOY);
                ComplexMap.setBlueOriginalX(blueOX);
                ComplexMap.setBlueOriginalY(blueOY);
                ComplexMap.setYellowOriginalX(yellowOX);
                ComplexMap.setYellowOriginalY(yellowOY);
                ComplexMap.setGreenOriginalX(greenOX);
                ComplexMap.setGreenOriginalX(greenOY);
                
                //Loading the current robot
                ComplexMap.setCurrentRobot(currentRobot);
            }
        }
        catch(Exception e){
            
        }
    }
}
