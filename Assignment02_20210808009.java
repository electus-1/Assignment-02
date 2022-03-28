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

    public AccountNotFoundException(String acctNum) {
        super("AccountNotFoundException: " + acctNum);
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
        super("BalanceRemainingException: " + balance);
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "BalanceRemainingException: " + getBalance();
    }
}