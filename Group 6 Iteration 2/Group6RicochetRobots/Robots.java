import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;
import java.lang.Math;
/**
 * Write a description of class Robots here.
 *
 * @author (Faisal Younis)
 * @version (a version number or a date)
 */
public class Robots implements ActionListener
{
    // instance variables - replace the example below with your own
    private JButton robot1;
    private int rLocx; //Postion x of robot
    private int rLocy; //Position y of robot
    private ImageIcon roboticon;
    private int countMoves;
    private boolean selected = false;
    private Icon temp;
    
    /**
     * Constructor for objects of class Robots
     */
     public Robots()
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
        || (rLocx == 3 && rLocy == 1)
        || (rLocx == 3 && rLocy == 9)
        || (rLocx == 4 && rLocy == 6)
        || (rLocx == 5 && rLocy == 14)
        || (rLocx == 6 && rLocy == 3
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
        || (rLocx == 15 && rLocy == 2))
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
    public int getrLocx()
    {
        return rLocx;
    }
    
    public int getrLocy()
    {
        return rLocy;
    }
    
    public void addImage(ImageIcon icon)
    {
        roboticon = new ImageIcon((icon.getImage()).getScaledInstance(45,45,java.awt.Image.SCALE_SMOOTH));
    }
    
    public ImageIcon getIcon()
    {
        return roboticon;
    }
    
    public void changeState()
    {
        if(selected)
        selected = false;
        else
        selected = true;
    }
    
    public boolean isSelected()
    {
        return selected;
    }
    
    public void move(int x, int y)
    {
        if( y < rLocy)
        {
            int initialx = rLocx;
            int initialy = rLocy;
            SimpleMap.getBoardSquare(initialx,initialy).changeRobotOnSquare();
            if( !SimpleMap.getBoardSquare(rLocx, rLocy-1).getRobotOnSquare())
            {
            while(!SimpleMap.getBoardSquare(rLocx,rLocy).isLeftEdge() &&
            !SimpleMap.getBoardSquare(rLocx, rLocy-1).getRobotOnSquare())
            {
               rLocy--;
            }
        
            SimpleMap.replaceIcon(rLocx, rLocy, roboticon);
            SimpleMap.replaceIcon(initialx,initialy,SimpleMap
            .getBoardSquare(initialx,initialy).getIcons());           
            SimpleMap.getBoardSquare(rLocx,rLocy).changeRobotOnSquare();
            }
        }
        else if( y > rLocy)
        {   
            int initialx = rLocx;
            int initialy = rLocy;
            SimpleMap.getBoardSquare(initialx,initialy).changeRobotOnSquare();
            if( !SimpleMap.getBoardSquare(rLocx, rLocy+1).getRobotOnSquare())
            {
            while(!SimpleMap.getBoardSquare(rLocx,rLocy).isRightEdge() &&
            !SimpleMap.getBoardSquare(rLocx, rLocy+1).getRobotOnSquare())
            {
               rLocy++;
            }
            SimpleMap.replaceIcon(rLocx, rLocy, roboticon);
            SimpleMap.replaceIcon(initialx,initialy,SimpleMap
            .getBoardSquare(initialx,initialy).getIcons());            
            SimpleMap.getBoardSquare(rLocx,rLocy).changeRobotOnSquare();
            }
        }
        
        else if( x < rLocx)
        {
            int initialx = rLocx;
            int initialy = rLocy;
            SimpleMap.getBoardSquare(initialx,initialy).changeRobotOnSquare();
            if( !SimpleMap.getBoardSquare(rLocx-1, rLocy).getRobotOnSquare())
            {
            while(  !SimpleMap.getBoardSquare(rLocx,rLocy).isTopEdge() &&
            !SimpleMap.getBoardSquare(rLocx-1, rLocy).getRobotOnSquare())
            {
               rLocx--;
            }
            SimpleMap.replaceIcon(rLocx, rLocy, roboticon);
            SimpleMap.replaceIcon(initialx,initialy,SimpleMap
            .getBoardSquare(initialx,initialy).getIcons());
            SimpleMap.getBoardSquare(rLocx,rLocy).changeRobotOnSquare();
            }
        }
        
        else if( x > rLocx)
        {
           int initialx = rLocx;
           int initialy = rLocy;
           SimpleMap.getBoardSquare(initialx,initialy).changeRobotOnSquare();
           if( !SimpleMap.getBoardSquare(rLocx+1, rLocy).getRobotOnSquare())
           {
           while( !SimpleMap.getBoardSquare(rLocx,rLocy).isBottomEdge() &&
           !SimpleMap.getBoardSquare(rLocx+1, rLocy).getRobotOnSquare())
           {
              rLocx++;
           }
           SimpleMap.replaceIcon(rLocx, rLocy, roboticon);
           SimpleMap.replaceIcon(initialx,initialy,SimpleMap
           .getBoardSquare(initialx,initialy).getIcons());
           SimpleMap.getBoardSquare(rLocx,rLocy).changeRobotOnSquare();
           }
        }
    }
    
    public void actionPerformed(ActionEvent aevt)
    {
        if(aevt.getSource() == roboticon){
         changeState();
        }
    }
}
