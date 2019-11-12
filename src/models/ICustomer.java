package models;

public interface ICustomer {
    boolean areYou(String customerSurname);

    String getCustomerName();

    String getCustomerSurname();

    String getCustomerPhoneNumber();
}
