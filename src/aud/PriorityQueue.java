package aud;

import java.util.Comparator;

import aud.adt.AbstractPriorityQueue;

/**
 * Priority queue based on binary min-heap.
 * <p>
 *
 * This data structure additionally allows for limited set semantics
 * ({@link #contains}) and <em>updating</em> priorities of contained entries:
 * {@link #lower}, {@link #raise}.
 * <p>
 *
 * This functionality is required, e.g., by Dijkstra's algorithm. It is
 * implemented as an additional {@link HashMap} that maps entries {@code T}
 * (keys) to {@link #position_}s (values), i.e., for any entry we know its
 * position in the heap and can therefore apply {@link #upheap} and
 * {@link #downheap}.
 * <p>
 *
 * <b>Note</b> that using the above feature requires that entries in the PQ are
 * <em>unique</em>! Therefore {@link #push} throws an exception if the same
 * entry (tested by {@code equals}) is added twice!
 * <p>
 *
 * <b>Hint:</b> For an <em>efficient</em> implementation of Dijkstra's
 * algorithm, it is usually better to store the map {@link #position_}
 * as an {@code int} attribute of nodes in the graph (efficient and
 * direct access).  This, however, requires that the implementation of
 * the graph data structure and the priority queue are closely
 * coupled.
 *
 * <b>Note:</b> You can alternatively use Java's
 * <tt>java.util.PriorityQueue</tt> to implement Dijkstra's
 * algorithm. In this case you need to replace {@link #lower} by
 * calling <tt>remove</tt> followed by <tt>add</tt>, which changes
 * priority. <br> Note that, e.g., the C++ standard library provides
 * only a data structure <tt>std::priority_queue</tt> that does
 * <em>neither</em> provide direct update of priority <em>nor</em>
 * removal of arbitrary entries! (You need to implement you own data
 * structure similar to this one!)
 */
public class PriorityQueue<T> extends AbstractPriorityQueue<T> {

  // TODO define unit test for PriorityQueue
  // TODO Is it better to "outsource" the mapping? (public upheap, downheap)?

  /** reserve storage for at least this number of entries */
  private static final int MINENTRIES=64;

  /** stores the heap (effectively ease of memory management;
   *  hack: we are accessing internal data directly) */
  Vector<T>          heap_;

  /** map entries {@code T} to heap positions {@code Integer} */
  HashMap<T,Integer> position_;

  /** Create empty priority queue
   * @param n expected number of entries (preallocates/reserves storage)
   * @param cmp compare entries
   */
  public PriorityQueue(int n,Comparator<T> cmp) {
    super(cmp);
    heap_=new Vector<T>();
    heap_.reserve(n);
    position_=new HashMap<T,Integer>(n);
  }
  public PriorityQueue(Comparator<T> cmp) { this(MINENTRIES,cmp); }

  public PriorityQueue(int n) { this(n,null); }
  public PriorityQueue() { this(MINENTRIES,null); }

  @Override public T front() {
    return heap_.front(); // may throw
  }

  @Override public boolean is_empty() {
    return heap_.empty();
  }

  /** Is {@code x} contained in PQ? */
  public boolean contains(T x) { return position_.contains(x); }

  /** get number of entries */
  public int size() { return heap_.size(); }

  @Override public T pop() {
    T root=heap_.front(); // may throw
    position_.remove(root);

    if (heap_.size()>1) {
      position_.insert((heap_.data_[0]=heap_.back()),0);
      heap_.pop_back();
      downheap(0);
    }
    else
      heap_.pop_back();

    return root;
  }

  /** get index of parent (0-based) */
  int getParent(int k) { return ((k+1)/2)-1; }
  /** get index of left child (0-based) */
  int getLeftChild(int k) { return ((k+1)*2)-1; }
  /** compare entries at positions i,j */
  boolean less(int i,int j) {
    return super.less(heap_.at(i),heap_.at(j));
  }
  /** test for less or equal (used by {@link #checkConsistency}) */
  boolean leq(int i,int j) {
    if (less(i,j))  return true;
    else            return !less(j,i); // symmetric test fails for equality
  }
  /** compare {@code x} with entry at at positions k */
  boolean less(T x,int k) {
    return super.less(x,heap_.at(k));
  }
  /** move entry from position {@code from} to {@code to} */
  void move(int from,int to) {
    T x=heap_.data_[from];
    heap_.data_[to]=x;
    position_.insert(x,to);
  }
  /** set entry at position {@code k} to {@code x} */
  void set(int k,T x) {
    heap_.data_[k]=x;
    position_.insert(x,k);
  }

