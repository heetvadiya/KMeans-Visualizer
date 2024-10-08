package org.heetvadiya;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class KMeansVisualizer extends JPanel {
    private static final int WIDTH = 1500;
    private static final int HEIGHT = 800;
    private static final int MAX_ITERATIONS = 20;

    private int numClusters;
    private int numPoints;

    private ArrayList<Point> points;
    private ArrayList<Point> centroids;
    private ArrayList<ArrayList<Point>> clusters;
    private Color[] clusterColors;

    public KMeansVisualizer(int numClusters, int numPoints) {
        this.numPoints = numPoints;
        this.numClusters = numClusters;

        points = generateRandomPoints(numPoints);
        centroids = initializeCentroids(numClusters);
        clusterColors = generateClusterColors(numClusters);

        clusters = new ArrayList<>(numClusters);
        for (int i = 0; i < numClusters; i++) {
            clusters.add(new ArrayList<>());
        }
        this.setBackground(Color.BLACK);
    }

    private ArrayList<Point> generateRandomPoints(int numPoints) {
        ArrayList<Point> points = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < numPoints; i++) {
            int x = rand.nextInt(WIDTH);
            int y = rand.nextInt(HEIGHT);
            points.add(new Point(x, y));
        }
        return points;
    }

    private ArrayList<Point> initializeCentroids(int numCentroids) {
        ArrayList<Point> centroids = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < numCentroids; i++) {
            int x = rand.nextInt(WIDTH);
            int y = rand.nextInt(HEIGHT);
            centroids.add(new Point(x, y));
        }
        return centroids;
    }

    private Color[] generateClusterColors(int numClusters) {
        Color[] colors = new Color[numClusters];
        for (int i = 0; i < numClusters; i++) {
            float hue = (float) i / numClusters;
            colors[i] = Color.getHSBColor(hue, 0.8f, 0.8f); // Adjust saturation and brightness as needed
        }
        return colors;
    }

    private int findClosestCentroid(Point p) {
        double minDist = Double.MAX_VALUE;
        int closestCentroid = -1;

        for (int i = 0; i < centroids.size(); i++) {
            double dist = p.distance(centroids.get(i));
            if (dist < minDist) {
                minDist = dist;
                closestCentroid = i;
            }
        }
        return closestCentroid;
    }

    private void updateCentroids() {
        for (int i = 0; i < numClusters; i++) {
            int sumX = 0, sumY = 0;
            for (Point p : clusters.get(i)) {
                sumX += p.x;
                sumY += p.y;
            }
            if (!clusters.get(i).isEmpty()) {
                int meanX = sumX / clusters.get(i).size();
                int meanY = sumY / clusters.get(i).size();
                centroids.set(i, new Point(meanX, meanY));
            }
        }
    }

    public void kmeans() {
        for (int iteration = 0; iteration < MAX_ITERATIONS; iteration++) {
            for (ArrayList<Point> cluster : clusters) {
                cluster.clear();
            }

            for (Point p : points) {
                int closestCentroid = findClosestCentroid(p);
                clusters.get(closestCentroid).add(p);
            }

            updateCentroids();
            repaint();

            try {
                Thread.sleep(1000); // Pause to visually observe changes
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw points
        for (int i = 0; i < numClusters; i++) {
            for (Point p : clusters.get(i)) {
                g.setColor(clusterColors[i]);
                g.fillOval(p.x, p.y, 7, 7);
            }
        }

        // Draw centroids
        for (int i = 0; i < centroids.size(); i++) {
            g.setColor(clusterColors[i]);
            g.fillRect(centroids.get(i).x - 5, centroids.get(i).y - 5, 14, 14);

            g.setColor(Color.WHITE);
            g.drawRect(centroids.get(i).x - 5, centroids.get(i).y - 5, 14, 14);
        }

    }

    public static void main(String[] args) {

        int numPoints = Integer.parseInt(JOptionPane.showInputDialog("Enter number of points:"));
        int numClusters = Integer.parseInt(JOptionPane.showInputDialog("Enter number of clusters:"));

        JFrame frame = new JFrame("K-Means Clustering Visualizer");
        KMeansVisualizer visualizer = new KMeansVisualizer(numClusters, numPoints);
        frame.add(visualizer);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        visualizer.kmeans();
    }
}

