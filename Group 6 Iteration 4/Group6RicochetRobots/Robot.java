import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;
import java.lang.Math;
import java.util.*;

/**
 * The class Robot responsible to keep track of the location of the
 * robots and move them accordingly while maintaining pprevious icons.
 */
public class Robot
{
    private int rLocx; //Postion x of robot
    private int rLocy; //Position y of robot
    private ImageIcon roboticon;
    private boolean selected = false;
    private Icon temp;
    private String colour;

    /**
     * Constructor for objects of class Robot
     */
    public Robot()///String color)
    {
        //Sets a random location for robot ignoring the middle positions
        rLocx = (int)(Math.random()*16);
        rLocy = (int)(Math.random()*16);
        while((rLocx == 7 && rLocy == 7) 
        || (rLocx == 7 && rLocy == 8) 
        || (rLocx == 8 && rLocy == 7) 
        || (rLocx == 8 && rLocy == 8)
        || (rLocx == 1 && rLocy == 4)
        || (rLocx == 1 && rLocy == 13)
        || (rLocx == 2 && rLocy == 1)
        || (rLocx == 2 && rLocy == 9)
        || (rLocx == 3 && rLocy == 6)
        || (rLocx == 5 && rLocy == 14)
        || (rLocx == 6 && rLocy == 3)
        || (rLocx == 0 && rLocy == 1)
        || (rLocx == 0 && rLocy == 2)
        || (rLocx == 0 && rLocy == 11)
        || (rLocx == 0 && rLocy == 12)
        || (rLocx == 3 && rLocy == 15)
        || (rLocx == 4 && rLocy == 15)
        || (rLocx == 5 && rLocy == 0)
        || (rLocx == 6 && rLocy == 0)
        || (rLocx == 11 && rLocy == 0)
        || (rLocx == 12 && rLocy == 0)
        || (rLocx == 11 && rLocy == 15)
        || (rLocx == 10 && rLocy == 15)
        || (rLocx == 15 && rLocy == 6)
        || (rLocx == 15 && rLocy == 7)
        || (rLocx == 15 && rLocy == 11)
        || (rLocx == 15 && rLocy == 2)
        || (rLocx == 6 && rLocy == 11)
        || (rLocx == 8 && rLocy == 5)
        || (rLocx == 9 && rLocy == 1)
        || (rLocx == 9 && rLocy == 14)
        || (rLocx == 10 && rLocy == 4)
        || (rLocx == 10 && rLocy == 8)
        || (rLocx == 11 && rLocy == 13)
        || (rLocx == 13 && rLocy == 5)
        || (rLocx == 13 && rLocy == 10)
        || (rLocx == 14 && rLocy == 3)) 
        {
            rLocx = (int)Math.random()*16;
            rLocy = (int)Math.random()*16;
        }
        
    }

    /*
     * Alternate constructor to be used when loading data
     */
    public Robot(int x, int y, ImageIcon icon)
    {
        rLocx = x;
        rLocy = y;
        roboticon = icon;
    }
    
    public int getrLocx()
    {
        return rLocx;
    }
    
    public int getrLocy()
    {
        return rLocy;
    }
    
    ///Setting the robot location
    public static void setrLocx(Robot robot, int locx)
    {
        robot.rLocx = locx;
    }
    
    public static void setrLocy(Robot robot, int locy)
    {
        robot.rLocy = locy;
    }
    
    ///Set the colour of the robot
    public void setColour(String color)
    {
        colour = color;
    }
    
    
    ///Return the colour of the robot
    public String getColour()
    {
        return colour;
    }
    
    //Selects or deselects a robot
    public void changeState()
    {
        if(selected) {
            selected = false;
        } else {   
            selected = true;
        }
    }
    
    public boolean isSelected()
    {
        return selected;
    }
    
    public ImageIcon getIcon()
    {
        return roboticon;
    }
    
