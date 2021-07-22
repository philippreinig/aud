package aud;

import java.util.Iterator;

/** Implementation of an <em>unordered map</em> based on a hash table.
 *  <p>
 *   {@code HashMap} implements a hash table with separate chaining for
 *   collision handling. (For better performance, it implements its own singly
 *   linked list made of {@code Bucket} instances.) In  case a user
 *   defined threshold on the load factor (see {@link #getLoadFactor},
 *   {@link #setMaximumLoadFactor} is exceeded on {@link #insert}, a
 *   {@link #rehash}ing is triggered. The size of the hash table
 *   {@link #getHashtableSize} is doubled on rehashing and automatically
 *   maintained to be prime (see {@link #getNextPrime}). It may be a good ideap
 *   to {@link #reserve} enough buckets to avoid rehashing if there is a
 *   reasonable guess on the expected number of entries.
 *   <p>
 *
 *   {@code HashMap} provides an iterator that enumerates all key-value-pairs
 *   ({@code Entry}) in an <em>arbitrary order</em>. In particular, the
 *   order may change after an {@link #insert} operation. Any manipulation of
 *   the map (insert or {@link #remove}) invalidates all iterators in use!
 *   <p>
 *
 *   The key-value mapping uses the special convention that {@code null}
 *   values are <b>never</b> mapped. Inserting a key wit a {@code null} value
 *   is hence equivalent to {@link #remove}ing the entry! (Use another
 *   value instance like "nil" as a placeholder in case you need such
 *   semantics.)
 */
public class HashMap<Key,Value> {

  // TODO define ADT AbstractUnorderedMap (extends AbstractMap)
  // TODO define unit test for HashMap

  // primes stolen from an older version of gcc's STL
  static final int PRIMES[]={
    31,        53,         97,         193,       389,
    769,       1543,       3079,       6151,      12289,
    24593,     49157,      98317,      196613,    393241,
    786433,    1572869,    3145739,    6291469,   12582917,
    25165843,  50331653,   100663319,  201326611, 402653189,
    805306457, 1610612741
  };
  /** get the next larger prime in {@CODE PRIMES} table */
  static int getNextPrime(int n) {
    for (int p : PRIMES)
      if (p>=n)
        return p;
    throw new RuntimeException("table size exceeds limits"); // unrealistic!
  }

  /** entry {@code (key,value)} in {@link HashMap} with link to {@code next} */
  class Bucket {
    Bucket(Key key,Value value) {
      this.key=key;
      this.value=value;
    }
    Key    key;
    Value  value;
    Bucket next=null;
  };

  /** entry {@code (key,value)} in {@link HashMap} (iterator) */
  public class Entry {
    Entry(Key key,Value value) {
      this.key=key;
      this.value=value;
    }
    public Key    key;
    public Value  value;
  }

  /** provide {@link HashMap} iterator over key-value pairs ({@link Entry}) */
  public class Entries implements Iterable<Entry> {
    HashMap<Key,Value> hashmap_;
    Entries(HashMap<Key,Value> hashmap) { hashmap_=hashmap; }
    @Override public Iterator<Entry> iterator() {
      return new EntryIterator(hashmap_);
    }
  }

  /** iterator over key-value pairs ({@link Entry}) in {@link HashMap}*/
  class EntryIterator implements Iterator<Entry> {
    HashMap<Key,Value> hashmap_;
    int                idx_;
    Bucket             bucket_=null;

    @SuppressWarnings("unchecked")
    EntryIterator(HashMap<Key,Value> hashmap) {
      hashmap_=hashmap;
      // find first entry
      for (idx_=0;idx_<hashmap_.table_.length;++idx_) {
        if ((bucket_=(Bucket) hashmap_.table_[idx_])!=null)
          break;
      }
    }

    /** advance to next entry */
    @SuppressWarnings("unchecked") void advance() {
      assert(bucket_!=null);
      bucket_=bucket_.next; // we may reach the end of the list

      while (bucket_==null) {
        if (++idx_>=hashmap_.table_.length)
          break;
        bucket_=(Bucket) hashmap_.table_[idx_];
      }
    }

    @Override public boolean hasNext() {
      return bucket_!=null;
    }

    @Override public Entry next() {
      assert(bucket_!=null);
      Entry entry=new Entry(bucket_.key,bucket_.value);
      advance();
      return entry;
    }

    @Override public void remove() {
      throw new UnsupportedOperationException();
    }
  }


  Object  table_[] = null;  // stores Bucket instances (Java generics suck!)
  double  maxLoad_ = 0.75;
  int     size_    = 0;

  /** create empty map
   * @param size estimated size (sets initial number of buckets)
   */
  public HashMap(int size) {
    int n=(int) ((double) size/maxLoad_);
    if (n<size) n=size;
    n=getNextPrime(n);
    table_=new Object[n];
  }

  /** create empty map */
  public HashMap() {
    this(PRIMES[0]);
  }

  /** get current size of hash table (will be prime) */
  public int getHashtableSize() {
    return table_.length;
  }

  /** get current number of entries */
  public int size() { return size_; }

  /** is map empty? */
  public boolean is_empty() { return size_==0; }

