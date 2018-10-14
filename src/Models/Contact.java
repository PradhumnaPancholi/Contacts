package Models;


import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;

import java.security.SecureRandom;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;


public class Contact{
    private String firstName, lastName, address, phone;
    private LocalDate dateOfBirth;
    private File image;

    public Contact(String firstName, String lastName, String address, LocalDate dateOfBirth, String phone)
    {
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setDateOfBirth(dateOfBirth);
        setPhone(phone);
        setImage(new File("./src/img/default.png"));
    }

    public Contact(String firstName, String lastName, String address, LocalDate dateOfBirth, String phone, File image) throws IOException, NoSuchAlgorithmException
    {
        this(firstName, lastName, address, dateOfBirth, phone);
        setImage(image);
        copyImage();
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
            this.address = address;
        else
            throw new IllegalArgumentException("Address can't be empty!!!");
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
        if(phone.matches("[2-9]\\d{2}[-.]?\\d{3}[-.]\\d{4}"))//this validates phone number//
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

    //this method will copy image file to server with unique name//
    public void copyImage() throws IOException
    {
        //path to copy image to local directory//
        Path sourcePath = image.toPath();
        //for unique name//
        String uniqueFileName = getUniqueFileName(image.getName());

        Path targetPath = Paths.get("./src/img/"+uniqueFileName);
        //copy files to new directory//
        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        //update image file to point  to new File//
        image = new File(targetPath.toString());
    }

    //this method will receive a file name and return it with some random prefix//
    public String getUniqueFileName(String oldFileName)
    {
        String newName;

        //random number generator//
        SecureRandom rng = new SecureRandom();

        do
        {
            newName = "";

            //generate 32 random characters//
            for (int count=1; count <= 32; count++)
            {
                int nextChar;

                do
                {
                    nextChar = rng.nextInt(123);
                }while (!validCharacterValue(nextChar));

                newName = String.format("%s%c",newName, nextChar);
            }
            newName += oldFileName;
        }while (!uniqueFileInDirectory(newName));

        return newName;
    }

    //this method will help in validating random generated number//
    public  boolean validCharacterValue(int asciiValue)
    {
        //0-9 ascii range is from 48-57
        if (asciiValue >= 48 && asciiValue <= 57)
            return true;

        //A-Z ascii range is from 65-90
        if (asciiValue >= 65 && asciiValue <= 90)
            return true;

        //a-z ascii range is from 97-122
        if (asciiValue >= 97 && asciiValue <= 122)
            return true;

        return false;
    }

    //this method will check if file name is unique//
    public boolean uniqueFileInDirectory (String fileName)
    {
        File directory = new File("./src/img/");

        File[] dir_contents = directory.listFiles();

        for (File file: dir_contents)
        {
            if (file.getName().equals(fileName))
                return false;
        }
        return true;
    }

    // This method will return a formatted String with the persons' first name, last name and age//
    public String toString()
    {
        return String.format("%s %s %s %d %s", firstName, lastName, address, Period.between(dateOfBirth,LocalDate.now()).getYears(), phone);
    }


    //this method will write instance of contact into database//
    public void insertIntoDB() throws SQLException
    {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try
        {
            //1. Connect to database//
            conn = DriverManager.getConnection("jdbc:sqlserver://assign01.database.windows.net:1433;database=contact;user=pradhumna@assign01;password=assign6629#;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");

            //2. Create a string to hold query for user input//
            String sql = "INSERT INTO contacts (firstName, lastName, address, dateOfBirth, phone, image)"
                          + "Values(?,?,?,?,?,?)";

            //3. Prepare the query//
            preparedStatement = conn.prepareStatement(sql);

            //4. Convert the birthday into a sql date//
            Date dob = java.sql.Date.valueOf(dateOfBirth);

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



