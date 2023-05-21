package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;

public class AStarAlgorithm extends DijkstraAlgorithm {

    private Node destinationNode;
    private double maxSpeedGraph;

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
        this.destinationNode = data.getDestination();
        switch (data.getMode()) {
            case TIME:
                this.maxSpeedGraph = (double) data.getGraph().getGraphInformation().getMaximumSpeed() / 3.6;
                break;
            case LENGTH:
                this.maxSpeedGraph = 1.0;
                break;
        }
    }

    @Override
    protected void initializeLabel(Label[] labelTab, ShortestPathData data) {
        for (Node node: data.getGraph().getNodes()) {
            // On effectue cette division pour se rassurer que notre heuristique est bien une borne inferieure du cout reel 
            labelTab[node.getId()] = new LabelStar(node, Point.distance(node.getPoint(), destinationNode.getPoint()) / maxSpeedGraph);
        }
    }

    
}
