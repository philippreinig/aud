package exam;

import aud.BinaryTree;
import aud.util.DotViewer;

public class HashDataStructure {
    /// Node in a binary tree.
    /// Each node represents an entire (sub-tree)

    private static int stringToHash(String s){
        int hashValue = 0;
        for (int i = 0; i < s.length(); i++) {
            hashValue += s.charAt(i);
        }
        return hashValue;

    }
    static public class TreeNode {
        String data;
        TreeNode left;
        TreeNode right;

        TreeNode(String data, TreeNode left, TreeNode right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }



        // Additional method needed: A method which transforms the returned int value to a valid index for the size of the hash table
        // by calculating hashCode() % <size_of_hash_table>
        @Override
        public int hashCode(){
            if (this.left == null && this.right == null) return HashDataStructure.stringToHash(data);
            else {
                int left = this.left != null ? this.left.hashCode() : 0;
                int right = this.right != null ? 2 * this.right.hashCode() : 0;
                return left + right + HashDataStructure.stringToHash(data);
            }
        }

    }

    public static void main(String[] args) {
        TreeNode tn = new TreeNode("1", new TreeNode("2", new TreeNode("4", null, null), null),
                      new TreeNode("3", null, new TreeNode("5", null, null)));
        System.out.println(tn.hashCode());

        TreeNode tn2 = new TreeNode("1", new TreeNode("2", null, new TreeNode("4", null, null)),
                new TreeNode("3", new TreeNode("5", null, null), null));

        System.out.println(tn2.hashCode());
    }


    //
    // Assume you want to insert trees into a hash table. This means
    // you need to provide a hash function.
    //
    // 1. Two trees a and b are equal, if and only if their data values
    //    AND their structure (position of children) are equal:
    //
    // 2. Implement hashCode() for TreeNode!
    //    DON'T use any auxiliary data structure such as printing to a
    //    String!
    //
    // 3. Which additional method must be implemented?
    //    Answer in a comment, no implementation required.
    //
    // Hint: The String class already implements hashCode().
    //       You can "combine" hash values h1, h2 by addition h1 + h2
    //       (or exclusive or/XOR h1 ^ h2).

}
