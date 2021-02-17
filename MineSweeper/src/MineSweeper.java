import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;

public class MineSweeper extends JPanel
    implements ActionListener, KeyListener, MouseListener
{
  // 35 width 33 height
  int rows = 20;
  int cols = 20;
  
  Tile[][] tiles = new Tile[rows][cols];
  
  boolean gameOver = false, gameWon = false;
  int totalFlagged = 0;
  int totalMines = 0;

  public MineSweeper()
  {
    setFocusable(true);
    addMouseListener(this);
    addKeyListener(this);
    setBackground(Color.GRAY);

    Timer timer = new Timer(1000 / 60, this);
    timer.start();

    randomize();

  }

  public void actionPerformed(ActionEvent e)
  {
    step();
  }

  public void step()
  {
    int totalVisible = 0;
    for (int a = 0; a < tiles.length; a++)
    {
      for (int b = 0; b < tiles[0].length; b++)
      {
        if (tiles[a][b] != null)
        {
          if (!gameWon)
          {
            if (!gameOver)
            {
              tiles[a][b].setFont(new Font("Sans Serif", Font.PLAIN, 23));
              tiles[a][b].setHorizontalAlignment(SwingConstants.CENTER);
              tiles[a][b].setVerticalAlignment(SwingConstants.CENTER);
              tiles[a][b].setOpaque(true);
              tiles[a][b]
                  .setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
              tiles[a][b].setBackground(Color.LIGHT_GRAY);
              if (tiles[a][b].getVisible())
              {
                totalVisible++;
                tiles[a][b].setBackground(Color.GRAY);

                if (tiles[a][b].getNumMine() < 0)
                {
                  tiles[a][b].setForeground(Color.BLACK);
                  tiles[a][b].setText("*");
                }
                else
                {
                  switch (tiles[a][b].getNumMine())
                  {
                  case 1:
                    tiles[a][b].setForeground(Color.BLUE);
                    break;
                  case 2:
                    tiles[a][b].setForeground(Color.GREEN);
                    break;
                  case 3:
                    tiles[a][b].setForeground(Color.PINK);
                    break;
                  case 4:
                    tiles[a][b].setForeground(Color.MAGENTA);
                    break;
                  case 5:
                    tiles[a][b].setForeground(Color.ORANGE);
                    break;
                  case 6:
                    tiles[a][b].setForeground(Color.CYAN);
                    break;
                  case 7:
                    tiles[a][b].setForeground(Color.YELLOW);
                    break;
                  case 8:
                    tiles[a][b].setForeground(Color.RED);
                    break;

                  }
                  if (tiles[a][b].getNumMine() > 0)
                    tiles[a][b].setText(tiles[a][b].getNumMine() + "");
                }

              }
              else if (tiles[a][b].getFlagged())
              {
                tiles[a][b].setForeground(Color.RED);
                tiles[a][b].setText("#");
              }
              else
              {
                tiles[a][b].setText("");
              }
            }
            else
            {
              tiles[a][b].setFont(new Font("Sans Serif", Font.PLAIN, 23));
              tiles[a][b].setHorizontalAlignment(SwingConstants.CENTER);
              tiles[a][b].setVerticalAlignment(SwingConstants.CENTER);
              tiles[a][b].setOpaque(true);
              tiles[a][b]
                  .setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
              tiles[a][b].setBackground(Color.RED);
              tiles[a][b].setText("");
            }
          }
          else
          {
            tiles[a][b].setFont(new Font("Sans Serif", Font.PLAIN, 23));
            tiles[a][b].setHorizontalAlignment(SwingConstants.CENTER);
            tiles[a][b].setVerticalAlignment(SwingConstants.CENTER);
            tiles[a][b].setOpaque(true);
            tiles[a][b]
                .setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            tiles[a][b].setBackground(Color.GREEN);
            tiles[a][b].setText("");
          }
        }
      }
    }
    if (rows * cols == totalVisible + totalFlagged
        && totalFlagged == totalMines)
    {
      gameWon = true;
    }
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
  }

  public void mouseClicked(MouseEvent e)
  {
    // TODO Auto-generated method stub
    int xCoordinate = (int) ((e.getY() - 10) / 33.0);
    int yCoordinate = (int) (e.getX() / 35.0);

    if (e.getButton() == MouseEvent.BUTTON1)
    {
      if (e.getY() > 10 && e.getY() < 690)
        if (tiles[xCoordinate][yCoordinate].getNumMine() > 0)
          tiles[xCoordinate][yCoordinate].reveal();
        else if (tiles[xCoordinate][yCoordinate].getNumMine() < 0
            && !tiles[xCoordinate][yCoordinate].getFlagged())
          gameOver = true;
        else
          fillQueue(xCoordinate, yCoordinate);

    }
    if (e.getButton() == MouseEvent.BUTTON3)
    {
      totalFlagged += tiles[xCoordinate][yCoordinate].getFlagged() ? -1 : 1;
      tiles[xCoordinate][yCoordinate].flag();
    }
  }

  public void keyPressed(KeyEvent e)
  {
    // TODO Auto-generated method stub

    if (e.getKeyCode() == KeyEvent.VK_SPACE)
    {
      randomize();
      gameOver = false;
      gameWon = false;
    }
    if (!gameOver && !gameWon && e.getKeyCode() == KeyEvent.VK_R)
    {
      for (int r = 0; r < rows; r++)
      {
        for (int c = 0; c < cols; c++)
        {
          tiles[r][c].reveal();
        }
      }
    }
  }

  public void randomize()
  {
    setLayout(new GridLayout(rows, cols));
    totalFlagged = 0;
    totalMines = 0;

    for (int i = 0; i < tiles.length; i++)
    {
      for (int j = 0; j < tiles[0].length; j++)
      {
        if (tiles[i][j] != null)
          remove(tiles[i][j]);
        if (Math.random() <= 0.13)
        {
          tiles[i][j] = new Tile(-1);
          totalMines++;
        }
        else
          tiles[i][j] = null;

      }
    }
    for (int r = 0; r < tiles.length; r++)
    {
      for (int c = 0; c < tiles[0].length; c++)
      {
        if (tiles[r][c] == null)
        {
          int counter = 0;

          if (r - 1 >= 0 && c - 1 >= 0)
          {
            if (tiles[r - 1][c - 1] != null
                && tiles[r - 1][c - 1].getNumMine() < 0)
              counter++;
          }

          if (r - 1 >= 0)
          {
            if (tiles[r - 1][c] != null && tiles[r - 1][c].getNumMine() < 0)
              counter++;
          }

          if (r - 1 >= 0 && c + 1 < tiles[0].length)
          {
            if (tiles[r - 1][c + 1] != null
                && tiles[r - 1][c + 1].getNumMine() < 0)
              counter++;
          }

          if (c - 1 >= 0)
          {
            if (tiles[r][c - 1] != null && tiles[r][c - 1].getNumMine() < 0)
              counter++;
          }

          if (c + 1 < tiles[0].length)
          {
            if (tiles[r][c + 1] != null && tiles[r][c + 1].getNumMine() < 0)
              counter++;
          }

          if (r + 1 < tiles.length && c - 1 >= 0)
          {
            if (tiles[r + 1][c - 1] != null
                && tiles[r + 1][c - 1].getNumMine() < 0)
              counter++;
          }

          if (r + 1 < tiles.length)
          {
            if (tiles[r + 1][c] != null && tiles[r + 1][c].getNumMine() < 0)
              counter++;
          }

          if (r + 1 < tiles.length && c + 1 < tiles[0].length)
          {
            if (tiles[r + 1][c + 1] != null
                && tiles[r + 1][c + 1].getNumMine() < 0)
              counter++;
          }

          tiles[r][c] = new Tile(counter);
        }
        add(tiles[r][c]);
      }
    }
  }

  public void fillRecursive(int x, int y)
  {
    if (tiles[x][y].getNumMine() == 0 && !tiles[x][y].getVisible())
    {
      tiles[x][y].reveal();

      if (x - 1 > -1 && y - 1 > -1)
        fillRecursive(x - 1, y - 1);
      if (x - 1 > -1)
        fillRecursive(x - 1, y);
      if (x - 1 > -1 && y + 1 < rows)
        fillRecursive(x - 1, y + 1);
      if (y - 1 > -1)
        fillRecursive(x, y - 1);
      if (y + 1 < rows)
        fillRecursive(x, y + 1);
      if (x + 1 < cols && y - 1 > -1)
        fillRecursive(x + 1, y - 1);
      if (x + 1 < cols)
        fillRecursive(x + 1, y);
      if (x + 1 < cols && y + 1 < rows)
        fillRecursive(x + 1, y + 1);

    }
    else
    {
      tiles[x][y].reveal();
    }

  }

  public void fillQueue(int x, int y)
  {
    ArrayList<Point> queue = new ArrayList<Point>();

    tiles[x][y].reveal();

    queue.add(new Point(x, y));

    while (queue.size() > 0)
    {

      Point first = queue.remove(0);

      int firstX = (int) first.getX();
      int firstY = (int) first.getY();

      if (firstX - 1 > -1 && !tiles[firstX - 1][firstY].getVisible())
      {
        tiles[firstX - 1][firstY].reveal();
        if (tiles[firstX - 1][firstY].getNumMine() == 0)
          queue.add(new Point(firstX - 1, firstY));
      }
      if (firstY - 1 > -1 && !tiles[firstX][firstY - 1].getVisible())
      {
        tiles[firstX][firstY - 1].reveal();
        if (tiles[firstX][firstY - 1].getNumMine() == 0)
          queue.add(new Point(firstX, firstY - 1));
      }
      if (firstY + 1 < rows && !tiles[firstX][firstY + 1].getVisible())
      {
        tiles[firstX][firstY + 1].reveal();
        if (tiles[firstX][firstY + 1].getNumMine() == 0)
          queue.add(new Point(firstX, firstY + 1));
      }
      if (firstX + 1 < cols && !tiles[firstX + 1][firstY].getVisible())
      {
        tiles[firstX + 1][firstY].reveal();
        if (tiles[firstX + 1][firstY].getNumMine() == 0)
          queue.add(new Point(firstX + 1, firstY));
      }
      if (firstX - 1 > -1 && firstY - 1 > -1
          && !tiles[firstX - 1][firstY - 1].getVisible())
      {
        tiles[firstX - 1][firstY - 1].reveal();
        if (tiles[firstX - 1][firstY - 1].getNumMine() == 0)
          queue.add(new Point(firstX - 1, firstY - 1));
      }
      if (firstX + 1 < cols && firstY + 1 < rows
          && !tiles[firstX + 1][firstY + 1].getVisible())
      {
        tiles[firstX + 1][firstY + 1].reveal();
        if (tiles[firstX + 1][firstY + 1].getNumMine() == 0)
          queue.add(new Point(firstX + 1, firstY + 1));
      }
      if (firstX + 1 < cols && firstY - 1 > -1
          && !tiles[firstX + 1][firstY - 1].getVisible())
      {
        tiles[firstX + 1][firstY - 1].reveal();
        if (tiles[firstX + 1][firstY - 1].getNumMine() == 0)
          queue.add(new Point(firstX + 1, firstY - 1));
      }
      if (firstX - 1 > -1 && firstY + 1 < rows
          && !tiles[firstX - 1][firstY + 1].getVisible())
      {
        tiles[firstX - 1][firstY + 1].reveal();
        if (tiles[firstX - 1][firstY + 1].getNumMine() == 0)
          queue.add(new Point(firstX - 1, firstY + 1));
      }
    }
  }

  public void mouseEntered(MouseEvent e)
  {
    // TODO Auto-generated method stub

  }

  public void mouseExited(MouseEvent e)
  {
    // TODO Auto-generated method stub

  }

  public void keyReleased(KeyEvent e)
  {
    // TODO Auto-generated method stub

  }

  public void mousePressed(MouseEvent e)
  {
    // TODO Auto-generated method stub

  }

  public void mouseReleased(MouseEvent e)
  {
    // TODO Auto-generated method stub
  }

  public void keyTyped(KeyEvent e)
  {
    // TODO Auto-generated method stub

  }
}
