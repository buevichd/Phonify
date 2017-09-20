package com.expertsoft.web.form.order;

import javax.validation.constraints.Pattern;

public class OrderContactInfoForm {

    @Pattern(regexp = "[a-zA-Z]{2,}")
    private String firstName;

    @Pattern(regexp = "[a-zA-Z]{2,}")
    private String lastName;

    @Pattern(regexp = ".{2,}")
    private String deliveryAddress;

    @Pattern(regexp = "\\d{9}")
    private String contactPhoneNo;

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

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getContactPhoneNo() {
        return contactPhoneNo;
    }

    public void setContactPhoneNo(String contactPhoneNo) {
        this.contactPhoneNo = contactPhoneNo;
    }
}
