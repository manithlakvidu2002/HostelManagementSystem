

package lk.ijse.hms.controller;

import animatefx.animation.Shake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hms.bo.BOFactory;
import lk.ijse.hms.bo.custom.RoomsBO;
import lk.ijse.hms.dto.RoomsDTO;
import lk.ijse.hms.util.Navigation;
import lk.ijse.hms.util.Routes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoomsController {
    @FXML
    private AnchorPane pane;

    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXButton btnEdit;
    @FXML
    private JFXButton btnNew;
    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private TableView<RoomsDTO> tblRooms;
    @FXML
    private TableColumn colRoomTypeID;
    @FXML
    private TableColumn colType;
    @FXML
    private TableColumn colKeyMoney;
    @FXML
    private TableColumn colQty;

    @FXML
    private JFXTextField txtKeyMoney;
    @FXML
    private JFXTextField txtRoomTypeID;
    @FXML
    private JFXTextField txtType;
    @FXML
    private JFXTextField txtQty;


    RoomsBO roomsBO = (RoomsBO) BOFactory.getBoFactory().getBO(BOFactory.Type.ROOM);

    public void initialize() {
        colRoomTypeID.setCellValueFactory(new PropertyValueFactory<>("room_type_id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colKeyMoney.setCellValueFactory(new PropertyValueFactory<>("key_money"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        makeEditableTxtField(false);
        btnDelete.setDisable(true);
        btnCancel.setDisable(true);
        btnSave.setDisable(true);
        btnEdit.setDisable(true);

        tblRooms.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setData(newValue);
                btnDelete.setDisable(true);
                btnCancel.setDisable(true);
                btnSave.setDisable(true);
                btnEdit.setDisable(false);
            }
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            loadRoomData(newValue);
            makeEditableTxtField(false);
        });

        loadRoomData("");
    }

    private void loadRoomData(String SearchID) {
        ObservableList<RoomsDTO> list = FXCollections.observableArrayList();

        ArrayList<RoomsDTO> roomsDTOS = roomsBO.getRoomsData();
        for (RoomsDTO std : roomsDTOS) {
            if (std.getRoom_type_id().contains(SearchID) ||
                    std.getKey_money().contains(SearchID) ||
                    std.getType().contains(SearchID)) {
                RoomsDTO roomsDTO = new RoomsDTO(
                        std.getRoom_type_id(),
                        std.getType(),
                        std.getKey_money(),
                        std.getQty());
                list.add(roomsDTO);
            }
        }
        tblRooms.setItems(list);
    }

    private void makeEditableTxtField(boolean b) {
        //txtRoomTypeID.setEditable(b);
        txtType.setEditable(b);
        txtKeyMoney.setEditable(b);
        txtQty.setEditable(b);
    }

    private void setData(RoomsDTO newValue) {
        txtRoomTypeID.setText(newValue.getRoom_type_id());
        txtType.setText(newValue.getType());
        txtKeyMoney.setText(newValue.getKey_money());
        txtQty.setText(String.valueOf(newValue.getQty()));
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if (!txtRoomTypeID.getText().equals("") || txtType.getText().equals("") || txtKeyMoney.getText().equals("")) {
            String roomTypeIDText = txtRoomTypeID.getText();
            String typeText = txtType.getText();
            String keyMoneyText = txtKeyMoney.getText();
            int qtyText = Integer.parseInt(txtQty.getText());

            if (isValidType() && isValidKeyMoney() && isValidQTY()) {
                if (btnSave.getText().equals("Save")) {
                    RoomsDTO roomsDTO = new RoomsDTO(roomTypeIDText, typeText, keyMoneyText, qtyText);
                    Boolean isAdded = roomsBO.addRoom(roomsDTO);

                    if (isAdded) {
                        new Alert(Alert.AlertType.INFORMATION, " Room Added ! ").show();
                    } else {
                        new Alert(Alert.AlertType.ERROR, " Error ! ").show();
                    }
                }

                if (btnSave.getText().equals("Update")) {
                    RoomsDTO roomsDTO = new RoomsDTO(roomTypeIDText, typeText, keyMoneyText, qtyText);
                    Boolean isUpdated = roomsBO.updateRoom(roomsDTO);

                    if (isUpdated) {
                        new Alert(Alert.AlertType.INFORMATION, " Room Updated ! ").show();
                        clearFields();
                    } else {
                        new Alert(Alert.AlertType.ERROR, " Error ! ").show();
                        clearFields();
                    }
                }
                loadRoomData("");

            } else {
                new Alert(Alert.AlertType.WARNING, "Fill data !").show();
            }
        }
    }

    private boolean isValidQTY() {
        Pattern pattern = Pattern.compile("^[0-9]{1,}$");
        Matcher matcher = pattern.matcher(txtQty.getText());

        boolean isMatches = matcher.matches();
        if (isMatches) {
            return true;
        } else {
            Shake shakeUserName = new Shake(txtQty);
            txtQty.requestFocus();
            shakeUserName.play();
            return false;
        }
    }

    private boolean isValidKeyMoney() {
        Pattern pattern = Pattern.compile("^[0-9]{3,}$");
        Matcher matcher = pattern.matcher(txtKeyMoney.getText());

        boolean isMatches = matcher.matches();
        if (isMatches) {
            return true;
        } else {
            Shake shakeUserName = new Shake(txtKeyMoney);
            txtKeyMoney.requestFocus();
            shakeUserName.play();
            return false;
        }
    }

    private boolean isValidType() {
        Pattern pattern = Pattern.compile("^(AC|Non-AC|None)$");
        Matcher matcher = pattern.matcher(txtType.getText());

        boolean isMatches = matcher.matches();
        if (isMatches) {
            return true;
        } else {
            Shake shakeUserName = new Shake(txtType);
            txtType.requestFocus();
            shakeUserName.play();
            return false;
        }
    }

    private boolean isValidRoomTypeID() {
        Pattern pattern = Pattern.compile("^(?:RM-)[0-9]{4}$");
        Matcher matcher = pattern.matcher(txtRoomTypeID.getText());

        boolean isMatches = matcher.matches();
        if (isMatches) {
            return true;
        } else {
            Shake shakeUserName = new Shake(txtRoomTypeID);
            txtRoomTypeID.requestFocus();
            shakeUserName.play();
            return false;
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Deleted Selected ?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            String idText = txtRoomTypeID.getText();

            RoomsDTO roomsDTO = new RoomsDTO();
            roomsDTO.setRoom_type_id(idText);

            Boolean isAdded = roomsBO.deleteRoom(roomsDTO);

            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, " Room Deleted ! ").show();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, " Error ! ").show();
            }
        }

        loadRoomData("");
    }


    public void btnEditOnAction(ActionEvent actionEvent) {
        if (!txtRoomTypeID.getText().equals("")) {
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
        //String nextID = generateNextID(roomsBO.getCurrentID());
        //txtRoomTypeID.setText(nextID);
        txtRoomTypeID.requestFocus();
        txtRoomTypeID.setText("RM-");
    }

    private void clearFields() {
        txtRoomTypeID.clear();
        txtType.clear();
        txtKeyMoney.clear();
        txtQty.clear();
    }

    /*private String generateNextID(String currentID) {
        if (currentID != null) {
            String[] ids = currentID.split("R0");
            int id = Integer.parseInt(ids[1]);
            id += 1;

            return "R0" + id;
        }
        return "R01";
    }*/

    public void btnCancelOnAction(ActionEvent actionEvent) throws IOException {
        clearFields();
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.DASHBOARD, pane);
    }
}
