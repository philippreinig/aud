package examprep;

public class RingTest {

    public static void main(final String[] args) {
//        final JFrame frame = new JFrame();
//        final DotViewer dv = new DotViewer(frame);
//        dv.display("digraph G{\n" + "\"4\"->\"1\";\n}");
//        frame.setVisible(true);
//        frame.pack();
//        dv.display("digraph G{\n" + "\"4\"->\"2\";\n}");


        final IntRing intRing = new IntRing();

//        intRing.show();
//
        intRing.insertFront(3);
//        intRing.show();
        System.out.println(intRing);

        intRing.insertFront(5);
//        intRing.show();
        System.out.println(intRing);


        intRing.insertFront(7);
//        intRing.show();
        System.out.println(intRing);


        intRing.insertFront(9);
//        intRing.show();
        System.out.println(intRing);


        intRing.insertFront(11);
//        intRing.show();
        System.out.println(intRing);


        intRing.insertFront(13);
//        intRing.show();
        System.out.println(intRing);


    }
}
