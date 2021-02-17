import javax.swing.JFrame;

public class Main
{
  public static void main(String args[])
  {
    JFrame window = new JFrame("Tetris");
    Tetris game = new Tetris();

    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.add(game);
    window.setSize(700, 800);
    window.setResizable(false);
    window.setVisible(true);
  }
}
