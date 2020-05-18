import java.util.*;  
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collection;

class Student {
    private int labID;
    private String tutID;
    List<Comparator<Student>> comparatorsList = new ArrayList<>(
        Arrays.asList(new comp1(), new comp2())
    );

    Student(int labID, String tutID) {
        this.labID = labID;
        this.tutID = tutID;
    }
    
    public List<Comparator<Student>> getComparators() {
        return comparatorsList;
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

    /*public void comparison() {
        List<Student> students = new ArrayList<>(
            Arrays.asList(new Student(1, "a2"),
                        new Student(3, "b1"),
                        new Student(3, "a2"),
                        new Student(1, "b1")));

        for(Comparator<Student> c : comparatorsList) {

        }

    }*/


}

class comp1 implements Comparator<Student>{

    public int compare(Student s1, Student s2) {
        if(s1.getLabID() == s2.getLabID()) {
            return 0;
        } else if (s1.getLabID() > s2.getLabID()) {
            return 1;
        } else {
            return -1;
        }

    }
}

class comp2 implements Comparator<Student> {
    
    public int compare(Student s1, Student s2) {
        return s1.getTutID().compareTo(s2.getTutID());

    }
}

/*class StudentComparator implements Comparable<Student> {


    @Override
    public int compareTo(Student s2) {
        for

    }

}*/