import javax.swing.JFrame;

public class Runner4d
{
  public static void main(String[] args)
  {

    JFrame window = new JFrame("Rotating 4D Cube");

    Rotating4dCube panel = new Rotating4dCube();

    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setSize(700, 700);
    window.add(panel);
    window.setResizable(false);
    window.setLocation(0, 0);
    window.setVisible(true);
  }
}
