package antrenmantakipcom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
import javafx.scene.layout.VBox;

public class Main {
    static BorderPane root;
    static Label nameLabel;
    static Label passwordLabel;
    static TextField nameField;
    static PasswordField passwordField;
    static Button LoginButton;
    static Button RegisterButton;
    static String username;
    static String password;
    static ArrayList<String> Kullanicilar = new ArrayList<>();
    static ImageView infoIcon;
    static Image infoImage;

    public static BorderPane getRoot() {
        root = new BorderPane();
        bilesenler();
        try {
            ayarlamalar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }

    public static void ayarlamalar() throws Exception {

        HBox genellayout = new HBox();
        genellayout.setAlignment(Pos.CENTER);

        VBox layout = new VBox(15);
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
        buttonsRow.setPadding(new Insets(-250, 0, 200, 0));
        buttonsRow.getChildren().addAll(LoginButton, RegisterButton);

        layout.getChildren().addAll(Column1);
        layout2.getChildren().addAll(Column2, passwordHBox);

        genellayout.getChildren().addAll(layout, layout2, buttonsRow);

        root.setCenter(genellayout);
        root.setBottom(buttonsRow);
    }

    public static void bilesenler() {

        nameLabel = new Label("Kullanıcı Adınızı Giriniz  : ");
        passwordLabel = new Label("Parolanızı Giriniz : ");
        nameLabel.setMinWidth(120);
        passwordLabel.setMinWidth(120);

        nameField = new TextField();
        nameField.setPromptText("Adınızı girin");
        nameField.setMinWidth(120);
        nameField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        passwordField = new PasswordField();
        passwordField.setPromptText("Parolanızı Giriniz");
        passwordField.setMinWidth(120);

        infoImage = new Image(Main.class.getResource("/ICONS/info.png").toExternalForm());
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
                "Parolanız Yanlışsa Kontrol Ediniz:\n" +
                        "- En az 8 karakter\n" +
                        "- En az 1 büyük harf\n" +
                        "- En az 1 rakam\n" +
                        "- En az 1 özel karakter (@, #, !, vs.)");
        Tooltip.install(infoIcon, passwordTooltip);

        LoginButton = new Button("Giriş Yap");
        LoginButton.setOnAction(e -> {
            try {

                kullaniciKontrol();

            } catch (Exception e1) {

            }

        });
        LoginButton.setMinWidth(120);

        RegisterButton = new Button("Yeni Kullanıcı Oluştur");
        RegisterButton.setOnAction(e -> {

            try {
                KullaniciEklemeEkrani ekran = new KullaniciEklemeEkrani();
                AnaKontrolEkrani.setRoot(ekran.getRoot());
            } catch (Exception ex) {
                System.out.println("yeni sekme açılırken hata oluştu...");
                ex.printStackTrace();
            }
        });
        RegisterButton.setMinWidth(120);

        nameLabel.setStyle("-fx-font-style:italic;-fx-font-size:20px;-fx-text-fill:white");
        // nameField.setStyle("-fx-border-width: 1px;-fx-prompt-text-fill:black;");
        passwordLabel.setStyle("-fx-font-style:italic;-fx-font-size:20px;-fx-text-fill:white");
        // passwordField.setStyle("-fx-border-width: 1px;-fx-prompt-text-fill:black;");
        // LoginButton.setStyle("-fx-border-width: 1px;");
        // RegisterButton.setStyle("-fx-border-width: 1px; color:white");

    }

    public static void kullaniciKontrol() throws Exception {

        username = nameField.getText();
        password = passwordField.getText();
        if (username.equals("") && password.equals("")) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Bilgi");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen Gerekli Alanları Doldurunuz.");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(Main.class.getResource("/static/alertStyle.css").toExternalForm());
            alert.showAndWait();
        } else {
            String sorgu = "SELECT * FROM users WHERE username=? AND password=?";

            try (Connection con = Database.connect()) {

                PreparedStatement ps = con.prepareStatement(sorgu);
                ps.setString(1, username);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    AnaEkran.setUsername(username);
                    AnaKontrolEkrani.setRoot(AnaEkran.getRoot());

                    System.out.println(username + " adlı kullanıcı bulundu");
                } else {
                    Alert alert2 = new Alert(AlertType.INFORMATION);
                    alert2.setTitle("Bilgi");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Kullanıcı Bulunamadı.");

                    DialogPane dialogPane2 = alert2.getDialogPane();
                    dialogPane2.getStylesheets().add(Main.class.getResource("/static/alertStyle.css").toExternalForm());
                    alert2.showAndWait();
                }

            } catch (SQLException e) {
                System.out.println("Hata var. Database ile bağlanılamadı.");
                e.printStackTrace();
            }
        }

    }
}
