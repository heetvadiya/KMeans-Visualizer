package org.heetvadiya;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class KMeansVisualizer extends JPanel {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int NUM_POINTS = 300;

    private static final int MAX_ITERATIONS = 50;
    private static final int NUM_CLUSTERS = 4;

    private ArrayList<Point> points;
    private ArrayList<Point> centroids;

    public KMeansVisualizer() {
        points = generateRandomPoints(NUM_POINTS);
        centroids = initializeCentroids(NUM_CLUSTERS);
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

    private void updateCentroids(ArrayList<ArrayList<Point>> clusters) {
        for (int i = 0; i < NUM_CLUSTERS; i++) {
            int sumX = 0, sumY = 0;
            for (Point p : clusters.get(i)) {
                sumX += p.x;
                sumY += p.y;
            }
            int meanX = sumX / clusters.get(i).size();
            int meanY = sumY / clusters.get(i).size();
            centroids.set(i, new Point(meanX, meanY));
        }
    }

    public void kmeans() {
        for (int iteration = 0; iteration < MAX_ITERATIONS; iteration++) {
            ArrayList<ArrayList<Point>> clusters = new ArrayList<>(NUM_CLUSTERS);

            for (int i = 0; i < NUM_CLUSTERS; i++) {
                clusters.add(new ArrayList<>());
            }

            for (Point p : points) {
                int closestCentroid = findClosestCentroid(p);
                clusters.get(closestCentroid).add(p);
            }

            updateCentroids(clusters);
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

        for (Point p : points) {
            g.setColor(Color.BLUE);
            g.fillOval(p.x, p.y, 5, 5);
        }

        for (Point centroid : centroids) {
            g.setColor(Color.RED);
            g.fillRect(centroid.x - 5, centroid.y - 5, 10, 10);
        }
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame("K-Means Clustering Visualizer");
        KMeansVisualizer visualizer = new KMeansVisualizer();
        frame.add(visualizer);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        visualizer.kmeans();
    }
}
