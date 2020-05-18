import java.util.Scanner;

class Console {
    private String id;
    private String logic;

    Console(String id, String logic) {
        this.id = id;
        this.logic = logic;
    }

    void start() {
        Scanner sc = new Scanner(System.in);
        String command;
        do {
            System.out.print("Enter a command: ");
            command = sc.next();
            logic.invoke(command, this);
        } while (!command.equals("exit"));
    }

    void feedback(String mesg) {
        System.out.println(id + ": " + mesg);
    }
}

class Logic {
    void invoke(String command, Console console) {
        // do something based on the command
        console.feedback(command + " executed");
    }
}

class Main {
    public static void main(String[] args) {
        Logic logic = new Logic();
        Console console = new Console("main", logic.invoke(command, console););
        console.start();
    }
}
