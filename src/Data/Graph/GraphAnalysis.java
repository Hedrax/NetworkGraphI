package Data.Graph;
import Data.Database.Database;
import Resource.Node;
import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.algorithms.importance.BetweennessCentrality;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.scoring.ClosenessCentrality;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.*;
import java.util.List;
import org.knowm.xchart.*;


public class GraphAnalysis {
    public static Graph<String,Integer> buildGraph(){
        Graph<String, Integer> graph = new SparseGraph<>();
        List<Node> nodes = Database.SelectNodesRange(1,10);
        System.out.println(nodes.size());
        int i = 0;
        for(Node n:nodes){
            for (String follower:n.followers){
                i++;
                graph.addEdge(i, n.userName,follower , EdgeType.DIRECTED);
            }
        }
        System.out.println("build");
        return graph;
    }
    public static int vertaxDegree(Graph g ,String ver ){
        return g.getOutEdges(ver).size();
    }
    public static void degreeDistribution(Graph<String,Integer> graph ){
        Map<Integer, Integer> degreeDistribution = new HashMap<>();
        for (String vertex : graph.getVertices()) {
            int degree = vertaxDegree(graph,vertex);
            degreeDistribution.put(degree, degreeDistribution.getOrDefault(degree, 0) + 1);
        }
        // Print the degree distribution
        XYChart chart = new XYChartBuilder().width(800).height(600)
                .title("Degree Distribution").xAxisTitle("Degree").yAxisTitle("Frequency").build();

        // Customize chart style
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Create arrays to store x and y values
        double[] degrees = new double[degreeDistribution.size()];
        double[] frequencies = new double[degreeDistribution.size()];

        int index = 0;
        // Populate the arrays with degree and frequency values
        for (Map.Entry<Integer, Integer> entry : degreeDistribution.entrySet()) {
            degrees[index] = entry.getKey();
            frequencies[index] = entry.getValue();
            index++;
        }

        // Add the series to the chart
        chart.addSeries("Degree Distribution", degrees, frequencies);

        // Display the chart
        new SwingWrapper<>(chart).displayChart();
    }
    public static void findShortestPath(Graph<String,Integer> graph, String start, String end) {
        DijkstraShortestPath<String, Integer> shortestPath = new DijkstraShortestPath<>(graph);
        List<Integer> path = shortestPath.getPath(start, end);
        System.out.println(path);
    }
    public static void CentralityAnalysis(Graph<String,Integer> graph, String vertax) {
        System.out.println("Degree centrality: "+vertaxDegree(graph,vertax));
        BetweennessCentrality<String,Integer> betweenness = new BetweennessCentrality<>(graph);
        betweenness.setRemoveRankScoresOnFinalize(false);
        betweenness.evaluate();
        double betweennessCentrality = betweenness.getVertexRankScore(vertax);
        System.out.println("Betweenness centrality: "+ betweennessCentrality);
        ClosenessCentrality<String,Integer> closeness = new ClosenessCentrality<>(graph);
        double closenessCentrality = closeness.getVertexScore(vertax);
        System.out.println("Closeness centrality: "+ closenessCentrality);
    }
    public static void connectedComponentsAnalysis(Graph<String,Integer> graph, String vertax){
        WeakComponentClusterer<String,Integer> clusterer = new WeakComponentClusterer<>();
        Set<Set<String>> components = clusterer.transform(graph);
        System.out.println("Found " + components.size() + " connected component(s):");
        // print out the nodes in each connected component
        for (Set<String> component : components) {
            System.out.println(component);
        }
    }
    public static void clusteringCoefficients(Graph<String,Integer> graph){
        // compute the clustering coefficient for each node in the graph
        for (String v : graph.getVertices()) {
            int k = graph.degree(v);
            int triangles = 0;
            for (String u : graph.getNeighbors(v)) {
                for (String w : graph.getNeighbors(v)) {
                    if (graph.isNeighbor(u, w)) {
                        triangles++;
                    }
                }
            }
            double cc = 0.0;
            if (k > 1) {
                cc = (double) triangles / (double) (k * (k - 1) / 2);
            }
            System.out.println("Clustering coefficient of node " + v + ": " + cc);
        }
        // compute the global clustering coefficient for the graph
        int triangles = 0;
        for (String v : graph.getVertices()) {
            for (String u : graph.getNeighbors(v)) {
                for (String w : graph.getNeighbors(v)) {
                    if (graph.isNeighbor(u, w)) {
                        triangles++;
                    }
                }
            }
        }
        int k = graph.getEdgeCount();
        double globalCC = 0.0;
        if (k > 1) {
            globalCC = (double) triangles / (double) (k * (k - 1) / 2);
        }
        System.out.println("Global clustering coefficient: " + globalCC);
    }
    public static void densityAnalysis(Graph<String,Integer> graph){
        int V = graph.getVertexCount();
        int E = graph.getEdgeCount();
        double density = 2.0 * E / (V * (V - 1));
        System.out.println("Density of the graph: " + density);
    }
    public static void communityDiscovery(Graph<String,Integer> graph){
        // perform community detection by partitioning the graph into connected components
        Set<Set<String>> communities = new HashSet<>();
        for (String vertex : graph.getVertices()) {
            Set<String> community = new HashSet<>();
            community.add(vertex);
            Set<String> neighbors = new HashSet<>(graph.getNeighbors(vertex));
            while (!neighbors.isEmpty()) {
                String neighbor = neighbors.iterator().next();
                community.add(neighbor);
                neighbors.addAll(graph.getNeighbors(neighbor));
                neighbors.removeAll(community);
            }
            communities.add(community);
        }
        // print the detected communities
        System.out.println("Detected communities:");
        for (Set<String> community : communities) {
            System.out.println(community);
        }
    }
    public static void networkvisualization(Graph<String,Integer> graph){
        // Create a visualization of the graph
        Layout<String, Integer> layout = new FRLayout<>(graph);
        layout.setSize(new Dimension(600,600));

        // Create the visualization viewer
        VisualizationViewer<String, Integer> vv = new VisualizationViewer<>(layout);
        vv.setPreferredSize(new Dimension(700,700));

        // Set the vertex and edge labels
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<>());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<>());

        // Set the vertex and edge shapes
        vv.getRenderContext().setVertexShapeTransformer(v -> new Ellipse2D.Double(-10, -10, 20, 20));
        vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line<>());
        // Set up the mouse interaction
        DefaultModalGraphMouse<String, Integer> gm = new DefaultModalGraphMouse<>();
        gm.setMode(DefaultModalGraphMouse.Mode.TRANSFORMING);
        vv.setGraphMouse(gm);

        // Create and show the frame
        JFrame frame = new JFrame("Graph Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }
}
