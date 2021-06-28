package exercises.week10;


import java.sql.SQLOutput;
import java.util.*;

public class GraphDataStructureConversion {
    private static int[][] edgeListToAdjacencyMatrix(int[] edgeList){
        int[][] adjacencyMatrix = new int[edgeList[0]][edgeList[0]];
        for(int i = 2; i < edgeList.length-1; i+=2){
            adjacencyMatrix[edgeList[i]-1][edgeList[i+1]-1] = 1;
        }
        return adjacencyMatrix;
    }

    private static Map<Integer, ArrayList<Integer>> edgeListToAdjacencyList(int[] edgeList){
        int counter = 0;
        HashMap<Integer, ArrayList<Integer>> adjacencyList = new HashMap<>();
        for(int i = 0; i < edgeList[0]; i++){
            adjacencyList.put(i, new ArrayList<>());
        }
        for(int i = 2; i < edgeList.length; i+=2){
            adjacencyList.get(edgeList[i]-1).add(edgeList[i+1]-1);
            counter++;
        }
        assert(counter == edgeList[1]);
        return adjacencyList;
    }

    private static void printAdjacencyList(Map<Integer, ArrayList<Integer>> adjacencyList){
        for(Map.Entry<Integer, ArrayList<Integer>> entry : adjacencyList.entrySet()){
            System.out.print((entry.getKey()+1) + " -> ");
            for(Integer elem : entry.getValue()){
                System.out.print((elem+1) + ", ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[] edgeList = {5,8,1,2,1,3,1,4,1,5,2,4,2,5,3,4,4,5};
        int[][] adjacencyMatrix = edgeListToAdjacencyMatrix(edgeList);
        Map<Integer, ArrayList<Integer>> adjacencyList = edgeListToAdjacencyList(edgeList);
        System.out.println("edgelist:");
        System.out.println(Arrays.toString(edgeList));
        System.out.println("adjacencyMatrix:");
        System.out.println(Arrays.deepToString(adjacencyMatrix).replace("], ", "]\n").length());
        System.out.println(Arrays.deepToString(adjacencyMatrix).replace("], ", "]\n").substring(1, (Arrays.deepToString(adjacencyMatrix).replace("], ", "]\n").length()-1)));
        System.out.println("adjacencyList:");
        printAdjacencyList(adjacencyList);

    }
}

