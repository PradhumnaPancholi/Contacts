package Models;


import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;

public class Contact{
    private String firstName, lastName, address, phone;
    private LocalDate dateOfBirth;
    private File image;

    public Contact(String name, String lastName, String address, LocalDate dateOfBirth, String firstName) {
        setFirstName(firstName);
        setLastName(this.lastName);
        setAddress(this.address);
        setDateOfBirth(this.dateOfBirth);
        setPhone(phone);
        setImage(new File("./src/img/default.png"));
    }

    public Contact(String firstName, String lastName, String address, LocalDate dateOfBirth, String phone, File image) {
        this(firstName, lastName, address, dateOfBirth, phone);
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
        return address;
    }

    public void setAddress(String address) {
        if(!address.isEmpty())
            address = address;
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

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }


    //this method will write instance of contact into database//
    public void insertIntoDB() throws SQLException
    {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try
        {
            //1. Connect to database//
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/contact","root", "");

            //2. Create a string to hold query for user input//
            String sql = "INSERT INTO contacts (firstName, lastName, address, dateOfBirth, phone, image)"
                          + "Values(?,?,?,?,?,?)";

            //3. Prepare the query//
            preparedStatement = conn.prepareStatement(sql);

            //4. Convert the birthday into a sql date//
            Date dob = Date.valueOf(dateOfBirth);

            //5 Bind Values to the pararmeter//
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, address);
            preparedStatement.setDate(4, dob);
            preparedStatement.setString(5, phone);
            preparedStatement.setString(6, image.getName());

            preparedStatement.executeUpdate();

        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        finally
        {
            if(preparedStatement != null)
                preparedStatement.close();

            if(conn != null)
                conn.close();

        }

    }

}



