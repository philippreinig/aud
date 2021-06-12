package exercises.week08;

import java.util.*;

//-----------------------------------------------------------------//
public class MaxHeap<T extends Comparable<T>> {
    private static final int INVALID_INDEX = -1;
    //-----------------------------------------------------------------//
    //------------- !!Do not change the following lines!! -------------//
    public String toString() {
        return heap.toString();  // don't change because of backend
    }

    //---------------------------------------------------------------//
    //--------------- !!Insert your solution below!! ----------------//
    private final ArrayList<T> heap;  // or Vector

    //---------------------------------------------------------------//
    public MaxHeap() {
        heap = new ArrayList<>();
    }

    //---------------------------------------------------------------//
    public MaxHeap(T[] arr) {
        heap = new ArrayList<>();
        heap.addAll(Arrays.asList(arr));
        for(int i = heap.size()/2; i >= 0; i--){
            this.downHeap(i);
        }
    }

    //---------------------------------------------------------------//
    public ArrayList<T> getHeap(){
        return heap;
    }

    //---------------------------------------------------------------//
    public int getSize() {
        return heap.size();
    }

    //---------------------------------------------------------------//
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    private int checkIndex(int i){
        if (i >= 0 && i < this.getSize()) return i;
        else return INVALID_INDEX;
    }

    private void swap(int i, int j){
        if (!isValid(i) || !isValid(j)) throw new IndexOutOfBoundsException();
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    private boolean isValid(int i){
        return checkIndex(i) != -1;
    }

    //---------------------------------------------------------------//
    public void downHeap(int n) {
        if (!isValid(n)) throw new IndexOutOfBoundsException();
        int leftChild = checkIndex(2*n+1);
        int rightChild = checkIndex(2*n+2);
        T leftChildValue = isValid(leftChild) ? heap.get(leftChild) : null;
        T rightChildValue = isValid(rightChild) ? heap.get(rightChild) : null;
        int swapped_with = -1;
        if (isValid(leftChild) && isValid(rightChild)){
            //both children exist
            //check if value at n is smaller than value at index of leftChild or rightChild -> swap with smaller one or with none if value at n is bigger than value at leftChild and rightChild
            if (heap.get(leftChild).compareTo(heap.get(rightChild)) > 0){
                //check if necesary to swap leftchild and curr node
                if(heap.get(n).compareTo(heap.get(leftChild)) < 0){
                    swap(n, leftChild);
                    swapped_with = leftChild;
                }
            }
            else{
                if (heap.get(n).compareTo(heap.get(rightChild)) < 0){
                    swap(n, rightChild);
                    swapped_with = rightChild;
                }
            }
        }else if (isValid(leftChild)){
            if(heap.get(n).compareTo(heap.get(leftChild)) < 0){
                swap(n, leftChild);
                swapped_with = leftChild;
            }
            // only the leftChild exists
            // check if value at n is smaller than value at index of rightChild -> if true, then swap, else nothing to do
        }else if (isValid(rightChild)){
            if(heap.get(n).compareTo(heap.get(rightChild)) < 0){
                swap(n, rightChild);
                swapped_with = rightChild;
            }
            // only the rightChild exists
            // check if value at index is smaller than value at index of rightChild -> if true, then swap, else nothing to do
        }else{
            //no children exist -> nothing to do
            return;
        }
        if (isValid(swapped_with)){
            downHeap(swapped_with);
        }
    }

    //---------------------------------------------------------------//
    public void insert(T obj) {
        heap.add(obj);
        this.upHeap(heap.size()-1);
    }

    //---------------------------------------------------------------//
    private void upHeap(int n) {
        if(!isValid(n)) throw new IndexOutOfBoundsException();
        T x = heap.get(n);
        while(n > 0 && x.compareTo(heap.get((n-1)/2)) > 0){
            heap.set(n, heap.get((n-1)/2));
            heap.set((n-1)/2, x);
            n=(n-1)/2;
            x=heap.get(n);
        }
    }

    //---------------------------------------------------------------//
    public static void main(String[] args) {
        Integer[] arr = {2,1,3,5,4,7,10,1};
        MaxHeap<Integer> mh = new MaxHeap<>(arr);
        System.out.println(mh);

        String[] arr_string = {"I", "T", "G", "O", "N", "M", "N", "R", "E", "A", "A", "X"};
        MaxHeap<String> mh2 = new MaxHeap<>(arr_string);
        System.out.println(mh2);

    }
}