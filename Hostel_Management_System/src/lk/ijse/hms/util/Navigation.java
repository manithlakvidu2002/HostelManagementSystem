package lk.ijse.hms.util;

import animatefx.animation.FadeIn;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {
    private static AnchorPane pane;

    public static void navigate(Routes route, AnchorPane pane) throws IOException {
        Navigation.pane = pane;
        Navigation.pane.getChildren().clear();
        Stage window = (Stage) Navigation.pane.getScene().getWindow();

        switch (route) {
            case LOGIN:
                window.setTitle("24D Hostel Management System | Login");
                initUI("Login.fxml");
                break;
            case DASHBOARD:
                window.setTitle("24D Hostel Management System | Dashboard");
                initUI("Dashboard.fxml");
                break;
            case STUDENT:
                window.setTitle("24D Hostel Management System | Student");
                initUI("Student.fxml");
                break;
            case ROOMS:
                window.setTitle("24D Hostel Management System | Rooms");
                initUI("Rooms.fxml");
                break;
            case RESERVATION:
                window.setTitle("24D Hostel Management System | Reservation");
                initUI("Reservation.fxml");
                break;
            case CHANGE_PASSWORD:
                window.setTitle("24D Hostel Management System | Change Password");
                initUI("ChangePassword.fxml");
                break;
            case CREATE_USER_ACC:
                window.setTitle("24D Hostel Management System | Create User Account");
                initUI("CreatePassword.fxml");
                break;
            default:
                new Alert(Alert.AlertType.ERROR, "UI Not Found!").show();
        }
    }
    public static void initUI(String location) throws IOException {
        Navigation.pane.getChildren()
                .add(FXMLLoader.load(Navigation.class.getResource("/resources/view/" + location)));
        new FadeIn(pane).setSpeed(3).play();
    }
}
