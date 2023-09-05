package lk.ijse.hms.controller;

import animatefx.animation.Shake;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.hms.bo.BOFactory;
import lk.ijse.hms.bo.custom.UserBO;
import lk.ijse.hms.dto.UserDTO;
import lk.ijse.hms.entity.User;
import lk.ijse.hms.util.Navigation;
import lk.ijse.hms.util.Routes;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePasswordController {
    public JFXTextField newUsername;

    @FXML
    private AnchorPane pane;


    @FXML
    private JFXPasswordField newPassword;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.Type.USER);

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.DASHBOARD, pane);
    }

    @FXML
    void changeClickOnAction(ActionEvent event) throws IOException {
        if(!newUsername.getText().isEmpty() && !newPassword.getText().isEmpty()) {
            String name = newUsername.getText();
            String pas = newPassword.getText();
            User user= userBO.getUser(name);
            if(user != null) {
                UserDTO userDTO = new UserDTO(name, pas);
                boolean isUpdate = userBO.updateUser(userDTO);
                if (isUpdate) {
                    new Alert(Alert.AlertType.CONFIRMATION, "User Password Update Succesfully!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "User Password Not Updated!").show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "User ID not found!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Please Fill Details!").show();
        }
    }

}