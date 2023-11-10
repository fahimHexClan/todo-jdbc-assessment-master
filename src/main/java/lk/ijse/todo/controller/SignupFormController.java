package lk.ijse.todo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.todo.Db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupFormController {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPw;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        String userName = txtUserName.getText();
        String email = txtEmail.getText();
        String password = txtPw.getText();


        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "INSERT INTO Users (Email, UserName, Password) VALUES (?, ?, ?)";
            try (PreparedStatement stm = connection.prepareStatement(sql)) {
                stm.setString(1, email);
                stm.setString(2, userName);
                stm.setString(3, password);

                int affectedRows = stm.executeUpdate();

                if (affectedRows > 0) {

                    showInfoAlert("Registration Successful", "User registered successfully!");
                    navigateToLoginPage();
                } else {
                    showErrorAlert("Registration Failed", "Failed to register user.");
                }
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
            showErrorAlert("Error", "An error occurred during registration.");
        }
    }

    @FXML
    void hyperLoginHereOnAction(ActionEvent event) throws IOException {
        navigateToLoginPage();
    }

    private void navigateToLoginPage() throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Scene scene = new Scene(rootNode);

        root.getChildren().clear();
        Stage primaryStage = (Stage) root.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Form");
    }

    private void showInfoAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }



}
