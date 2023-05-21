package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements java.lang.Comparable<Label>{

    private Node sommetCourant;
    private Boolean isMarqued;
    private double coutRealized; // valeur courante du plus court chemin depuis l'origine vers le sommet.
    private Arc pere;


    public Label(Node sommetCourant){
        this.sommetCourant = sommetCourant;
        this.isMarqued = false;
        // On met le cout maximal posible a la creation du label qui correspond a infini
        this.coutRealized = Double.POSITIVE_INFINITY;
        this.pere = null;
    }

    public void setCost(double cout) {
		this.coutRealized = cout;
	}

    public Node getSommetCourant() {
        return sommetCourant;
    }
    public Boolean getIsMarqued() {
        return isMarqued;
    }
    public double getEstimatedCost() {
        return 0;
    }
    public Arc getPere() {
        return pere;
    }

    public double getCost(){
        return coutRealized;
    }

    public double getTotalCost(){
        return this.getCost()+this.getEstimatedCost();
    }


    public void setIsMarqued(Boolean isMarqued) {
        this.isMarqued = isMarqued;
    }

    public void setPere(Arc pere) {
        this.pere = pere;
    }

    @Override
    public int compareTo(Label toCompare) {
        return Double.compare(this.getTotalCost(), toCompare.getTotalCost());
    }


}
