package Controllers;

import Models.Contact;
import Views.SceneChanger;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
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
    private Label output;

    private File image;

    private boolean imageChanged;

    //Cancel button functionality--> this method will change scene back to Contact Table (button is label never mind on GUI)//
    public void cancelButtonPushed(javafx.event.ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "ContactTable.fxml", "All Contacts" );
    }

    //for choose image functionality//
    public void chooseImageButtonPushed(javafx.event.ActionEvent event)
    {
        //stage to open a new windows//
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        //instantiating file chooser object//
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");

        //filter for image files only//
        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("Image File (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("Image File (*.png)", "*.png");

        //adding filters to fileChooser//
        fileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter);

        //to put opened file in instance variable image //
        File tmpImageFile = fileChooser.showOpenDialog(stage);

        //validation to avoid null input//
        if (tmpImageFile != null){

            image = tmpImageFile;

            //update image in imageView//
            if(image.isFile())
            {
                try
                {
                    BufferedImage bufferedImage = ImageIO.read(image);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    imageView.setImage(image);
                    imageChanged = true;
                }
                catch (IOException e)
                {
                    System.err.println(e.getMessage());
                }
            }
        }

    }

    //this method will try to fetch data from GUI, run validation and save it into database//
    public void saveButtonPushed(javafx.event.ActionEvent event) throws IOException, NoSuchAlgorithmException {

        try
        {
            Contact contact;
            if (imageChanged)
            {
                contact = new Contact(firstNameTextField.getText(), lastNameTextField.getText(), addressTextField.getText(), dateOfBirthDatePicker.getValue(), phoneTextField.getText(), image);
            }
            else
            {
                contact = new Contact(firstNameTextField.getText(), lastNameTextField.getText(), addressTextField.getText(), dateOfBirthDatePicker.getValue(), phoneTextField.getText());
            }
            output.setText("");
            contact.insertIntoDB();
            SceneChanger sc = new SceneChanger();
            sc.changeScene(event, "ContactTable.fxml", "All Contacts");
        }

        catch (SQLException e)
        {
            output.setText(e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //to set a default year / minimum age for dob//
        dateOfBirthDatePicker.setValue(LocalDate.now().minusYears(10));
        //to check if custom image is selected//
        imageChanged = false;//initial value is false //
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


