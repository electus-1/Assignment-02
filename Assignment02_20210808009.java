/*
 * @author: Baran Kirca
 * @version: 30.03.2022
 */

import java.lang.RuntimeException;
import java.util.ArrayList;

public class Assignment02_20210808009 {
    public static void main(String[] args) {
        Bank b = new Bank("My Bank", "My Bank's Address");
        b.addCompany(1, "Company 1");
        b.getCompany(1).openAccount("1234", 0.05);
        b.addAccount(b.getCompany(1).getAccount("1234"));
        b.getAccount("1234").deposit(500000);
        b.getCompany(1).getAccount("1234").deposit(500000);
        b.getCompany(1).openAccount("1235", 0.03);
        b.addAccount(b.getCompany(1).getAccount("1235"));
        b.getCompany(1).getAccount("1235").deposit(25000);
        b.addCompany(2, "Company 2");
        b.getCompany(2).openAccount("2345", 0.03);
        b.addAccount(b.getCompany(2).getAccount("2345"));
        b.getCompany(2).getAccount("2345").deposit(350);
        b.addCustomer(1, "Customer", "1");
        b.addCustomer(2, "Customer", "2");
        Customer c = b.getCustomer(1);
        c.openAccount("3456");
        c.openAccount("3457");
        c.getAccount("3456").deposit(150);
        c.getAccount("3457").deposit(250);
        c = b.getCustomer(2);
        c.openAccount("4567");
        c.getAccount("4567").deposit(1000);
        b.addAccount(c.getAccount("4567"));
        c = b.getCustomer(1);
        b.addAccount(c.getAccount("3456"));
        b.addAccount(c.getAccount("3457"));
        System.out.println(b.toString());
    }
}


class Bank {
    private String name;
    private String address;
    private ArrayList<Customer> customers;
    private ArrayList<Company> companies;
    private ArrayList<Account> accounts;

    public Bank(String name, String address) {
        this.name = name;
        this.address = address;
        this.customers = new ArrayList<>();
        this.companies = new ArrayList<>();
        this.accounts = new ArrayList<>();
    }

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


    public void addCustomer(int id, String name, String surname) {
        Customer customer = new Customer(name, surname);
        customer.setId(id);
        customers.add(customer);

    }

    public void addCompany(int id, String name) {
        Company company = new Company(name);
        company.setId(id);
        companies.add(company);
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
        throw new CustomerNotFoundException(id);
    }


    public Customer getCustomer(String name, String surname) {
        for (Customer customer : customers) {
            if (customer.getName().equals(name) && customer.getSurname().equals(surname)) {
                return customer;
            }
        }
        throw new CustomerNotFoundException(name, surname);
    }

    public Company getCompany(int id) {
        for (Company company : companies) {
            if (company.getId() == id) {
                return company;
            }
        }
        throw new CompanyNotFoundException(id);
    }

    public Company getCompany(String name) {
        for (Company company : companies) {
            if (company.getName().equals(name)) {
                return company;
            }
        }
        throw new CompanyNotFoundException(name);
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

    public String writeCompaniesDown() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Company company : companies) {
            stringBuilder.append("\t" + company.getName() + "\n" + company.writeBusinessAccountsDown());
        }
        return String.valueOf(stringBuilder);
    }

    public String writeCustomersDown() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Customer customer : customers) {
            stringBuilder.append("\t" + customer.getName() + " " + customer.getSurname()
                    + "\n" + customer.writePersonalAccountsDown());
        }
        return String.valueOf(stringBuilder);
    }

    @Override
    public String toString() {
        return getName() + "\t" + getAddress() + "\n" + writeCompaniesDown() + writeCustomersDown();
    }
}

class Account {
    private String accountNumber;
    private double balance;

    Account(String accountNumber) {
        this(accountNumber, 0.0d);
    }

    Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        if (balance >= 0) {
            this.balance = balance;
        } else {
            throw new IllegalArgumentException("Balance must be non-negative");
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
        return "Account " + this.accountNumber + " has " + this.balance;
    }

}

class PersonalAccount extends Account {
    private String name;
    private String surname;
    private String PIN;


