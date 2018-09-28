package Models;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.time.Period;

public class Contacts<date> {
    private String firstName, lastName, Address;
    private LocalDate dateOfBirth;
    private String phone;
    private Image image;

    public Contacts(String firstName, String lastName, String address, LocalDate dateOfBirth, String phone, Image image) {
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setDateOfBirth(dateOfBirth);
        setPhone(phone);
        setImage(image);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if(!firstName.isEmpty())
            this.firstName = firstName;
        else
            throw new IllegalArgumentException("First Name can't be empty!!!");
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if(!lastName.isEmpty())
            this.lastName = lastName;
        else
            throw new IllegalArgumentException("Last name can't be empty!!!");
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        if(!address.isEmpty())
            Address = address;
        else
            throw new IllegalArgumentException("Address can't be empty");
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth){
        int age = Period.between(dateOfBirth, LocalDate.now()).getYears();
        //This code is to validate if age is between 10-100//
        if(age >= 10 && age<=100)
            this.dateOfBirth = dateOfBirth;
        else
            throw new IllegalArgumentException("Age must between 10-100");
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if(phone.matches("[2-9]\\d{2} [-.]?\\d{3} [-.]\\d{4}"))//this validated hone number//
            this.phone = phone;
        else
            throw new IllegalArgumentException("Phone number must be in pattern of XXX-XXX-XXXX");
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}


