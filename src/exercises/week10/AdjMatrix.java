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
            List<Integer> first = adjacent(i, m);
            for(Integer j : first){
                List<Integer> second = adjacent(j, m);
                for(Integer k : second){
                    List<Integer> third = adjacent(k, m);
                    for(Integer l : third){
                        if (i == l){
//                            System.out.println(i + ", " + j + ", " + k + ", " + l);
                            return true;
                        }
                    }
                }

            }
        }
        return false;

    }

    public static void main(String args[]) {
        int[][] adjacencyMatrix =
                {
                    {0,1,0,0,1},
                    {0,0,0,1,0},
                    {0,1,0,0,0},
                    {0,0,1,0,0},
                    {0,0,0,1,0}
                };
        System.out.println(Arrays.deepToString(adjacencyMatrix).replace("], ", "]\n").substring(1, (Arrays.deepToString(adjacencyMatrix).replace("], ", "]\n").length()-1)));
        for(int i = 0; i < adjacencyMatrix.length; ++i){
            System.out.println(i + ": indegree " + inDegree(i, adjacencyMatrix) + ", outdegree: " + outDegree(i, adjacencyMatrix));
        }
        System.out.println("hasTriangle: " + hasTriangle(adjacencyMatrix));

    }
}