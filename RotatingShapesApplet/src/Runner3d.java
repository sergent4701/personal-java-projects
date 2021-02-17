import javax.swing.JFrame;

public class Runner3d
{
  public static void main(String[] args)
  {
    JFrame window = new JFrame("Rotating 3D Shape");
    Rotating3dShape panel = new Rotating3dShape();

    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setSize(700, 700);
    window.add(panel);
    window.setResizable(false);
    window.setLocation(0, 0);
    window.setVisible(true);

  }
}
