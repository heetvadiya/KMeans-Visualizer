# K-Means Clustering Visualizer

This project is a Java-based visualizer for the K-Means clustering algorithm. It allows users to see how the K-Means algorithm works by dynamically clustering random points into a specified number of clusters, with each cluster being color-coded. The visualizer updates in real-time, showing the movement of centroids and the clustering of points at each iteration.

## Features

- **Real-Time Visualization**: Watch as the algorithm dynamically clusters points and updates the positions of centroids.
- **Dynamic Parameters**: Choose the number of clusters and the number of points to visualize.
- **Cluster Boundaries**: The final clusters are outlined with black borders to show the boundaries of each cluster.

## Requirements

- Java 8 or higher

## How to Run

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/yourusername/KMeansVisualizer.git
   cd KMeansVisualizer
   ```

2. **Compile and Run**:
   ```bash
   javac -d bin src/org/heetvadiya/*.java
   java -cp bin org.heetvadiya.KMeansVisualizer
   ```

3. **Run the JAR File (if available)**:
   If you have already packaged the application as a JAR file, you can run it using:
   ```bash
   java -jar KMeansVisualizer.jar
   ```

4. **User Input**:
   When you run the application, you will be prompted to enter:
    - The number of clusters.
    - The number of points.

   The visualizer will then display the clustering process in a graphical window.

## Future Improvements

- Convert the visualizer to a web-based application using JavaScript and D3.js.
- Deploy the application online so that others can access it without needing to download the JAR file.
- Add more customization options, such as changing the delay between iterations or selecting different distance metrics.

## Contributing
Contributions are welcome! Please feel free to submit a pull request or open an issue for any bugs or feature requests.
