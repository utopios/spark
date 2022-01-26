package models;

public class Customer {
    private int cusotmerId;
    private String firstName;
    private String lastName;

    public Customer() {
    }

    public Customer(int cusotmerId, String firstName, String lastName) {
        this.cusotmerId = cusotmerId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getCusotmerId() {
        return cusotmerId;
    }

    public void setCusotmerId(int cusotmerId) {
        this.cusotmerId = cusotmerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
