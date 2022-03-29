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


class Bank {
    private String name;
    private String address;
    private ArrayList<Customer> customers;
    private ArrayList<Company> companies;
    private ArrayList<Account> accounts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //surname isn't mentioned as a parameter in the pdf file
    public void addCustomer(int id, String name, String surname) {
        customers.add(new Customer(name, surname));
        customers.get(customers.size() - 1).setId(id);
    }

    public void addCompany(int id, String name) {
        companies.add(new Company(name));
        companies.get(companies.size() - 1).setId(id);
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public Customer getCustomer(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        throw new CustomerNotFoundException(id, null, null);
    }

    //surname isn't mentioned as a parameter in the pdf file
    public Customer getCustomer(String name, String surname) {
        for (Customer customer : customers) {
            if (customer.getName().equals(name) && customer.getName().equals(surname)) {
                return customer;
            }
        }
        throw new CustomerNotFoundException(0, name, surname);
    }

    public Company getCompany(int id) {
        for (Company company : companies) {
            if (company.getId() == id) {
                return company;
            }
        }
        throw new CompanyNotFoundException(id, null);
    }

    public Company getCompany(String name) {
        for (Company company : companies) {
            if (company.getName().equals(name)) {
                return company;
            }
        }
        throw new CompanyNotFoundException(0, name);
    }

    public Account getAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        throw new AccountNotFoundException(accountNumber);
    }

    public void transferFunds(String accountFrom, String accountTo, double amount) {
        getAccount(accountFrom).withdrawal(amount);
        getAccount(accountTo).deposit(amount);

    }

    public void closeAccount(String accountNumber) {
        if (getAccount(accountNumber).getBalance() > 0) {
            throw new BalanceRemainingException(getAccount(accountNumber).getBalance());
        } else {
            accounts.remove(getAccount(accountNumber));
        }
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

class PersonalAccount extends Account {
    private String name;
    private String surname;
    private String PIN;

    PersonalAccount() {

    }

    PersonalAccount(String accountNumber, String name, String surname) {
        super(accountNumber);
        setName(name);
        setSurname(surname);
        setPIN(makeRandomPIN());

    }

    PersonalAccount(String accountNumber, String name, String surname, double balance) {
        super(accountNumber, balance);
        setName(name);
        setSurname(surname);
        setPIN(makeRandomPIN());

    }

    public static String makeRandomPIN() {
        return "" + (Math.round(Math.random() * 10)) + (Math.round(Math.random() * 10)) +
                (Math.round(Math.random() * 10)) + (Math.round(Math.random() * 10));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public String toString() {
        return "Account " + getAccountNumber() + " belonging to " + getName() + " " + getSurname().toUpperCase()
                + " has " + getBalance();
    }

}

class BusinessAccount extends Account {
    private double interestRate;

    BusinessAccount() {

    }

    BusinessAccount(String accountNumber, double interestRate) {
        super(accountNumber);
        setInterestRate(interestRate);

    }

    BusinessAccount(String accountNumber, double balance, double interestRate) {
        super(accountNumber, balance);
        setInterestRate(interestRate);

    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double calculateInterest() {
        return (getBalance() * getInterestRate());
    }
}

class Customer {
    private int id;
    private String name;
    private String surname;
    private ArrayList<PersonalAccount> personalAccounts;

    Customer() {

    }

    Customer(String name, String surname) {
        setName(name);
        setSurname(surname);
    }

    public void openAccount(String accountNumber) {
        personalAccounts.add(new PersonalAccount(accountNumber, getName(), getSurname()));
    }

    public PersonalAccount getAccount(String accountNumber) {
        for (PersonalAccount personalAccount : personalAccounts) {
            if (personalAccount.getAccountNumber().equals(accountNumber)) {
                return personalAccount;
            }
        }
        throw new AccountNotFoundException(accountNumber);
    }

    public void closeAccount(String accountNumber) {
        boolean flag = true;
        for (int i = 0; i < personalAccounts.size(); i++) {
            if (personalAccounts.get(i).getAccountNumber().equals(accountNumber)) {
                flag = false;
                if (personalAccounts.get(i).getBalance() > 0) {
                    throw new BalanceRemainingException(personalAccounts.get(i).getBalance());
                } else {
                    personalAccounts.remove(personalAccounts.get(i));
                }
            }
        }
        if (flag) {
            throw new AccountNotFoundException(accountNumber);
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String toString() {
        return getName() + " " + getSurname().toUpperCase();
    }
}

class Company {
    private int id;
    private String name;
    private ArrayList<BusinessAccount> businessAccounts;

    Company() {

    }

    Company(String name) {
        setName(name);
    }

    public void openAccount(String accountNumber, double interestRate) {
        businessAccounts.add(new BusinessAccount(accountNumber, interestRate));
    }

    public BusinessAccount getAccount(String accountNumber) {
        for (BusinessAccount businessAccount : businessAccounts) {
            if (businessAccount.getAccountNumber().equals(accountNumber)) {
                return businessAccount;
            }
        }
        throw new AccountNotFoundException(accountNumber);
    }

    public void closeAccount(String accountNumber) {
        boolean flag = true;
        for (int i = 0; i < businessAccounts.size(); i++) {
            if (businessAccounts.get(i).getAccountNumber().equals(accountNumber)) {
                flag = false;
                if (businessAccounts.get(i).getBalance() > 0) {
                    throw new BalanceRemainingException(businessAccounts.get(i).getBalance());
                } else {
                    businessAccounts.remove(businessAccounts.get(i));
                }
            }
        }
        if (flag) {
            throw new AccountNotFoundException(accountNumber);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return getName();
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