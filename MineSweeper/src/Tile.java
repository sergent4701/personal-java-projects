import javax.swing.JLabel;

public class Tile extends JLabel
{
  private int numMines;
  private boolean mine, visible, flagged;

  public Tile(int numMines)
  {
    this.numMines = numMines;
    visible = false;
    flagged = false;

    if (numMines > 0)
    {
      mine = false;
    }
    else
    {
      mine = true;
    }
  }

  public int getNumMine()
  {
    return numMines;
  }

  public boolean getMine()
  {
    return mine;
  }

  public boolean getFlagged()
  {
    return flagged;
  }

  public boolean getVisible()
  {
    return visible;
  }

  public void reveal()
  {
    if (!flagged)
      visible = true;
  }

  public void flag()
  {
    if (!visible)
      flagged = flagged ? false : true;
  }
}
