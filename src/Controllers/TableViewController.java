package Controllers;

import Models.Contact;
import Views.SceneChanger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TableViewController implements Initializable {

    @FXML private TableView <Contact>contactTable;
    @FXML private TableColumn<Contact, String>firstNameColumn;
    @FXML private TableColumn<Contact, String>lastNameColumn;
    @FXML private TableColumn<Contact, String>addressColumn;
    @FXML private TableColumn<Contact, LocalDate>dateOfBirthColumn;
    @FXML private TableColumn<Contact, String>phoneColumn;
    @FXML private TableColumn<Contact, String>imageColumn;


    //this method with switch scene: Contact Form//
    public void addNewContactButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event,"ContactForm.fxml", "Add new Contact");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //configure the table column//
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("lastName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("address"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<Contact, LocalDate>("dateOfBirth"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("phone"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("image"));

        try
        {
            loadContacts();
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }
    //this method will load data from database into table//
    public void loadContacts() throws SQLException {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            //1 connection with DB//
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/contact","root", "");

            //2 create a statement object//
            statement = conn.createStatement();

            //3 create a SQL query //
            resultSet = statement.executeQuery("SELECT * FROM contacts");

            //4 create contact object from each record//
            while (resultSet.next())
            {
                Contact newContact  = new Contact(resultSet.getString("firstName"),
                                                  resultSet.getString("lastName"),
                                                  resultSet.getString("address"),
                                                  resultSet.getDate("dateOfBirth").toLocalDate(),
                                                  resultSet.getString("phone"));
                newContact.setImage(new File(resultSet.getString("image")));
                contacts.add(newContact);
            }
            contactTable.getItems().addAll(contacts);
        }catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        finally {
            if (conn != null)
                conn.close();
            if (statement != null)
                statement.close();
            if (resultSet != null)
                resultSet.close();
        }
    }
}
