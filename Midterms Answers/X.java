import java.util.ArrayList;
import java.util.List;

class X {
    private final int v;

    X(int v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return String.format("X:%s", this.v);
    }

}

class Y {
    X x;
    
    Y(X x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return String.format("Y->%s", this.x);
    }
}


class A {
    private int v;
    private A previous;
    A (int v, A previous) {
        this.v = v;
        this.previous = previous;
    }

    A(int v) {
        this.v = v;
        this.previous = null;
    }
    A next(int n) {
        return new A(n, this);
    }
    
    @Override
    public String toString() {
        return (this.previous == null ? "" : this.previous) + "[A:" + this.v + "]";
    }
}

class Letter {
    String s;

    Letter(String s){
        this.s = s;
    }

    Letter add(Letter l){
        return new Letter(this.s + l.s);
    }
    @Override
    public String toString() { 
        return s;
    }
}

class B extends Letter {
    B() {
        super("B");
    }
}

class C extends Letter {
    C() {
        super("C");
    }
}



class D {
    static <T> List<T> add(List<T> list, T v) {
        list.add(v);
        return list;
    }

    //join two types of list together as long as it is a sub type
    static <T> List<T> join(List<T> list1, List<? extends T> list2) {
        List<T> out = new ArrayList<>(list1);
        if (list1 != list2) {
            out.addAll(list2);
        }
        return out;

    }
}

class E {
    public String toString() {
        return "E";
    }
}

class F extends E {
    
    @Override
    public String toString(){
        return "F";
    }
}

//Q2

interface TypeCaster<T, S> {
    abstract S cast(T t);
}

class toString<S> implements TypeCaster<S, String> {

    @Override
    public String cast(S s){
        return s.toString();
    }
}

class Round implements TypeCaster<Double, Integer> {
    
    @Override
    public Integer cast(Double d){
        return (int) Math.rount(d);
    }
}

class ToList<T> implements TypeCaster<T[], List<T>> {

    @Override
    public List<T> cast(T[] array) {
        List<T> list = new ArrayList();
        for (T t: array) {
            list.add(t);
        }
        return list;
    }
}

class ListCaster {
    static <S,T> List<T> castList(List<S> list, TypeCaster<S,T> caster) {
        List<T> newList = new ArrayList<>();
        for (S s: List) {
            newList.add(caster.cast(s));
        }
        return newList;
    }
}

//Q3
//Pandemic

/* Read the following problem description carefully and propose an object-oriented design for the problem described below. Your design should abide by the object-oriented principles you have learned. Avoid cyclic dependency.
The COVID-19 Task Force is developing a system to keep track of confirmed COVID-19 cases in Singapore. Each confirmed case has an integer case id. There are two types of confirmed cases: imported and local. For each imported case (and only imported case), the system keeps track of the country the case is imported from. For each confirmed case, the system keeps track of the contacts of the case. A contact is another confirmed case (can be either local or imported). A case can have zero or more contacts. Each contact is labelled with the nature of the contact, which can be either one of the three: casual contact, close contact, family member.
The cases can be grouped into clusters. Each cluster has a name (a String). A cluster can contain one or more cases. But a case might not necessarily belong to a cluster. A case can belong to multiple clusters.
Please note that:

Declare the Java classes and/or interfaces, showing the relationships (composition, subtype, supertype) among them. You need to show the fields of each class, but you do not need to include any methods in your design.
Write down your declarations in either top-down or bottom-up fashion.
There is no need to show the external driver/client class (such as Main)
Remember to include import statements as necessary.

Write all the classes and/or interfaces in the box provided using valid Java 11 syntax. */

class Case {
    private int id;
}

class ImportedCase extends Case {
    private String country;
}

class LocalCase extends Case {
    
}

class Contact {
    final static int CASUAL = 0;
    final static int ClOSE = 1;
    final static int FAMILY_MEMBER = 2;
    
    Case case1;
    Case case2;
    int nature;
}

class Cluster {
    String name;
    List<Case> cases;
}

class Database {
    Map<Case, List<Contact>> contacts;
    Map<Case, List<Cluster>> clusters;
}