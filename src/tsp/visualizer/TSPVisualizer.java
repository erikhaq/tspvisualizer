package tsp.visualizer;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TSPVisualizer extends JFrame {
  private static int MAPWIDTH = 500;
  private static int MAPHEIGHT = 500;
  private static float SCALEX;
  private static float SCALEY;
  private Point[] tspInstance;
  private TSPCanvas canvas;
  private float maxX = 0, maxY = 0;
  public TSPVisualizer() {
    try {
      init();
      readInstance();
      drawInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void init() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    //setLayout(new FlowLayout());
    setSize(MAPWIDTH, MAPHEIGHT);
    setTitle("TSP map");
    setLocationRelativeTo(null);
    setVisible(true);
  }

  private void readInstance() throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int numOfPoints = Integer.parseInt(br.readLine());
    tspInstance = new Point[numOfPoints];
    String[] coordinates;
    float x, y;
    for(int i = 0; i < numOfPoints; i++) {
      coordinates = br.readLine().split(" ");
      x = Float.parseFloat(coordinates[0]);
      y = Float.parseFloat(coordinates[1]);
      if(x > maxX) maxX = x;
      if(y > maxY) maxY = y;
      tspInstance[i] = new Point(x,y);
    }
    SCALEX = (MAPWIDTH-20)/maxX;
    SCALEY = (MAPHEIGHT-20)/maxY;
  }

  private void drawInstance() {
    canvas = new TSPCanvas(tspInstance);
    add("Center", canvas);
  }

  @Override
  public String toString() {
    if(tspInstance == null) return "No instance";
    StringBuilder sb = new StringBuilder();
    sb.append("Size: ").append(tspInstance.length).append("\n");
    for(int i = 0; i < tspInstance.length; i++) {
      sb.append(tspInstance[i].getX()).append(" ").append(tspInstance[i].getY()).append("\n");
    }
    return sb.toString();
  }

  private class Point {
    private float x;
    private float y;
    public Point() {
      this(0, 0);
    }
    public Point(float x, float y) {
      this.x = x;
      this.y = y;
    }
    public Point(String x, String y) {
      this.x = Float.parseFloat(x);
      this.y = Float.parseFloat(y);
    }
    private float getY() {
      return y;
    }
    private void setY(float y) {
      this.y = y;
    }
    private float getX() {
      return x;
    }
    private void setX(float x) {
      this.x = x;
    }
  }

  private class TSPCanvas extends Canvas {
    private Point[] points;
    public TSPCanvas(Point[] points){
      this.points = points;
    }

    @Override
    public void paint(Graphics g) {
      g.drawString("TSPCanvas", 10, 20);
      drawBackground(g);
      drawPoints(g);
      //g.fillRect(50, 50, 5, 5);
    }

    private void drawBackground(Graphics g) {
      g.setColor(new Color(255,255,255));
      g.fillRect(0, 0, MAPWIDTH, MAPHEIGHT);
      g.setColor(new Color(0,0,0));
      g.drawRect(0,0, MAPWIDTH, MAPHEIGHT);
    }

    private void drawPoints(Graphics g) {
      int x, y;
      for(int i = 0; i < points.length; i++) {
        x = (int) (points[i].getX()*SCALEX);
        y = (int) (points[i].getY()*SCALEY);
        g.setColor(new Color(0,0,0));
        g.drawRect(x, y, 5, 5);
        g.setColor(new Color(20, 167, 8));
        g.fillRect(x, y, 5, 5);
      }
    }

  }

  public static void main(String[] args) {
    TSPVisualizer tsp = new TSPVisualizer();
    System.out.println(tsp);
  }

}
