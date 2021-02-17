import Jama.Matrix;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class Rotating4dCube extends JPanel implements ActionListener
{
  private JSlider sliderXY = new JSlider(0, 100, 0);
  private JSlider sliderYZ = new JSlider(0, 100, 0);
  private JSlider sliderXZ = new JSlider(0, 100, 0);
  private JSlider sliderXW = new JSlider(0, 100, 0);
  private JSlider sliderYW = new JSlider(0, 100, 0);
  private JSlider sliderZW = new JSlider(0, 100, 0);

  private JButton reset = new JButton("Reset");

  private int offset = 350;

  private Shape cube = new Shape("hypercube");

  public Rotating4dCube()
  {
    setFocusable(true);
    Timer timer = new Timer(1000 / 60, this);
    timer.start();
    setBackground(Color.DARK_GRAY);

    JLabel labelXY = new JLabel("XY");
    labelXY.setForeground(Color.WHITE);
    add(labelXY);
    add(sliderXY);

    JLabel labelYZ = new JLabel("YZ");
    labelYZ.setForeground(Color.WHITE);
    add(labelYZ);
    add(sliderYZ);

    JLabel labelXZ = new JLabel("XZ");
    labelXZ.setForeground(Color.WHITE);
    add(labelXZ);
    add(sliderXZ);

    JLabel labelXWSpace = new JLabel("      ");
    JLabel labelXW = new JLabel("XW");
    labelXW.setForeground(Color.WHITE);
    add(labelXWSpace);
    add(labelXW);
    add(sliderXW);

    JLabel labelYW = new JLabel("YW");
    labelYW.setForeground(Color.WHITE);
    add(labelYW);
    add(sliderYW);

    JLabel labelZW = new JLabel("ZW");
    labelZW.setForeground(Color.WHITE);
    add(labelZW);
    add(sliderZW);

    add(reset);
  }

  public void actionPerformed(ActionEvent e)
  {
    step();
  }

  public void step()
  {
    if (reset.getModel().isPressed())
    {
      cube.setVertices(new Shape("hypercube").getVertices());
      sliderXY.setValue(0);
      sliderYZ.setValue(0);
      sliderXZ.setValue(0);
      sliderXW.setValue(0);
      sliderYW.setValue(0);
      sliderZW.setValue(0);

    }

    // XY,YZ,XZ,XW,YW,ZW
    double[] angle = {sliderXY.getValue() / 1000.0,
        sliderYZ.getValue() / 1000.0, sliderXZ.getValue() / 1000.0,
        sliderXW.getValue() / 1000.0, sliderYW.getValue() / 1000.0,
        sliderZW.getValue() / 1000.0};

    Matrix xyRotation = new Matrix(
        new double[][] {{Math.cos(angle[0]), Math.sin(angle[0]), 0, 0},
            {-1 * Math.sin(angle[0]), Math.cos(angle[0]), 0, 0}, {0, 0, 1, 0},
            {0, 0, 0, 1}});

    Matrix yzRotation = new Matrix(new double[][] {{1, 0, 0, 0},
        {0, Math.cos(angle[1]), Math.sin(angle[1]), 0},
        {0, -1 * Math.sin(angle[1]), Math.cos(angle[1]), 0}, {0, 0, 0, 1}});

    Matrix xzRotation = new Matrix(new double[][] {
        {Math.cos(angle[2]), 0, -1 * Math.sin(angle[2]), 0}, {0, 1, 0, 0},
        {Math.sin(angle[2]), 0, Math.cos(angle[2]), 0}, {0, 0, 0, 1}});

    Matrix xwRotation = new Matrix(new double[][] {
        {Math.cos(angle[3]), 0, 0, Math.sin(angle[3])}, {0, 1, 0, 0},
        {0, 0, 1, 0}, {-1 * Math.sin(angle[3]), 0, 0, Math.cos(angle[3])}});

    Matrix ywRotation = new Matrix(new double[][] {{1, 0, 0, 0},
        {0, Math.cos(angle[4]), 0, -1 * Math.sin(angle[4])}, {0, 0, 1, 0},
        {0, Math.sin(angle[4]), 0, Math.cos(angle[4])}});

    Matrix zwRotation = new Matrix(new double[][] {{1, 0, 0, 0}, {0, 1, 0, 0},
        {0, 0, Math.cos(angle[5]), -1 * Math.sin(angle[5])},
        {0, 0, Math.sin(angle[5]), Math.cos(angle[5])}});

    cube.setVertices(xyRotation.times(cube.getVertices()));
    cube.setVertices(yzRotation.times(cube.getVertices()));
    cube.setVertices(xzRotation.times(cube.getVertices()));
    cube.setVertices(xwRotation.times(cube.getVertices()));
    cube.setVertices(ywRotation.times(cube.getVertices()));
    cube.setVertices(zwRotation.times(cube.getVertices()));

    repaint();
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    g.setColor(Color.WHITE);

    for (Point p : cube.getEdges())
    {
      g.drawLine((int) cube.getVertices().get(1, (int) p.getX()) + offset,
          (int) cube.getVertices().get(2, (int) p.getX()) + offset,
          (int) cube.getVertices().get(1, (int) p.getY()) + offset,
          (int) cube.getVertices().get(2, (int) p.getY()) + offset);
    }
  }
}
