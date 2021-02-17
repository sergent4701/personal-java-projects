import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

public class Tetris extends JPanel implements ActionListener, KeyListener
{
  Brick falling = new Brick((int) (7 * Math.random()));
  Brick image = new Brick(falling.getType());

  ArrayList<Brick> next = new ArrayList<Brick>();
  ArrayList<Brick> bricks = new ArrayList<Brick>();
  ArrayList<Point> topPoints = new ArrayList<Point>();
  ArrayList<Point> rightPoints = new ArrayList<Point>();
  ArrayList<Point> leftPoints = new ArrayList<Point>();

  Point highest = new Point(3, 20);
  Point rightest = new Point(-1, -2);
  Point leftest = new Point(10, -2);

  boolean upPressed = false;
  boolean downPressed = false;
  boolean leftPressed = false;
  boolean rightPressed = false;
  boolean spacePressed = false;
  boolean single = true;
  boolean single2 = true;
  boolean fallingSingle = true;
  int slow = 0;

  public Tetris()
  {
    setBackground(Color.GRAY);
    setFocusable(true);
    addKeyListener(this);

    Timer timer = new Timer(1000 / 60, this);
    timer.start();

    for (int i = 0; i < 3; i++)
    {
      next.add(new Brick((int) (7 * Math.random())));
    }
  }

