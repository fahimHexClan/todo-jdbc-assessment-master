package lk.ijse.todo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.todo.Db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtPassword;

    @FXML
    private AnchorPane root;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if (txtUserName.getText().isBlank() == false && txtPassword.getText().isBlank() == false) {
            validation();
        } else {
            new Alert(Alert.AlertType.WARNING, "Username and Password cannot be empty").show();
        }
    }

    private void validation() throws SQLException, ClassNotFoundException, IOException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(2) FROM Users WHERE name = ? AND password = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setObject(1, txtUserName.getText());
            stm.setObject(2, txtPassword.getText());
            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    navigateToMainWindow();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try again with the correct username and password").show();
                }
            }
        }
    }

    private void navigateToMainWindow() throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/main_form.fxml"));
        Scene scene = new Scene(rootNode);

        root.getChildren().clear();
        Stage primaryStage = (Stage) root.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("ToDo");
    }

    @FXML
    void hyperSignUpOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/signup_form.fxml"));

        Scene scene = new Scene(rootNode);

        root.getChildren().clear();
        Stage primaryStage = (Stage) root.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Sign Up");
    }
}
