import java.awt.Rectangle;

public class Ball extends Rectangle
{
  // instantiates fields
  private int diameter;

  public Ball(int diameter, int xPosition, int yPosition)
  {
    // calls rectangle constructor
    super(xPosition, yPosition, diameter, diameter);

    this.diameter = diameter;
  }

  // returns diameter
  public int getDiameter()
  {
    return diameter;
  }
}
