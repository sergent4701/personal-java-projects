import java.awt.Rectangle;

public class Block extends Rectangle
{
  // instantiates fields
  boolean status;

  public Block(int width, int height, int xPosition, int yPosition,
      boolean status)
  {
    // calls rectangle constructor
    super(xPosition, yPosition, width, height);
    this.status = true;
  }

  // sets status to new value
  public void setStatus(boolean s)
  {
    this.status = s;
  }

  // returns status
  public boolean getStatus()
  {
    return status;
  }
}