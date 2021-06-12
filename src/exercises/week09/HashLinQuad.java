package exercises.week09;

//-----------------------------------------------------------------//

import java.util.Arrays;
import java.util.Random;

/** simple Hashtable with linear/quadratic probing
 capacity is big enough */

public class HashLinQuad {
    private static final boolean DEBUG = false ;
    //---------------------------------------------------------------//
    private final int[] table;    // array of buckets or slots
    private int size;       // current number of elements
    private final int capacity;
    private int quadSummandRoot = 1;

    HashLinQuad(int capacity){
        this.capacity = capacity;
        this.table = new int[capacity];
    }

    //---------------------------------------------------------------//
    public int addLin(int obj) {
        if (size == capacity) throw new IllegalStateException();
        if (DEBUG) System.out.println("Before insertion lin: " + Arrays.toString(table) + "+" + obj);
        int pos = obj % capacity;
        int counter = 0;
        while (table[pos] != 0) {
            counter++;
            if (pos+1 > table.length-1) pos = 0;
            else pos++;
            if(counter == capacity) throw new IllegalStateException();
        }
        table[pos] = obj;
        if (DEBUG) System.out.println("After insertion lin: " + Arrays.toString(table) + "collisions: " + counter);
        size++;
        return counter;
    }

    //---------------------------------------------------------------//
    public int addQuad(int obj) {
        if (size == capacity) throw new IllegalStateException();
        if (DEBUG) System.out.println("Before insertion quad: " + Arrays.toString(table) + "+" + obj);
        int pos = obj % capacity;
        int counter = 0;
        while (table[pos] != 0) {
            counter++;

            for (int i = 0; i < quadSummandRoot * quadSummandRoot; i++) {
                if (pos+1 > table.length-1) pos = 0;
                else pos++;
            }
            quadSummandRoot++;
        }
        table[pos] = obj;
        if (DEBUG) System.out.println("After insertion quad: " + Arrays.toString(table) + "collisions: " + counter);
        size++;
        return counter;

    }

    //---------------------------------------------------------------//
    @Override
    public String toString() {
        return Arrays.toString(table);
    }

    //---------------------------------------------------------------//
    public static void main(String[] args) {
        int capacity = 1249;
        int amountToInsert = 1000;
        int max = 30;
        Random rand = new Random();
        HashLinQuad hlq_lin = new HashLinQuad(capacity);

        int counterLin = 0;
        int counterQuad = 0;

        for (int i = 0; i < amountToInsert; i++){
           counterLin += hlq_lin.addLin(rand.nextInt(max)+1);
        }

        HashLinQuad hlq_quad = new HashLinQuad(capacity);
        for (int i = 0; i < amountToInsert; i++){
           counterQuad += hlq_quad.addQuad(rand.nextInt(max)+1);
        }

        System.out.println("Collisions for linear probing: " + counterLin);
        System.out.println("Collisions for linear probing: " + counterQuad);
    }
}