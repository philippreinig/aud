package exercises.week09.hashfunction;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

//---------------------------------------------------------------//
public class MyHash {

    private static final int HASH_CONST = 3;
    //-------------------------------------------------------------//
    //-------------- !!Insert your solution below!! ---------------//
    public BucketArray e_;  //<-- !! USE ONLY THIS ARRAY !!
    int size;              //the capacity of the array

    //-------------------------------------------------------------//
    public MyHash(int size) {
        this.size = size;
        e_ = new BucketArray(size);
    }

    private static int testHashConstValues(String filepath, int size) {
        int bestVal = Integer.MAX_VALUE;
        for (int i = 0; i < 10000; i++) {
            MyHash hash = new MyHash(size);
            try {
                DataInput s = new DataInputStream(new FileInputStream(filepath));
                String line;
                while ((line = s.readLine()) != null) {
                    hash.insert_testing(line, i);

                    if (hash.e_.getCollisions() >= bestVal) {
                        System.out.println("for " + i + " currently at: " + hash.e_.getCollisions() + ", which is equal to " + bestVal + " -> aborting.");
                        break;
                    }
                }
            } catch (IOException e) {
                System.err.println("File not found");
            }
//      System.out.println(hash.e.toString());
            if (hash.e_.getCollisions() < bestVal) bestVal = hash.e_.getCollisions();
            System.out.println("Collisions for i =  " + i + ": " + hash.e_.getCollisions());
        }
        return bestVal;
    }

    private int calcHashValue(String s) {
        int hashValue = 0;
        for (int i = 0; i < s.length(); i++) {
            hashValue += s.charAt(i) * Math.pow(HASH_CONST, i);
        }
        return hashValue;
    }

    private int calcHashValue(String s, int hash_const) {
        int hashValue = 0;
        for (int i = 0; i < s.length(); i++) {
            hashValue += s.charAt(i) * Math.pow(hash_const, i);
        }
        return hashValue;
    }

    private int correctHashValue(int hashVal) {
        hashVal %= size;
        if (hashVal < 0) hashVal += size;
        assert (hashVal >= 0 && hashVal < size) : ("invalid hashVal: " + hashVal);
        return hashVal;
    }

    //-------------------------------------------------------------//
    public void insert(String s) {
//    System.out.println("inserting: " + s);
        int i = 0;
        final int hashValString = calcHashValue(s);
        int hashVal = correctHashValue(hashValString);
        while (!e_.insert(hashVal, s)) {
//      System.out.println("trying to insert: " + s + " at " + hashVal + " - try: " + i);
            i++;
            hashVal = correctHashValue((int) Math.pow(i, 2) * hashValString);
        }
        // TODO: implementation
        // work with return-value from BucketArray-insert
    }

    private void insert_testing(String s, int hash_const) {
//    System.out.println("inserting: " + s);
        int i = 0;
        final int hashValString = calcHashValue(s, hash_const);
        int hashVal = correctHashValue(hashValString);
        while (!e_.insert(hashVal, s)) {
//      System.out.println("trying to insert: " + s + " at " + hashVal + " - try: " + i);
            i++;
            hashVal = correctHashValue((int) Math.pow(i, 2) * hashValString);
        }
        // TODO: implementation
        // work with return-value from BucketArray-insert
    }

    //---------------------------------------------------------------//
    //---------------------------------------------------------------//
    //--------------- !!Do not change the code below!! --------------//
    class BucketArray {
        protected String[] arr;
        protected int col;

        //-------------------------------------------------------------//
        public BucketArray(int size) {
            col = 0;
            arr = new String[size];
        }

        //-------------------------------------------------------------//

        /**
         * Insert the String s at index pos into the the BucketArray.
         * If this index is already filled/blocked by another String,
         * s is *NOT* added and false is returned.
         * \return - true, if s was added correctly
         * - false, if there is already an entry for this position.
         */
        public boolean insert(int pos, String s) {
            if (arr[pos] == null) {
                arr[pos] = s;
                return true;
            }
            col++;
            return false;
        }

        //-------------------------------------------------------------//
        public int getCollisions() {
            return col;
        }

        //-------------------------------------------------------------//
        public String toString() {
            StringBuilder res = new StringBuilder();
            for (String s : arr)
                res.append(s).append("\n");
            return res.toString();
        }
    }

    //-------------------------------------------------------------//
    public static void main(String[] args) {
        System.out.println("Best value is: " + testHashConstValues("/home/philipp/Development/aud/src/exercises/week09/hashfunction/ww1.txt", 1249));


    /*
    // Idea for test
    int size = 1249;  // only an example (backend test)
    MyHash hash = new MyHash(size);
    try {
      DataInput s = new DataInputStream(new FileInputStream("/home/philipp/Development/aud/src/exercises/week09/hashfunction/ww1.txt"));
      // use correct Path
      String line;
      while ((line = s.readLine()) != null) {
        hash.insert(line);
      }
    } catch (IOException e) {
      System.err.println("File not found");
    }
    System.out.println(hash.e.toString());
    System.out.println("Collisions: " + hash.e.getCollisions() + "\n");

     */
    }
}
