import java.util.Scanner;  

class BankAccount {  
    private double balance;  

    public BankAccount(double initialBalance) {  
        this.balance = initialBalance;  
    }  

    public double getBalance() {  
        return balance;  
    }  

    public void deposit(double amount) {  
        balance += amount;  
        System.out.println("Successfully deposited: $" + amount);  
    }  

    public boolean withdraw(double amount) {  
        if (amount <= balance) {  
            balance -= amount;  
            System.out.println("Successfully withdrew: $" + amount);  
            return true;  
        } else {  
            System.out.println("Insufficient balance for withdrawal of: $" + amount);  
            return false;  
        }  
    }  
}  

class ATM {  
    private BankAccount bankAccount;  
    private Scanner scanner;  

    public ATM(BankAccount bankAccount) {  
        this.bankAccount = bankAccount;  
        this.scanner = new Scanner(System.in);  
    }  

    public void showMenu() {  
        int choice;  
        do {  
            System.out.println("ATM Menu:");  
            System.out.println("1. Withdraw");  
            System.out.println("2. Deposit");  
            System.out.println("3. Check Balance");  
            System.out.println("4. Exit");  
            System.out.print("Choose an option: ");  
            choice = scanner.nextInt();  

            switch (choice) {  
                case 1:  
                    handleWithdraw();  
                    break;  
                case 2:  
                    handleDeposit();  
                    break;  
                case 3:  
                    checkBalance();  
                    break;  
                case 4:  
                    System.out.println("Thank you for using the ATM. Goodbye!");  
                    break;  
                default:  
                    System.out.println("Invalid option. Please try again.");  
            }  
        } while (choice != 4);  
    }  

    private void handleWithdraw() {  
        System.out.print("Enter amount to withdraw: $");  
        double amount = scanner.nextDouble();  
        bankAccount.withdraw(amount);  
    }  

    private void handleDeposit() {  
        System.out.print("Enter amount to deposit: $");  
        double amount = scanner.nextDouble();  
        bankAccount.deposit(amount);  
    }  

    private void checkBalance() {  
        System.out.println("Current Balance: $" + bankAccount.getBalance());  
    }  
}  

public class Main {  
    public static void main(String[] args) {  
        BankAccount myAccount = new BankAccount(500.00); // Initialize with a balance  
        ATM atm = new ATM(myAccount);  
        atm.showMenu();  
    }  
}
