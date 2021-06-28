package exercises.week10;

import java.util.*;

//---------------------------------------------------------------//
public class AdjMatrix{
    //-------------------------------------------------------------//
    public  static int inDegree(int k, int[][] m){
        if (k < 0 || k>= m.length){
            throw new IllegalArgumentException();
        }
        int counter = 0;
        for(int i = 0; i < m.length; i++){
            if (m[i][k] != 0) ++counter;
        }
        return counter;
    }

    //-------------------------------------------------------------//
    public  static int outDegree(int k, int[][] m){
        if (k < 0 || k>= m.length){
            throw new IllegalArgumentException();
        }
        int counter = 0;
        for(int i = 0; i < m.length; i++){
            if (m[k][i] != 0) ++counter;
        }
        return counter;
    }

    //-------------------------------------------------------------//
    public static List<Integer> adjacent(int k, int[][] m){
        ArrayList<Integer> adjacents = new ArrayList<>();
        for(int i = 0; i < m.length; ++i){
                if (m[k][i] != 0) adjacents.add(i);
        }
        return adjacents;
    }

    //-------------------------------------------------------------//
    public static boolean hasTriangle(int[][] m) {
        int n = m.length;
        for(int i = 0; i < n; ++i){
            for(int j = 0; j < n; ++j){
                if (m[i][j] != 0){
                    for(int k = 0; k < n; k++){
                        if (m[j][k] != 0){
                            System.out.println("matrix has triangle for " + j + ", " + k);
                            return true;
                        }
                    }

                }
            }
        }
        return false;

    }

    //-------------------------------------------------------------//
    public static void main(String args[]) {
        int[][] adjacencyMatrix =
                {
                    {0,1,1,0,0},
                    {1,0,0,1,0},
                    {0,0,1,0,0},
                    {1,1,1,0,1},
                    {1,0,0,0,1}
                };
        System.out.println(Arrays.deepToString(adjacencyMatrix).replace("], ", "]\n").substring(1, (Arrays.deepToString(adjacencyMatrix).replace("], ", "]\n").length()-1)));
        for(int i = 0; i < adjacencyMatrix.length; ++i){
            System.out.println(i + ": indegree " + inDegree(i, adjacencyMatrix) + ", outdegree: " + outDegree(i, adjacencyMatrix));
        }
        System.out.println("hasTriangle: " + hasTriangle(adjacencyMatrix));

    }
}