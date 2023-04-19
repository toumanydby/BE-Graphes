package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.ElementNotFoundException;
import org.insa.graphs.model.Arc;
import org.insa.graphs.algorithm.AbstractSolution.Status;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    /**
     *  Cette fonction permet d'initialiser les labels de notre graph 
     * @param labelTab tableau de label 
     * @param data 
     */
    protected void initializeLabel(Label[] labelTab, ShortestPathData data){
        for (Node node: data.getGraph().getNodes()) {
            labelTab[node.getId()] = new Label(node);
        }
    }

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        // Plus court chemin retourne par Dijkstra
        ShortestPathSolution solution = null;
        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();

        final int nbNodes = graph.size();

        // On initialise notre tas
        BinaryHeap<Label> tas = new BinaryHeap<Label>();

        // On cree notre tableau d'etiquette et on l'initialise
        Label [] ourTLabels = new Label[nbNodes];
        initializeLabel(ourTLabels, data);

        // ON DEROULE L'ALGO DE DJIKSTRA

        // On met le cout de l'origine a 0 et on l'onsere dans le tas
        ourTLabels[data.getOrigin().getId()].setCost(0);
        tas.insert(ourTLabels[data.getOrigin().getId()]);
        // On signale avoir traite l'origine
        this.notifyOriginProcessed(data.getOrigin());
        
        while(!(ourTLabels[data.getDestination().getId()].getIsMarqued()) && !(tas.isEmpty())){

            // On recupere le sommet le noeud le ayant le plus petit cout sinon on sort du while
            Node current_node= tas.findMin().getSommetCourant();

            // On marque ensuite le noeud comme visite et on le notifie
            ourTLabels[current_node.getId()].setIsMarqued(true);
            this.notifyNodeMarked(current_node);

            // On boucle sur tous les successeurs de current_node
            for (Arc arc : current_node.getSuccessors()) {
                Node successor_desti_node = arc.getDestination();
                if(!(ourTLabels[successor_desti_node.getId()].getIsMarqued())){
                    if(
                        (
                            ourTLabels[successor_desti_node.getId()].getCost() > 
                            (ourTLabels[current_node.getId()].getCost() + data.getCost(arc))
                        ) 
                        &&
                        data.isAllowed(arc)
                    ){

                        // On efectue la mise a jour de l'etiquette dans le tas suppression et rajout avec le nouveau cout et le pere
                        try {
                            tas.remove(ourTLabels[successor_desti_node.getId()]);
                        } catch (ElementNotFoundException e) {}

                        ourTLabels[successor_desti_node.getId()].setPere(arc);
                        ourTLabels[successor_desti_node.getId()].setCost((ourTLabels[current_node.getId()].getCost() + data.getCost(arc)));
                        tas.insert(ourTLabels[successor_desti_node.getId()]);
                        this.notifyNodeReached(successor_desti_node);
                    }
                }
            }
        }

        if( ourTLabels[data.getDestination().getId()].getPere() == null){
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        } else{
            this.notifyDestinationReached(data.getDestination());
            //Contiendra tous les arcs empruntes durant le chemin
            ArrayList<Arc> arcs = new ArrayList<>();

            // On recupere le dernier arc du chemin
            Arc arc = ourTLabels[data.getDestination().getId()].getPere();
            
            while (arc != null) {
                arcs.add(arc);
                arc = ourTLabels[arc.getOrigin().getId()].getPere();
            }
           
            // On reorganise les arcs pour qu'ils partent de l'origine a la destination
            Collections.reverse(arcs);
            
            //Vérification grâce à Path.createShortestPathFromNodes
            Path path_to_take= new Path(graph,arcs);

            //Vérification de la validation du path
            if(path_to_take.isValid()){
                System.out.println("Notre chemin est correct");
            }else{
                System.out.println("Notre chemin n'est pas validé");
            }

            // On cree la solution du plus court chemin a retourner grace au path creee
            solution = new ShortestPathSolution(data, Status.OPTIMAL, path_to_take);
        }
        
        return solution;
    }

}
