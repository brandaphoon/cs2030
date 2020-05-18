import java.util.Scanner;

class BankApp {
    static void transfer(Account source, Account target, double amount) throws Exception {
        source.withdraw(amount);
        target.deposit(amount);
    }

    static double getAmount() {
        System.out.print("Enter amount to transfer: ");
        return new Scanner(System.in).nextDouble();
    }

    public static void main(String[] args) {
        Account s = x();
        Account t = getAccount();
        double amt = getAmount();

        transfer(s, t, amt);
        System.out.println("Transfer successful");
    }
    // getAccount and other methods omitted
}

class Account {
    private double balance;

    Account(double balance) {
        this.balance = balance;
    }

    void withdraw(double amount) throws Exception {
        
        if (this.balance < 0 | this.balance < amount) {
            throw new Exception("Unavailable Balance");
        } else if (amount == 0) {
            throw new Exception("Invalid withdrawal");
        }
        this.balance = this.balance - amount;
      
    }

    void deposit(double amount) {
        this.balance = this.balance + amount;
    }
    // other methods omitted
}

class SavingsAccount extends Account {
    private static int limit = 1000;

    SavingsAccount(double balance) {
        super(balance);
    }

    @Override
    void withdraw(double amount) throws Exception {
        if (amount > SavingsAccount.limit) {
            throw new Exception("Exceeded Limit");
        }

        super.withdraw(amount);
    }
}
