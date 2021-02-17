import java.awt.BorderLayout;
import javax.swing.JFrame;


public class Main
{
  public static void main(String[] args)
  {
    JFrame window = new JFrame("MineSweeper");

    MineSweeper2 mineSweeper = new MineSweeper2();

    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setSize(700, 700);
    window.add(mineSweeper);
    window.setResizable(false);
    window.setVisible(true);
  }
}
