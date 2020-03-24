import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Write a description of class Solution here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Solution
{
    // instance variables - replace the example below with your own
    private int movesSoFar;
    private int playerNum;
    //hint
  
    ///
    
    ///
    
    /**
     * Constructor for objects of class Solution
     */
    public Solution(String currentPlayer)
    {
        movesSoFar = 0;
        playerNum = Integer.parseInt(currentPlayer.substring(6,7));
        ///System.out.println(playerNum);
    }

    /**
     * Increases the counter of moves made so far for the current solution 
     * by 1
     */
    public void addToMoves()
    {
        movesSoFar += 1;
        ///System.out.println(movesSoFar);
        checkStatus();
    }
    
    private void checkStatus()
    {
        if (movesSoFar <= SimpleMap.getBidValue())
        {
            if ((SimpleMap.getBoardSquare()[SimpleMap.getCurrentRobot().getrLocx()][SimpleMap.getCurrentRobot().getrLocy()].getTargetSpace()) != null)
            {
                if ((SimpleMap.getBoardSquare()[SimpleMap.getCurrentRobot().getrLocx()][SimpleMap.getCurrentRobot().getrLocy()].getTargetSpace())
                    .equals(Turn.getCurrentRandTarget()))  
                {
                    ///For correct robot colour and correct target colour
                    if ((SimpleMap.getBoardSquare()[SimpleMap.getCurrentRobot().getrLocx()][SimpleMap.getCurrentRobot().getrLocy()].getSquareColour())
                    .equals(SimpleMap.getCurrentRobot().getColour()))
                    {
                        if(checkIfValid() == true)
                        {
                            handleSuccessfulSolution();
                        }
                        else
                        {
                            handleUnsuccessfulSolution();
                        }
                    }
                    
                    ///To cater for multivortex
                    if ((SimpleMap.getBoardSquare()[SimpleMap.getCurrentRobot().getrLocx()][SimpleMap.getCurrentRobot().getrLocy()].getSquareColour())
                    .equals("multi"))
                    {
                        if(checkIfValid() == true)
                        {
                            handleSuccessfulSolution();
                        }
                        else
                        {
                            handleUnsuccessfulSolution();
                        }
                    }
                }
            }
        } else {
            handleUnsuccessfulSolution();
        }
    }
    
    private void handleSuccessfulSolution()
    {
        ///System.out.println("Success");
        updateScore();
        SimpleMap.setCurrentSolved(true);
        SimpleMap.updateCurrentTarget();
        SimpleMap.resetEastLayout();
    }
    
    private void handleUnsuccessfulSolution()
    {
        ///System.out.println("Exceeded moves");
        movesSoFar = 0;
        SimpleMap.setStillCurrentTarget(true);
        SimpleMap.setCurrentSolved(false);
        SimpleMap.resetEastLayout();
    }
    
    private boolean checkIfValid()
    {
        //Check if robot ricocheted at least once
        if(movesSoFar > 1)
        {
            //Check if the actual number of moves made is less than or 
            //equal to the bid entered
            if(SimpleMap.getBidValue() < movesSoFar)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }
    
    private void updateScore()
    {
        JLabel[] tempPScore = SimpleMap.getPlayerScoresLabel();
        int[] tempScore = SimpleMap.getPlayerScores();
        
        int previousScore = 0;
        previousScore = tempScore[playerNum - 1];
        
        tempScore[playerNum - 1] = previousScore + 10; //Using an incrementer of 10 for every successfull demonstration

        //Update corresponding label
        tempPScore[playerNum - 1].setText("Player " + playerNum + ": " + tempScore[playerNum - 1]);
    }
}
