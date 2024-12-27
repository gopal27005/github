import java.util.Scanner;
public class linecode{
    static void unipolarNRZ(int[] data){
        System.out.println("Unipolar NRZ encoding:");
        for(int bit:data){
            if(bit == 1) System.out.print("|-|");
            else System.out.print("|_|");
        }
        System.out.println();
    }
    static void polarNRZ(int[] data){
        System.out.println("Polar NRZ encoding:");
        for(int bit:data){
            if(bit == 1) System.out.print("|-|");
            else System.out.print("|_|");
        }
        System.out.println();
    }
    static void manchester(int[] data){
        System.out.println("Manchester encoding:");
        for(int bit:data){
            if(bit == 1) System.out.print("|__ -|");
            else System.out.print("|-__|");
        }
        System.out.println();
    }
    static void diffmanchester(int[] data){
        System.out.println("Differential Manchester encoding:");
        int lasttransition = 1;
        for(int bit:data){
            if(bit == 0){
                if(lasttransition == 1) System.out.print("|_ -|");
                else System.out.print("|- __|");
                lasttransition = -lasttransition;
            }
            else{
                if(lasttransition == 1) System.out.print("|- __|");
                else System.out.print("|__ -|");
                lasttransition = -lasttransition;
            }
        }
        System.out.println();
    }
    public static void main(String[] args){
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter the number of bits in data:");
        int n = sc.nextInt();
        int[] data = new int[n];
        System.out.println("Enter binary data:");
        for (int i= 0; i< n;i++) {
            data[i] = sc.nextInt();
        }
        unipolarNRZ(data);
        polarNRZ(data);
        manchester(data);
        diffmanchester(data);
        sc.close();
    } 

}import os
import pickle

class BankAccount:
    def __init__(self, account_number, account_holder, balance=0):
        self.account_number = account_number
        self.account_holder = account_holder
        self.balance = balance

    def deposit(self, amount):
        if amount > 0:
            self.balance += amount
            return f"Deposit of {amount} successful. New balance is {self.balance}."
        else:
            return "Deposit amount must be greater than zero."

    def withdraw(self, amount):
        if amount > 0 and amount <= self.balance:
            self.balance -= amount
            return f"Withdrawal of {amount} successful. New balance is {self.balance}."
        elif amount > self.balance:
            return "Insufficient balance."
        else:
            return "Withdrawal amount must be greater than zero."

    def transfer(self, target_account, amount):
        if amount > 0 and amount <= self.balance:
            self.balance -= amount
            target_account.balance += amount
            return f"Transferred {amount} to account {target_account.account_number}. New balance is {self.balance}."
        elif amount > self.balance:
            return "Insufficient balance."
        else:
            return "Transfer amount must be greater than zero."

    def get_account_details(self):
        return f"Account Number: {self.account_number}\nAccount Holder: {self.account_holder}\nBalance: {self.balance}"

class Bank:
    def __init__(self):
        self.accounts = {}
        self.load_accounts()

    def create_account(self, account_number, account_holder, initial_deposit=0):
        if account_number in self.accounts:
            return "Account with this number already exists."
        new_account = BankAccount(account_number, account_holder, initial_deposit)
        self.accounts[account_number] = new_account
        self.save_accounts()
        return f"Account created successfully for {account_holder} with initial deposit {initial_deposit}."

    def get_account(self, account_number):
        return self.accounts.get(account_number, None)

    def deposit(self, account_number, amount):
        account = self.get_account(account_number)
        if account:
            return account.deposit(amount)
        return "Account not found."

    def withdraw(self, account_number, amount):
        account = self.get_account(account_number)
        if account:
            return account.withdraw(amount)
        return "Account not found."

    def transfer(self, from_account_number, to_account_number, amount):
        from_account = self.get_account(from_account_number)
        to_account = self.get_account(to_account_number)
        if from_account and to_account:
            return from_account.transfer(to_account, amount)
        return "One or both accounts not found."

    def view_account_details(self, account_number):
        account = self.get_account(account_number)
        if account:
            return account.get_account_details()
        return "Account not found."

    def save_accounts(self):
        with open("accounts.dat", "wb") as file:
            pickle.dump(self.accounts, file)

    def load_accounts(self):
        if os.path.exists("accounts.dat"):
            with open("accounts.dat", "rb") as file:
                self.accounts = pickle.load(file)

def main_menu():
    print("\n==== Bank Management System ====")
    print("1. Create Account")
    print("2. View Account Details")
    print("3. Deposit Money")
    print("4. Withdraw Money")
    print("5. Transfer Money")
    print("6. Exit")
    return input("Please choose an option: ")

def create_account_menu(bank):
    account_number = input("Enter account number: ")
    account_holder = input("Enter account holder's name: ")
    initial_deposit = float(input("Enter initial deposit (default is 0): ") or 0)
    print(bank.create_account(account_number, account_holder, initial_deposit))

def view_account_details_menu(bank):
    account_number = input("Enter account number to view details: ")
    print(bank.view_account_details(account_number))

def deposit_menu(bank):
    account_number = input("Enter account number to deposit into: ")
    amount = float(input("Enter deposit amount: "))
    print(bank.deposit(account_number, amount))

def withdraw_menu(bank):
    account_number = input("Enter account number to withdraw from: ")
    amount = float(input("Enter withdrawal amount: "))
    print(bank.withdraw(account_number, amount))

def transfer_menu(bank):
    from_account_number = input("Enter source account number: ")
    to_account_number = input("Enter target account number: ")
    amount = float(input("Enter transfer amount: "))
    print(bank.transfer(from_account_number, to_account_number, amount))

def main():
    bank = Bank()

    while True:
        choice = main_menu()
        
        if choice == '1':
            create_account_menu(bank)
        elif choice == '2':
            view_account_details_menu(bank)
        elif choice == '3':
            deposit_menu(bank)
        elif choice == '4':
            withdraw_menu(bank)
        elif choice == '5':
            transfer_menu(bank)
        elif choice == '6':
            print("Exiting the Bank Management System. Goodbye!")
            break
        else:
            print("Invalid option. Please try again.")

if __name__ == "__main__":
    main()

