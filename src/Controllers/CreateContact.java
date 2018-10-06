package Controllers;

import Models.Contact;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;


public class CreateContact {

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private DatePicker dateOfBirthDatePicker;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private Button chooseImageButton;

    @FXML
    private Button saveButton;

    @FXML
    private Label output;

    @FXML
    private Button cancelButton;

    //this method will try to fetch data from GUI, run validation and save it into database//
    public void saveButtonPushed(javafx.event.ActionEvent actionEvent)
    {

        try {
            Contact contact = new Contact(firstNameTextField.getText(), lastNameTextField.getText(), addressTextField.getText(), dateOfBirthDatePicker.getValue(), phoneTextField.getText());
            output.setText("");
            contact.insertIntoDB();
        } catch (SQLException e) {
            output.setText(e.getMessage());

        }
    }
}


