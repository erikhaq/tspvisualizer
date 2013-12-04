package tsp.visualizer;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
  Run example:
  javac ../src/tsp/visualizer/TSPVisualizer.java -d . && cat ../src/test1.in ../src/test1.out | java tsp.visualizer.TSPVisualizer
 */
public class TSPVisualizer extends JFrame {
  BufferedReader br;
  private static int MAPWIDTH = 500;
  private static int MAPHEIGHT = 500;
  private static int MARGINTOP = 50;
  private static int MARGINLEFT = 50;
  private static float SCALEX;
  private static float SCALEY;
  private int numOfPoints;
  private Point[] tspInstance;
  private int[] route;
  private TSPCanvas canvas;
  private float maxX = 0, maxY = 0;
  public TSPVisualizer() {
    try {
      init();
      readInstance();
      readTour();
      drawInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void init() {
    br = new BufferedReader(new InputStreamReader(System.in));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    //setLayout(new FlowLayout());
    setSize(MAPWIDTH+100, MAPHEIGHT+200);
    setTitle("TSP map");
    setLocationRelativeTo(null);
    setVisible(true);
  }

  private void readInstance() throws IOException {
    numOfPoints = Integer.parseInt(br.readLine());
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
    //br.close();
  }

  private void readTour() throws IOException {
    route = new int[numOfPoints];
    for(int i = 0; i < numOfPoints; i++) {
      route[i] = Integer.parseInt(br.readLine());
    }
  }

  private void drawInstance() {
    canvas = new TSPCanvas(tspInstance, route);
    add("Center", canvas);
    validate();
  }


  @Override
  public String toString() {
    if(tspInstance == null) return "No instance";
    StringBuilder instanceSb = new StringBuilder();
    StringBuilder routeSb = new StringBuilder();
    instanceSb.append("Size: ").append(tspInstance.length).append("\n");
    for(int i = 0; i < tspInstance.length; i++) {
      instanceSb.append(tspInstance[i].getX()).append(" ").append(tspInstance[i].getY()).append("\n");
      routeSb.append(route[i]).append("\n");
    }
    instanceSb.append("\n").append(routeSb);
    return instanceSb.toString();
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
    private int[] route;
    public TSPCanvas(Point[] points, int[] route){
      this.points = points;
      this.route = route;
    }

    @Override
    public void paint(Graphics g) {
      drawBackground(g);
      drawRoute(g);
      drawPoints(g);
      validate();
    }

    private void drawBackground(Graphics g) {
      g.setColor(new Color(255,255,255));
      g.fillRect(MARGINLEFT, MARGINTOP, MAPWIDTH, MAPHEIGHT);
      g.setColor(new Color(0,0,0));
      g.drawRect(MARGINLEFT, MARGINTOP, MAPWIDTH, MAPHEIGHT);
    }

    private void drawPoints(Graphics g) {
      int x, y;
      for(int i = 0; i < points.length; i++) {
        x = (int) (MARGINLEFT+10+points[i].getX()*SCALEX);
        y = (int) (MARGINTOP+10+points[i].getY()*SCALEY);
        g.setColor(new Color(0,0,0));
        g.drawOval(x, y, 4, 4);
        g.setColor(new Color(20, 167, 8));
        g.fillOval(x, y, 4, 4);
      }
    }

    private void drawRoute(Graphics g) {
      int x1, y1, x2, y2;
      for(int i = 0; i < route.length-1; i++) {
        x1 = (int) (MARGINLEFT+12+points[route[i]].getX()*SCALEX);
        y1 = (int) (MARGINTOP+12+points[route[i]].getY()*SCALEY);
        x2 = (int) (MARGINLEFT+12+points[route[i+1]].getX()*SCALEX);
        y2 = (int) (MARGINTOP+12+points[route[i+1]].getY()*SCALEY);
        g.setColor(new Color(0,0,0));
        g.drawLine(x1, y1, x2, y2);
      }

    }

  }

  public static void main(String[] args) {
    TSPVisualizer tsp = new TSPVisualizer();
    //System.out.println(tsp);
  }

}
