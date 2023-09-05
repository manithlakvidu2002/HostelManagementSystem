package lk.ijse.hms.controller;

import animatefx.animation.FadeIn;
import animatefx.animation.Shake;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.hms.bo.BOFactory;
import lk.ijse.hms.bo.custom.RoomsBO;
import lk.ijse.hms.bo.custom.UserBO;
import lk.ijse.hms.dto.UserDTO;
import lk.ijse.hms.entity.User;
import lk.ijse.hms.util.Navigation;
import lk.ijse.hms.util.Routes;

import java.io.IOException;

public class LoginController {
    @FXML
    private ImageView imgPasswordView;

    @FXML
    private Label shownPassword;
    @FXML
    private ToggleButton toggleButton;

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtUserName;

    UserBO userBO =(UserBO) BOFactory.getBoFactory().getBO(BOFactory.Type.USER);

    @FXML
    void forgotClickOnAction(ActionEvent event) {
        new Alert(Alert.AlertType.INFORMATION,"Please contact Developer !\n0783496963").show();
    }

    public void initialize(){
        shownPassword.setVisible(false);
    }

    @FXML
    private void passwordFieldKeyTyped(KeyEvent keyEvent) {
        shownPassword.textProperty().bind(Bindings.concat(txtPassword.getText()));
    }

    public void toggleButton(ActionEvent actionEvent) {
        if (toggleButton.isSelected()) {
            shownPassword.setVisible(true);
            shownPassword.textProperty().bind(Bindings.concat(txtPassword.getText()));
            toggleButton.setText("Hide");
            imgPasswordView.setImage(new Image("resources/img/eye-close.png"));

        }else{
            shownPassword.setVisible(false);
            txtPassword.setVisible(true);
            toggleButton.setText("Show");
            imgPasswordView.setImage(new Image("resources/img/eye-open.png"));
        }
    }


    @FXML
    void loginClickOnAction(ActionEvent event) throws IOException {
        if(!txtUserName.getText().isEmpty() && !txtPassword.getText().isEmpty()) {
            String id = txtUserName.getText();
            String pass = txtPassword.getText();

            User userDTO= userBO.getUser(id);
            if(userDTO != null){
                String usPass = userDTO.getPassword();
                if (usPass.equals(pass)){
                    Navigation.navigate(Routes.DASHBOARD, pane);
                }else{
                    new Alert(Alert.AlertType.ERROR, "Wrong password!").show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "User ID not found!").show();
            }

        }else {
            new Alert(Alert.AlertType.ERROR, "Please Fill Details!").show();
        }
    }

    public void createClickOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CREATE_USER_ACC, pane);
    }
}
