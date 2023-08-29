package lk.ijse.hms.controller;

import animatefx.animation.Shake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hms.bo.BOFactory;
import lk.ijse.hms.bo.custom.StudentBO;
import lk.ijse.hms.dto.StudentDTO;
import lk.ijse.hms.util.Navigation;
import lk.ijse.hms.util.Routes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentController {
    public AnchorPane pane;

    public JFXDatePicker dateDOB;
    public JFXButton btnDelete;
    public JFXButton btnCancel;
    @FXML
    private JFXButton btnEdit;

    @FXML
    private JFXButton btnNew;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private RadioButton rbFemale;

    @FXML
    private ToggleGroup gender;

    @FXML
    private RadioButton rbMale;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private TableView<StudentDTO> tblStudent;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colDOB;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private JFXButton btnSave;

    StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.Type.STUDENT);

    public void initialize() {
        txtID.setEditable(false);

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact_no"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

        makeEditableTxtField(false);
        txtID.setEditable(false);
        btnDelete.setDisable(true);
        btnCancel.setDisable(true);
        btnSave.setDisable(true);
        btnEdit.setDisable(true);

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setData(newValue);
                btnDelete.setDisable(true);
                btnCancel.setDisable(true);
                btnSave.setDisable(true);
                btnEdit.setDisable(false);
            }
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            loadStudentData(newValue);
            makeEditableTxtField(false);
        });

        loadStudentData("");
    }

    private void loadStudentData(String SearchID) {
        ObservableList<StudentDTO> list = FXCollections.observableArrayList();

        ArrayList<StudentDTO> studentDTOS = studentBO.getStudentData();
        for (StudentDTO std : studentDTOS) {
            if (std.getId().contains(SearchID) ||
                    std.getName().contains(SearchID) ||
                    std.getAddress().contains(SearchID)) {
                StudentDTO studentDTO = new StudentDTO(std.getId(),
                        std.getName(), std.getAddress(),
                        std.getContact_no(),
                        std.getDob(),
                        std.getGender());
                list.add(studentDTO);
            }
        }
        tblStudent.setItems(list);
    }

    private void makeEditableTxtField(boolean b) {
        txtName.setEditable(b);
        txtAddress.setEditable(b);
        txtContact.setEditable(b);
        dateDOB.setEditable(b);
    }

    private void setData(StudentDTO newValue) {
        txtID.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtAddress.setText(newValue.getAddress());
        txtContact.setText(newValue.getContact_no());
        dateDOB.setValue(LocalDate.parse(newValue.getDob()));
        if (newValue.getGender().equals("Male")) {
            rbMale.setSelected(true);
        } else {
            rbFemale.setSelected(true);
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if (!txtName.getText().equals("") || txtID.getText().equals("") || txtContact.getText().equals("")) {
            String nameText = txtName.getText();
            String addressText = txtAddress.getText();
            String contactText = txtContact.getText();
            String idText = txtID.getText();
            String dobText = dateDOB.getValue().toString();
            RadioButton rb = (RadioButton) gender.getSelectedToggle();
            String genderText = rb.getText();

            // regex
            if (isValidName() && isValidAddress() && isValidContact()) {
                if (btnSave.getText().equals("Save")) {
                    StudentDTO studentDTO = new StudentDTO(idText, nameText, addressText, contactText, dobText, genderText);
                    Boolean isAdded = studentBO.addStudent(studentDTO);

                    if (isAdded) {
                        new Alert(Alert.AlertType.INFORMATION, " Student Added ! ").show();
                    } else {
                        new Alert(Alert.AlertType.ERROR, " Error ! ").show();
                    }
                }

                if (btnSave.getText().equals("Update")) {
                    StudentDTO studentDTO = new StudentDTO(idText, nameText, addressText, contactText, dobText, genderText);
                    Boolean isUpdated = studentBO.updateStudent(studentDTO);

                    if (isUpdated) {
                        new Alert(Alert.AlertType.INFORMATION, " Student Updated ! ").show();
                        clearFields();
                    } else {
                        new Alert(Alert.AlertType.ERROR, " Error ! ").show();
                        clearFields();
                    }
                }
                loadStudentData("");

            } else {
                new Alert(Alert.AlertType.WARNING, "Fill data !").show();
            }
        }
    }

    private boolean isValidContact() {
        Pattern pattern = Pattern.compile("^(?:7|0|(?:\\+94))[0-9]{9,10}$");
        Matcher matcher = pattern.matcher(txtContact.getText());

        boolean isMatches = matcher.matches();
        if (isMatches) {
            return true;
        } else {
            Shake shakeUserName = new Shake(txtContact);
            txtContact.requestFocus();
            shakeUserName.play();
            return false;
        }
    }

    private boolean isValidAddress() {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{3,}$");
        Matcher matcher = pattern.matcher(txtAddress.getText());

        boolean isMatches = matcher.matches();
        if (isMatches) {
            return true;
        } else {
            Shake shakeUserName = new Shake(txtAddress);
            txtAddress.requestFocus();
            shakeUserName.play();
            return false;
        }
    }

    private boolean isValidName() {
        Pattern pattern = Pattern.compile("^[a-zA-Z]{3,}$");
        Matcher matcher = pattern.matcher(txtName.getText());

        boolean isMatches = matcher.matches();
        if (isMatches) {
            return true;
        } else {
            Shake shakeUserName = new Shake(txtName);
            txtName.requestFocus();
            shakeUserName.play();
            return false;
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Deleted Selected ?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            String idText = txtID.getText();

            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setId(idText);

            Boolean isAdded = studentBO.deleteStudent(studentDTO);

            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, " Student Deleted ! ").show();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, " Error ! ").show();
            }
        }

        loadStudentData("");
    }


    public void btnEditOnAction(ActionEvent actionEvent) {
        if (!txtID.getText().equals("")) {
            btnDelete.setDisable(false);
            btnCancel.setDisable(false);
            btnSave.setDisable(false);
            btnSave.setText("Update");
            makeEditableTxtField(true);

        } else {
            new Alert(Alert.AlertType.ERROR, "Not selected !").show();
        }
    }

    public void btnNewOnAction(ActionEvent actionEvent) {
        makeEditableTxtField(true);
        clearFields();

        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        btnCancel.setDisable(false);
        btnSave.setDisable(false);
        btnSave.setText("Save");
        String nextID = generateNextID(studentBO.getCurrentID());
        txtID.setText(nextID);
        txtName.requestFocus();
    }

    private void clearFields() {
        txtID.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        dateDOB.setValue(LocalDate.parse("2000-01-01"));
        rbMale.setSelected(true);
    }

    private String generateNextID(String currentID) {
        if (currentID != null) {
            String[] ids = currentID.split("S0");
            int id = Integer.parseInt(ids[1]);
            id += 1;

            return "S0" + id;
        }
        return "S01";
    }

    public void btnCancelOnAction(ActionEvent actionEvent) throws IOException {
        clearFields();
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.DASHBOARD, pane);
    }
}
