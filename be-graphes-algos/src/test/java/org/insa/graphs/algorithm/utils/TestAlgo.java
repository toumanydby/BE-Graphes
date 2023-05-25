package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.*;
import org.insa.graphs.algorithm.shortestpath.*;
import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.Test;
import org.junit.Assume;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class TestAlgo {
	private static Graph graph1;
	private static Graph graph2;
	private static Graph graph3;
	
	
    @Parameters
    public static Collection<ShortestPathData> data() throws IOException {
    	//**************************************************************
    	//**************************************************************
    	//CREATION DU GRAPHE DE LA CARTE HAUTE-GARONNE
        String mapName1 = "/media/toumany/disqueDocument/Documents/Graphes-et-Algorithmes/Maps/haute-garonne.mapgr";
        //String mapName1 = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/haute-garonne.mapgr";
        final FileInputStream file1= new FileInputStream(mapName1);
        // Create a graph reader.
        try {
            final GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName1))));
            // Small graph use for tests
            graph1 = reader.read();
        }catch(Exception e) {System.out.println("Fichier non lu!");}
      
        //******************************************************************
      //******************************************************************
      //CREATION DU GRAPHE DE LA CARTE CARRE
        String mapName2 = "/media/toumany/disqueDocument/Documents/Graphes-et-Algorithmes/Maps/carre.mapgr";
        //String mapName2 = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/carre.mapgr";
        final FileInputStream file2= new FileInputStream(mapName2);

        // Create a graph reader.
        try {
        final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName2))));
        
        
        // Small graph use for tests
        graph2 = reader.read();
        }
        catch(Exception e) {System.out.println("Fichier non lu!");}
        
        //******************************************************************
        //******************************************************************
        //CREATION DU GRAPHE DE LA CARTE POLYNESIE FRANCAISE
        String mapName3 = "/media/toumany/disqueDocument/Documents/Graphes-et-Algorithmes/Maps/french-polynesia.mapgr";
        //String mapName3 = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/french-polynesia.mapgr";
        final FileInputStream file3= new FileInputStream(mapName2);
          // Create a graph reader.
          try {
          final GraphReader reader = new BinaryGraphReader(
                  new DataInputStream(new BufferedInputStream(new FileInputStream(mapName3))));
          
          
          // Small graph use for tests
          graph3 = reader.read();
          } catch(Exception e) {
            System.out.println("Fichier non lu!");
        }
        file1.close();
        file2.close();
        file3.close(); 
          
        
    	Collection<ShortestPathData> objects = new ArrayList<>();
    	
    	//***************CARTE HAUTE-GARONNE****************************
    	//**************************************************************
    	//CHEMIN NUL AVEC 1 MODE DE DEPLACEMENTS
    	objects.add(new ShortestPathData(graph1, graph1.get(10991),graph1.get(10991),ArcInspectorFactory.getAllFilters().get(0)));
    	//CHEMIN AVEC 2 MODES DE DEPLACEMENTS
    	objects.add(new ShortestPathData(graph1, graph1.get(10991),graph1.get(10991),ArcInspectorFactory.getAllFilters().get(1)));
    	objects.add(new ShortestPathData(graph1, graph1.get(10991),graph1.get(10991),ArcInspectorFactory.getAllFilters().get(2)));
    	
        
        //***************CARTE CARRE************************************
    	//**************************************************************
    	//CHEMIN NUL AVEC 2 MODES DE DEPLACEMENTS
    	objects.add(new ShortestPathData(graph2, graph2.get(19),graph2.get(19),ArcInspectorFactory.getAllFilters().get(0)));
    	objects.add(new ShortestPathData(graph2, graph2.get(19),graph2.get(19),ArcInspectorFactory.getAllFilters().get(2)));
    	//CHEMIN AVEC 3 MODES DE DEPLACEMENTS
    	objects.add(new ShortestPathData(graph2, graph2.get(19),graph2.get(5),ArcInspectorFactory.getAllFilters().get(0)));
    	objects.add(new ShortestPathData(graph2, graph2.get(19),graph2.get(5),ArcInspectorFactory.getAllFilters().get(2)));
    	objects.add(new ShortestPathData(graph2, graph2.get(19),graph2.get(5),ArcInspectorFactory.getAllFilters().get(1)));
    	
    	
    	
    	//*************CARTE POLYNESIE FRANCAISE**********************
    	//************************************************************
    	//CHEMIN IMPOSSIBLE AVEC 2 MODES DE DEPLACEMENTS
    	objects.add(new ShortestPathData(graph3, graph3.get(8302),graph3.get(4904),ArcInspectorFactory.getAllFilters().get(0)));
    	objects.add(new ShortestPathData(graph3, graph3.get(8302),graph3.get(4904),ArcInspectorFactory.getAllFilters().get(2)));
        
        return objects;
    }
    
    
    @Parameter
    public ShortestPathData parameter;
    
    
    @Test
    public void testCheminNul()
    {	//Shortest path, all roads allowed
    	Assume.assumeTrue(parameter.getOrigin().getId()==parameter.getDestination().getId() && parameter.getGraph().getMapId() != graph3.getMapId());
    	ShortestPathAlgorithm Dijkstra= new DijkstraAlgorithm(parameter);
    	ShortestPathAlgorithm BF= new BellmanFordAlgorithm(parameter);
    	DijkstraAlgorithm Astar= new AStarAlgorithm(parameter);
    	assertEquals(Dijkstra.run().isFeasible(),BF.run().isFeasible());
    	assertEquals(Astar.run().isFeasible(),BF.run().isFeasible());
    }
    
   
    @Test
    public void testCheminCourt()
    {
    	Assume.assumeTrue(parameter.getOrigin().getId()!=parameter.getDestination().getId() && parameter.getGraph().getMapId() != graph3.getMapId());
    	ShortestPathAlgorithm Dijkstra= new DijkstraAlgorithm(parameter);
    	ShortestPathAlgorithm BF= new BellmanFordAlgorithm(parameter);
    	DijkstraAlgorithm Astar= new AStarAlgorithm(parameter);
    	assertEquals(Dijkstra.run().getPath().getLength(), BF.run().getPath().getLength(),0.0001);
    	assertEquals(Astar.run().getPath().getLength(),BF.run().getPath().getLength(),0.0001);
    	assertEquals(Dijkstra.run().getPath().getMinimumTravelTime(), BF.run().getPath().getMinimumTravelTime(),0.0001);
    	assertEquals(Astar.run().getPath().getMinimumTravelTime(),BF.run().getPath().getMinimumTravelTime(),0.0001);
    	
    }
    
    @Test
    public void testCheminImpossible()
    {	//Shortest path, all roads allowed
    	Assume.assumeTrue(parameter.getGraph().getMapId() == graph3.getMapId());
    	ShortestPathAlgorithm Dijkstra= new DijkstraAlgorithm(parameter);
    	ShortestPathAlgorithm BF= new BellmanFordAlgorithm(parameter);
    	DijkstraAlgorithm Astar= new AStarAlgorithm(parameter);
    	assertEquals(Dijkstra.run().isFeasible(),BF.run().isFeasible());
    	assertEquals(Astar.run().isFeasible(),BF.run().isFeasible());
    }

}
