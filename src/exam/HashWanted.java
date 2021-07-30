package exam;

public class HashWanted {
    String firstname;
    String lastname;
    int age;
    String[] friends;

    HashWanted(String firstname, String lastname, int age, String[] friends) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.friends = friends;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;

        if (!(other instanceof HashWanted))
            return false;

        HashWanted o = (HashWanted) other;

        if (o.firstname.equals(firstname) ||
                o.lastname.equals(lastname) ||
                o.age != age)
            return false;

        if (o.friends.length != friends.length)
            return false;

        // friends are ranked, order matters
        for (int i=0;i<friends.length;++i) {
            if (!o.friends[i].equals(friends[i]))
                return false;
        }

        return true;
    }

    @Override
    public int hashCode(){
        int hashCode = 0;
        hashCode += firstname.hashCode();
        hashCode += lastname.hashCode();
        hashCode += age;
        for(String str : friends){
            hashCode += str.hashCode();
        }
        return hashCode;
    }
}