    public void setIcon(ImageIcon icon)
    {
        roboticon = new ImageIcon((icon.getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
    }
    
    public void actionPerformed(ActionEvent aevt)
    {
        if(aevt.getSource() == roboticon){
            changeState();
        }
    }
    
    //Squares around edges have some issues
    public void move(int x, int y)
    {
        if(x == rLocx && y == rLocy + 1) {
            //moving right
            int initialX = rLocx;
            int initialY = rLocy;
            int newX = rLocx;
            int newY = rLocy;
            
            while(!SimpleMap.getBoardSquare(newX, newY).isRightEdge()
            && !SimpleMap.getBoardSquare(newX, newY + 1).getRobotOnSquare()) 
            {
                newY++;
            }
            
            if(newX != initialX || newY != initialY)
            {
                SimpleMap.replaceIcon(newX, newY, roboticon);
                Icon originalIcon = findIcon(initialX, initialY);
                SimpleMap.replaceIcon(initialX, initialY, originalIcon);
            
                SimpleMap.getBoardSquare(newX, newY).changeRobotOnSquare();
                SimpleMap.getBoardSquare(initialX, initialY).changeRobotOnSquare();
            
                rLocx = newX;
                rLocy = newY;
                
                ///To increment moves when time has been exhausted
                if (SimpleMap.getTimeExh().equals("exhausted"))
                {
                     SimpleMap.getCurrentTurn().getCurrentSolution().addToMoves();
                } else {
                    JFrame info = new JFrame("INFO");
                    JOptionPane.showMessageDialog(info,"Select player, bid and wait for time to exhaust.");
                    SimpleMap.resetEastLayout();
                }
                ///
            }
        }
        else if(x == rLocx && y == rLocy - 1) {
            //moving left
            int initialX = rLocx;
            int initialY = rLocy;
            int newX = rLocx;
            int newY = rLocy;
            
            while(!SimpleMap.getBoardSquare(newX, newY).isLeftEdge()
            && !SimpleMap.getBoardSquare(newX, newY - 1).getRobotOnSquare()) 
            {
                newY--;
            }
            if(newX != initialX || newY != initialY)
            {
                SimpleMap.replaceIcon(newX, newY, roboticon);
                Icon originalIcon = findIcon(initialX, initialY);
                SimpleMap.replaceIcon(initialX, initialY, originalIcon);
            
                SimpleMap.getBoardSquare(newX, newY).changeRobotOnSquare();
                SimpleMap.getBoardSquare(initialX, initialY).changeRobotOnSquare();
            
                rLocx = newX;
                rLocy = newY;
                
                ///To increment moves when time has been exhausted
                if (SimpleMap.getTimeExh().equals("exhausted"))
                {
                     SimpleMap.getCurrentTurn().getCurrentSolution().addToMoves();
                } else {
                    JFrame info = new JFrame("INFO");
                    JOptionPane.showMessageDialog(info,"Select player, bid and wait for time to exhaust.");
                    SimpleMap.resetEastLayout();
                }
                ///
            }
        }
        else if(x == rLocx + 1 && y == rLocy) {
            //moving down
            int initialX = rLocx;
            int initialY = rLocy;
            int newX = rLocx;
            int newY = rLocy;

            while(!SimpleMap.getBoardSquare(newX, newY).isBottomEdge()
            && !SimpleMap.getBoardSquare(newX + 1, newY).getRobotOnSquare()) 
            {
                newX++;
            }
            
            if(newX != initialX || newY != initialY)
            {
                SimpleMap.replaceIcon(newX, newY, roboticon);
                Icon originalIcon = findIcon(initialX, initialY);
                SimpleMap.replaceIcon(initialX, initialY, originalIcon);
            
                SimpleMap.getBoardSquare(newX, newY).changeRobotOnSquare();
                SimpleMap.getBoardSquare(initialX, initialY).changeRobotOnSquare();
            
                rLocx = newX;
                rLocy = newY;
                
                ///To increment moves when time has been exhausted
                if (SimpleMap.getTimeExh().equals("exhausted"))
                {
                     SimpleMap.getCurrentTurn().getCurrentSolution().addToMoves();
                } else {
                    JFrame info = new JFrame("INFO");
                    JOptionPane.showMessageDialog(info,"Select player, bid and wait for time to exhaust.");
                    SimpleMap.resetEastLayout();
                }
                ///
            }
        }
        else if(x == rLocx - 1 && y == rLocy) {
            //moving up
            int initialX = rLocx;
            int initialY = rLocy;
            int newX = rLocx;
            int newY = rLocy;
            
            while(!SimpleMap.getBoardSquare(newX, newY).isTopEdge()
            && !SimpleMap.getBoardSquare(newX - 1, newY).getRobotOnSquare()) 
            {
                newX--;
            }
            
            if(newX != initialX || newY != initialY)
            {
                SimpleMap.replaceIcon(newX, newY, roboticon);
                Icon originalIcon = findIcon(initialX, initialY);
                SimpleMap.replaceIcon(initialX, initialY, originalIcon);
            
                SimpleMap.getBoardSquare(newX, newY).changeRobotOnSquare();
                SimpleMap.getBoardSquare(initialX, initialY).changeRobotOnSquare();
            
                rLocx = newX;
                rLocy = newY;
                
                ///To increment moves when time has been exhausted
                if (SimpleMap.getTimeExh().equals("exhausted"))
                {
                     SimpleMap.getCurrentTurn().getCurrentSolution().addToMoves();
                } else {
                    JFrame info = new JFrame("INFO");
                    JOptionPane.showMessageDialog(info,"Select player, bid and wait for time to exhaust.");
                    SimpleMap.resetEastLayout();
                }
                ///
            }
        }
        else {
            ; //do nothing
        }
        
    }
    
    //Finds and returns the appropriate icon for each square.
    public static Icon findIcon(int x, int y)
    {
        BoardSquare square = SimpleMap.getBoardSquare(x, y);
        String targetType = square.getTargetSpace();
        HashMap targetMap = SimpleMap.getTargetSpaceMap();
        
        if(targetType != null) 
        {
            return (Icon)targetMap.get(targetType);
        }
        else if(square.isLeftEdge() && y != 0 && !(x == 7 && y == 9)
        && !(x == 8 && y == 9)) 
        {
            return SimpleMap.leftSideBarrier;
        }
        else if(square.isRightEdge() && y != 15 && !(x == 7 && y == 6)
        && !(x == 8 && y == 6))
        {
            return SimpleMap.rightSideBarrier;
        }
        else if(square.isTopEdge() && x != 0 && !(x == 9 && y == 7)
        && !(x == 9 && y == 8))
        {
            return SimpleMap.topSideBarrier;
        }
        else if(square.isBottomEdge() && x != 15 && !(x == 6 && y == 7)
        && !(x == 6 && y == 8))
        {
            return SimpleMap.bottomSideBarrier;
        }
        else 
        {
            return SimpleMap.basicSquareIcon;
        }
    }
}