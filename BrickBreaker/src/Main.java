import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Main
{
  public static void main(String[] args)
  {
    // creates new window object
    JFrame window = new JFrame("BLOCK DESTROYER!!!");

    // creates new block destruction game (jpanel)
    BlockDestruction game = new BlockDestruction();

    // set properties of window and adds jpanel
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setLayout(new BorderLayout());
    window.setSize(700, 700);
    window.add(game, BorderLayout.CENTER);
    window.setVisible(true);

  }
}