import java.util.ArrayList;
class Case {
    protected int caseId;
}
class ImportedCase extends Case {
    private String country;
}

interface ContactInfo {

}
class Contact implements ContactInfo {
    private String nature;
    private Case case1;
    private Case case2;
    //linked between two cases
}

class CasualContact extends Contact {
    private static String type = "CASUAL";
}
class CloseContact extends Contact {
    private static String type = "CLOSE";
}
class FamilyMember extends Contact {
    private static String type = "FAMILY";
}

class Cluster {
    private String name;
    private ArrayList<Case> cases;
}
