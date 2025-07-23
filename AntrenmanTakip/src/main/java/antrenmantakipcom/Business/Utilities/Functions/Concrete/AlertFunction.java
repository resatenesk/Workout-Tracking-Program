package antrenmantakipcom.Business.Utilities.Functions.Concrete;

import java.util.Optional;

import antrenmantakipcom.Business.Authorization.UserLoginFrame;
import antrenmantakipcom.Business.Utilities.Functions.Abstract.IFunction;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public class AlertFunction implements IFunction {

    private AlertFunction() {
    }

    public static void ShowDeletedAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("DELETED");
        alert.setHeaderText(null);
        alert.setContentText("Data has deleted succesfully.");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets()
                .add(AlertFunction.class.getResource("/static/alertStyle.css").toExternalForm());
        alert.showAndWait();

    }

    public static void WrongPasswordAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Password");
        alert.setContentText("Please comply with the password rules.");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(AlertFunction.class.getResource("/static/alertStyle.css").toExternalForm());
        alert.showAndWait();
    }

    public static void NoElementsSelectedAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("NOT SELECTED");
        alert.setHeaderText(null);
        alert.setContentText("Please select a data correctly.");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets()
                .add(AlertFunction.class.getResource("/static/alertStyle.css").toExternalForm());
        alert.showAndWait();
    }

    public static void NoConnectionWithDatabaseAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("NO CONNECTION");
        alert.setHeaderText(null);
        alert.setContentText("Please make you sure you have a database connection.");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets()
                .add(AlertFunction.class.getResource("/static/alertStyle.css").toExternalForm());
        alert.showAndWait();
    }

    public static void MissingDataAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("MISSING DATA");
        alert.setHeaderText(null);
        alert.setContentText("Please enter all the data.");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets()
                .add(AlertFunction.class.getResource("/static/alertStyle.css").toExternalForm());
        alert.showAndWait();
    }

    public static void UserAlreadyExist() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("The user already exist");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets()
                .add(AlertFunction.class.getResource("/static/alertStyle.css").toExternalForm());
        alert.showAndWait();
    }

    public static void UserNotFound() {
        Alert alert2 = new Alert(AlertType.INFORMATION);
        alert2.setTitle("User Not Found");
        alert2.setHeaderText(null);
        alert2.setContentText("Please make you sure that you wrote the data correctly");

        DialogPane dialogPane2 = alert2.getDialogPane();
        dialogPane2.getStylesheets()
                .add(UserLoginFrame.class.getResource("/static/alertStyle.css").toExternalForm());
        alert2.showAndWait();
    }

    public static void SuccessAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("SUCCESSFUL");
        alert.setHeaderText(null);
        alert.setContentText("The data has transacted with success.");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets()
                .add(AlertFunction.class.getResource("/static/alertStyle.css").toExternalForm());
        alert.showAndWait();
    }

    public static void FailAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("The transaction is failed.");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(AlertFunction.class.getResource("/static/alertStyle.css").toExternalForm());
        alert.showAndWait();
    }

    public static void NumberFormatExceptionAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("Please enter just real numbers");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(AlertFunction.class.getResource("/static/alertStyle.css").toExternalForm());
        alert.showAndWait();
    }

    public static void TheWorkoutHasAlreadyModifiedAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("The workout that you've chosen already set");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(AlertFunction.class.getResource("/static/alertStyle.css").toExternalForm());
        alert.showAndWait();

    }

    public static Optional<ButtonType> ConfirmAlert() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("CONFIRM");
        alert.setContentText("Are you sure ?");
        alert.setHeaderText(null);
        ButtonType evet = new ButtonType("Yes");
        ButtonType hayir = new ButtonType("No");
        alert.getButtonTypes().setAll(evet, hayir);
        Optional<ButtonType> result = alert.showAndWait();
        return result;

    }

    public static void DateIsNotSelectedAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Date is not selected");
        alert.setHeaderText(null);
        alert.setContentText("Please select a date");
        alert.showAndWait();
    }

    public static void ThereIsNoWorkoutThereAlert() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Hareket Yok!");
        alert.setHeaderText(null);
        alert.setContentText("There is no data for this move in this training template. The chart could not be generated.");
       
        alert.showAndWait();
    }
}
