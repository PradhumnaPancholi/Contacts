package Controllers;

import Models.Contact;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.awt.event.ActionEvent;

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
    public void saveButtonPushed(ActionEvent event) {

        try
        {
            Contact contact = new Contact(firstNameTextField.getText());

            contact.insertIntoDB();
        }
        catch (Exception e)
        {
            output.setText(e.getMessage());
        }
    }
}

