import java.util.ArrayList;
import java.awt.Point;
import java.awt.Color;

public class Brick extends ArrayList<Point>
{
  private Color color;
  private int orientation, type, pivotX, pivotY;
  ArrayList<Point> lowestPoints = new ArrayList<Point>();
  ArrayList<Point> leftmostPoints = new ArrayList<Point>();
  ArrayList<Point> rightmostPoints = new ArrayList<Point>();

  public Brick(int t)
  {
    type = t;
    orientation = 0;
    pivotX = 3;
    pivotY = -3;

    switch (type)
    {
    case 0:
      color = new Color(56, 214, 217);
      add(new Point(pivotX, pivotY + 1));
      add(new Point(pivotX + 1, pivotY + 1));
      add(new Point(pivotX + 2, pivotY + 1));
      add(new Point(pivotX + 3, pivotY + 1));
      break;
    case 1:
      color = Color.YELLOW;
      add(new Point(pivotX + 1, pivotY + 1));
      add(new Point(pivotX + 2, pivotY + 1));
      add(new Point(pivotX + 1, pivotY + 2));
      add(new Point(pivotX + 2, pivotY + 2));

      break;
    case 2:
      color = Color.RED;
      add(new Point(pivotX, pivotY + 1));
      add(new Point(pivotX + 1, pivotY + 1));
      add(new Point(pivotX + 1, pivotY + 2));
      add(new Point(pivotX + 2, pivotY + 2));
      break;
    case 3:
      color = Color.GREEN;
      add(new Point(pivotX, pivotY + 2));
      add(new Point(pivotX + 1, pivotY + 2));
      add(new Point(pivotX + 1, pivotY + 1));
      add(new Point(pivotX + 2, pivotY + 1));
      break;
    case 4:
      color = Color.MAGENTA;
      add(new Point(pivotX, pivotY + 1));
      add(new Point(pivotX + 1, pivotY + 1));
      add(new Point(pivotX + 2, pivotY + 1));
      add(new Point(pivotX + 1, pivotY + 2));
      break;
    case 5:
      color = new Color(255, 147, 46);
      add(new Point(pivotX, pivotY + 1));
      add(new Point(pivotX + 1, pivotY + 1));
      add(new Point(pivotX + 2, pivotY + 1));
      add(new Point(pivotX, pivotY + 2));
      break;
    case 6:
      color = Color.BLUE;
      add(new Point(pivotX, pivotY + 1));
      add(new Point(pivotX + 1, pivotY + 1));
      add(new Point(pivotX + 2, pivotY + 1));
      add(new Point(pivotX + 2, pivotY + 2));
      break;
    }
    updateLowestPoints();
    updateLeftmostPoints();
    updateRightmostPoints();
  }

  public int getPivotY()
  {
    return pivotY;
  }

  public int getPivotX()
  {
    return pivotX;
  }

  public int getType()
  {
    return type;
  }

  public Color getColor()
  {
    return color;
  }

  public int getOrientation()
  {
    return orientation;
  }

  public void moveDown()
  {
    for (int j = 0; j < 4; j++)
    {
      if ((int) get(j).getY() >= 19)
      {
        return;
      }
    }
    pivotY++;
    for (int i = 0; i < 4; i++)
    {
      get(i).setLocation((int) get(i).getX(), (int) get(i).getY() + 1);
    }
    updateLowestPoints();
    updateLeftmostPoints();
    updateRightmostPoints();
  }

  public void moveUp()
  {
    for (int j = 0; j < 4; j++)
    {
      if ((int) get(j).getY() <= 0)
      {
        return;
      }
    }
    pivotY--;
    for (int i = 0; i < 4; i++)
    {
      get(i).setLocation((int) get(i).getX(), (int) get(i).getY() - 1);
    }
    updateLowestPoints();
    updateLeftmostPoints();
    updateRightmostPoints();
  }

  public void moveLeft()
  {
    for (int j = 0; j < 4; j++)
    {
      if ((int) get(j).getX() <= 0)
      {
        return;
      }
    }
    pivotX--;
    for (int i = 0; i < 4; i++)
    {
      get(i).setLocation((int) get(i).getX() - 1, (int) get(i).getY());
    }
    updateLowestPoints();
    updateLeftmostPoints();
    updateRightmostPoints();
  }

