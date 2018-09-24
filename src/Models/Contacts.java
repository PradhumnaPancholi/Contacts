package Models;

import javafx.scene.image.Image;

public class Contacts<date> {
    private String firstName, lastName, Address;
    private date dateOfBirth;
    private double phone;
    private Image image;

    public Contacts(String firstName, String lastName, String address, date dateOfBirth, double phone, Image image) {
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

    public date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getPhone() {
        return phone;
    }

    public void setPhone(double phone) {
        this.phone = phone;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}