  /** establish heap property (bottom up) */
  void downheap(int k) {
    T x=heap_.data_[k];
    int j=getLeftChild(k);

    while (j<heap_.size()) {
      if (j<heap_.size()-1 && less(j+1,j)) j=j+1;
      if (less(x,j)) break;
      move(j,k);
      k=j;
      j=getLeftChild(k);
    }
    set(k,x);
  }

  /** Add entry to PQ.
   * @param x new entry that <b>must not</b> be already contained in PQ
   * @throws RuntimeException if {@code x} is already contained in PQ
   */
  @Override public void push(T x) {
    if (contains(x))
      throw new RuntimeException(x.toString()+" already contained in PQ");

    int k=heap_.size();
    heap_.push_back(x);
    position_.insert(x,k);
    upheap(k);
  }

  /** establish heap property (top down) */
  void upheap(int k) {
    if (k>0) {
      T x=heap_.data_[k];
      int p;
      while (k>0 && less(x,p=getParent(k))) {
        move(p,k);
        k=p;
      }
      set(k,x);
    }
  }

  /** update heap <em>after raising</em> priority of {@code x} */
  public void raise(T x) {
    assert(position_.contains(x));
    int k=position_.find(x);

    assert(k==0 || less(getParent(k),k));   // priority was raised!

    downheap(k);
  }

  /** update heap <em>after lowering</em> priority of {@code x} */
  public void lower(T x) {
    assert(position_.contains(x));
    int k=position_.find(x);

    // Assert that priority was lowered!
    assert(getLeftChild(k)  >=heap_.size() || less(k,getLeftChild(k)));
    assert(getLeftChild(k)+1>=heap_.size() || less(k,getLeftChild(k)+1));

    upheap(k);
  }

  @Override public String toString() {
    return position_.toString();
  }

  /** check consistency */
  public void checkConsistency() {
    System.err.println("size="+heap_.size());
    // check mapping between heap_ and position_
    if (heap_.size()!=position_.size())
      throw new RuntimeException("size mismatch");
    for (int i=0;i<heap_.size();++i)
      if (position_.find(heap_.at(i))==null || position_.find(heap_.at(i))!=i)
        throw new RuntimeException("invalid mapping for key='"+heap_.at(i)+"'");

    // check heap property
    for (int i=0;i<heap_.size();++i) {
      if (i>0) {
        int p=getParent(i);
        if (!leq(p,i))
          throw new RuntimeException("heap property violated (parent");
      }
      int left=getLeftChild(i);
      if (left<heap_.size()) {
        if (!leq(i,left))
          throw new RuntimeException("heap property violated (left)");

        int right=left+1;
        if (right<heap_.size()) {
          if (!leq(i,right))
            throw new RuntimeException("heap property violated (right)");
        }
      }
    }
  }

  /** example and test */
  public static void main(String[] args) {

    {
      // use as a "standard" priority queue

      PriorityQueue<Integer> pq=new PriorityQueue<Integer>();

      pq.push(2); System.out.println(pq);
      pq.push(4); System.out.println(pq);
      pq.push(3); System.out.println(pq);
      pq.push(1); System.out.println(pq);

      System.out.println(pq.pop());
      System.out.println(pq);

      System.out.println(pq.pop());
      System.out.println(pq);
    }
    {
      // now the following provides a mapping from entries to priorities
      final HashMap<String,Integer> priority=new HashMap<String,Integer>();
      priority.insert("a",1);
      priority.insert("b",2);
      priority.insert("c",3);
      priority.insert("d",4);

      PriorityQueue<String> pq=
        new PriorityQueue<String>
        (new Comparator<String>() {
          @Override public int compare(String a,String b) {
            return priority.find(a)-priority.find(b);
          }
        });

      // first we do the same as above

      pq.push("b"); System.out.println(pq);
      pq.push("d"); System.out.println(pq);
      pq.push("c"); System.out.println(pq);
      pq.push("a"); System.out.println(pq);

      System.out.println(pq.pop());
      System.out.println(pq);

      System.out.println(pq.pop());
      System.out.println(pq);

      // restore what we had before
      pq.push("b"); System.out.println(pq);
      pq.push("a"); System.out.println(pq);

      // now we gonna change the priority mapping and update pq

      System.out.println("front="+pq.front());
      priority.insert("a",5); // RAISE priority ...
      System.out.println("new prioities: "+priority);
      pq.raise("a");         // ... and update pq
      System.out.println("front="+pq.front());
      priority.insert("c",0); // LOWER priority ...
      System.out.println("new prioities: "+priority);
      pq.lower("c");         // ... and update pq
      System.out.println("front="+pq.front());
    }
  }

}
