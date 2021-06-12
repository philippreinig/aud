package exercises.week09.food;

import java.util.Arrays;

/*
Alternative ways to identify food objects: food1==food2, food1.getCategory().equals(food2.getCategory)) && food1.getName().equals(food2.getName())

 */

/**Container that can store food elements.*/
public class IceBox {
  final Food[] storage;
  final int capacity;
  int size;

  private static final boolean DEBUG = true;

  public IceBox(int capacity){
    if (capacity < 0 ) throw new IllegalArgumentException();
    this.storage = new Food[capacity];
    this.capacity = capacity;
    this.size = 0;
  }
  //---------------------------------------------------------------//
  // TODO: attributes and constructor

  //---------------------------------------------------------------//
  public int add(Food obj) {
    if (size >= capacity) throw new IllegalStateException();
    if (DEBUG) System.out.println("Before insertion: " + Arrays.toString(storage) + " + " + obj);
    int pos = obj.hashCode() % capacity;
    int counter = 0;
    while (storage[pos] != null) {
      counter++;
      if (pos+1 > storage.length-1) pos = 0;
      else pos++;
      if(counter == capacity) throw new IllegalStateException("Storage of IceBox is full, cant add more food!");
    }
    storage[pos] = obj;
    if (DEBUG) System.out.println("After insertion: " + Arrays.toString(storage) + "collisions: " + counter);
    size++;
    return counter;
  }

  //---------------------------------------------------------------//
  public boolean contains(Food obj) {
    for(int i = 0; i < capacity; i++){
      if (storage[i] != null && storage[i].hashCode() == obj.hashCode()){
        return true;
      }
    }
    return false;
  }

  //---------------------------------------------------------------//
  public static void main(String[] args) {
    IceBox iceBox = new IceBox(10);

    Food f1 = new Food("fruits", "banana");
    Food f2 = new Food("fruits", "apple");
    Food f3 = new Food("fruits", "orange");
    Food f4 = new Food("fruits", "pear");
    Food f5 = new Food("vegetables", "cucumber");
    Food f6 = new Food("vegetables", "tomato");
    Food f7 = new Food("vegetables", "eggplant");
    Food f8 = new Food("meat", "Schnitzel");
    Food f9 = new Food("meat", "NochMehrSchnitzel");
    Food f10 = new Food("meat", "IchWillImmerNochMehrSchnitzel");

    Food[] aLotOfFod = {f1, f2, f3,f4,f5,f6,f7,f8,f9,f10};
    for (Food food : aLotOfFod){
      iceBox.add(food);
    }
  }
}
