import Jama.Matrix;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JSlider;

public class Rotating3dShape extends JPanel implements ActionListener
{
  private JSlider sliderX = new JSlider(0, 100, 0);
  private JSlider sliderY = new JSlider(0, 100, 0);
  private JSlider sliderZ = new JSlider(0, 100, 0);

  private JButton reset = new JButton("reset");

  private int offset = 350;

  private String[] typeList = {"cube", "rectangular prism", "square pyramid"};

  private JComboBox dropDown = new JComboBox(typeList);

  private Shape shape = new Shape((String) dropDown.getSelectedItem());

  public Rotating3dShape()
  {
    setFocusable(true);
    Timer timer = new Timer(1000 / 60, this);
    timer.start();
    setBackground(Color.DARK_GRAY);

    JLabel labelX = new JLabel("X");
    labelX.setForeground(Color.WHITE);
    add(labelX);
    add(sliderX);

    JLabel labelY = new JLabel("Y");
    labelY.setForeground(Color.WHITE);
    add(labelY);
    add(sliderY);

    JLabel labelZ = new JLabel("Z");
    labelZ.setForeground(Color.WHITE);
    add(labelZ);
    add(sliderZ);

    add(reset);

    dropDown.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        shape = new Shape((String) dropDown.getSelectedItem());
      }
    });
    add(dropDown);
  }

  public void actionPerformed(ActionEvent e)
  {

    step();
  }

  public void step()
  {

    if (reset.getModel().isPressed())
    {
      shape = new Shape(shape.getType());
      sliderX.setValue(0);
      sliderY.setValue(0);
      sliderZ.setValue(0);
    }

    double angleX = sliderX.getValue() / 1000.0,
        angleY = sliderY.getValue() / 1000.0,
        angleZ = sliderZ.getValue() / 1000.0;

    Matrix zRotation = new Matrix(
        new double[][] {{Math.cos(angleZ), -1 * Math.sin(angleZ), 0},
            {Math.sin(angleZ), Math.cos(angleZ), 0}, {0, 0, 1}});
    Matrix yRotation = new Matrix(
        new double[][] {{Math.cos(angleY), 0, Math.sin(angleY)}, {0, 1, 0},
            {-1 * Math.sin(angleY), 0, Math.cos(angleY)}});
    Matrix xRotation = new Matrix(
        new double[][] {{1, 0, 0}, {0, Math.cos(angleX), -1 * Math.sin(angleX)},
            {0, Math.sin(angleX), Math.cos(angleX)}});

    shape.setVertices(xRotation.times(shape.getVertices()));
    shape.setVertices(yRotation.times(shape.getVertices()));
    shape.setVertices(zRotation.times(shape.getVertices()));

    repaint();
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    g.setColor(Color.WHITE);

    for (Point p : shape.getEdges())
    {
      g.drawLine((int) shape.getVertices().get(1, (int) p.getX()) + offset,
          (int) shape.getVertices().get(2, (int) p.getX()) + offset,
          (int) shape.getVertices().get(1, (int) p.getY()) + offset,
          (int) shape.getVertices().get(2, (int) p.getY()) + offset);
    }

  }
}