  public ArrayList<Point> updateLowestPoints()
  {
    ArrayList<Point> checked = new ArrayList<Point>(this);
    lowestPoints.clear();

    while (!checked.isEmpty())
    {
      Point lowest = checked.get(0);
      int lowestIndex = 0;
      boolean add = true;

      for (int i = 0; i < checked.size(); i++)
      {
        if (checked.get(i).getY() > lowest.getY())
        {
          lowest = checked.get(i);
          lowestIndex = i;
        }
      }
      checked.remove(lowestIndex);

      for (int j = 0; j < lowestPoints.size(); j++)
      {
        if (lowest.getX() == lowestPoints.get(j).getX())
        {
          add = false;
        }
      }

      if (add)
      {
        lowestPoints.add(lowest);
      }
    }
    return lowestPoints;
  }

  public ArrayList<Point> updateLeftmostPoints()
  {
    ArrayList<Point> checked = new ArrayList<Point>(this);
    leftmostPoints.clear();

    while (!checked.isEmpty())
    {
      Point leftmost = checked.get(0);
      int leftmostIndex = 0;
      boolean add = true;

      for (int i = 0; i < checked.size(); i++)
      {
        if (checked.get(i).getX() < leftmost.getX())
        {
          leftmost = checked.get(i);
          leftmostIndex = i;
        }
      }
      checked.remove(leftmostIndex);

      for (int j = 0; j < leftmostPoints.size(); j++)
      {
        if (leftmost.getY() == leftmostPoints.get(j).getY())
        {
          add = false;
        }
      }

      if (add)
      {
        leftmostPoints.add(leftmost);
      }
    }
    return leftmostPoints;
  }

  public ArrayList<Point> updateRightmostPoints()
  {
    ArrayList<Point> checked = new ArrayList<Point>(this);
    rightmostPoints.clear();

    while (!checked.isEmpty())
    {
      Point rightmost = checked.get(0);
      int rightmostIndex = 0;
      boolean add = true;

      for (int i = 0; i < checked.size(); i++)
      {
        if (checked.get(i).getX() > rightmost.getX())
        {
          rightmost = checked.get(i);
          rightmostIndex = i;
        }
      }
      checked.remove(rightmostIndex);

      for (int j = 0; j < rightmostPoints.size(); j++)
      {
        if (rightmost.getY() == rightmostPoints.get(j).getY())
        {
          add = false;
        }
      }

      if (add)
      {
        rightmostPoints.add(rightmost);
      }
    }
    return rightmostPoints;
  }

  public void moveRight()
  {
    for (int j = 0; j < 4; j++)
    {
      if ((int) get(j).getX() >= 9)
      {
        return;
      }
    }
    pivotX++;
    for (int i = 0; i < 4; i++)
    {
      get(i).setLocation((int) get(i).getX() + 1, (int) get(i).getY());
    }
    updateLowestPoints();
    updateLeftmostPoints();
    updateRightmostPoints();

  }

