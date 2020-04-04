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
public class ComplexRobot
{
    private int rLocx; //Postion x of robot
    private int rLocy; //Position y of robot
    private ImageIcon roboticon;
    private boolean selected = false;
    private Icon temp;
    private String colour;
    
    ////
    private static int newX, newY;
    private int initialX, initialY;
    
    //the number of times a robot has hit a diagonal barrier during one move
    private int diagonalCounter = 0; 

    /**
     * Constructor for objects of class Robot
     */
    public ComplexRobot()///String color)
    {
        //Sets a random location for robot ignoring the middle positions
        rLocx = (int)(Math.random()*16);
        rLocy = (int)(Math.random()*16);
        while((rLocx == 7 && rLocy == 7) 
        || (rLocx == 7 && rLocy == 8) 
        || (rLocx == 8 && rLocy == 7) 
        || (rLocx == 8 && rLocy == 8)
        || (rLocx == 3 && rLocy == 1)    ////For Targets
        || (rLocx == 1 && rLocy == 10)
        || (rLocx == 6 && rLocy == 2)
        || (rLocx == 4 && rLocy == 8)
        || (rLocx == 4 && rLocy == 6)
        || (rLocx == 5 && rLocy == 13)
        || (rLocx == 6 && rLocy == 3)
        || (rLocx == 6 && rLocy == 13)
        || (rLocx == 10 && rLocy == 7)
        || (rLocx == 13 && rLocy == 3)
        || (rLocx == 9 && rLocy == 10)
        || (rLocx == 12 && rLocy == 3)
        || (rLocx == 11 && rLocy == 12)
        || (rLocx == 11 && rLocy == 13)
        || (rLocx == 14 && rLocy == 5)
        || (rLocx == 13 && rLocy == 9)
        || (rLocx == 9 && rLocy == 2) 
        || (rLocx == 15 && rLocy == 6) //Next to barriers
        || (rLocx == 15 && rLocy == 7)
        || (rLocx == 15 && rLocy == 10)
        || (rLocx == 15 && rLocy == 11)
        || (rLocx == 13 && rLocy == 15)
        || (rLocx == 12 && rLocy == 15)
        || (rLocx == 2 && rLocy == 15)
        || (rLocx == 3 && rLocy == 15)
        || (rLocx == 0 && rLocy == 5)
        || (rLocx == 0 && rLocy == 6)
        || (rLocx == 0 && rLocy == 8)
        || (rLocx == 0 && rLocy == 9)
        || (rLocx == 6 && rLocy == 0)
        || (rLocx == 5 && rLocy == 0)
        || (rLocx == 11 && rLocy == 0)
        || (rLocx == 10 && rLocy == 0)
        //FOR DIAGONAL AS WELL
        || (rLocx == 7 && rLocy == 11)
        || (rLocx == 13 && rLocy == 1)
        || (rLocx == 2 && rLocy == 14)
        || (rLocx == 14 && rLocy == 11)
        || (rLocx == 10 && rLocy == 0)
        || (rLocx == 7 && rLocy == 5)
        || (rLocx == 12 && rLocy == 9)
        || (rLocx == 1 && rLocy == 4)
        || (rLocx == 12 && rLocy == 6)) 
        {
            rLocx = (int)Math.random()*16;
            rLocy = (int)Math.random()*16;
        }
        
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
    public static void setrLocx(ComplexRobot robot, int locx)
    {
        robot.rLocx = locx;
    }
    
    public static void setrLocy(ComplexRobot robot, int locy)
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
    
    public void move(int x, int y)
    {
        String direction;
        if(x == rLocx && y == rLocy + 1) {
            //moving right
            if(diagonalCounter == 0)
            {
                initialX = rLocx;
                initialY = rLocy;
            }
            int newX = rLocx;
            int newY = rLocy;
            boolean diagonalOrNot = false;
            direction = "right";
            
            while(!ComplexMap.getBoardSquare(newX, newY).isRightEdge()
            && !ComplexMap.getBoardSquare(newX, newY + 1).getRobotOnSquare()) 
            {
                newY++;
                ////
                diagonalOrNot = checkDiagonal(newX, newY);
                if(diagonalOrNot == true)
                {
                    rLocx = newX;
                    rLocy = newY;
                    handleDiagonalBarrier(newX, newY, direction);
                    return;
                }
            }
            
            if(newX != initialX || newY != initialY)
            {
                ComplexMap.replaceIcon(newX, newY, roboticon);
                Icon originalIcon = findIcon(initialX, initialY);
                ComplexMap.replaceIcon(initialX, initialY, originalIcon);
            
                ComplexMap.getBoardSquare(newX, newY).changeRobotOnSquare();
                ComplexMap.getBoardSquare(initialX, initialY).changeRobotOnSquare();
            
                rLocx = newX;
                rLocy = newY;
                
                diagonalCounter = 0;
                
                ///To increment moves when time has been exhausted
                if (ComplexMap.getTimeExh().equals("exhausted"))
                {
                     ComplexMap.getCurrentTurn().getCurrentSolution().addToMoves();
                } else {
                    JFrame info = new JFrame("INFO");
                    JOptionPane.showMessageDialog(info,"Select player, bid and wait for time to exhaust.");
                    ComplexMap.resetEastLayout();
                }
                ///
            }
        }
        else if(x == rLocx && y == rLocy - 1) {
            //moving left
            if(diagonalCounter == 0)
            {
                initialX = rLocx;
                initialY = rLocy;
            }
            int newX = rLocx;
            int newY = rLocy;
            boolean diagonalOrNot = false;
            direction = "left";
            
            while(!ComplexMap.getBoardSquare(newX, newY).isLeftEdge()
            && !ComplexMap.getBoardSquare(newX, newY - 1).getRobotOnSquare()) 
            {
                newY--;
                ////
                diagonalOrNot = checkDiagonal(newX, newY);
                if(diagonalOrNot == true)
                {
                    rLocx = newX;
                    rLocy = newY;
                    handleDiagonalBarrier(newX, newY, direction);
                    return;
                }
            }
            if(newX != initialX || newY != initialY)
            {
                ComplexMap.replaceIcon(newX, newY, roboticon);
                Icon originalIcon = findIcon(initialX, initialY);
                ComplexMap.replaceIcon(initialX, initialY, originalIcon);
            
                ComplexMap.getBoardSquare(newX, newY).changeRobotOnSquare();
                ComplexMap.getBoardSquare(initialX, initialY).changeRobotOnSquare();
            
                rLocx = newX;
                rLocy = newY;
                
                diagonalCounter = 0;
                
                ///To increment moves when time has been exhausted
                if (ComplexMap.getTimeExh().equals("exhausted"))
                {
                     ComplexMap.getCurrentTurn().getCurrentSolution().addToMoves();
                } else {
                    JFrame info = new JFrame("INFO");
                    JOptionPane.showMessageDialog(info,"Select player, bid and wait for time to exhaust.");
                    ComplexMap.resetEastLayout();
                }
                ///
            }
        }
        else if(x == rLocx + 1 && y == rLocy) {
            //moving down
            if(diagonalCounter == 0)
            {
                initialX = rLocx;
                initialY = rLocy;
            }
            int newX = rLocx;
            int newY = rLocy;
            boolean diagonalOrNot = false;
            direction = "down";

            while(!ComplexMap.getBoardSquare(newX, newY).isBottomEdge()
            && !ComplexMap.getBoardSquare(newX + 1, newY).getRobotOnSquare()) 
            {
                newX++;
                ////
                diagonalOrNot = checkDiagonal(newX, newY);
                if(diagonalOrNot == true)
                {
                    rLocx = newX;
                    rLocy = newY;
                    handleDiagonalBarrier(newX, newY, direction);
                    return;
                }
            }
            
            if(newX != initialX || newY != initialY)
            {
                ComplexMap.replaceIcon(newX, newY, roboticon);
                Icon originalIcon = findIcon(initialX, initialY);
                ComplexMap.replaceIcon(initialX, initialY, originalIcon);
            
                ComplexMap.getBoardSquare(newX, newY).changeRobotOnSquare();
                ComplexMap.getBoardSquare(initialX, initialY).changeRobotOnSquare();
            
                rLocx = newX;
                rLocy = newY;
                
                diagonalCounter = 0;
                
                ///To increment moves when time has been exhausted
                if (ComplexMap.getTimeExh().equals("exhausted"))
                {
                     ComplexMap.getCurrentTurn().getCurrentSolution().addToMoves();
                } else {
                    JFrame info = new JFrame("INFO");
                    JOptionPane.showMessageDialog(info,"Select player, bid and wait for time to exhaust.");
                    ComplexMap.resetEastLayout();
                }
                ///
            }
        }
        else if(x == rLocx - 1 && y == rLocy) {
            //moving up
            if(diagonalCounter == 0)
            {
                initialX = rLocx;
                initialY = rLocy;
            }
            int newX = rLocx;
            int newY = rLocy;
            boolean diagonalOrNot = false;
            direction = "up";
            
            while(!ComplexMap.getBoardSquare(newX, newY).isTopEdge()
            && !ComplexMap.getBoardSquare(newX - 1, newY).getRobotOnSquare()) 
            {
                newX--;
                ////
                diagonalOrNot = checkDiagonal(newX, newY);
                if(diagonalOrNot == true)
                {
                    rLocx = newX;
                    rLocy = newY;
                    handleDiagonalBarrier(newX, newY, direction);
                    return;
                }
            }
            
            if(newX != initialX || newY != initialY)
            {
                ComplexMap.replaceIcon(newX, newY, roboticon);
                Icon originalIcon = findIcon(initialX, initialY);
                ComplexMap.replaceIcon(initialX, initialY, originalIcon);
            
                ComplexMap.getBoardSquare(newX, newY).changeRobotOnSquare();
                ComplexMap.getBoardSquare(initialX, initialY).changeRobotOnSquare();
            
                rLocx = newX;
                rLocy = newY;
                
                diagonalCounter = 0;
                
                ///To increment moves when time has been exhausted
                if (ComplexMap.getTimeExh().equals("exhausted"))
                {
                     ComplexMap.getCurrentTurn().getCurrentSolution().addToMoves();
                } else {
                    JFrame info = new JFrame("INFO");
                    JOptionPane.showMessageDialog(info,"Select player, bid and wait for time to exhaust.");
                    ComplexMap.resetEastLayout();
                }
                ///
            }
        }
        else {
            ; //do nothing
        }
    }
    
    /* 
     * Method to check if the square is diagonal
    */
    private boolean checkDiagonal(int xC, int yC)
    {
        if(ComplexMap.getBoardSquare(xC, yC).isDiagonal())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    //Gets and returns the colour of a diagonal barrier square
    private String getDiagonalColour(int xC, int yC)
    {
        return ComplexMap.getBoardSquare(xC, yC).getSquareColour();
    }
    
    private void handleDiagonalBarrier(int x, int y, String dir)
    {
        String colourOfDiagonal = getDiagonalColour(x, y);
        diagonalCounter++;
        
        //When the colour of the robot matches the colour of the barrier,
        //continue to move in the same direction as before
        if(colour.equals(colourOfDiagonal))
        {
            if(dir.equals("right"))
            {
                move(rLocx, rLocy + 1);
            }
            else if(dir.equals("left"))
            {
                move(rLocx, rLocy - 1);
            }
            else if(dir.equals("down"))
            {
                move(rLocx + 1, rLocy);
            }
            else if(dir.equals("up"))
            {
                move(rLocx - 1, rLocy);
            }
        }
        //Else, the direction changes based on the direction of the
        //diagonal barrier
        else
        {
            if(dir.equals("right") && (colourOfDiagonal.equals("red")) ||
                                      (colourOfDiagonal.equals("green")))
            {
                //move up
                move(rLocx - 1, rLocy);
            }
            else if(dir.equals("left") && (colourOfDiagonal.equals("red")) ||
                                          (colourOfDiagonal.equals("green")))
            {
                //move down
                move(rLocx + 1, rLocy);
            }
            else if(dir.equals("down") && (colourOfDiagonal.equals("red")) ||
                                          (colourOfDiagonal.equals("green")))
            {
                //move left
                move(rLocx, rLocy - 1);
            }
            else if(dir.equals("up") && (colourOfDiagonal.equals("red")) ||
                                        (colourOfDiagonal.equals("green")))
            {
                //move right
                move(rLocx, rLocy + 1);
            }
            else if(dir.equals("right") && (colourOfDiagonal.equals("blue")) ||
                                           (colourOfDiagonal.equals("yellow")))
            {
                //move down
                move(rLocx + 1, rLocy);
            }
            else if(dir.equals("left") && (colourOfDiagonal.equals("blue")) ||
                                          (colourOfDiagonal.equals("yellow")))
            {
                //move up
                move(rLocx - 1, rLocy);
            }
            else if(dir.equals("down") && (colourOfDiagonal.equals("blue")) ||
                                          (colourOfDiagonal.equals("yellow")))
            {
                //move right
                move(rLocx, rLocy + 1);
            }
            else if(dir.equals("up") && (colourOfDiagonal.equals("blue")) ||
                                        (colourOfDiagonal.equals("yellow")))
            {
                //move left
                move(rLocx, rLocy - 1);
            }
        }
    }
    
    //Finds and returns the appropriate icon for each square.
    public static Icon findIcon(int x, int y)
    {
        BoardSquare square = ComplexMap.getBoardSquare(x, y);
        String targetType = square.getTargetSpace();
        HashMap targetMap = ComplexMap.getTargetSpaceMap();
        
        if(targetType != null) 
        {
            return (Icon)targetMap.get(targetType);
        }
        else if(square.isLeftEdge() && y != 0 && !(x == 7 && y == 9)
        && !(x == 8 && y == 9)) 
        {
            return ComplexMap.leftSideBarrier;
        }
        else if(square.isRightEdge() && y != 15 && !(x == 7 && y == 6)
        && !(x == 8 && y == 6))
        {
            return ComplexMap.rightSideBarrier;
        }
        else if(square.isTopEdge() && x != 0 && !(x == 9 && y == 7)
        && !(x == 9 && y == 8))
        {
            return ComplexMap.topSideBarrier;
        }
        else if(square.isBottomEdge() && x != 15 && !(x == 6 && y == 7)
        && !(x == 6 && y == 8))
        {
            return ComplexMap.bottomSideBarrier;
        }
        else 
        {
            return ComplexMap.basicSquareIcon;
        }
    }
}