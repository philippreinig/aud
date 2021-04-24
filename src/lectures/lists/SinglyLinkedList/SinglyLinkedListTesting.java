package Vorlesung.Lists.SinglyLinkedList;

public class SinglyLinkedListTesting {
    public static void main(String[] args) {
        SinglyLinkedList<String> sll = new SinglyLinkedList<>();
        System.out.println(sll.toString());

        try {
            sll.insertAt(sll.size(), "string_1");
            sll.insertAt(sll.size(), "string_2");
            System.out.println(sll.size());
        } catch (SinglyLinkedListOutofBoundsException e) {
            e.printStackTrace();
        }
        System.out.println(sll.toString());
    }
}