  public void rotate(int x, int y, int z)
  {
    if (pivotX < x)
    {
      moveRight();
      rotate(x, y, z);
      return;
    }
    if (pivotX > y)
    {
      moveLeft();
      rotate(x, y, z);
      return;
    }
    if (getType() == 0 && pivotX > z)
    {
      moveLeft();
      rotate(x, y, z);
      return;
    }
    orientation++;
    switch (type)
    {
    case 0:
      if (orientation % 2 == 0)
      {
        set(0, new Point(pivotX, pivotY + 1));
        set(1, new Point(pivotX + 1, pivotY + 1));
        set(2, new Point(pivotX + 2, pivotY + 1));
        set(3, new Point(pivotX + 3, pivotY + 1));
      }
      else
      {
        set(0, new Point(pivotX + 2, pivotY));
        set(1, new Point(pivotX + 2, pivotY + 1));
        set(2, new Point(pivotX + 2, pivotY + 2));
        set(3, new Point(pivotX + 2, pivotY + 3));
      }
      break;
    case 1:
      break;
    case 2:
      if (orientation % 2 == 0)
      {
        set(0, new Point(pivotX, pivotY + 1));
        set(1, new Point(pivotX + 1, pivotY + 1));
        set(2, new Point(pivotX + 1, pivotY + 2));
        set(3, new Point(pivotX + 2, pivotY + 2));
      }
      else
      {
        set(0, new Point(pivotX + 2, pivotY));
        set(1, new Point(pivotX + 2, pivotY + 1));
        set(2, new Point(pivotX + 1, pivotY + 1));
        set(3, new Point(pivotX + 1, pivotY + 2));
      }
      break;
    case 3:
      if (orientation % 2 == 0)
      {
        set(0, new Point(pivotX, pivotY + 2));
        set(1, new Point(pivotX + 1, pivotY + 2));
        set(2, new Point(pivotX + 1, pivotY + 1));
        set(3, new Point(pivotX + 2, pivotY + 1));
      }
      else
      {
        set(0, new Point(pivotX, pivotY));
        set(1, new Point(pivotX, pivotY + 1));
        set(2, new Point(pivotX + 1, pivotY + 1));
        set(3, new Point(pivotX + 1, pivotY + 2));
      }
      break;
    case 4:
      if (orientation % 4 == 0)
      {
        set(0, new Point(pivotX, pivotY + 1));
        set(1, new Point(pivotX + 1, pivotY + 1));
        set(2, new Point(pivotX + 2, pivotY + 1));
        set(3, new Point(pivotX + 1, pivotY + 2));
      }
      else if (orientation % 4 == 1)
      {
        set(0, new Point(pivotX + 1, pivotY));
        set(1, new Point(pivotX, pivotY + 1));
        set(2, new Point(pivotX + 1, pivotY + 1));
        set(3, new Point(pivotX + 1, pivotY + 2));
      }
      else if (orientation % 4 == 2)
      {
        set(0, new Point(pivotX, pivotY + 2));
        set(1, new Point(pivotX + 1, pivotY + 2));
        set(2, new Point(pivotX + 1, pivotY + 1));
        set(3, new Point(pivotX + 2, pivotY + 2));
      }
      else
      {
        set(0, new Point(pivotX + 1, pivotY));
        set(1, new Point(pivotX + 2, pivotY + 1));
        set(2, new Point(pivotX + 1, pivotY + 1));
        set(3, new Point(pivotX + 1, pivotY + 2));
      }
      break;
    case 5:
      if (orientation % 4 == 0)
      {
        set(0, new Point(pivotX, pivotY + 1));
        set(1, new Point(pivotX + 1, pivotY + 1));
        set(2, new Point(pivotX + 2, pivotY + 1));
        set(3, new Point(pivotX, pivotY + 2));
      }
      else if (orientation % 4 == 1)
      {
        set(0, new Point(pivotX, pivotY));
        set(1, new Point(pivotX + 1, pivotY));
        set(2, new Point(pivotX + 1, pivotY + 1));
        set(3, new Point(pivotX + 1, pivotY + 2));
      }
      else if (orientation % 4 == 2)
      {
        set(0, new Point(pivotX, pivotY + 2));
        set(1, new Point(pivotX + 1, pivotY + 2));
        set(2, new Point(pivotX + 2, pivotY + 1));
        set(3, new Point(pivotX + 2, pivotY + 2));
      }
      else
      {
        set(0, new Point(pivotX + 1, pivotY));
        set(1, new Point(pivotX + 2, pivotY + 2));
        set(2, new Point(pivotX + 1, pivotY + 1));
        set(3, new Point(pivotX + 1, pivotY + 2));
      }
      break;
    case 6:
      if (orientation % 4 == 0)
      {
        set(0, new Point(pivotX, pivotY + 1));
        set(1, new Point(pivotX + 1, pivotY + 1));
        set(2, new Point(pivotX + 2, pivotY + 1));
        set(3, new Point(pivotX + 2, pivotY + 2));

      }
      else if (orientation % 4 == 1)
      {
        set(0, new Point(pivotX + 1, pivotY));
        set(1, new Point(pivotX, pivotY + 2));
        set(2, new Point(pivotX + 1, pivotY + 1));
        set(3, new Point(pivotX + 1, pivotY + 2));
      }
      else if (orientation % 4 == 2)
      {
        set(0, new Point(pivotX, pivotY + 2));
        set(1, new Point(pivotX, pivotY + 1));
        set(2, new Point(pivotX + 1, pivotY + 2));
        set(3, new Point(pivotX + 2, pivotY + 2));
      }
      else
      {
        set(0, new Point(pivotX + 1, pivotY));
        set(1, new Point(pivotX + 2, pivotY));
        set(2, new Point(pivotX + 1, pivotY + 1));
        set(3, new Point(pivotX + 1, pivotY + 2));
      }
      break;
    }
    updateLowestPoints();
    updateLeftmostPoints();
    updateRightmostPoints();
  }
}
