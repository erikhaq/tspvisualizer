package tsp.visualizer;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TSPVisualizer extends JFrame {
  private Point[] tspInstance;
  private TSPCanvas canvas;
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
    setSize(500, 500);
    setTitle("TSP map");
    setLocationRelativeTo(null);
    setVisible(true);
  }

  private void readInstance() throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int numOfPoints = Integer.parseInt(br.readLine());
    tspInstance = new Point[numOfPoints];
    String[] coordinates;
    for(int i = 0; i < numOfPoints; i++) {
      coordinates = br.readLine().split(" ");
      tspInstance[i] = new Point(coordinates[0], coordinates[1]);
    }
  }

  private void drawInstance() {
    canvas = new TSPCanvas();
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
    @Override
    public void paint(Graphics g) {
      g.drawString("TSPCanvas", 10, 20);
    }
  }

  public static void main(String[] args) {
    TSPVisualizer tsp = new TSPVisualizer();
    System.out.println(tsp);
  }

}
