package exercises.week08;

/*
class MaxHeap
Schreiben Sie eine Klasse MaxHeap<T extends Comparable<T>> mit einer geeigneten
Datenstruktur (aud.Vector oder java.util.ArrayList), Konstruktor und verschiedenen Methoden:
  public MaxHeap(T[] a):
Konstruktor, baut einen MaxHeap aus dem Feld a mittels downHeap auf.
  public ArrayList<T> getHeap() oder
  public Vector<T> getHeap(): liefert den internen Heap zurück,
  public int size(): gibt die Anzahl der Einträge im Heap an,
  public boolean is_empty(): gibt an, ob der Heap leer ist,
  public void downHeap(int k): stellt die Heap-Eigenschaft durch ein "Versickern" des Elementes im Knoten k her,
  private void upHeap(int k): stellt Heap-Eigenschaft durch ein "Aufsteigen " des Elements im Knoten k wieder her.
  public void insert(T obj): fügt ein Element in den Heap ein und stellt die Heap-Eigenschaft mit upHeap wieder her.
Testen Sie diese Methode, indem Sie aus den Buchstaben
  X, T, O, G, S, M, N, A, E, R, A und I
einen MaxHeap erzeugen.
*/

import java.util.ArrayList;

public class MaxHeapSolution<T extends Comparable<T>> {

    private final ArrayList<T> heap = new ArrayList<T>();

    public MaxHeapSolution() {
        heap.clear();
    }

    public MaxHeapSolution(T[] a) {
        heap.clear();
        for (int i = 0; i < a.length; i++) {
            heap.add(a[i]);
        }

        // create Heap with downHeap
        int k = heap.size() / 2;
        while (k >= 0) {
            this.downHeap(k);
            k--;
        }
    }

    public ArrayList<T> getHeap() {
        // do not change because of backend-control
        return heap;
    }

    public int getSize() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public void downHeap(int k) {
        int n = this.getSize();
        T x = heap.get(k);

        while (k + 1 <= n / 2) {
            int child = 2 * (k + 1) - 1; // get left Child

            if (child < (n - 1)
                    && heap.get(child).compareTo(heap.get(child + 1)) < 0) {
                child = child + 1; // choose greater child
            }

            if (x.compareTo(heap.get(child)) >= 0) {
                break;
            }

            heap.remove(k);
            heap.add(k, heap.get(child - 1));
            k = child;
        }
        heap.remove(k);
        heap.add(k, x);
    }

    public void insert(T obj) {
        heap.add(obj);
        this.upHeap(heap.size() - 1); // last Element = size - 1, because index
        // starts with 0
    }

    private void upHeap(int k) {
        T x = heap.get(k);

        while (k > 0 && x.compareTo(heap.get((k - 1) / 2)) > 0) { // (k-1)/2 =
            // index of
            // parent

            heap.remove(k);
            heap.add(k, heap.get((k - 1) / 2));

            k = (k - 1) / 2;

        }

        heap.remove(k);
        heap.add(k, x);

    }

    public String toString() {
        // do not change because of backend-control
        return heap.toString();
    }

    public static void main(String[] args) {
        String[] list = {"X", "T", "O", "G", "S", "M", "N", "A", "E", "R",
                "A", "I"};

        MaxHeapSolution<String> mh = new MaxHeapSolution<String>(list);
        MaxHeapSolution<String> mh2 = new MaxHeapSolution<String>();

        System.out.println("maxHeap1: " + mh + "\nmaxHeap1.getSize() = " + mh.getSize());
        mh.insert("Z");
        System.out.println("maxHeap1.insert(\"Z\"): " + mh + "\nmaxHeap1.getSize() = " + mh.getSize());
        mh2.insert("Z");
        System.out.println("maxHeap2: " + mh2 + "\nmaxHeap2.getSize() = " + mh2.getSize() + "\n");

        Integer[] intList = {8, 3, 7, 1, 5, 6, 18, 9};
        MaxHeapSolution<Integer> intHeap = new MaxHeapSolution<>(intList);
        System.out.println("maxHeap3: " + intHeap);
    }
}

/*
  	Testdaten:
	maxHeap1: [X, T, O, G, S, M, N, A, E, R, A, I]
	maxHeap1.getSize() = 12
	maxHeap1.insert("Z"): [Z, T, X, G, S, O, N, A, E, R, A, I, M]
	maxHeap1.getSize() = 13
	maxHeap2: [Z]
	maxHeap2.getSize() = 1
	maxHeap3: [18, 9, 8, 3, 5, 6, 7, 1]
 */