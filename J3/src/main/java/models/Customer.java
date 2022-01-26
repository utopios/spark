package models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize()
public class Customer {
    private Integer cusotmerId;
    private String firstName;
    private String lastName;

    public Customer() {
    }

    public Customer(Integer cusotmerId, String firstName, String lastName) {
        this.cusotmerId = cusotmerId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getCusotmerId() {
        return cusotmerId;
    }

    public void setCusotmerId(Integer cusotmerId) {
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
