package adhoc;

public class Ring {
    RNode head;

    public Ring(RNode head) {
        this.head = head;

    }

    public void insertBefore(RNode n, RNode position) {
        RNode prevprev = position.prev;
        position.prev = n;
        n.prev = prevprev;
        n.next = position;
        prevprev.next = n;
    }

    public RNode find(int x) {
        RNode node = this.head.next;
        if (this.head.data == x) return this.head;
        while (node != this.head) {
            if (node.data == x) return node;
            node = node.next;
        }
        return null;
    }

    static class RNode {
        int data = 0;
        RNode prev = null;
        RNode next = null;

        RNode(int data, RNode prev, RNode next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return this.data + ", " + this.prev.data + ", " + this.next.data;
        }
    }

    public static void main(String[] args) {
        RNode head = new RNode(0, null, null);
        RNode r1 = new RNode(1, null, null);
        RNode r2 = new RNode(2, null, null);
        RNode r3 = new RNode(3, null, null);
        RNode r4 = new RNode(4, null, null);

        head.prev = r4;
        head.next = r1;
        r1.prev = head;
        r1.next = r2;
        r2.prev = r1;
        r2.next = r3;
        r3.prev = r2;
        r3.next = r4;
        r4.prev = r3;
        r4.next = head;

        Ring ring = new Ring(head);

        RNode find = ring.find(-1);

        RNode insert = new RNode(-1, null, null);

        ring.insertBefore(insert, r3);
        System.out.println(head.toString());
        System.out.println(r1.toString());
        System.out.println(r2.toString());
        System.out.println(insert.toString());
        System.out.println(r3.toString());
        System.out.println(r4.toString());
        System.out.println(find == null);
        //System.out.println(r3 == find);
    }
}
