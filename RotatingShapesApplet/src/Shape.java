import java.awt.Point;

import Jama.Matrix;

public class Shape
{
  Matrix vertices;
  Point[] edges;
  String type;

  public Shape(Matrix vertices, Point[] edges)
  {
    this.vertices = vertices;
    this.edges = edges;
  }

  public Shape(String type)
  {
    this.type = type;
    switch (type)
    {
    case "hypercube":
      vertices = new Matrix(new double[][] {
          {-100, 100, 100, -100, -100, 100, 100, -100, -100, 100, 100, -100,
              -100, 100, 100, -100},
          {-100, -100, 100, 100, -100, -100, 100, 100, -100, -100, 100, 100,
              -100, -100, 100, 100},
          {100, 100, 100, 100, -100, -100, -100, -100, 100, 100, 100, 100, -100,
              -100, -100, -100},
          {100, 100, 100, 100, 100, 100, 100, 100, -100, -100, -100, -100, -100,
              -100, -100, -100}});
      edges = new Point[] {new Point(0, 1), new Point(1, 2), new Point(2, 3),
          new Point(3, 0), new Point(4, 5), new Point(5, 6), new Point(6, 7),
          new Point(7, 4), new Point(0, 4), new Point(1, 5), new Point(2, 6),
          new Point(3, 7),

          new Point(8, 9), new Point(9, 10), new Point(10, 11),
          new Point(11, 8), new Point(12, 13), new Point(13, 14),
          new Point(14, 15), new Point(15, 12), new Point(8, 12),
          new Point(9, 13), new Point(10, 14), new Point(11, 15),
          new Point(0, 8), new Point(1, 9), new Point(2, 10), new Point(3, 11),
          new Point(4, 12), new Point(5, 13), new Point(6, 14),
          new Point(7, 15)};
      break;
    case "cube":
      vertices = new Matrix(
          new double[][] {{-100, 100, 100, -100, -100, 100, 100, -100},
              {-100, -100, 100, 100, -100, -100, 100, 100},
              {100, 100, 100, 100, -100, -100, -100, -100}});
      edges = new Point[] {new Point(0, 1), new Point(1, 2), new Point(2, 3),
          new Point(3, 0), new Point(4, 5), new Point(5, 6), new Point(6, 7),
          new Point(7, 4), new Point(0, 4), new Point(1, 5), new Point(2, 6),
          new Point(3, 7)};
      break;

    case "rectangular prism":
      vertices = new Matrix(
          new double[][] {{-50, 50, 50, -50, -50, 50, 50, -50},
              {-50, -50, 50, 50, -50, -50, 50, 50},
              {150, 150, 150, 150, -150, -150, -150, -150}});
      edges = new Point[] {new Point(0, 1), new Point(1, 2), new Point(2, 3),
          new Point(3, 0), new Point(4, 5), new Point(5, 6), new Point(6, 7),
          new Point(7, 4), new Point(0, 4), new Point(1, 5), new Point(2, 6),
          new Point(3, 7)};
      break;

    case "square pyramid":
      vertices = new Matrix(new double[][] {{-100, 100, 100, -100, 0},
          {-100, -100, 100, 100, 0}, {100, 100, 100, 100, -100}});
      edges = new Point[] {new Point(0, 1), new Point(1, 2), new Point(2, 3),
          new Point(3, 0), new Point(0, 4), new Point(1, 4), new Point(2, 4),
          new Point(3, 4)};
      break;

    }
  }

  public Matrix getVertices()
  {
    return vertices;
  }

  public Point[] getEdges()
  {
    return edges;
  }

  public String getType()
  {
    return type;
  }

  public void setVertices(Matrix v)
  {
    vertices = v;
  }

  public void setEdges(Point[] e)
  {
    edges = e;
  }

}
