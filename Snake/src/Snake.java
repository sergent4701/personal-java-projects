import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;

public class Snake extends JPanel implements ActionListener, KeyListener
{
  int rows = 25, cols = 25;

  ArrayList<Point> snake = new ArrayList<Point>();
  String direction = "Stop";
  Point food = null;
  boolean gameLost = false;

  public Snake()
  {
    setBackground(Color.DARK_GRAY);
    setFocusable(true);
    addKeyListener(this);

    Timer timer = new Timer(1000 / 10, this);
    timer.start();

    randomize();
  }

  public void keyTyped(KeyEvent e)
  {
    // TODO Auto-generated method stub

  }

  public void keyPressed(KeyEvent e)
  {
    // TODO Auto-generated method stub
    if (e.getKeyCode() == KeyEvent.VK_UP)
      direction = "Up";
    if (e.getKeyCode() == KeyEvent.VK_LEFT)
      direction = "Left";
    if (e.getKeyCode() == KeyEvent.VK_RIGHT)
      direction = "Right";
    if (e.getKeyCode() == KeyEvent.VK_DOWN)
      direction = "Down";
    if (gameLost && e.getKeyCode() == KeyEvent.VK_SPACE)
    {
      gameLost = false;
      randomize();
    }
  }

  public void keyReleased(KeyEvent e)
  {
    // TODO Auto-generated method stub

  }

  public void actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub
    step();
  }

  public void step()
  {
    switch (direction)
    {
    case "Stop":
      break;
    case "Up":
      snake.add(0,
          new Point((int) snake.get(0).getX(), (int) snake.get(0).getY() - 1));
      break;
    case "Down":
      snake.add(0,
          new Point((int) snake.get(0).getX(), (int) snake.get(0).getY() + 1));
      break;
    case "Left":
      snake.add(0,
          new Point((int) snake.get(0).getX() - 1, (int) snake.get(0).getY()));
      break;
    case "Right":
      snake.add(0,
          new Point((int) snake.get(0).getX() + 1, (int) snake.get(0).getY()));
      break;
    }
    if (!food.equals(snake.get(0)) && !direction.equals("Stop"))
    {
      snake.remove(snake.size() - 1);
    }
    else
    {
      food = new Point((int) (Math.random() * cols),
          (int) (Math.random() * rows));
    }
    if ((int) snake.get(0).getX() < 0 || (int) snake.get(0).getY() < 0
        || (int) snake.get(0).getX() >= cols
        || (int) snake.get(0).getY() >= rows)
      gameLost = true;

    for (int i = 1; i < snake.size(); i++)
    {
      if (snake.get(0).equals(snake.get(i)))
        gameLost = true;
    }

    repaint();
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    for (int r = 0; r < rows; r++)
    {
      for (int c = 0; c < cols; c++)
      {
        if (c == (int) food.getX() && r == (int) food.getY() || gameLost)
        {
          g.setColor(Color.RED);
        }
        else
        {
          g.setColor(Color.BLACK);
        }
        g.fillRect(25 * c + 1, 25 * r + 1, 23, 23);

      }
    }
    for (int i = 0; i < snake.size(); i++)
    {
      if (!gameLost)
      {
        g.setColor(Color.WHITE);
        g.fillRect(25 * (int) snake.get(i).getX() + 1,
            25 * (int) snake.get(i).getY() + 1, 23, 23);
      }
    }
  }

  public void randomize()
  {
    direction = "Stop";
    snake.clear();
    snake.add(
        new Point((int) (Math.random() * cols), (int) (Math.random() * rows)));
    food = new Point((int) (Math.random() * cols),
        (int) (Math.random() * rows));
  }
}
