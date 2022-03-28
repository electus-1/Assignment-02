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

class AccountNotFoundException extends RuntimeException {
    private String acctNum;

    @Override
    public String toString() {
        return "AccountNotFoundException: " + this.acctNum;
    }
}

class BalanceRemainingException extends RuntimeException {
    private double balance;

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

    @Override
    public String toString() {
        return "InvalidAmountException: " + this.amount;
    }
}