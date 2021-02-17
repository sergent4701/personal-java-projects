import javax.swing.JFrame;

public class Main
{
  public static void main(String[] args)
  {
    JFrame window = new JFrame("Snake");

    Snake snake = new Snake();

    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setSize(625, 647);
    window.add(snake);
    window.setResizable(true);
    window.setVisible(true);
  }
}
