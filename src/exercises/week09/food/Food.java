package exercises.week09.food;

//-----------------------------------------------------------------//
public class Food {
  private static final int INVALID = -1;
  private final String category;
  private final String name;

  public Food(String category, String name){
    this.category = category;
    this.name = name;
  }

  private static int letterToNum(char letter){
    switch (letter){
      case 'a':
      case 'A':
        return 1;
      case 'b':
      case 'B':
        return 2;
      case 'c':
      case 'C':
        return 3;
      case 'd':
      case 'D':
        return 4;
      case 'e':
      case 'E':
        return 5;
      case 'f':
      case 'F':
        return 6;
      case 'g':
      case 'G':
        return 7;
      case 'h':
      case 'H':
        return 8;
      case 'i':
      case 'I':
        return 9;
      case 'j':
      case 'J':
        return 10;
      case 'k':
      case 'K':
        return 11;
      case 'l':
      case 'L':
        return 12;
      case 'm':
      case 'M':
        return 13;
      case 'n':
      case 'N':
        return 14;
      case 'o':
      case 'O':
        return 15;
      case 'p':
      case 'P':
        return 16;
      case 'q':
      case 'Q':
        return 17;
      case 'r':
      case 'R':
        return 18;
      case 's':
      case 'S':
        return 19;
      case 't':
      case 'T':
        return 20;
      case 'u':
      case'U':
        return 21;
      case 'v':
      case 'V':
        return 22;
      case 'w':
      case 'W':
        return 23;
      case 'x':
      case 'X':
        return 24;
      case 'y':
      case 'Y':
        return 25;
      case 'z':
      case 'Z':
        return 26;
      default:
        System.err.println(letter + " is invalid!");
        return INVALID;
    }

  }
  
  private static int getSumOfWord(String word){
    int sum = 0;
    for(int i = 0; i < word.length(); i++){
      char letter = word.charAt(i);
      int valueOfLetter = letterToNum(letter);
      if (valueOfLetter == INVALID) throw new IllegalArgumentException();
      else sum += valueOfLetter;
    }
    return sum;
  }

  //---------------------------------------------------------------//
  public int hashCode() {
    int sum = getSumOfWord(category) + getSumOfWord(name);
    int length = category.length() + name.length();
    int hashValue = sum % length > 0 ? sum % length : sum % length + length;
    return hashValue;
  }

  //---------------------------------------------------------------//
  public String toString() {
    return "[category:" + category + ",name:" + name +  ", hashCode:" + hashCode() + "]";
  }

  public static void main(String[] args) {
    Food f1 = new Food("fruits", "banana");
    Food f2 = new Food("fruits", "apple");
    System.out.println(f1.hashCode());
    System.out.println(f2.hashCode());

  }
}
