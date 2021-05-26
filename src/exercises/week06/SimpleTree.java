package exercises.week06;

//-----------------------------------------------------------------//
public class SimpleTree<T extends Comparable<T>> {
    //---------------------------------------------------------------//
    private Node root;

    //---------------------------------------------------------------//
    public SimpleTree() {
    }

    //---------------------------------------------------------------//
    public static void main(String[] args) {
        SimpleTree<Integer> tree = new SimpleTree<Integer>();
        tree.add(4);
        System.out.println(tree);
        tree.add(2);
        System.out.println(tree);
        tree.add(4);
        System.out.println(tree);
        tree.add(6);
        System.out.println(tree);
        tree.add(10);
        System.out.println(tree);
        tree.add(12);
        System.out.println(tree);
        tree.add(7);
        System.out.println(tree);

        assert (tree.contains(4));
        assert (tree.contains(6));
        assert (tree.contains(7));
        assert (!tree.contains(-1));
        System.out.println("tree inorder: " + tree);
    }

    //---------------------------------------------------------------//
    public void add(T data) {
        if (root == null) root = new Node(data);
        else {
            Node node = this.root;
            while (node != null) {
                if (data.compareTo(node.data) == 0) {
                    System.out.println(data + " already contained in tree");
                    return;
                } else if (data.compareTo(node.data) < 0) {
                    if (node.left == null) break;
                    else node = node.left;
                } else {
                    if (node.right == null) break;
                    else node = node.right;
                }
            }
            if (node.data.compareTo(data) < 0) {
                node.setRight(new Node(data));
            } else {
                node.setLeft(new Node(data));
            }
        }
    }

    //---------------------------------------------------------------//
    public boolean contains(T data) {
        Node node = this.root;
        while (node != null) {
            if (data.compareTo(node.data) == 0) return true;
            else if (data.compareTo(node.data) < 0) node = node.left;
            else node = node.right;
        }
        return false;
    }

    //---------------------------------------------------------------//
    @Override
    public String toString() {
        return toString("");
    }

    private String toString(String str) {
        return root != null ? root.toString() : "";
    }

    //---------------------------------------------------------------//
    public class Node {
        T data;
        Node right;
        Node left;

        Node(T data) {
            this(data, null, null);
        }

        Node(T data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public boolean isLeaf() {
            return this.right == null && this.left == null;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        @Override
        public String toString() {
            return toString(this);

        }

        private String toString(Node node) {
            String str = "";
            str += node.left != null ? node.left.toString() : "";
            str += node.data.toString() + " ";
            str += node.right != null ? node.right.toString() : "";
            return str;
        }

        public String toStringDetailed() {
            return toStringDetailed(this);
        }

        private String toStringDetailed(Node node) {
            String str = "";
            str += node.left != null ? node.left.toString() : "";
            str += node.data.toString() +
                    "[" +
                    (node.left != null ? node.left.data.toString() : "null") +
                    "," +
                    (node.right != null ? node.right.data.toString() : "null") +
                    "]" + " ";
            str += node.right != null ? node.right.toString() : "";
            return str;
        }
    }
}