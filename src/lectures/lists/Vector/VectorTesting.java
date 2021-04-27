package lectures.lists.Vector;

public class VectorTesting {

    public static void main(String[] args) {
        Vector<String> stringVector = new Vector<>();
        try {
            stringVector.insert(stringVector.getSize(), "string_1");
            System.out.println(stringVector);
            System.out.println(stringVector.getCapacity());
            System.out.println(stringVector.getSize());

            stringVector.insert(stringVector.getSize(), "string_2");
            System.out.println(stringVector);
            System.out.println(stringVector.getCapacity());
            System.out.println(stringVector.getSize());

            stringVector.erase(0);
            System.out.println(stringVector);
            System.out.println(stringVector.getCapacity());
            System.out.println(stringVector.getSize());

            stringVector.pop_back();
            System.out.println(stringVector);
            System.out.println(stringVector.getCapacity());
            System.out.println(stringVector.getSize());

            for (int i = 0; i < 50; i++) {
                stringVector.insert(stringVector.getSize(), "string_" + (stringVector.getSize() + 1));
            }
            System.out.println(stringVector);
            System.out.println(stringVector.getCapacity());
            System.out.println(stringVector.getSize());

            System.out.println(stringVector.at(10).toString());

            for (int i = stringVector.getSize() - 1; i > 10; i--) {
                stringVector.erase(i);
            }

            System.out.println(stringVector);
            System.out.println(stringVector.getCapacity());
            System.out.println(stringVector.getSize());
        } catch (VectorOutOfBoundsException voobe) {
            voobe.printStackTrace();
        }

    }
}
