package antrenmantakipcom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class KullaniciEklemeEkrani {
    StackPane pane;
    BorderPane root = new BorderPane();
    Label nameLabel;
    Label passwordLabel;
    static TextField nameField;
    static PasswordField passwordField;
    Button GeriDonButton;
    Button KullaniciEkle;
    VBox layout;
    static String username;
    static String password;
    ImageView infoIcon;
    Image infoImage;

    public StackPane getRoot() {
        pane = new StackPane();
        pane.setId("rootGenel");
        pane.getStylesheets().add(Main.class.getResource("/static/style.css").toExternalForm());
        bilesenler();

        root.setId("kullaniciEklemeRoot");
        bilesenler();
        try {
            ayarlamalar();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return pane;
    }

    public void ayarlamalar() throws Exception {
        HBox genellayout = new HBox();
        genellayout.setAlignment(Pos.CENTER);
        genellayout.setPadding(new Insets(400, 0, 0, 0));

        VBox layout = new VBox(15);
        // layout.setPadding(new Insets(0,0,100,0));
        layout.setAlignment(Pos.CENTER);

        VBox layout2 = new VBox(15);
        layout2.setAlignment(Pos.CENTER);

        VBox Column1 = new VBox(10);
        Column1.setAlignment(Pos.CENTER);
        Column1.getChildren().addAll(nameLabel, passwordLabel);

        HBox passwordHBox = new HBox(10);
        passwordHBox.setAlignment(Pos.CENTER);
        passwordHBox.getChildren().addAll(passwordField, infoIcon);

        VBox Column2 = new VBox(10);
        Column2.setAlignment(Pos.CENTER);
        Column2.getChildren().addAll(nameField);

        HBox buttonsRow = new HBox(10);
        buttonsRow.setAlignment(Pos.CENTER);
        buttonsRow.setPadding(new Insets(0, 0, 400, 0));
        buttonsRow.getChildren().addAll(KullaniciEkle, GeriDonButton);

        layout.getChildren().addAll(Column1);
        layout2.getChildren().addAll(Column2, passwordHBox);

        genellayout.getChildren().addAll(layout, layout2, buttonsRow);
        root.setTop(genellayout);
        root.setCenter(buttonsRow);
        pane.getChildren().add(root);
    }

    public void bilesenler() {
        root = new BorderPane();
        nameLabel = new Label("Enter your username: ");
        passwordLabel = new Label("Enter your password: ");
        nameLabel.setMinWidth(120);
        passwordLabel.setMinWidth(120);

        nameField = new TextField();
        nameField.setPromptText("Username");
        nameField.setMinWidth(120);
        nameField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMinWidth(120);

        infoImage = new Image(getClass().getResource("/ICONS/info.png").toExternalForm());
        infoIcon = new ImageView(infoImage);
        infoIcon.setStyle(
                "-fx-text-fill: #007acc; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 16px; " +
                        "-fx-cursor: hand;");
        infoIcon.setFitHeight(20);
        infoIcon.setFitWidth(20);
        infoIcon.setPreserveRatio(true);
        infoIcon.setStyle("-fx-cursor: hand;");
        Tooltip passwordTooltip = new Tooltip(
                "Parola Kuralları:\n" +
                        "- En az 8 karakter\n" +
                        "- En az 1 büyük harf\n" +
                        "- En az 1 rakam\n" +
                        "- En az 1 özel karakter (@, #, !, vs.)");
        Tooltip.install(infoIcon, passwordTooltip);

        Image image = new Image(Main.class.getResourceAsStream("/ICONS/go-back-icon.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        GeriDonButton = new Button("Geri Dön",imageView);
        GeriDonButton.setOnAction(e -> {
            try {
                AnaKontrolEkrani.setRoot(Main.getRoot());
            } catch (Exception e1) {

                e1.printStackTrace();
            }

        });
        GeriDonButton.setMinWidth(120);

        Image image2 = new Image(Main.class.getResourceAsStream("/ICONS/ekle.png"));
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(20);
        imageView2.setFitHeight(20);

        KullaniciEkle = new Button("Kullanıcı Ekle",imageView2);
        KullaniciEkle.setOnAction(e -> {
            try {
                kullaniciEkle();

            } catch (Exception e1) {

                e1.printStackTrace();
            }

        });
        KullaniciEkle.setMinWidth(120);

        nameLabel.setStyle("-fx-font-style:italic;-fx-font-size:20px;-fx-text-fill:white");
        // nameField.setStyle("-fx-border-width: 1px;-fx-prompt-text-fill:black;");
        passwordLabel.setStyle("-fx-font-style:italic;-fx-font-size:20px;-fx-text-fill:white");
        // passwordField.setStyle("-fx-border-width: 1px;-fx-prompt-text-fill:black;");

    }

    public void kullaniciEkle() {
        username = nameField.getText();
        password = passwordField.getText();

        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#!$%^&*])[A-Za-z\\d@#!$%^&*]{8,}$";

        if (username.equals("") && password.equals("")) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Bilgi");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen Gerekli Alanları Doldurunuz.");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/static/alertStyle.css").toExternalForm());
            alert.showAndWait();

        } else {
            if (password.matches(regex)) {
                try (Connection con = Database.connect()) {
                    String sorgu = "INSERT INTO users (username,password) VALUES (?,?)";
                    PreparedStatement ps = con.prepareStatement(sorgu);
                    ps.setString(1, username);
                    ps.setString(2, password);
                    int result = ps.executeUpdate();
                    if (result != -1) {
                        System.out.println("işlem başarılı");
                        AnaKontrolEkrani.setRoot(Main.getRoot());
                    } else {
                        System.out.println("işlem başarısız.");
                    }

                } catch (SQLException e) {
                    System.out.println("Database'e bağlanılamadı...'");
                    e.printStackTrace();
                }
                try {

                } catch (Exception e1) {

                    e1.printStackTrace();

                    ((Stage) GeriDonButton.getScene().getWindow()).close();
                }
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Hata");
                alert.setHeaderText("Parola Hatası");
                alert.setContentText("Lütfen Parola Kurallarına Uyunuz.");

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/static/alertStyle.css").toExternalForm());
                alert.showAndWait();
            }

        }
    }

}
