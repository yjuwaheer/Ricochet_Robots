import java.awt.Color;
import javax.swing.*;

/**
 * Each object of this class represents a single square on the board.
 * The edges of the squares can be set as a barrier and the appropriate
 * squares can be set are target spaces.
 */
 
public class BoardSquare extends JButton
{
    //Constants you can use to set the edges as barriers
    private boolean BARRIER = true;
    private boolean NOT_BARRIER = false;
   
    private int xcoord, ycoord;
    private boolean topEdge, bottomEdge, leftEdge, rightEdge;
    private boolean robotOnSquare;
    private Icon squareIcon;
    
    //For non-target squares, set this value to null
    //For target squares, set as (colour)Circle, (colour)Square, (colour)Triangle, (colour)Star, or multivortex
    //For example, blueCircle or redSquare
    private String targetSpace;
    
    public BoardSquare()
    {
        super();
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        
        //Each edge starts as being not a barrier by default
        this.topEdge = NOT_BARRIER;
        this.bottomEdge = NOT_BARRIER;
        this.leftEdge = NOT_BARRIER;
        this.rightEdge = NOT_BARRIER;
    }
    
    public void sendIcon(Icon icon)
    {
        squareIcon = icon;
    }
    
    public Icon getIcons()
    {
        return squareIcon; 
    }
    
    public boolean isLeftEdge()
    {
        return leftEdge;
    }
    
    public boolean isRightEdge()
    {
        return rightEdge;
    }
    
    public boolean isTopEdge()
    {
        return topEdge;
    }
    
    public boolean isBottomEdge()
    {
        return bottomEdge;
    }
    
    public void setTopEdge(boolean barrierOrNot)
    {
        topEdge = barrierOrNot;
    }
    
    public void setBottomEdge(boolean barrierOrNot)
    {
        bottomEdge = barrierOrNot;
    }
    
    public void setLeftEdge(boolean barrierOrNot)
    {
        leftEdge = barrierOrNot;
    }
    
    public void setRightEdge(boolean barrierOrNot)
    {
        rightEdge = barrierOrNot;
    }
    
    public void setXCoord(int x)
    {
        xcoord = x;
    }
    
    public void setYCoord(int y)
    {
        ycoord = y;
    }
    
    public int getXCoord()
    {
        return xcoord;
    }
   
    public int getYCoord()
    {
        return ycoord;
    }
    
    public boolean getRobotOnSquare()
    {
        return robotOnSquare;
    }
    
    public void changeRobotOnSquare()
    {
        if(robotOnSquare)
        robotOnSquare = false;
        else
        robotOnSquare = true;
    }
    
    public void setTargetSpace(String type)
    {
        targetSpace = type;
    }
}