  public void step()
  {
    slow++;

    if (slow % 3 == 0)
    {
      if (!spacePressed)
        single2 = true;

      if (upPressed && single)
      {
        falling.rotate((int) rightest.getX() + 1, (int) leftest.getX() - 3,
            (int) leftest.getX() - 4);
        image.rotate((int) rightest.getX() + 1, (int) leftest.getX() - 3,
            (int) leftest.getX() - 4);
        single = false;
      }
      if (!upPressed)
        single = true;

      if (leftPressed)
      {
        for (int b = 0; b < falling.updateLeftmostPoints().size(); b++)
        {
          if (rightest.getY() == falling.updateLeftmostPoints().get(b).getY()
              && rightest.getX() < falling.updateLeftmostPoints().get(b).getX()
                  - 1)
          {
            falling.moveLeft();
            image.moveLeft();
          }
        }
        for (int t = 0; t < falling.updateLeftmostPoints().size(); t++)
        {
          for (int u = 0; u < bricks.size(); u++)
          {
            for (int p = 0; p < bricks.get(u).size(); p++)
            {
              if (bricks.get(u).get(p)
                  .equals(falling.updateLeftmostPoints().get(t)))
              {
                falling.moveRight();
                image.moveRight();
              }
            }
          }
        }
      }
      if (rightPressed)
      {
        for (int f = 0; f < falling.updateRightmostPoints().size(); f++)
        {
          if (leftest.getY() == falling.updateRightmostPoints().get(f).getY()
              && leftest.getX() > falling.updateRightmostPoints().get(f).getX()
                  + 1)
          {
            falling.moveRight();
            image.moveRight();
          }
        }
        for (int t2 = 0; t2 < falling.updateRightmostPoints().size(); t2++)
        {
          for (int u2 = 0; u2 < bricks.size(); u2++)
          {
            for (int p2 = 0; p2 < bricks.get(u2).size(); p2++)
            {
              if (bricks.get(u2).get(p2)
                  .equals(falling.updateRightmostPoints().get(t2)))
              {
                falling.moveLeft();
                image.moveLeft();
              }
            }
          }
        }

      }
      if (downPressed)
      {
        for (int f = 0; f < falling.updateLowestPoints().size(); f++)
        {
          if (highest.getX() == falling.updateLowestPoints().get(f).getX()
              && highest.getY() > falling.updateLowestPoints().get(f).getY()
                  + 1)
          {
            falling.moveDown();
          }
        }

        for (int t3 = 0; t3 < falling.updateLowestPoints().size(); t3++)
        {
          for (int u3 = 0; u3 < bricks.size(); u3++)
          {
            for (int p3 = 0; p3 < bricks.get(u3).size(); p3++)
            {
              if (bricks.get(u3).get(p3)
                  .equals(falling.updateLowestPoints().get(t3)))
              {
                falling.moveUp();
                image.moveUp();
              }
            }
          }
        }
      }
      if (spacePressed && single2)
      {
        while (falling.getPivotY() != image.getPivotY())
        {
          falling.moveDown();
        }
        single2 = false;
        fallingSingle = false;
      }
    }
    // -------------------------------------------------------------------------
    if (slow % 60 == 0 && fallingSingle)
      falling.moveDown();

    int[] yVal = new int[20];
    for (int u6 = 0; u6 < bricks.size(); u6++)
    {
      for (int p6 = 0; p6 < bricks.get(u6).size(); p6++)
      {
        if ((int) bricks.get(u6).get(p6).getY() < 0)
        {
          bricks.clear();
          return;
        }
        else
        {
          yVal[(int) bricks.get(u6).get(p6).getY()]++;
        }
      }
    }

    for (int h = 0; h < yVal.length; h++)
    {
      if (yVal[h] == 10)
      {
        for (int u7 = 0; u7 < bricks.size(); u7++)
        {
          for (int p7 = 0; p7 < bricks.get(u7).size(); p7++)
          {
            if (bricks.get(u7).get(p7).getY() == h)
            {
              bricks.get(u7).remove(p7);
              p7--;
            }
          }
        }
        for (int u8 = 0; u8 < bricks.size(); u8++)
        {
          for (int p8 = 0; p8 < bricks.get(u8).size(); p8++)
          {
            if (bricks.get(u8).get(p8).getY() < h)
            {
              bricks.get(u8).get(p8).setLocation(
                  (int) bricks.get(u8).get(p8).getX(),
                  (int) bricks.get(u8).get(p8).getY() + 1);
            }
          }
        }
      }
    }
    if (falling.getPivotY() == image.getPivotY() && slow % 22 == 0)
    {
      bricks.add(falling);
      falling = next.remove(0);
      next.add(new Brick((int) (7 * Math.random())));
      image = new Brick(falling.getType());
      fallingSingle = true;
    }

    ArrayList<Point> projection = new ArrayList<Point>();
    for (int d = 0; d < bricks.size(); d++)
    {
      for (int z = 0; z < bricks.get(d).size(); z++)
      {
        for (int e = 0; e < falling.updateLowestPoints().size(); e++)
        {
          if (bricks.get(d).get(z).getX() == falling.updateLowestPoints().get(e)
              .getX()
              && bricks.get(d).get(z).getY() > falling.updateLowestPoints()
                  .get(e).getY())
            projection.add(bricks.get(d).get(z));

        }
      }
    }

    topPoints.clear();

    while (!projection.isEmpty())
    {
      Point top = projection.get(0);
      int topIndex = 0;
      boolean add = true;

      for (int r = 0; r < projection.size(); r++)
      {
        if (projection.get(r).getY() < top.getY())
        {
          top = projection.get(r);
          topIndex = r;
        }
      }
      projection.remove(topIndex);

      for (int g = 0; g < topPoints.size(); g++)
      {
        if (top.getX() == topPoints.get(g).getX())
        {
          add = false;
        }
      }

      if (add)
      {
        topPoints.add(top);
      }
    }

    double shortestDistance = 20;
    highest = new Point((int) falling.get(0).getX(), 20);
    for (int y = 0; y < falling.updateLowestPoints().size(); y++)
    {
      for (int o = 0; o < topPoints.size(); o++)
      {
        if (falling.updateLowestPoints().get(y).getX() == topPoints.get(o)
            .getX())
        {
          if (topPoints.get(o).getY()
              - falling.updateLowestPoints().get(y).getY() < shortestDistance)
          {
            shortestDistance = topPoints.get(o).getY()
                - falling.updateLowestPoints().get(y).getY();
            highest = topPoints.get(o);
          }
        }
      }
    }
    for (int s = 0; s < image.updateLowestPoints().size(); s++)
    {
      if (image.updateLowestPoints().get(s).getX() == highest.getX())
      {
        int counter = 0;
        while (image.updateLowestPoints().get(s).getY() != highest.getY() - 1
            && counter != 20)
        {
          if (image.updateLowestPoints().get(s).getY() >= highest.getY())
          {
            image.moveUp();
          }
          if (image.updateLowestPoints().get(s).getY() < highest.getY() - 1)
          {
            image.moveDown();
          }
          counter++;
        }
      }
    }

    ArrayList<Point> projectionLeft = new ArrayList<Point>();
    for (int dl = 0; dl < bricks.size(); dl++)
    {
      for (int zl = 0; zl < bricks.get(dl).size(); zl++)
      {
        for (int el = 0; el < falling.updateLeftmostPoints().size(); el++)
        {
          if (bricks.get(dl).get(zl).getY() == falling.updateLeftmostPoints()
              .get(el).getY()
              && bricks.get(dl).get(zl).getX() < falling.updateLeftmostPoints()
                  .get(el).getX())
            projectionLeft.add(bricks.get(dl).get(zl));

        }
      }
    }

    rightPoints.clear();

    while (!projectionLeft.isEmpty())
    {
      Point right = projectionLeft.get(0);
      int rightIndex = 0;
      boolean add = true;

      for (int rl = 0; rl < projectionLeft.size(); rl++)
      {
        if (projectionLeft.get(rl).getX() > right.getX())
        {
          right = projectionLeft.get(rl);
          rightIndex = rl;
        }
      }
      projectionLeft.remove(rightIndex);

      for (int gl = 0; gl < rightPoints.size(); gl++)
      {
        if (right.getY() == rightPoints.get(gl).getY())
        {
          add = false;
        }
      }

      if (add)
      {
        rightPoints.add(right);
      }
    }

    double shortestDistanceLeft = 10;
    rightest = new Point(-1, (int) falling.get(0).getY());
    for (int yl = 0; yl < falling.updateLeftmostPoints().size(); yl++)
    {
      for (int ol = 0; ol < rightPoints.size(); ol++)
      {
        if (falling.updateLeftmostPoints().get(yl).getY() == rightPoints.get(ol)
            .getY())
        {
          if (falling.updateLeftmostPoints().get(yl).getX()
              - rightPoints.get(ol).getX() < shortestDistanceLeft)
          {
            shortestDistanceLeft = falling.updateLeftmostPoints().get(yl).getX()
                - rightPoints.get(ol).getX();
            rightest = rightPoints.get(ol);
          }
        }
      }
    }

    ArrayList<Point> projectionRight = new ArrayList<Point>();
    for (int dr = 0; dr < bricks.size(); dr++)
    {
      for (int zr = 0; zr < bricks.get(dr).size(); zr++)
      {
        for (int er = 0; er < falling.updateRightmostPoints().size(); er++)
        {
          if (bricks.get(dr).get(zr).getY() == falling.updateRightmostPoints()
              .get(er).getY()
              && bricks.get(dr).get(zr).getX() > falling.updateRightmostPoints()
                  .get(er).getX())
            projectionRight.add(bricks.get(dr).get(zr));

        }
      }
    }

    leftPoints.clear();

    while (!projectionRight.isEmpty())
    {
      Point left = projectionRight.get(0);
      int leftIndex = 0;
      boolean add = true;

      for (int rr = 0; rr < projectionRight.size(); rr++)
      {
        if (projectionRight.get(rr).getX() < left.getX())
        {
          left = projectionRight.get(rr);
          leftIndex = rr;
        }
      }
      projectionRight.remove(leftIndex);

      for (int gr = 0; gr < leftPoints.size(); gr++)
      {
        if (left.getY() == leftPoints.get(gr).getY())
        {
          add = false;
        }
      }

      if (add)
      {
        leftPoints.add(left);
      }
    }

    double shortestDistanceRight = 10;
    leftest = new Point(10, (int) falling.get(0).getY());
    for (int yr = 0; yr < falling.updateRightmostPoints().size(); yr++)
    {
      for (int or = 0; or < leftPoints.size(); or++)
      {
        if (falling.updateRightmostPoints().get(yr).getY() == leftPoints.get(or)
            .getY())
        {
          if (leftPoints.get(or).getX() - falling.updateRightmostPoints()
              .get(yr).getX() < shortestDistanceRight)
          {
            shortestDistanceRight = leftPoints.get(or).getX()
                - falling.updateRightmostPoints().get(yr).getX();
            leftest = leftPoints.get(or);
          }
        }
      }
    }
    repaint();
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.setColor(Color.DARK_GRAY);
    g.fillRect(37, 17, 373, 743);
    g.fillRect(433, 17, 230, 400);

    g.setColor(Color.BLACK);
    g.fillRect(436, 20, 224, 394);

    g.setColor(Color.GRAY);
    g.setFont(new Font("Sans Serif", Font.PLAIN, 30));
    g.drawString("NEXT", 515, 45);

    g.setColor(Color.BLACK);

    for (int i = 0; i < 20; i++)
    {
      for (int j = 0; j < 10; j++)
      {
        g.fillRect(37 * j + 40, 37 * i + 20, 34, 34);
      }
    }

    for (int w = 0; w < 4; w++)
    {
      if ((int) falling.get(w).getY() >= 0)
      {
        g.setColor(falling.getColor());
        g.fillRect(37 * (int) falling.get(w).getX() + 40,
            37 * (int) falling.get(w).getY() + 20, 34, 34);
      }
      if ((int) image.get(w).getY() >= 0)
      {
        g.setColor(image.getColor());
        for (int h = 1; h < 3; h++)
          g.drawRect(37 * (int) image.get(w).getX() + 40 + h,
              37 * (int) image.get(w).getY() + 20 + h, 34 - 2 * h, 34 - 2 * h);
      }
    }
    for (int k = 0; k < bricks.size(); k++)
    {
      g.setColor(bricks.get(k).getColor());
      for (int y = 0; y < bricks.get(k).size(); y++)
      {
        if ((int) bricks.get(k).get(y).getY() >= 0)
        {
          g.fillRect(37 * (int) bricks.get(k).get(y).getX() + 40,
              37 * (int) bricks.get(k).get(y).getY() + 20, 34, 34);
        }
      }
    }
    g.setColor(Color.DARK_GRAY);

    for (int r = 0; r < 19; r++)
    {
      for (int c = 0; c < 9; c++)
      {
        g.fillRect(37 * c + 71, 37 * r + 51, 9, 9);
      }
    }

    for (int q = 0; q < 3; q++)
    {
      int xShift = 0;
      for (int e = 0; e < 4; e++)
      {
        g.setColor(next.get(q).getColor());
        switch (next.get(q).getType())
        {
        case 0:
          xShift = 401;
          break;
        case 1:
          xShift = 402;
          break;
        case 2:
          xShift = 420;
          break;
        case 3:
          xShift = 420;
          break;
        case 4:
          xShift = 420;
          break;
        case 5:
          xShift = 420;
          break;
        case 6:
          xShift = 420;
          break;
        }
        g.fillRect(37 * ((int) next.get(q).get(e).getX() - 1) + xShift,
            37 * (int) next.get(q).get(e).getY() + 156 + 100 * q, 34, 34);
      }
    }

  }