  /** get current load factor */
  public double getLoadFactor() {
    return (double) size_/(double) table_.length;
  }

  /** set maximum load factor (may trigger {@link #rehash} */
  void setMaximumLoadFactor(double alpha) {
    if (alpha<maxLoad_ && getLoadFactor()>alpha)
      rehash(alpha);

    maxLoad_=alpha;
  }

  /** Reserve buckets for expected {@code size}.
   * May call {@link #rehash}, will only increase table size.
   * @param size expected number of entries
   */
  public void reserve(int size) {
    if (size>size_)
      rehash((int) ((double) size)/maxLoad_);
  }

  /** eventually rehash to satisfy {@code alpha<}{@link #getMaximumLoadFactor} */
  void rehash(double alpha) {
    rehash((int) (alpha* (double) size_));
  }

  /** force rehash (may increase or decrease table size)
   *  @param newSize new hash table size (will be "rounded up" to a prime number
   */
  @SuppressWarnings("unchecked")
  void rehash(int newSize) {
    int n=getNextPrime(newSize);
    System.err.println("INFO: rehash "+table_.length+" -> "+n+
        " ("+size_+" entries, load factor "+getLoadFactor()+")");
    HashMap<Key,Value> ht=new HashMap<Key,Value>(n);
    for (Object position : table_) {
      for (Bucket b=(Bucket) position;b!=null;b=b.next) {
        ht._set(b.key,b.value);
      }
    }
    // steal data from ht
    table_=ht.table_;
    size_=ht.size_;
  }

  /** get iterable instance<br>
   *  {@code for (HashMap<K,T>.Entry entry : map.entries()) { ... }}
   */
  public Entries entries() { return new Entries(this); }

  /** {@link #insert} w/o checking load factor
   * @param key key to be inserted
   * @param value value associated with {@code key}. Can be a "new" value to
   * change an existing entry. In the latter case <em>remove</em> entry if
   * {@code value==null}
   * @return old value (equals {@code null} if there was no mapping for key
   */
  @SuppressWarnings("unchecked")
  Value _set(Key key,Value value) {
    int hash = key.hashCode();
    hash = hash < 0 ? -hash : hash;
    int i=hash % table_.length;
    if (table_[i]==null) {
      if (value!=null)                 // (key,null) is never stored
        table_[i]=new Bucket(key,value);
    }
    else {                             // collision
      Bucket tail=null;
      for (Bucket b=(Bucket) table_[i];b!=null;b=b.next) {
        if (b.key.equals(key)) {       // Is key already in list?
          Value old=b.value;
          b.value=value;               // YES: change

          if (value==null) {           // special case: remove
            if (tail==null)
              table_[i]=b.next;
            else
              tail.next=b.next;
            --size_;
          }

          return old;
        }
        tail=b;
      }
      assert(tail!=null);
      tail.next=new Bucket(key,value); // NO: insert at end of list
    }
    ++size_;

    return null;
  }

  /** insert key-value pair
   * @param key if map already {@link #contains} key then its value is changed
   * @param value stored (or new) value. Providing {@code value=null} is
   * equivalent to {@link #remove}ing an enty ({@code null} values are not
   * stored!}
   * @return old value, i.e., {@code null} if the key was not contained in map
   * before
   */
  public Value insert(Key key,Value value) {
    Value v=_set(key,value);
    if (v==null && value!=null) {      // increased size()?
      if (getLoadFactor()>maxLoad_)
        rehash(table_.length*2);       // rehash if maximum load factor exceeded
    }
    return v;
  }

  /** remove entry (equivalent to {@code insert(key,null)})
   *  @param key
   *  @return value of entry ({@code null} if the key was not conatined in map)
   */
  public Value remove(Key key) {
    return _set(key,null);
  }

  /** find value for {@code key}
   *  @return value or {@code null} if the key is not contained in map
   */
  @SuppressWarnings("unchecked")
  public Value find(Key key) {
    int hash=key.hashCode();
    hash = hash < 0 ? -hash : hash;
    int i=hash % table_.length;
    if (table_[i]!=null) {
      for (Bucket b=(Bucket) table_[i];b!=null;b=b.next) {
        if (b.key.equals(key))
          return b.value;
      }
    }
    return null;
  }

  /** Is {@code key} contained in map? */
  public boolean contains(Key key) {
    return find(key)!=null;
  }

  @Override public String toString() {
    String s="{ ";
    for (Entry entry : entries())
      s+=(entry.key.toString()+"=>"+entry.value+",");
    s=s.substring(0,s.length()-1);
    return s+=" };";
  }


  public static void main(String args[]) {
    HashMap<String,Integer> map=new HashMap<String,Integer>();

    map.insert("a",1);
    map.insert("b",2);
    map.insert("c",3);
    map.insert("d",4);
    map.insert("e",5);
    map.insert("f",6);
    map.insert("g",7);
    map.insert("h",8);

    for (HashMap<String,Integer>.Entry entry : map.entries()) {
      System.out.println(entry.key+" => "+entry.value);
    }
    System.out.println(map);
    map.remove("a");
    System.out.println(map);
    System.out.println(map.find("c"));
    System.out.println(map.find("x"));
  }
}
