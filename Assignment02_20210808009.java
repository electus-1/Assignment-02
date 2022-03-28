/*
 * @author: Baran Kirca
 * @version: 29.03.2022
 */

import java.lang.RuntimeException;
import java.util.ArrayList;

public class Assignment02_20210808009 {
    public static void main(String[] args) {
        
    }
}

class Account {
    private String accountNumber;
    private double balance;

    Account() {

    }

    Account(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0.0;

    }

    Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        if (balance < 0) {
            this.balance = 0.0;
        } else {
            this.balance = balance;
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount >= 0) {
            this.balance += amount;
        } else {
            throw new InvalidAmountException(amount);
        }
    }

    public void withdrawal(double amount) {
        if (amount >= 0 && this.balance >= amount) {
            this.balance -= amount;
        } else {
            throw new InvalidAmountException(amount);
        }
    }

    public String toString() {
        return "Account " + getAccountNumber() + " has " + getBalance();
    }

}

class AccountNotFoundException extends RuntimeException {
    private String acctNum;

    public AccountNotFoundException(String acctNum) {
        this.acctNum = acctNum;
    }

    @Override
    public String toString() {
        return "AccountNotFoundException: " + this.acctNum;
    }
}

class BalanceRemainingException extends RuntimeException {
    private double balance;

    public BalanceRemainingException(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return this.balance;
    }

    @Override
    public String toString() {
        return "BalanceRemainingException: " + getBalance();
    }
}

class CompanyNotFoundException extends RuntimeException {
    private int id;
    private String name;

    public CompanyNotFoundException(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        if (this.id == 0) {
            return "CompanyNotFoundException: name - " + this.name;
        }
        if (this.name == null) {
            return "CompanyNotFoundException: id - " + this.id;
        }
        return "CompanyNotFoundException";
    }
}

class CustomerNotFoundException extends RuntimeException {
    private int id;
    private String name;
    private String surname;

    public CustomerNotFoundException(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String toString() {
        if (this.id == 0) {
            return "CustomerNotFoundException: name - " + name
                    + " " + surname;
        }
        if (this.name == null || this.surname == null) {
            return "CustomerNotFoundException: id - " + id;
        }
        return "CustomerNotFoundException";
    }
}

class InvalidAmountException extends RuntimeException {
    private double amount;

    public InvalidAmountException(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "InvalidAmountException: " + this.amount;
    }
}