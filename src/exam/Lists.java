package exam;

public class Lists {
    /// Node in a doubly linked list storing an int value.
    /// The node also defines a list recursively.
    static class Node {
        Node(int data, Node prev, Node next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
        int data;
        Node prev = null;
        Node next = null;
    }

    /// generate an example for testing
    public static Node toList(int[] data) {
        int n = data.length;

        if (data == null || n == 0)
            return null;

        Node head = null;

        while (--n >= 0) {
            Node next = head;
            head = new Node(data[n], null, head);
            if (next != null)
                next.prev = head;
        }

        return head;
    }

    /// print the list (for testing)
    public static void print(Node node) {
        while (node != null) {
            System.out.print(node.data + " ");
            node = node.next;
        }
        System.out.println();
    }

    /// Find the maximum data entry in list (the first node/"head"
    /// in a list) and remove the node. You may assume that there
    /// is one distinct maximum (no equal entries).
    ///
    /// Return the head of the remaining list.
    ///
    /// - Your implementation must handle special cases!
    /// - Your implementation should do only one single pass through
    ///   the list! ("Move only forward via node.next!")
    public static Node removeMaximum(Node list) {
        if (list == null || list == list.next) return null;
        else{
            Node max = list;
            Node next = list;
            while(next != null){
                if (next.data > max.data) max = next;
                next = next.next;
            }
            if (max.prev != null){ // highest element not the first one
                max.prev.next = max.next;
                if (max.next != null) max.next.prev = max.prev;
                max.prev = null;
                max.next = null;
                return list;
            }else{ // highest element is the first one in the list
                return list.next;
            }

        }
    }

    public static void main(String[] args) {
        //
        // You can use this as an example!
        //
        int[] ary = {13, 2, 3, 5, 7, 11, 10, 8, 6, 4, 1, 9 , 12};
        Node list = toList(ary);

        print(list);
        list = removeMaximum(list);
        print(list);

    }
}