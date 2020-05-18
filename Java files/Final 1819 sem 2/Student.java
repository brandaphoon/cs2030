import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Student {
    private int labID;
    private String tutID;

    

    Student(int labID, String tutID) {
        this.labID = labID;
        this.tutID = tutID;
    }

    public int getLabID() {
        return this.labID;
    }

    public String getTutID() {
        return this.tutID;
    }

    @Override
    public String toString() {
        return labID + " : " + tutID;
    }

}

class comp1 implements Comparator<Student> {


    public int compare(Student s1, Student s2) {
        return s1.getLabID() - s2.getLabID();
    }


}

class comp2 implements Comparator<Student> {

    public int compare(Student s1, Student s2) {
        return s1.getTutID().compareTo(s2.getTutID());
    }
}

class StudentComparator implements Comparator<Student> {
    
    private List<Comparator<Student>> comparatorList;

    StudentComparator(List<Comparator<Student>> list) {
        comparatorList = list;
    }

    StudentComparator() {
        List<Comparator<Student>> list = new ArrayList<>();
        list.add(new comp1());
        list.add(new comp2());

        comparatorList = list;
    }

    public StudentComparator add(Comparator<Student> list) {
        List<Comparator<Student>> nl = new ArrayList<>(this.comparatorList);
        nl.add(list);
        return new StudentComparator(nl);
        
    }

    public int compare(Student s1, Student s2) {
        List<Integer> num = new ArrayList<>();
        for (Comparator<Student> com: this.comparatorList) {
            num.add(com.compare(s1,s2));
        }

        for (Integer n : num) {
            if (n != 0) {
                return n;
            }
        }
        return 0;

    }

    public void test() {
        List<Student> al = new ArrayList<>();

        al.add(new Student(1,"a2"));
        al.add(new Student(3, "b1"));
        al.add(new Student(3,"a2"));
        al.add(new Student(1, "b1"));

        Collections.sort(al, new StudentComparator());
        System.out.println(al);
    }

}