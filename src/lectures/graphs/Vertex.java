package lectures.graphs;

import java.util.ArrayList;


class Vertex<T>{
    private final ArrayList<Vertex<T>> neighbours;

    private final T data;

    Vertex(T data){
        this(null, data);
    }

    Vertex(ArrayList<Vertex<T>> neighbours,T data){
        if (data == null) throw new IllegalArgumentException("data may not be null!");
        this.neighbours = neighbours != null ? neighbours : new ArrayList<>();
        this.data = data;
    }
    
    public ArrayList<Vertex<T>> getNeighbours(){
         return this.neighbours;
    }

    public boolean addNeighbour(Vertex<T> neighbour){
        if (this.neighbours.contains(neighbour)) return false;
        else{
            this.neighbours.add(neighbour);
            return true;
        }
    }

    public boolean removeNeighbour(Vertex<T> neighbour){
        if (this.neighbours.contains(neighbour)){
            this.neighbours.remove(neighbour);
            return true;
        }else{
            return false;
        }
    }

    public boolean hasNeighbour(Vertex<T> neighbour){
        return this.neighbours.contains(neighbour);
    }

    public T getData(){
        return this.data;
    }


    @Override
    public boolean equals(Object other){
        return other instanceof Vertex<?> &&this.getData().equals(((Vertex<?>)other).getData());
    }

    @Override
    public String toString(){
        String output = "\u001B[36m" + "(" + this.data.toString() + ")" + "\u001B[0m";
        if(this.neighbours.size() == 0){
            output += "[]";
        }else {
            output += "[";
            for(int i = 0; i < this.neighbours.size() - 1; ++i){
                output += this.neighbours.get(i).toStringJustData() + ", ";
            }
            output += this.neighbours.get(this.neighbours.size() - 1).toStringJustData();
            output += "]";
        }
        return output;
    }

    public String toStringJustData(){
        return "(" + this.data.toString() + ")";
    }
}