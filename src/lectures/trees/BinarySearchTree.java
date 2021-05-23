package lectures.trees;

/**
 * Binary Search Tree Implementation in Java
 * Durjoy Acharya
 * da-durjoy@outlook.com
 */
class Node {
    int data;
    Node left;
    Node right;

    public Node(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

public class BinarySearchTree {
    Node root;

    private static boolean Search(Node root, int key) {
        if (root == null) return false;
        if (key == root.data) return true;
        else if (key >= root.data) return Search(root.right, key);
        else return Search(root.left, key);
    }

    public static void main(String[] args) {
        BinarySearchTree BST = new BinarySearchTree();
        BST.Insert_Node(50);
        BST.Insert_Node(40);
        BST.Insert_Node(60);
        BST.Insert_Node(30);
        BST.Insert_Node(35);
        BST.Insert_Node(55);
        BST.InorderPrint();
        System.out.println();
        System.out.println(BST.SearcNode(77));
        BST.FindMinimumNodeValue();
        BST.PreorderPrint();
        System.out.println();
        BST.delete_node_data(30);
        BST.PostorderPrint();
    }

    public void Insert_Node(int data) {
        Node parrent = null;
        Node node = new Node(data);
        if (this.root == null) this.root = node;
        else {
            Node current;
            current = this.root;
            while (current != null) {
                parrent = current;
                if (node.data > current.data) current = current.right;
                else current = current.left;
            }
            if (node.data > parrent.data) parrent.right = node;
            else parrent.left = node;
        }
    }

    public void InorderPrint() {
        System.out.print("Inorder Traversal : ");
        this.Inorder(this.root);
    }

    public void PreorderPrint() {
        System.out.print("Preorder Traversal : ");
        this.PreOrder(this.root);

    }

    private void PreOrder(Node root) {
        if (root == null) return;
        System.out.print("->" + root.data);
        this.PreOrder(root.left);
        this.PreOrder(root.right);
    }

    public void PostorderPrint() {
        System.out.print("Postorder Traversal : ");
        this.PostOrder(this.root);

    }

    private void PostOrder(Node root) {
        if (root == null) return;
        this.PostOrder(root.left);
        this.PostOrder(root.right);
        System.out.print("->" + root.data);
    }

    private void Inorder(Node root) {
        if (root == null) return;
        else {
            this.Inorder(root.left);
            System.out.print("->" + root.data);
            this.Inorder(root.right);
        }
    }

    public boolean SearcNode(int key) {
        return Search(this.root, key);
    }

    public int find(Node root) {
        while (root.left != null) root = root.left;
        return root.data;
    }

    public void FindMinimumNodeValue() {
        System.out.println("Minimum Node Value: " + this.find(this.root));
    }

    public void delete_node_data(int value) {
        this.deleteRecord(this.root, value);
    }

    public Node deleteRecord(Node root, int value) {
        if (root == null) return root;
        if (value < root.data) root.left = this.deleteRecord(root.left, value);
        else if (value > root.data) root.right = this.deleteRecord(root.right, value);
        else {
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;
            root.data = this.find(root.right);
            root.right = this.deleteRecord(root.right, root.data);
        }
        return root;
    }
}