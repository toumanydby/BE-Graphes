package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;

public class LabelStar extends Label {
    
    private double estimatedCost; // notre heuristique

    public LabelStar(Node sommetCourant, double estimatedCost){
        super(sommetCourant);
        this.estimatedCost = estimatedCost;
    }

    public double getEstimatedCost(){
        return this.estimatedCost;
    }

    public void setEstimatedCost(double estimatedCost){
        this.estimatedCost = estimatedCost;
    }

    public double getTotalCost(){
        return super.getCost() + this.estimatedCost;
    }
}
