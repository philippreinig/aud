package lectures.lists.Vector;

public class Vector<T> {
    private final int MIN_SIZE = 16;
    private T[] data = (T[]) new Object[0];
    private int size = 0;

    public Vector() {
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        if (data != null) {
            return data.length;
        } else return 0;
    }

    public boolean isValidIndex(int i) {
        return i >= 0 && i <= size - 1;
    }

    public T at(int i) throws VectorOutOfBoundsException {
        if (isValidIndex(i)) return data[i];
        else throw new VectorOutOfBoundsException(i);
    }

    private void reserve(int n) {
        n = Math.max(n, MIN_SIZE);
        System.err.println("RESIZING FROM " + getCapacity() + " TO " + n);
        if (getCapacity() < n) {
            T[] temp = (T[]) new Object[n];
            for (int i = 0; i < size; i++) {
                temp[i] = data[i];
            }
            for (int i = size; i < temp.length; i++) {
                temp[i] = null;
            }
            data = temp;
        }
    }

    private void resize(int n) {
        if (n > getCapacity()) {
            int c = (int) (getCapacity() * 1.5);
            reserve(Math.max(n, c));
        } else if (n < size) {
            n = Math.max(0, n);
            for (int i = n; i < size; i++) {
                data[i] = null;
            }
        }
        size = n;
    }

    public void push_back(T objectToInsert) {
        int n = size;
        resize(n + 1);
        data[n] = objectToInsert;
    }

    public void insert(int i, T objectToInsert) throws VectorOutOfBoundsException {
        if (i < 0 || i > size) throw new VectorOutOfBoundsException(i);
        else {
            int n = size;
            resize(n + 1);

            for (int j = i + 1; j < size; j++) {
                data[j] = data[j - 1];
            }
            data[i] = objectToInsert;
        }

    }

    public void pop_back() throws VectorOutOfBoundsException {
        if (!isValidIndex(0)) throw new VectorOutOfBoundsException(0);
        data[size - 1] = null;
        size--;
    }

    public void erase(int i) throws VectorOutOfBoundsException {
        if (!isValidIndex(i)) throw new VectorOutOfBoundsException(i);
        else {
            for (int j = i; j < size; j++) {
                data[j] = data[j + 1];
            }
            size--;
            data[size] = null;
        }
    }

    @Override
    public String toString() {
        if (size > 0) {
            String temp = "[";
            for (int i = 0; i < size; i++) {
                temp += data[i].toString() + ", ";
            }
            temp = temp.substring(0, temp.length() - 2) + "]";
            return temp;
        } else {
            return "[]";
        }

    }

    public String toStringTechnical() {
        if (data.length > 0) {
            String temp = "[";
            for (int i = 0; i < data.length - 1; i++) {
                if (data[i] != null) temp += data[i].toString() + ", ";
                else temp += "null , ";
            }

            if (data[getCapacity() - 1] != null) temp += data[getCapacity() - 1].toString() + "]";
            else temp += "null]";
            return temp;
        } else return "[]";
    }
}

