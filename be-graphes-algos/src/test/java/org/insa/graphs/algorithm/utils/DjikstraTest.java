package org.insa.graphs.algorithm.utils;

import java.io.IOException;
import java.util.List;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;

public class DjikstraTest {
    private static int nbSituations = 5;

    private static ShortestPathData[] datas;
    private static Node[] origins;
    private static Node[] destinations;
    private static Graph[] graphs;
    private static ArcInspector[] arrayFilters;
    
    @BeforeClass
    public static void initAll() throws IOException {
        origins = new Node[nbSituations];
        destinations = new Node[nbSituations];
        graphs = new Graph[nbSituations];
        datas = new ShortestPathData[nbSituations];

        List<ArcInspector> filters = ArcInspectorFactory.getAllFilters();
        arrayFilters = filters.toArray(ArcInspector[]::new);
    }

    // TEST POUR CHEMIN DE LONGUEUR NULLE
    @Test
    public void testSituation1() throws Exception{
        String mapName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
        final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

        graphs[0] = reader.read();
        origins[0] = graphs[0].get(30);
        destinations[0] = graphs[0].get(30);
        datas[0] = new ShortestPathData(graphs[0], origins[0], destinations[0], arrayFilters[0]);

        DijkstraAlgorithm djikstra = new DijkstraAlgorithm(datas[0]);

        //ShortestPathSolution djikstraPath = djikstra.run();
        Path djikstraPath = djikstra.run().getPath();
        double djikstraPathLength = 0;
        if( djikstraPath != null){
            djikstraPathLength = djikstraPath.getLength();
        }

        assertEquals(0,djikstraPathLength,0.00001);
    }
}
  