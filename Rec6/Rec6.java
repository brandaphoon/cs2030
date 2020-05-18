import java.util.Scanner;

class InvalidQuestionException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidQuestionException(String msg) {
        super(msg);
    }
}

class InvalidMCQException extends InvalidQuestionException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidMCQException(String mesg) {
        super(mesg);
    }
}
class InvalidTFQException extends InvalidQuestionException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidTFQException(String mesg) {
        super(mesg);
    }
}

abstract class QA {

    String question;
    char answer;

    QA(String q) {
        this.question = q;
    }

    abstract void getAnswer() throws InvalidQuestionException;

    char askQuestion() {
        Scanner sc= new Scanner(System.in);
        System.out.println(question + " ");
        return sc.next().charAt(0);
    }
}

class MCQ extends QA {

    public MCQ(String q) {
        super(q);
    }

    @Override
    void getAnswer() throws InvalidMCQException {
        answer = super.askQuestion();
        if (answer < 'A' || answer > 'E') {
            throw new InvalidMCQException("Invalid MCQ answer");
        }
    }

}

class TFQ extends QA {

    public TFQ(String s){
        super(s);
    }

    @Override
    void getAnswer() throws InvalidTFQException {
        answer = super.askQuestion();
        if (answer != 'T' && answer != 'F') {
            throw new InvalidTFQException("Invalid TFQ answer");
        }
    }

}

class Main {

    static void processQuestion (QA qn) {
        try {
            qn.getAnswer();
        } catch (InvalidQuestionException ex) {
            System.out.println(ex);
        }
    }

    public static void main(String[] args) {
        QA mcq = new MCQ("What is the ans?");
        QA tfq = new TFQ("What is the ans?");

        processQuestion(mcq);
        processQuestion(tfq);
    }

}

//Function<String,Integer> findFirstSpace = str -> str.indexOf(' ');

Function<String,Integer> findFirstSpace = new Function<>() {
    @Override
    public Integer apply(String str) {
        return str.indexOf(" ");
    }
};}

