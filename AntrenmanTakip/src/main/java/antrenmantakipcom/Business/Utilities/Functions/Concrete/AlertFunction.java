package antrenmantakipcom.Business.Utilities.Functions.Concrete;

import antrenmantakipcom.Business.Utilities.Functions.Abstract.IFunction;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
}