  public void keyTyped(KeyEvent e)
  {
    // TODO Auto-generated method stub

  }

  public void keyPressed(KeyEvent e)
  {
    // TODO Auto-generated method stub
    if (e.getKeyCode() == KeyEvent.VK_UP)
      upPressed = true;
    if (e.getKeyCode() == KeyEvent.VK_DOWN)
      downPressed = true;
    if (e.getKeyCode() == KeyEvent.VK_LEFT)
      leftPressed = true;
    if (e.getKeyCode() == KeyEvent.VK_RIGHT)
      rightPressed = true;
    if (e.getKeyCode() == KeyEvent.VK_SPACE)
      spacePressed = true;
  }

  public void keyReleased(KeyEvent e)
  {
    // TODO Auto-generated method stub
    if (e.getKeyCode() == KeyEvent.VK_UP)
      upPressed = false;
    if (e.getKeyCode() == KeyEvent.VK_DOWN)
      downPressed = false;
    if (e.getKeyCode() == KeyEvent.VK_LEFT)
      leftPressed = false;
    if (e.getKeyCode() == KeyEvent.VK_RIGHT)
      rightPressed = false;
    if (e.getKeyCode() == KeyEvent.VK_SPACE)
      spacePressed = false;
  }

  public void actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub
    step();
  }
}