    PersonalAccount(String accountNumber, String name, String surname) {
        this(accountNumber, name, surname, 0.0d);
        this.PIN = makeRandomPIN();

    }

    PersonalAccount(String accountNumber, String name, String surname, double balance) {
        super(accountNumber, balance);
        this.name = name;
        this.surname = surname;
        this.PIN = makeRandomPIN();

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

    BusinessAccount(String accountNumber, double interestRate) {
        this(accountNumber, 0.0d, interestRate);
    }

    BusinessAccount(String accountNumber, double balance, double interestRate) {
        super(accountNumber, balance);
        if (interestRate > 0) {
            setInterestRate(interestRate);
        } else {
            throw new IllegalArgumentException("Rate must be positive");
        }
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        if (interestRate > 0) {
            setInterestRate(interestRate);
        } else {
            throw new IllegalArgumentException("Rate must be positive");
        }
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

    Customer(String name, String surname) {
        setName(name);
        setSurname(surname);
        personalAccounts = new ArrayList<>();
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
        for (PersonalAccount personalAccount : personalAccounts) {
            if (personalAccount.getAccountNumber().equals(accountNumber)) {
                flag = false;
                if (personalAccount.getBalance() > 0) {
                    throw new BalanceRemainingException(personalAccount.getBalance());
                } else {
                    personalAccounts.remove(personalAccount);
                }
            }
        }
        if (flag) {
            throw new AccountNotFoundException(accountNumber);
        }
    }

    public String writePersonalAccountsDown() {
        StringBuilder stringBuilder = new StringBuilder();
        for (PersonalAccount personalAccount : personalAccounts) {
            stringBuilder.append("\t" + "\t" + personalAccount.getAccountNumber() + "\t" + personalAccount.getBalance()
                    + "\n");
        }
        return String.valueOf(stringBuilder);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id > 0) {
            this.id = id;
        } else {
            throw new IllegalArgumentException("ID must be positive");
        }
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

    Company(String name) {
        setName(name);
        businessAccounts = new ArrayList<>();
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
        for (BusinessAccount businessAccount : businessAccounts) {
            if (businessAccount.getAccountNumber().equals(accountNumber)) {
                flag = false;
                if (businessAccount.getBalance() > 0) {
                    throw new BalanceRemainingException(businessAccount.getBalance());
                } else {
                    businessAccounts.remove(businessAccount);
                }
            }
        }
        if (flag) {
            throw new AccountNotFoundException(accountNumber);
        }
    }

    public String writeBusinessAccountsDown() {
        StringBuilder stringBuilder = new StringBuilder();
        for (BusinessAccount businessAccount : businessAccounts) {
            stringBuilder.append("\t" + "\t" + businessAccount.getAccountNumber() + "\t" +
                    businessAccount.getInterestRate() + "\t" + businessAccount.getBalance() + "\n");
        }
        return String.valueOf(stringBuilder);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id > 0) {
            this.id = id;
        } else {
            throw new IllegalArgumentException("ID must be positive");
        }
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
        return "BalanceRemainingException: " + this.balance;
    }
}

class CompanyNotFoundException extends RuntimeException {
    private int id;
    private String name;

    public CompanyNotFoundException(int id) {
        this.id = id;
        this.name = null;
    }

    public CompanyNotFoundException(String name) {
        this.name = name;
        this.id = 1;
    }

    @Override
    public String toString() {
        if (this.name == null) {
            return "CompanyNotFoundException: id - " + this.id;
        } else {
            return "CompanyNotFoundException: name - " + this.name;
        }
    }
}

class CustomerNotFoundException extends RuntimeException {
    private int id;
    private String name;
    private String surname;

    public CustomerNotFoundException(int id) {
        this.id = id;
        this.name = null;
        this.surname = null;
    }

    public CustomerNotFoundException(String name, String surname) {
        this.id = 1;
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String toString() {
        if (this.name == null || this.surname == null) {
            return "CustomerNotFoundException: id - " + id;
        } else {
            return "CustomerNotFoundException: name - " + name + " " + surname;
        }
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