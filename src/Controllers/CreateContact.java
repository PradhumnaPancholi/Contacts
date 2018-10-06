package Controllers;

import Models.Contact;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javafx.scene.image.ImageView;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class CreateContact implements Initializable {

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
    private ImageView imageView;

    @FXML
    private Button chooseImageButton;

    @FXML
    private Button saveButton;

    @FXML
    private Label output;

    @FXML
    private Button cancelButton;

    private File image;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //to load default image//
        try{
            image = new File("./src/img/default.png");
            BufferedImage bufferedImage = ImageIO.read(image);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imageView.setImage(image);
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}


