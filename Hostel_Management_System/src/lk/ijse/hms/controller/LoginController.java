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
        new Alert(Alert.AlertType.INFORMATION,"Please contact Developer !\n0773572070").show();
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
        Shake shakeUserName = new Shake(txtUserName);
        Shake shakePassword = new Shake(txtPassword);

        if( isCorrectPassword() && isCorrectUserName()){
            txtUserName.setFocusColor(Paint.valueOf("BLUE"));
            Navigation.navigate(Routes.DASHBOARD, pane);
            new FadeIn(pane).setSpeed(3).play();

        }else if (isCorrectPassword() && !isCorrectUserName()) {
            txtUserName.requestFocus();
            txtUserName.setFocusColor(Paint.valueOf("RED"));
            shakeUserName.play();
        } else if (!isCorrectPassword() && isCorrectUserName()) {
            txtPassword.requestFocus();
            txtPassword.setFocusColor(Paint.valueOf("RED"));
            shakePassword.play();
        } else{
            new Alert(Alert.AlertType.ERROR,"Try again !").show();
            txtPassword.clear();
            txtUserName.clear();
        }
    }

    private boolean isCorrectUserName() {
        String user = userBO.getUser("1");
        if(user == null){
            new Alert(Alert.AlertType.ERROR," Database Error !").show();
            return false;
        }
        return txtUserName.getText().equals(user);
    }

    private boolean isCorrectPassword() {
        String password = userBO.getPassword("1");
        System.out.println(password);
        if(password == null){
            new Alert(Alert.AlertType.ERROR," Database Error !").show();
            return false;
        }
        return txtPassword.getText().equals(password);
    }


}
