import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BlockDestruction extends JPanel
    implements ActionListener, KeyListener
{

  // instantiate fields
  private boolean showTitleScreen = true;
  private boolean playing = false;
  private boolean gameOver = false;

  private boolean gameStatus = false;

  private boolean leftPressed = false;
  private boolean rightPressed = false;

  private int sliderHeight = 10;
  private int sliderWidth = 70;
  private int sliderX = 315;
  private int sliderY = 650;
  private int sliderSpeed = 8;

  private Ball ball = new Ball(20, 340, 600);

  private int ballDeltaX = -6;
  private int ballDeltaY = -5;

  private int score = 0;
  private int finalScore = 0;

  private int blockHeight = 30;
  private int blockWidth = 70;

  private int row = 10;
  private int col = 8;

  private Block[][] blocks = new Block[row][col];
  private Color[][] colors = new Color[row][col];

  public BlockDestruction()
  {
    // sets jpanel in focus and adds key listener
    setFocusable(true);
    addKeyListener(this);

    // starts timer at 60FPS
    Timer timer = new Timer(1000 / 60, this);
    timer.start();

    // fills the block and color arrays with block and color objects
    for (int r = 0; r < blocks.length; r++)
    {
      for (int c = 0; c < blocks[r].length; c++)
      {
        blocks[r][c] = new Block(blockWidth, blockHeight,
            c * blockWidth + ((getWidth() - 560) / 2), r * blockHeight, true);
        colors[r][c] = new Color((int) (Math.random() * 0x1000000));
      }
    }

    // sets jpanel background to black
    setBackground(Color.BLACK);
  }

  // timer calls this method over and over again
  public void actionPerformed(ActionEvent e)
  {
    step();
  }

  // called over and over again
  public void step()
  {
    // loops through array: centering blocks to screen, handling the ball
    // bouncing of the blocks

    for (int r = 0, p = 1; r < blocks.length; r++)
    {
      for (int c = 0; c < blocks[r].length; c++)
      {
        blocks[r][c].setLocation(c * blockWidth + ((getWidth() - 560) / 2),
            r * blockHeight);
        if (blocks[r][c].intersects(ball))
        {
          if (blocks[r][c].getStatus())
          {
            score++;

            if (blocks[r][c].intersection(ball).getWidth() > blocks[r][c]
                .intersection(ball).getHeight())
            {
              ballDeltaY *= p == 1 ? -1 : 1;
              p = 0;

            }
            else
            {
              ballDeltaX *= p == 1 ? -1 : 1;
              p = 0;

            }

          }

          blocks[r][c].setStatus(false);
        }
      }
    }

    // updates slider with respect to panel size
    sliderY = getHeight() - 30;
    sliderWidth = getWidth() / 10;

    // slider moves in response to keys
    if (leftPressed)
    {
      if (sliderX - sliderSpeed > 0)
      {
        sliderX -= sliderSpeed;
      }
    }
    if (rightPressed)
    {
      if (sliderX + sliderSpeed + sliderWidth < getWidth())
      {
        sliderX += sliderSpeed;
      }
    }

    // calculates the next position of the ball
    int nextBallLeft = (int) ball.getX() + ballDeltaX;
    int nextBallRight = (int) ball.getX() + ball.getDiameter() + ballDeltaX;
    int nextBallTop = (int) ball.getY() + ballDeltaY;
    int nextBallBottom = (int) ball.getY() + ball.getDiameter() + ballDeltaY;

    // handles ball bouncing off walls
    if (nextBallLeft < 0 || nextBallRight > getWidth())
    {
      ballDeltaX *= -1;
    }
    if (nextBallTop < 0)
    {
      ballDeltaY *= -1;
    }
    if (nextBallBottom > sliderY)
    {
      if (nextBallRight < sliderX || nextBallLeft > sliderX + sliderWidth)
      {
        finalScore = score;
        gameOver = true;
        playing = false;
      }
      else
      {
        ballDeltaY *= -1;
      }
    }

    if (score == row * col)
    {
      finalScore = score;
      gameStatus = true;
      gameOver = true;
      playing = false;
    }

    // updates ball location
    ball.setLocation((int) ball.getX() + ballDeltaX,
        (int) ball.getY() + ballDeltaY);

    // repaints graphics
    repaint();
  }

  public void keyPressed(KeyEvent e)
  {
    // start of game
    if (showTitleScreen)
    {
      if (e.getKeyCode() == KeyEvent.VK_SPACE)
      {
        showTitleScreen = false;
        playing = true;
      }
    }
    // playing the game
    else if (playing)
    {
      if (e.getKeyCode() == KeyEvent.VK_LEFT)
      {
        leftPressed = true;
      }
      else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
      {
        rightPressed = true;
      }
    }
    // end of game (resets all fields)
    else if (gameOver)
    {
      if (e.getKeyCode() == KeyEvent.VK_SPACE)
      {
        gameOver = false;
        showTitleScreen = false;
        playing = true;
        sliderX = 315;
        ball.setLocation(340, 600);
        score = 0;
        gameStatus = false;
        ballDeltaX = -6;
        ballDeltaY = -5;
        rightPressed = false;
        leftPressed = false;

        for (int i = 0; i < blocks.length; i++)
        {
          for (int j = 0; j < blocks[i].length; j++)
          {
            blocks[i][j].setStatus(true);
          }
        }

      }
    }
  }

  public void keyReleased(KeyEvent e)
  {
    // updates fields based on key released
    if (playing)
    {
      if (e.getKeyCode() == KeyEvent.VK_LEFT)
      {
        leftPressed = false;
      }
      else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
      {
        rightPressed = false;
      }

    }
  }

  public void keyTyped(KeyEvent e)
  {
    // not used
  }

  public void paintComponent(Graphics g)
  {
    // calls jpanel paint component method
    super.paintComponent(g);

    // sets color to white
    g.setColor(Color.WHITE);

    // graphics during game
    if (playing)
    {

      // draw the score
      g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
      g.drawString(String.valueOf(score), 100, getHeight() - 50);

      // draw the ball
      g.fillOval((int) ball.getX(), (int) ball.getY(), ball.getDiameter(),
          ball.getDiameter());

      // draw the paddles
      g.fillRect(sliderX, sliderY, sliderWidth, sliderHeight);

      // draw the blocks
      for (int i = 0; i < blocks.length; i++)
      {
        for (int j = 0; j < blocks[i].length; j++)
        {
          if (blocks[i][j].getStatus())
          {
            g.setColor(colors[i][j]);
            g.fillRect((int) blocks[i][j].getX(), (int) blocks[i][j].getY(),
                (int) blocks[i][j].getWidth(), (int) blocks[i][j].getHeight());
          }
        }
      }
      // resets color to white
      g.setColor(Color.WHITE);
    }

    // graphics at start of game
    else if (showTitleScreen)
    {

      g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
      g.drawString("BLOCK DESTROYER!!!", (getWidth() - 392) / 2, 175);

      g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
      g.drawString("By Josh, Julie, and Srdjan", (getWidth() - 250) / 2, 225);

      g.setFont(new Font(Font.DIALOG, Font.BOLD, 23));

      g.drawString("Press 'Space' to play.", (getWidth() - 244) / 2, 475);
    }
    // graphics at end of game
    else if (gameOver)
    {

      g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));

      // winning graphics
      if (gameStatus)
      {
        g.drawString("You Won!", (getWidth() - 177) / 2, 200);
      }
      // losing graphics
      else
      {
        g.drawString("Ha, you're a loser!! :(", (getWidth() - 375) / 2, 200);
      }
      g.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
      g.drawString("Score: " + String.valueOf(finalScore),
          (getWidth() - 98) / 2, 420);

      g.setFont(new Font(Font.DIALOG, Font.BOLD, 23));
      g.drawString("Press 'Space' to play again.", (getWidth() - 320) / 2, 475);
    }

  }
}