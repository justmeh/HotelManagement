package models;

import java.io.Serializable;

public class Customer implements ICustomer, Serializable {

    String name, surname, phoneNumber;

    public Customer(String name, String surname, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean areYou(String customerSurname) {
        return surname == customerSurname;
    }

    @Override
    public String getCustomerName() {
        return name;
    }

    @Override
    public String getCustomerSurname() {
        return surname;
    }

    @Override
    public String getCustomerPhoneNumber() {
        return phoneNumber;
    }
}
