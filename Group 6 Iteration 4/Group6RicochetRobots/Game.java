import java.awt.*;
import javax.swing.*;

/**
 * Game class which invokes the main class
 *
 * @author Yudish
 * @version 0.1
 */
public class Game
{
    public static void main(String[] args) {       
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame();
            }
        });
    }
}
